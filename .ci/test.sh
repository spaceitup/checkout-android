#!/bin/bash
set -e

PATH=$PATH:/opt/build

if [ -d target ]; then
    rm -rf target
fi

gradle clean :payment:test
