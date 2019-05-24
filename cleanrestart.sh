#!/usr/bin/env bash

if [ $# -lt 1 ]
then
    echo "Missing Parameter for Service Version (now using latest)"
    TAG='latest'
else
    TAG=$1
fi

docker stop trainingservice || true
docker rm -f trainingservice || true
export FLYWAY_URL=jdbc:postgresql://$DB_HOST_PROD:$DB_PORT_PROD/$DB_DATABASE_PROD &
export FLYWAY_DRIVER=org.postgresql.Driver &
export FLYWAY_USER=$DB_USER_PROD &
export FLYWAY_PASSWORD=$DB_PASS_PROD &
docker run -it --rm --net=host \
    -e DB_USERNAME=$DB_USER_PROD \
    -e DB_PASSWORD=$DB_PASS_PROD \
    -e DB_HOST=$DB_HOST_PROD \
    -e DB_PORT=$DB_PORT_PROD \
    -e DB_DATABASE=trainingservice \
    --name trainingservice \
    iselisa/trainingservice:$TAG