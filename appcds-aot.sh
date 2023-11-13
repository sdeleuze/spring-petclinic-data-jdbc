#!/usr/bin/env bash
set -e

# Require https://github.com/sdeleuze/spring-framework/tree/exit-on-refresh and https://github.com/snicoll/spring-boot/tree/appcds

./gradlew clean build -x test
java -Djarmode=layertools -jar build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar extract2 --destination build/libs/
java -Dspring.context.exit=onRefresh -XX:ArchiveClassesAtExit=build/libs/spring-petclinic.jsa -Dspring.aot.enabled=true -jar build/libs/run-app.jar
if [[ $1 != "-b" ]]; then
  java -XX:SharedArchiveFile=build/libs/spring-petclinic.jsa -Dspring.aot.enabled=true -jar build/libs/run-app.jar
fi