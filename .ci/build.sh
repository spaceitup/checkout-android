#!/bin/bash
set -e

PATH=$PATH:/opt/build

source /opt/build/java/buildlib.sh

gradle_build
