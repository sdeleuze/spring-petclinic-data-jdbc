#!/usr/bin/env bash
set -e

./build-exploded.sh
echo "Training run to create the dynamic shared archive"
cd build/libs
java -DautoQuit=true -XX:ArchiveClassesAtExit=spring-petclinic.jsa org.springframework.boot.loader.launch.JarLauncher
