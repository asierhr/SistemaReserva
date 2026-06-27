#!/bin/bash
deploy_test() {
    docker-compose -f docker-compose.yaml -f docker-compose-test.yaml --env-file ../.env up -d
}

deploy_prod() {
    docker-compose -f docker-compose.yaml -f docker-compose-prod.yaml --env-file ../.env up -d 
}

case "$1" in
    "-delete")
        docker compose -f docker-compose.yaml -f docker-compose-test.yaml -f docker-compose-prod.yaml --env-file ../.env down
        ;;
    "test")
        deploy_test
        ;;
    "prod")
        deploy_prod
        ;;
    *)
        echo "Uso: ./initScript.sh [test | prod | -delete]"
        ;;
esac
