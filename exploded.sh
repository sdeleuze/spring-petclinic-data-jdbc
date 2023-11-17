#!/usr/bin/env bash
set -e

./gradlew clean build -x test
./explode-boot-jar.sh -d build/libs build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar
if [[ $1 != "-b" ]]; then
  java -jar build/libs/run-app.jar
fi
