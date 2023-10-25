#!/bin/bash

cd /data
set -x
java -XX:SharedArchiveFile=spring-petclinic.jsa org.springframework.boot.loader.launch.JarLauncher
