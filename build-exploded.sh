#!/usr/bin/env bash
set -e

./gradlew build -x test
cd build/libs
# See https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment.efficient
jar xf spring-petclinic-1.0.0-SNAPSHOT.jar
