#!/usr/bin/env bash
set -e

case $(uname -m) in
    arm64)   url="https://cdn.azul.com/zulu/bin/zulu17.44.55-ca-crac-jdk17.0.8.1-linux_aarch64.tar.gz" ;;
    *)       url="https://cdn.azul.com/zulu/bin/zulu17.44.55-ca-crac-jdk17.0.8.1-linux_x64.tar.gz" ;;
esac

echo "Using CRaC enabled JDK $url"

./gradlew clean build -x test
docker build -t sdeleuze/spring-petclinic:builder -f Dockerfile.cracdeploy --build-arg CRAC_JDK_URL=$url .
docker run -d --privileged --rm --name=spring-petclinic sdeleuze/spring-petclinic:builder
echo "Please wait during creating the checkpoint..."
sleep 20
docker commit --change='ENTRYPOINT ["/opt/app/entrypoint.sh"]' $(docker ps -qf "name=spring-petclinic") sdeleuze/spring-petclinic:checkpoint
docker kill $(docker ps -qf "name=spring-petclinic")