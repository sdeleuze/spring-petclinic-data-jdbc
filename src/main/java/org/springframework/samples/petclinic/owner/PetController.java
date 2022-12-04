/*
 * Copyright 2012-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;

import com.azure.core.util.BinaryData;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.PublicAccessType;
import com.azure.storage.blob.options.BlobContainerCreateOptions;
import jakarta.validation.Valid;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Maciej Walkowiak
 */
@Controller
@RequestMapping("/owners/{ownerId}")
@RegisterReflectionForBinding({ Pet.class, PetType.class})
public class PetController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private final PetRepository pets;
	private final OwnerRepository owners;
	private final BlobContainerClient blobContainerClient;
	private final RestTemplate restTemplate;

	@Value("${resize.function.url}")
	private String resizeFunctionUrl;

	public PetController(PetRepository pets, OwnerRepository owners, BlobServiceClient blobServiceClient, RestTemplateBuilder restTemplateBuilder) {
		this.pets = pets;
		this.owners = owners;
		BlobContainerCreateOptions options = new BlobContainerCreateOptions();
		options.setPublicAccessType(PublicAccessType.BLOB);
		this.blobContainerClient = blobServiceClient.createBlobContainerIfNotExistsWithResponse("images", options, Context.NONE).getValue();
		this.restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(5))
				.setReadTimeout(Duration.ofSeconds(5)).build();
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.pets.findPetTypes();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return this.owners.findById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		var pet = new Pet();
		pet.setOwner(owner);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model, @RequestParam MultipartFile image) {
		if (StringUtils.hasLength(pet.getName()) && pet.isNew()
				&& !pets.findByOwnerIdAndName(owner.getId(), pet.getName()).isEmpty()) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		pet.setOwner(owner);
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			setImageUrls(pet, image);
			this.pets.save(pet);
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") int petId, ModelMap model) {
		var pet = this.pets.findById(petId);
		model.put("pet", pet);
		model.put("owner", this.owners.findById(pet.getOwner()));
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model, @RequestParam MultipartFile image) {
		if (result.hasErrors()) {
			pet.setOwner(owner);
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			setImageUrls(pet, image);
			pet.setOwner(owner);
			this.pets.save(pet);
			return "redirect:/owners/{ownerId}";
		}
	}

	private void setImageUrls(Pet pet, MultipartFile image){
		if (!image.isEmpty()) {
			pet.setImageUrl(upload(image));
			ResizeOptions options = new ResizeOptions(pet.getImageUrl(), 0, 100, 0);
			String smallImageUrl = this.restTemplate.postForObject(this.resizeFunctionUrl, options, String.class);
			pet.setSmallImageUrl(smallImageUrl);
		}
	}

	private String upload(MultipartFile file) {
		BlobClient blobClient = this.blobContainerClient.getBlobClient(file.getOriginalFilename());
		BlobHttpHeaders blobHttpHeaders = new BlobHttpHeaders();
		blobHttpHeaders.setContentType(file.getContentType());
		try {
			blobClient.getBlockBlobClient().upload(BinaryData.fromBytes(file.getBytes()), true);
		}
		catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		blobClient.setHttpHeaders(blobHttpHeaders);
		return blobClient.getBlobUrl();
	}

	record ResizeOptions (String url, double ratio, int width, int height) {}

}
