#!/bin/sh
mvn clean package && docker build -t iselisa/trainingservice .
docker rm -f trainingservice || true &&
docker-compose up
