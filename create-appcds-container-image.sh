#!/usr/bin/env bash
set -e

./build-appcds.sh
docker build -t sdeleuze/spring-petclinic:appcds -f Dockerfile.appcds .