#!/usr/bin/env bash
set -ex

REBUILD=false
HOST_WORK_DIR="$( pwd )"
CONTAINER_WORK_DIR=/workspace

while test $# -gt 0; do
  case "$1" in
    -h|--help)
      echo "run-dev-container.sh [options]"
      echo " "
      echo "options:"
      echo "-h, --help                show brief help"
      echo "-r, --rebuild             force container image rebuild"
      exit 0
      ;;
    -w)
      shift
      if test $# -gt 0; then
        export HOST_WORK_DIR=$(cd $1; pwd)
      else
        echo "no working directory specified"
        exit 1
      fi
      shift
      ;;
    --workdir)
      export HOST_WORK_DIR=`echo $(cd $1; pwd) | sed -e 's/^[^=]*=//g'`
      shift
      ;;
    -r)
      export REBUILD=true
      shift
      ;;
    --rebuild)
      export REBUILD=true
      shift
      ;;
    *)
      break
      ;;
  esac
done


docker image ls | grep spring-checkpoint-restore-dev >/dev/null 2>&1 || export REBUILD=true

if [ $REBUILD ]; then
  case $(uname -m) in
      arm64)   url="https://cdn.azul.com/zulu/bin/zulu17.44.55-ca-crac-jdk17.0.8.1-linux_aarch64.tar.gz" ;;
      *)       url="https://cdn.azul.com/zulu/bin/zulu17.44.55-ca-crac-jdk17.0.8.1-linux_x64.tar.gz" ;;
  esac
  echo "Using CRaC enabled JDK $url"
  docker build -t spring-checkpoint-restore-dev -f Dockerfile.crac --build-arg CRAC_JDK_URL=$url .
fi

docker run -it --entrypoint /bin/bash --privileged -p 8080:8080 -v $HOME/.m2:/root/.m2:ro -v $HOST_WORK_DIR:$CONTAINER_WORK_DIR:delegated -w $CONTAINER_WORK_DIR spring-checkpoint-restore-dev