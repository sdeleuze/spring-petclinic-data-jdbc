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
package org.springframework.samples.petclinic;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 * @author Sebastien Deleuze
 */
@SpringBootApplication(proxyBeanMethods = false)
@ImportRuntimeHints(PetClinicApplication.Hints.class)
public class PetClinicApplication {

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		SpringApplication.run(PetClinicApplication.class, args);
		long end = System.currentTimeMillis();
		boolean quit = Boolean.getBoolean("autoQuit");
		if (quit) {
			System.out.println("#### Booted and returned in " + (end - begin) + "ms");
			System.exit(0);
		}
	}

	static class Hints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.resources().registerResourceBundle("messages/messages");
		}
	}

}
