#!/bin/sh

PROCESSES=`ps ax | grep /usr/bin/java | sed 's/\([0-9][0-9]*\).*/\1/'`

for PROCESS in $PROCESSES
do
  kill -9 $PROCESS &>/dev/null
  echo "$PPROCESS killed successfully"
done
