#!/bin/sh
mvn clean package && docker build -t ch.opentrainingcenter/trainingservice .
docker rm -f sessions || true && docker run -d -p 8080:8080 -p 4848:4848 --name trainingservice ch.opentrainingcenter/trainingservice 
