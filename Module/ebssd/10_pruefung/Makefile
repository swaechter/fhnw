obj-m = vorlage_test19hs.o

# KERNEL_SRC = /lib/modules/$(shell uname -r)/build
KERNEL_SRC = /opt/ebssd/linux-stable/

export ARCH = arm
export CROSS_COMPILE = arm-linux-gnueabihf-

all:
	make -C $(KERNEL_SRC) M=$(PWD) modules
	rm -f *.o *.mod.c modules.order Module.symvers
	scp vorlage_test19hs.ko root@192.168.254.254:/tmp

clean:
	make -C $(KERNEL_SRC) M=$(PWD) clean
