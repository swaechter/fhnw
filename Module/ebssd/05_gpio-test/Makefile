CC=arm-linux-gnueabihf-gcc
CFLAGS=-mthumb -march=armv7-a -g -Wall

all: gpio-test

gpio-test: gpio-test.c
	$(CC) $(CFLAGS) -o gpio-test gpio-test.c

install:
	scp gpio-test root@192.168.254.254:/usr/bin/gpio-test

installkey:
	ssh-copy-id root@192.168.254.254

.PHONY: clean
clean:
	rm gpio-test
