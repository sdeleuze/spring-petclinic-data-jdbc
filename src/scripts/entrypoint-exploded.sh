#!/bin/bash

cd /data
set -x
java org.springframework.boot.loader.launch.JarLauncher
