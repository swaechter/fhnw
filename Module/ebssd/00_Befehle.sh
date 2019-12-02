#!/bin/bash

# Woche 1

# Platform vorbereiten
sudo dpkg --add-architecture i386
sudo apt install libc6:i386 libstdc++6:i386 libncurses5:i386 zlib1g:i386 autoconf

# Serielles Terminal installieren und konfigurieren
sudo apt-get install picocom
sudo usermod -a -G dialout swaechter

# Wandboard mit Testimage bespielen
wget ftp://download.technexion.net/demo_software/wandboard-imx6/wandboard-imx6_yocto-2.0_qt5.5_20170508.zip
unzip wandboard-imx6_yocto-2.0_qt5.5_20170508.zip
sudo dd if=wandboard-imx6_yocto-2.0_qt5.5_20170508/yocto-2.0_qt5.5_wandboard_20170508.img of=/dev/sdc bs=1M status=progress && sudo sync

# Wandboard an serielle Konsole anschliessen
sudo dmesg
sudo picocom --b 115200 --f none /dev/ttyUSB0 --> Beenden mit Ctrl + A & Ctrl + X

# Woche 2

# Workspace vorbereiten
sudo mkdir -p /opt/ebssd
sudo chown -R swaechter:swaechter /opt/ebssd
cd /opt/ebssd

# Limaro GCC Compiler herunterladen
wget https://releases.linaro.org/components/toolchain/binaries/7.4-2019.02/arm-linux-gnueabihf/gcc-linaro-7.4.1-2019.02-x86_64_arm-linux-gnueabihf.tar.xz
tar -xf gcc-linaro-7.4.1-2019.02-x86_64_arm-linux-gnueabihf.tar.xz 
mv gcc-linaro-7.4.1-2019.02-x86_64_arm-linux-gnueabihf gcc-arm-linux-gnueabihf/

# Umgebungsvariablen setzen - am besten persistent!
export PATH=$PATH:/opt/ebssd/gcc-arm-linux-gnueabihf/bin/

# Bootloader U-Boot herunterladen und anpassen
git clone git://git.denx.de/u-boot.git
cd u-boot/
git checkout v2019.04 -b v2019.04-ebssd
wget -c https://rcn-ee.com/repos/git/u-boot-patches/v2019.04/0001-wandboard-uEnv.txt-bootz-n-fixes.patch
patch -p1 < 0001-wandboard-uEnv.txt-bootz-n-fixes.patch

# Bootloader U-Boot bauen
sudo apt-get install build-essential bison flex
make ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf- distclean
make ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf- wandboard_defconfig
make ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf-

# Bootloader U-Boot auf Wandboard spielen
sudo lsblk 
export DISK=/dev/sdc
export PART=/dev/sdc1
sudo umount ${DISK}*
sudo dd if=/dev/zero of=$DISK bs=1M count=10
sudo gparted --> MSDOS Layout mit 1 ext4 Partition ab 1MB Offset mit Grösse 1024 MiB, Name rootfs
sudo fdisk -l $DISK --> Boot-Sektorstart von /dev/sdc1 um 2048
sudo dd if=SPL of=$DISK seek=1 bs=1k
sudo dd if=u-boot.img of=$DISK seek=69 bs=1k
sudo sync

# Kernel herunterladen
cd /opt/ebssd/
git clone git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git
cd linux-stable
git checkout v5.3.1 -b v5.3.1-ebssd

# Kernel bauen
make ARCH=arm imx_v6_v7_defconfig
make ARCH=arm menuconfig --> Danach alles gemäss Beschreibung in Versuch ebssd_V2_WB_Linux_from_Scratch-Teil1(5.3.1).pdf konfigurieren
make -j5 ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf-
make ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf- dtbs

# Kernel kopieren (Wichtig: Gerät vorher mounten)
sudo mkdir /media/$USER/rootfs/boot/
cd /opt/ebssd/linux-stable
cat include/generated/utsrelease.h
make ARCH=arm kernelrelease
sudo cp arch/arm/boot/zImage /media/$USER/rootfs/boot/vmlinuz-5.3.1-wb
sudo mkdir -p /media/$USER/rootfs/boot/dtbs/5.3.1-wb/
sudo cp arch/arm/boot/dts/imx6dl-wandboard.dtb /media/$USER/rootfs/boot/dtbs/5.3.1-wb/
sudo nano /media/$USER/rootfs/boot/uEnv.txt
make ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf- modules
sudo make ARCH=arm INSTALL_MOD_PATH=/media/$USER/rootfs/ modules_install
sudo umount /media/$USER/rootfs

# Busybox herunterladen
cd /opt/ebssd
git clone git://busybox.net/busybox.git
cd busybox

# Busybox bauen und installieren
make ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf- menuconfig --> Einstellungen anpassen
make
sudo make install

# Woche 3

# Buildroot herunterladen
git clone git://git.buildroot.net/buildroot
cd buildroot
git checkout 2019.08 -b 2019.08-ebssd

