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
import org.springframework.samples.petclinic.visit.Visit
import org.springframework.samples.petclinic.visit.VisitRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import java.util.stream.Collectors

@Controller
class OwnerController(
	private val owners: OwnerRepository,
	private val pets: PetRepository,
	private val visits: VisitRepository
) {
	@InitBinder
	fun setAllowedFields(dataBinder: WebDataBinder) {
		dataBinder.setDisallowedFields("id")
	}

	@GetMapping("/owners/new")
	fun initCreationForm(model: MutableMap<String?, Any?>): String {
		val owner = Owner()
		model["owner"] = owner
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM
	}

	@PostMapping("/owners/new")
	fun processCreationForm(owner: @Valid Owner, result: BindingResult): String {
		return if (result.hasErrors()) {
			VIEWS_OWNER_CREATE_OR_UPDATE_FORM
		} else {
			owners.save(owner)
			"redirect:/owners/" + owner.id
		}
	}

	@GetMapping("/owners/find")
	fun initFindForm(model: MutableMap<String?, Any?>): String {
		model["owner"] = Owner()
		return "owners/findOwners"
	}

	@GetMapping("/owners")
	fun processFindForm(owner: Owner, result: BindingResult, model: MutableMap<String?, Any?>): String {

		// find owners by last name
		val results = owners.findByLastName(owner.lastName)
			.map { o: Owner -> OwnerDetails(o, pets.findByOwnerId(o.id!!)) }
		return if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found")
			"owners/findOwners"
		} else if (results.size == 1) {
			// 1 owner found
			"redirect:/owners/" + results.iterator().next().owner.id
		} else {
			// multiple owners found
			model["selections"] = results
			"owners/ownersList"
		}
	}

	@GetMapping("/owners/{ownerId}/edit")
	fun initUpdateOwnerForm(@PathVariable("ownerId") ownerId: Int, model: Model): String {
		val owner = owners.findById(ownerId)
		model.addAttribute(owner!!)
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM
	}

	@PostMapping("/owners/{ownerId}/edit")
	fun processUpdateOwnerForm(
		owner: @Valid Owner,
		result: BindingResult,
		@PathVariable("ownerId") ownerId: Int
	): String {
		return if (result.hasErrors()) {
			VIEWS_OWNER_CREATE_OR_UPDATE_FORM
		} else {
			owners.save(owner.copy(id = ownerId))
			"redirect:/owners/{ownerId}"
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	fun showOwner(@PathVariable("ownerId") ownerId: Int): ModelAndView {
		val mav = ModelAndView("owners/ownerDetails")
		mav.addObject(owners.findById(ownerId)!!)
		mav.addObject("pets",
			pets.findByOwnerId(ownerId).stream()
				.map { pet: Pet ->
					PetDetails(
						pet,
						pets.findPetType(pet.typeId),
						visits.findByPetId(pet.id)
					)
				}
				.collect(Collectors.toList()))
		return mav
	}

	data class OwnerDetails(val owner: Owner, val pets: List<Pet>)

	data class PetDetails(val pet: Pet, val type: PetType, val visits: List<Visit>)

	companion object {
		private const val VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm"
	}
}
