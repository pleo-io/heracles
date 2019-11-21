#!/usr/bin/env bash

set -e

# npm lint
tslint './src/**/*.ts*' --format stylish --project . --force
