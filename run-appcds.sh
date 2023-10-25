#!/usr/bin/env bash
set -e

echo "Optimized run with the dynamic shared archive"
cd build/libs && java -XX:SharedArchiveFile=spring-petclinic.jsa org.springframework.boot.loader.launch.JarLauncher