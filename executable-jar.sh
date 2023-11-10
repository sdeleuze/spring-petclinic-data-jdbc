#!/usr/bin/env bash
set -e

./gradlew clean build -x test
java -jar build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar
