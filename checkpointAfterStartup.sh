#!/usr/bin/env bash
set -e

java -XX:CRaCCheckpointTo=/opt/crac-files \
     -jar /tmp/gradle-build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar&
sleep 5
echo "Load testing of vets.html"
hey -z 10s http://localhost:8080/vets.html
jcmd /tmp/gradle-build/libs/spring-petclinic-1.0.0-SNAPSHOT.jar JDK.checkpoint
