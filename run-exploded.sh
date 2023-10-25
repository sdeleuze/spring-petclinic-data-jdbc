#!/usr/bin/env bash
set -e

echo "Exploded run"
cd build/libs && java org.springframework.boot.loader.launch.JarLauncher