#!/bin/bash
set -e

PATH=$PATH:/opt/gocd/build

source /opt/gocd/build/java/buildlib.sh

if [ -d target ]; then
    rm -rf target
fi

gradle_run_tests