# Buildroot bauen
make wandboard_defconfig
make menuconfig
make

# Buildroot kopieren
sudo tar -xvf output/images/rootfs.tar -C /media/$USER/rootfs/
sudo umount /media/$USER/rootfs

# Shellprompt verbessern
echo "PS1='\h:\w\# '" > .profile

# Netzwerkschnittstelle auf Host konfigurieren
Network Manager --> New Ethernet Interface --> Manual / IP setzen, /24 Subnetz, kein Gateway / MAC Adresse auf Board locken

# Netzwerkschnittstelle konfigurieren
ifconfig -a --> Zeigt eth0 an
cat "auto eth0" > /etc/network/interfaces
cat "iface eth0 inet static" > /etc/network/interfaces
cat "address 192.168.254.254" > /etc/network/interfaces
cat "netmask 255.255.255.0" > /etc/network/interfaces

# Passwot setzen
passwd

# Music Player installieren
nano /etc/fstab --> /dev/sda1 /mnt/usbstick vfat ro 0 0
mkdir /mnt/usbstick
nano /etc/inittab --> null::sysinit:/bin/sleep 1 # wait 1s before mounting the usbstick
nano /etc/mpd.conf --> Config setzen

# WLAN aktivieren
sudo mkdir -p /media/$USER/rootfs/lib/firmware/brcm
sudo cp brcmfmac4330-sdio.bin /media/$USER/rootfs/lib/firmware/brcm/
sudo cp brcmfmac4330-sdio.txt /media/$USER/rootfs/lib/firmware/brcm/brcmfmac4330-sdio.wand,imx6dl-wandboard.txt

# Netzwerkeinstellungen anpassen /etc/network/interfaces
auto wlan0
iface wlan0 inet dhcp
  pre-up modprobe brcmfmac && sleep 1 && wpa_supplicant -B -Dnl80211 -iwlan0 -c/etc/wpa_supplicant.conf
  post-down killall wpa_supplicant ; sleep 1 ; modprobe -r brcmfmac
  hostname wandboard


# WPA Informationen setzen /etc/wpa_supplicant.conf
ctrl_interface=DIR=/var/run/wpa_supplicant
update_config=1
country=CH

network={
     ssid="KremlinNet Agricultures"
     psk="SNIPPED"
     scan_ssid=1
     key_mgmt=WPA-PSK
     proto=RSN
     group=CCMP TKIP
     pairwise=CCMP TKIP
}


# Woche 5

# Device Tree anpassen
cd /opt/ebssd/linux-stable/
find . -name '*wandboard*.dts'
find . -name '*wandboard*.dtb'
cat ./arch/arm/boot/dts/imx6dl-wandboard.dts
cat ./arch/arm/boot/dts/imx6dl-wandboard-revb1.dtb 
nano arch/arm/boot/dts/imx6qdl-wandboard-revc1.dtsi
touch .scmversion
make ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf- dtbs
scp arch/arm/boot/dts/imx6dl-wandboard.dtb root@192.168.254.254:/boot/dtbs/5.3.1-wb/

# DebugFS verwenden
mount -t debugfs debugfs /sys/kernel/debug
cat /sys/kernel/debug/gpio

# Tastapplikation bauen
cd 05_gpio-test
make clean
make all
make install

# Woche 6

# Reaktionszeitapplikation bauen
cd 06_reaktionszeit
make clean
make all
make install

# Woche 7

# httpd konfigurieren und kompilieren
cd /opt/ebssd/buildroot
make busybox-menuconfig (Networking Utilities)
make

# httpd kopieren
scp output/target/bin/busybox root@192.168.254.254:/bin/busybox2
Via ssh dann: mv /bin/busybox2 /bin/busybox

# Symlink erstellen
ln -s busybox /bin/httpd

# Webseite erstellen und Webserver starten
nano /srv/www/index.html
httpd -h /srv/www/

# CGI Skript erstellen
nano /srv/www/cgi-bin/show-procinfo.cgi
chmod +x /srv/www/cgi-bin/show-procinfo.cgi

# Datei /etc/shadown anzeigen lassen
wget http://192.168.254.254/cgi-bin/show-procinfo.cgi?info=../etc/shadow

# Webserver absichern
adduser httpd --shell=/bin/false
httpd -f -u 1000:1000 -h /srv/www/

# Woche 8

# Treiber kopieren und installieren
make
Via ssh dann: insmod /tmp/mod_hrtimer_dev.ko
Via ssh danach: dmesg, um Major Node herauszufinden

# Device Node manuell erstellen
mknod -m 666 /dev/mod_hrtimer_dev c <node> 0

# Schreiben und lesen vom Modul
echo 'hoffentlich klapps!' > /dev/mod_hrtimer_dev
cat /dev/mod_hrtimer_dev

# udev Regel setzen
mkdir -p /etc/udev/rules.d/
echo 'SUBSYSTEM=="mod_hrtimer_dev", MODE="0666"' > /etc/udev/rules.d/99-ebssd-mod-hrtimer.rules
udevadm control --reload-rules
