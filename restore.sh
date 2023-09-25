#!/usr/bin/env bash
set -ex

java -XX:CRaCRestoreFrom=$CRAC_FILES_DIR&
PID=$!
trap "kill $PID" SIGINT SIGTERM
wait $PID