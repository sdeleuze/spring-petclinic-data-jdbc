#!/bin/bash
set -x

cd /data
java -XX:SharedArchiveFile=build/unpacked/run-app.jsa -jar build/unpacked/run-app.jar
