#!/usr/bin/env bash
set -e

if [ ! -f build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar ]; then
  ./gradlew build -x test
fi
./unpack-executable-jar.sh -d build/unpacked build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar
java -Dspring.context.exit=onRefresh -XX:ArchiveClassesAtExit=build/unpacked/run-app.jsa -jar build/unpacked/run-app.jar
if [[ $1 != "-b" ]]; then
  java -XX:SharedArchiveFile=build/unpacked/run-app.jsa -jar build/unpacked/run-app.jar
fi