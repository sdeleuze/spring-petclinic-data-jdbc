#!/usr/bin/env bash
set -e

./gradlew clean build -x test
if ! [ $1 = "-b" ]; then
  java -jar build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar
fi
