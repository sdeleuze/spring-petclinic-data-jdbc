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
package org.springframework.samples.petclinic.visit

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class Visit(
	val description: String,
	val petId: Int,
	@Id val id: Long? = null,
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column("VISIT_DATE")
	val date: LocalDate = LocalDate.now()) {

	val isNew: Boolean get() = id == null
}
