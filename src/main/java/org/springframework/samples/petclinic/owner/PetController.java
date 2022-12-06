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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.lang.Nullable;
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
import org.springframework.web.client.HttpClientErrorException;
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
@RegisterReflectionForBinding({ Pet.class, PetType.class, PetController.ImageProcessingResult.class })
public class PetController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private static final PetType AUTO_DETECT_PET_TYPE = new PetType(-1, "Autodetect");
	private final PetRepository pets;
	private final OwnerRepository owners;
	private final RestTemplate restTemplate;

	@Value("${resize.function.url}")
	private String resizeFunctionUrl;

	public PetController(PetRepository pets, OwnerRepository owners, RestTemplateBuilder restTemplateBuilder) {
		this.pets = pets;
		this.owners = owners;
		this.restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(5))
				.setReadTimeout(Duration.ofSeconds(5)).build();
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		List<PetType> petTypes = this.pets.findPetTypes();
		List<PetType> petTypesWithAutoDetect = new ArrayList<>(petTypes.size() + 1);
		petTypesWithAutoDetect.add(AUTO_DETECT_PET_TYPE);
		petTypesWithAutoDetect.addAll(petTypes);
		return petTypesWithAutoDetect;
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
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model, @Nullable @RequestParam MultipartFile image) {
		if (StringUtils.hasLength(pet.getName()) && pet.isNew()
				&& !pets.findByOwnerIdAndName(owner.getId(), pet.getName()).isEmpty()) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		validateType(pet, result, image);
		pet.setOwner(owner);
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			processImage(pet, result, image);
			if (result.hasErrors()) {
				pet.setOwner(owner);
				model.put("pet", pet);
				return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
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
	public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model, @Nullable @RequestParam MultipartFile image) {
		validateType(pet, result, image);
		if (result.hasErrors()) {
			pet.setOwner(owner);
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			processImage(pet, result, image);
			if (result.hasErrors()) {
				pet.setOwner(owner);
				model.put("pet", pet);
				return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			pet.setOwner(owner);
			this.pets.save(pet);
			return "redirect:/owners/{ownerId}";
		}
	}

	private void validateType(Pet pet, BindingResult result, @Nullable MultipartFile image) {
		if ((image == null || image.isEmpty()) && AUTO_DETECT_PET_TYPE.id().equals(pet.getType())) {
			result.rejectValue("type", "mandatory", "Type need to be defined when no image is selected");
		}
	}

	private void processImage(Pet pet, BindingResult result, @Nullable MultipartFile image) {
		if (image != null && !image.isEmpty()) {
			String url = resizeFunctionUrl + "?filename=" + image.getOriginalFilename();
			try {
				ImageProcessingResult imageProcessingResult = this.restTemplate.postForObject(url, image.getResource(), ImageProcessingResult.class);
				pet.setImageUrl(imageProcessingResult.url);
				if (AUTO_DETECT_PET_TYPE.id().equals(pet.getType())) {
					List<PetType> petTypes = this.pets.findPetTypes();
					Map<String, PetType> petTypesMap = petTypes.stream().collect(Collectors.toMap(PetType::name, Function.identity()));
					Optional<PetType> petTypeOptional = imageProcessingResult.tags().stream().filter(tag -> petTypesMap.containsKey(tag.name())).map(tag -> petTypesMap.get(tag.name())).findFirst();
					petTypeOptional.ifPresentOrElse(petType -> pet.setType(petType.id()), () -> result.rejectValue("type", "mandatory", "Type need to be defined when image recognition fails"));
				}
			} catch (HttpClientErrorException.UnprocessableEntity ex) {
				result.rejectValue("imageUrl", "animal", "Looks like this is not an animal");
			}
		}
	}

	public record ImageProcessingResult(String url, List<Tag> tags) {}

	public record Tag(String name, Float confidence) {}

}
