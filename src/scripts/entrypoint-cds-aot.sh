#!/bin/bash
set -x

cd /data
java -XX:SharedArchiveFile=build/unpacked/application.jsa -Dspring.aot.enabled=true -jar build/unpacked/run-app.jar
