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
package org.springframework.samples.petclinic.owner

import jakarta.validation.Valid
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Controller
@RequestMapping("/owners/{ownerId}")
class PetController(
	private val pets: PetRepository,
	private val owners: OwnerRepository,
	restTemplateBuilder: RestTemplateBuilder
) {
	private val restTemplate: RestTemplate

	init {
		restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(20))
			.setReadTimeout(Duration.ofSeconds(20)).build()
	}

	@ModelAttribute("types")
	fun populatePetTypes(): Collection<PetType?> {
		return pets.findPetTypes()
	}

	@ModelAttribute("owner")
	fun findOwner(@PathVariable("ownerId") ownerId: Int): Owner? {
		return owners.findById(ownerId)
	}

	@InitBinder("owner")
	fun initOwnerBinder(dataBinder: WebDataBinder) {
		dataBinder.setDisallowedFields("id")
	}

	@InitBinder("pet")
	fun initPetBinder(dataBinder: WebDataBinder) {
		dataBinder.validator = PetValidator()
	}

	@GetMapping("/pets/new")
	fun initCreationForm(owner: Owner, model: ModelMap): String {
		val pet = Pet(ownerId = owner.id)
		model["pet"] = pet
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM
	}

	@PostMapping("/pets/new")
	fun processCreationForm(
		owner: Owner,
		pet: @Valid Pet,
		result: BindingResult,
		model: ModelMap
	): String {
		if (StringUtils.hasLength(pet.name) && pet.isNew
			&& !pets.findByOwnerIdAndName(owner.id, pet.name!!).isEmpty()
		) {
			result.rejectValue("name", "duplicate", "already exists")
		}
		pet.ownerId = owner.id
		return if (result.hasErrors()) {
			model["pet"] = pet
			VIEWS_PETS_CREATE_OR_UPDATE_FORM
		} else {
			pets.save(pet)
			"redirect:/owners/{ownerId}"
		}
	}

	@GetMapping("/pets/{petId}/edit")
	fun initUpdateForm(@PathVariable("petId") petId: Int, model: ModelMap): String {
		val pet = pets.findById(petId)!!
		model["pet"] = pet
		model["owner"] = owners.findById(pet.ownerId)
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM
	}

	@PostMapping("/pets/{petId}/edit")
	fun processUpdateForm(
		pet: @Valid Pet,
		result: BindingResult,
		owner: Owner,
		model: ModelMap
	): String {
		return if (result.hasErrors()) {
			pet.ownerId = owner.id
			model["pet"] = pet
			VIEWS_PETS_CREATE_OR_UPDATE_FORM
		} else {
			pet.ownerId = owner.id
			pets.save(pet)
			"redirect:/owners/{ownerId}"
		}
	}

	companion object {
		private const val VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm"
	}
}
