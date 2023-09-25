#!/usr/bin/env bash
set -ex

gradle build -x test
java -XX:CRaCCheckpointTo=$CRAC_FILES_DIR -jar build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar&
sleep 5
#hey -z 60s http://localhost:8080/vets.html
jcmd build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar JDK.checkpoint