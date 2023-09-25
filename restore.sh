#!/usr/bin/env bash
set -e

java -XX:CRaCRestoreFrom=/opt/crac-files&
PID=$!
trap "kill $PID" SIGINT SIGTERM
wait $PID