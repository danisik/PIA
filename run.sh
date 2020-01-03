#!/bin/bash

mvn clean package
docker build -t pia/danisik ./
docker run --rm -p 8080:8080 pia/danisik
