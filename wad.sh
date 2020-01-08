#!/usr/bin/env bash

docker build -t trainingservice:dev .
docker stop trainingservice || true
docker stop postgres-dev || true
echo "start database"
docker run -d --rm \
   --name postgres-dev \
   -p 5432:5432 \
   -e POSTGRES_USER=$DB_USER_DEV \
   -e POSTGRES_PASSWORD=$DB_PASS_DEV \
   -e POSTGRES_DB=trainingservice_dev \
   -v /var/lib/postgresql_docker:/var/lib/postgresql/data \
   postgres:11.3-alpine

docker run -d --rm \
  --name trainingservice \
  -p 8080:8800 \
  -p 8787:8787 \
  -p 9999:9990 \
  -e DB_USERNAME=$DB_USER_DEV \
  -e DB_PASSWORD=$DB_PASS_DEV \
  -e DB_HOST=$DB_HOST_DEV \
  -e DB_PORT=$DB_PORT_DEV \
  -e DB_DATABASE=trainingservice_dev \
  -v /tmp/wad-dropins/:/opt/jboss/wildfly/standalone/deployments \
  --net=host \
  trainingservice:dev

java -Dmaven.home=/usr/share/maven -jar ~/bin/wad.jar /tmp/wad-dropins/