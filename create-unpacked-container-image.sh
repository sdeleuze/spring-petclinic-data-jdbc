#!/usr/bin/env bash
set -e

docker build -t sdeleuze/spring-petclinic:unpacked -f Dockerfile.unpacked .