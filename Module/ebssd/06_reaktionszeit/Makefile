CC=arm-linux-gnueabihf-gcc
CFLAGS=-mthumb -march=armv7-a -g -Wall

all: reaktionszeit

reaktionszeit: reaktionszeit.c
	$(CC) $(CFLAGS) -o reaktionszeit reaktionszeit.c

install:
	scp reaktionszeit root@192.168.254.254:/usr/bin/reaktionszeit

installkey:
	ssh-copy-id root@192.168.254.254

.PHONY: clean
clean:
	rm reaktionszeit
