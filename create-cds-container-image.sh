#!/usr/bin/env bash
set -e

docker build -t sdeleuze/spring-petclinic:cds -f Dockerfile.cds .