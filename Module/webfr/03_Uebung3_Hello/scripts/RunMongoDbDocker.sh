#!/bin/bash

docker pull mongo
docker run -it -d mongo
mongo 172.17.0.2:27017
