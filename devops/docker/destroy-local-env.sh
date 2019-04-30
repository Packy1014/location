#!/usr/bin/env bash
echo "destroy local environment..."
docker stop packy-mysql-container packy-project-container && \
docker rm packy-mysql-container packy-project-container

docker image rm packy-mysql-image:1.0.1 com.packy/location:1.0.1
