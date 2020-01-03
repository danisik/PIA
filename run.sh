#!/bin/bash

mvn clean package
docker build -t pia/danisik ./
docker run --rm pia/danisik
