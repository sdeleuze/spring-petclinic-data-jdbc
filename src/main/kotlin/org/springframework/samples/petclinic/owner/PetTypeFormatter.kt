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

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.Formatter
import org.springframework.stereotype.Component
import java.text.ParseException
import java.util.*

@Component
class PetTypeFormatter @Autowired constructor(private val pets: PetRepository) : Formatter<PetType?> {
	override fun print(petType: PetType, locale: Locale): String {
		return petType.name
	}

	@Throws(ParseException::class)
	override fun parse(text: String, locale: Locale): PetType {
		val findPetTypes: Collection<PetType?>? = pets.findPetTypes()
		for (type in findPetTypes!!) {
			if (type!!.name == text) {
				return type
			}
		}
		throw ParseException("type not found: $text", 0)
	}
}
