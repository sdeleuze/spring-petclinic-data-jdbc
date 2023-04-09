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

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface OwnerRepository : Repository<Owner?, Int?> {


	@Query("SELECT * FROM owner WHERE last_name LIKE concat(:lastName,'%')")
	@Transactional(readOnly = true)
	fun findByLastName(@Param("lastName") lastName: String?): Collection<Owner>

	@Transactional(readOnly = true)
	fun findById(@Param("id") id: Int?): Owner?

	fun save(owner: Owner?)
}
