#!/usr/bin/env bash
set -e

rm -rf build/repos
mkdir -p build/repos
cd build/repos

git clone https://github.com/sdeleuze/spring-framework.git --branch exit-on-refresh --single-branch
cd spring-framework
./gradlew publishToMavenLocal -PskipDocs -x test
cd ..
git clone  https://github.com/snicoll/spring-boot.git --branch appcds --single-branch
cd spring-boot
./gradlew publishToMavenLocal -x test -x intTest
cd ..
rm -rf repos