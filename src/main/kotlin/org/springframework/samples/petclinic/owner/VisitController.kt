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
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*

@Controller
internal class VisitController(
	private val visits: VisitRepository,
	private val pets: PetRepository,
	private val owners: OwnerRepository
) {
	@InitBinder
	fun setAllowedFields(dataBinder: WebDataBinder) {
		dataBinder.setDisallowedFields("id")
	}

	@ModelAttribute("visit")
	fun loadPetWithVisit(@PathVariable("petId") petId: Int, model: MutableMap<String, Any>): Visit {
		val pet = pets.findById(petId)!!
		model["pet"] = pet
		model["owner"] = owners.findById(pet.ownerId)!!
		val visit = Visit("", petId)
		model["visit"] = visit
		model["petVisits"] = visits.findByPetId(petId)
		return visit
	}

	@GetMapping("/owners/*/pets/{petId}/visits/new")
	fun initNewVisitForm(@PathVariable("petId") petId: Long?, model: Map<String?, Any?>?): String {
		return "pets/createOrUpdateVisitForm"
	}

	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	fun processNewVisitForm(visit: @Valid Visit, result: BindingResult): String {
		return if (result.hasErrors()) {
			"pets/createOrUpdateVisitForm"
		} else {
			visits.save(visit)
			"redirect:/owners/{ownerId}"
		}
	}
}
