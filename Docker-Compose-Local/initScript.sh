#!/usr/bin/env bash

if [[ "$1" == "-delete" ]]; then
    docker-compose -f docker-compose.yaml -f docker-compose-test.yaml -f docker-compose-prod.yml down
else
    docker-compose -f docker-compose.yaml -f docker-compose-test.yaml -f docker-compose-prod.yml --env-file ../.env up -d
fi
