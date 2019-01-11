#!/bin/bash
set -e

PATH=$PATH:/opt/gocd/build

source /opt/gocd/build/java/buildlib.sh

gradle_build
