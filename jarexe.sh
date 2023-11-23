#!/usr/bin/env bash
set -e

if [ ! -f build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar ]; then
  ./gradlew build -x test
fi
if [[ $1 != "-b" ]]; then
  java -jar build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar
fi
