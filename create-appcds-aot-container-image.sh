#!/usr/bin/env bash
set -e

docker build -t sdeleuze/spring-petclinic:appcds-aot -f Dockerfile.appcds-aot .