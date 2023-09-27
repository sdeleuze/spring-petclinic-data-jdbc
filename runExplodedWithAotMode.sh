#!/usr/bin/env bash
set -e

cd build/libs
# See https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment.efficient
jar xf spring-petclinic-1.0.0-SNAPSHOT.jar
java -cp "BOOT-INF/classes:BOOT-INF/lib/*" -Dspring.aot.enabled=true org.springframework.samples.petclinic.PetClinicApplication