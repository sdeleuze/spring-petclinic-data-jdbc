#!/usr/bin/env bash
set -e

rm -rf build
docker build -t sdeleuze/spring-petclinic:jarexe -f Dockerfile.jarexe .