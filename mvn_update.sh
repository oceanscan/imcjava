#!/usr/bin/env bash

./gradlew publishToMavenLocal
version=`./gradlew printVersion | sed -n 3p`

echo ""
echo "Publishing ICMJava v$version to omst's Maven repository..."
sleep 1

scp -r ~/.m2/repository/pt/lsts/imcjava/$version omst@192.168.61.1:/srv/data/www/maven/pt/lsts/imcjava/
