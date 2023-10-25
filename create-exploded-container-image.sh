#!/usr/bin/env bash
set -e

./gradlew build -x test
docker build -t sdeleuze/spring-petclinic:exploded -f Dockerfile.exploded .