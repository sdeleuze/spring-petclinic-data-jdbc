#!/usr/bin/env bash
set -e

rm -rf build
docker build -t sdeleuze/spring-petclinic:appcds-aot -f Dockerfile.appcds-aot .