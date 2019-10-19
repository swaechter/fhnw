CC=arm-linux-gnueabihf-gcc
CFLAGS=-mthumb -march=armv7-a -g -Wall

all: hello

hello: hello.c
	$(CC) $(CFLAGS) -o hello hello.c

install:
	scp hello root@192.168.254.254:/usr/bin/hello

installkey:
	ssh-copy-id root@192.168.254.254

.PHONY: clean
clean:
	rm hello
