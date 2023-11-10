#!/bin/bash
set -x

cd /data
java -XX:SharedArchiveFile=build/libs/spring-petclinic.jsa -jar build/libs/run-app.jar
