#!/usr/bin/env bash
set -e

./gradlew clean build -x test
java -Djarmode=layertools -jar build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar extract2 --destination build/libs/
if ! [ $1 = "-b" ]; then
  java -jar build/libs/run-app.jar
fi
