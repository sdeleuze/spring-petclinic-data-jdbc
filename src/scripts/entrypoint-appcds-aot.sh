#!/bin/bash
set -x

cd /data
java -XX:SharedArchiveFile=build/libs/spring-petclinic.jsa -Dspring.aot.enabled=true -jar build/libs/run-app.jar
