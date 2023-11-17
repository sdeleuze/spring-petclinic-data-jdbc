#!/usr/bin/env bash
set -e

./gradlew clean build -x test
./explode-boot-jar.sh -d build/libs build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar
java -Dspring.context.exit=onRefresh -XX:ArchiveClassesAtExit=build/libs/spring-petclinic.jsa -jar build/libs/run-app.jar
if [[ $1 != "-b" ]]; then
  java -XX:SharedArchiveFile=build/libs/spring-petclinic.jsa -jar build/libs/run-app.jar
fi