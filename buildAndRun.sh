#!/bin/sh

if [ -z "$DB_USER_DEV" ]; then
    echo "Need to set env DB_USER_DEV --> DB Username"
    exit 1
fi

if [ -z "$DB_PASS_DEV" ]; then
    echo "Need to set env DB_PASS_DEV --> DB Password"
    exit 1
fi

if [ -z "$DB_HOST_DEV" ]; then
    echo "Need to set env DB_HOST_DEV --> DB Host"
    exit 1
fi

mvn clean package && docker build -t iselisa/trainingservice .
docker rm -f trainingservice || true && docker run -it --rm -p 8080:8080 -p 9990:9990 -e DB_USERNAME=$DB_USER_DEV -e DB_PASSWORD=$DB_PASS_DEV -e DB_HOST=$DB_HOST_DEV -e DB_PORT=3307 -e DB_DATABASE=trainingservice iselisa/trainingservice 
