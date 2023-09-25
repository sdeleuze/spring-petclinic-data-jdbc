#!/usr/bin/env bash
set -e

java -Dspring.context.checkpoint=onRefresh \
     -XX:CRaCCheckpointTo=$CRAC_FILES_DIR \
     -jar /tmp/gradle-build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar
