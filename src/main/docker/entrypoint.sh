#!/bin/sh

echo "debut init"
exec java ${JAVA_OPTS} -jar "${HOME}/app.jar" "$@"
