#!/usr/bin/env bash
set -e

while test $# -gt 0; do
  case "$1" in
    -h|--help)
      echo "explode-boot-jar.sh - explode Spring Boot JAR in order to run the application efficiently"
      echo " "
      echo "explode-boot-jar.sh [options] application.jar"
      echo " "
      echo "options:"
      echo "-d directory              Create the files in directory"
      echo "-h, --help                Show brief help"
      exit 0
      ;;
    -d)
      shift
      if test $# -gt 0; then
        export DESTINATION=$1
      else
        echo "no destination specified"
        exit 1
      fi
      shift
      ;;
    *)
      export JAREXE=$1
      shift
      ;;
  esac
done

if [ -z "$JAREXE" ]; then
  echo "specifying the executable JAR is mandatory"
  exit 1
fi

EXPLODE_TMPDIR="${TMPDIR}explode-boot-jar"
if [ -d "$EXPLODE_TMPDIR" ]; then rm -Rf "$EXPLODE_TMPDIR"; fi

if [ -z "$DESTINATION" ]; then
  DESTINATION="$(pwd)"
fi

unzip -q "$JAREXE" -d "$EXPLODE_TMPDIR"
mkdir -p "${DESTINATION}/application"
mkdir -p "${DESTINATION}/dependencies"

jar cf "${DESTINATION}/application/$(basename "$JAREXE")" -C "${EXPLODE_TMPDIR}/BOOT-INF/classes/" .

MANIFEST_RUN_APP="${EXPLODE_TMPDIR}/MANIFEST-RUN-APP.MF"
MAIN_CLASS=$(grep "Start-Class:" "${EXPLODE_TMPDIR}/META-INF/MANIFEST.MF" | cut -d ' ' -f 2)

echo "Manifest-Version: 1.0" > "${MANIFEST_RUN_APP}"
echo "Main-Class: ${MAIN_CLASS}" >> "${MANIFEST_RUN_APP}"
echo "Class-Path: application/$(basename "$JAREXE")" >> "${MANIFEST_RUN_APP}"
cut -d '/' -f 3 < "${EXPLODE_TMPDIR}/BOOT-INF/classpath.idx" | cut -d '"' -f 1 | while IFS= read -r lib
do
  cp -r "${EXPLODE_TMPDIR}/BOOT-INF/lib/${lib}" "${DESTINATION}/dependencies/"
  echo "  dependencies/$lib" >> "${MANIFEST_RUN_APP}"
done

jar cfm "${DESTINATION}/run-app.jar" "${MANIFEST_RUN_APP}"

