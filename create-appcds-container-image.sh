#!/usr/bin/env bash
set -e

docker build -t sdeleuze/spring-petclinic:appcds -f Dockerfile.appcds .