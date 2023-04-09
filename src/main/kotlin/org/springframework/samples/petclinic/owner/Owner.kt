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

import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.annotation.Id

data class Owner(
	val firstName: String = "",
	val lastName : String = "",
	val address: String = "",
	val city: String = "",
	@Digits(fraction = 0, integer = 10)
	val telephone: String  = "",
    @Id val id: Int? = null) {

	val isNew get() = id == null
}
