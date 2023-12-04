#!/bin/bash
set -x

cd /data
java -XX:SharedArchiveFile=build/unpacked/application.jsa -jar build/unpacked/run-app.jar
