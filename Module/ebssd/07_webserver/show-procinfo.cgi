#! /bin/sh
echo "Content-type: text/plain"
echo ""
# get value of the submitted $REQUEST_URI field 'info'
info=`echo "$REQUEST_URI" | sed -re 's/.*info=(.*)/\1/' `
echo "------------------------------------------------"
echo "The content of '/proc/$info' is:"
echo
[ -f /proc/$info ] && cat /proc/$info
echo
echo "------------------------------------------------"
echo "The environment variables submitted to this cgi script were:"
echo
env
echo "------------------------------------------------"
