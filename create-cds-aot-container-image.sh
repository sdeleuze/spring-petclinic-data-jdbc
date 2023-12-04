#!/usr/bin/env bash
set -e

docker build -t sdeleuze/spring-petclinic:cds-aot -f Dockerfile.cds-aot .