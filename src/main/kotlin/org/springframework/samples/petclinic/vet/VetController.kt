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
package org.springframework.samples.petclinic.vet

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.util.stream.Collectors

@Controller
class VetController(private val vets: VetRepository, private val specialties: SpecialtyRepository) {

	@GetMapping("/vets.html")
	fun showVetList(model: MutableMap<String, Any>): String {
		model["vets"] = Vets(vetToVetDto(vets.findAll()))
		return "vets/vetList"
	}

	@GetMapping("/vets")
	@ResponseBody
	fun showResourcesVetList(): Vets {
		return Vets(vetToVetDto(vets.findAll()))
	}

	private fun vetToVetDto(vets: Collection<Vet>): List<VetDto> {
		return vets.stream().map { v: Vet -> this.vetToVetDto(v) }.collect(Collectors.toList())
	}

	private fun vetToVetDto(v: Vet): VetDto {
		val specialtyList = v.specialties.map { s: VetSpecialty ->
			specialties.findById(s.specialty			)
		}
		return VetDto(v.id!!, v.firstName, v.lastName, specialtyList)
	}
}
