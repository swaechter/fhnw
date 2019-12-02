#! /bin/sh

if [ ! -f /sys/class/gpio/export ]; then
        echo 200 >/sys/class/gpio/export
        echo 'out' >/sys/class/gpio/gpio200/direction
fi

info=`echo "$REQUEST_URI" | sed -re 's/.*ledstatus=(.*)/\1/' `

echo "Content-type: text/html"
echo ""
echo "<p>Status LED:</p>"
echo `cat /sys/class/gpio/gpio200/value`
echo $info > /sys/class/gpio/gpio200/value

echo '<p>Change the LED status</p>'
echo '<form action="/cgi-bin/show-ledstatus.cgi">'
echo '<input type="radio" name="ledstatus" value="1" checked="checked"> Enabled<br>'
echo '<input type="radio" name="ledstatus" value="0"> Disabled<br>'
echo '<input type="submit" value="Submit">'
echo '</form>'
