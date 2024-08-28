#!/bin/sh -l

java -Dspring.profiles.active=prod -jar /usr/app.jar $(pwd)