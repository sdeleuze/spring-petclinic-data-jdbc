#!/usr/bin/env bash
set -e

docker build -t sdeleuze/spring-petclinic:jarexe -f Dockerfile.jarexe .