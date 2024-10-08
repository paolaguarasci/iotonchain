#!/usr/bin/bash

docker run \
-p 8881:8080 \
-e KEYCLOAK_ADMIN=admin \
-e KEYCLOAK_ADMIN_PASSWORD=admin \
-v ./data:/opt/keycloak/data/import \
quay.io/keycloak/keycloak:latest \
start-dev \
--import-realm
