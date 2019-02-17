#!/bin/sh

# start now all microservices
PROJECTS="moviemgmt usermgmt rentalmgmt"

for PROJECT in $PROJECTS
do
  java -jar $PROJECT/build/libs/*.jar &
  sleep 2
done
