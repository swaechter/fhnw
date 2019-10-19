# Packet mit FHNW CA installieren
wget --no-check-certificate http://srvtol01.cyberlab.fhnw.ch/repos/cyberlab/pool/main/c/cyberlab-trust/cyberlab-trust_20181019-1_all.deb
apt-get install -y ./cyberlab-trust_20181019-1_all.deb

# Proxmox PVE Enterprise Package Repository deaktivieren
echo "#deb https://enterprise.proxmox.com/debian/pve stretch pve-enterprise" > /etc/apt/sources.list.d/pve-enterprise.list

# Ceph Package Repository registrieren
wget -q -O- 'https://download.ceph.com/keys/release.asc' | apt-key add -
echo deb https://download.ceph.com/debian-luminous/ stretch main | tee /etc/apt/sources.list.d/ceph.list

# Alle Abhängigkeiten installieren
apt-get update -y
apt-get install -y sudo ntp ntpdate ntp-doc python python-pip parted uuid-runtime openssh-server ceph ceph-deploy
 
# Systembenutzer erstellen
useradd -d /home/cephuser -m cephuser
passwd cephuser
 
# Passwordless sudo erlauben
echo "cephuser ALL = (root) NOPASSWD:ALL" | tee /etc/sudoers.d/cephuser
chmod 0440 /etc/sudoers.d/cephuser
 
# Allfälliges requiretty deaktivieren (Eher auf RHEL/CentOS)
--> Defaults:ceph !requiretty
sed -i s'/Defaults requiretty/#Defaults requiretty'/g /etc/sudoers
 
# NTP synchronisieren
systemctl stop ntp
ntpdate time.google.com
hwclock --systohc
systemctl enable ntp
systemctl start ntp

# OK: Die Lab Images verwenden keine Firewall, von dem her haben wir das nicht getestet
# Firewallregeln anpassen (Eventuell optional, wenn kein UFW vorhanden)
ufw enable
ufw allow 22/tcp
ufw allow 80/tcp
ufw allow 2003/tcp
ufw allow 4505:4506/tcp
ufw allow 6789/tcp
ufw allow 6800:7300/tcp

# SSH Konfiguration erstellen
mkdir /home/cephuser/.ssh
bash -c 'cat > /home/cephuser/.ssh/config <<EOF
Host pve1.swaechter.ch
   Hostname pve1.swaechter.ch
   User cephuser
Host pve2.swaechter.ch
   Hostname pve2.swaechter.ch
   User cephuser
Host pve3.swaechter.ch
   Hostname pve3.swaechter.ch
   User cephuser
EOF'
chown -R cephuser:cephuser /home/cephuser/.ssh
chmod 700 /home/cephuser/.ssh

# Festplatte für den OSD Speicher vorbereiten
fdisk -l /dev/sdb
parted -s /dev/sdb mklabel gpt mkpart primary xfs 0% 100%
mkfs.xfs -f /dev/sdb


# ===== Für Adminnode, welche die Installation "treibt" =====
 
# Benutzer wechseln
su - cephuser

# SSH Key ohne Passphrase erstellen
ssh-keygen
 
# SSH Key auf andere Nodes verteilen
ssh-copy-id cephuser@pve2.swaechter.ch
ssh-copy-id cephuser@pve3.swaechter.ch

# Verzeichnis erstellen
mkdir my-cluster
cd my-cluster

# Ceph Cluster Konfiguration + lokale Monitor Konfiguration erstellen
ceph-deploy new pve1.swaechter.ch
 
# Ceph auf allen Nodes erstellen
ceph-deploy install pve1.swaechter.ch pve2.swaechter.ch pve3.swaechter.ch
 
# Initialen Monitor starten + Keys sharen
ceph-deploy mon create-initial
ceph-deploy gatherkeys pve1.swaechter.ch

# Konfigurationsdatei und Admin Keys auf alle Nodes kopieren
ceph-deploy admin pve1.swaechter.ch pve2.swaechter.ch pve3.swaechter.ch

# Manager erstellen und starten
ceph-deploy mgr create pve1.swaechter.ch

# Disks prüfen
ceph-deploy disk list pve1.swaechter.ch pve2.swaechter.ch pve3.swaechter.ch

# OSDs auf allen Nodes erstellen
ceph-deploy osd create --data /dev/sdb pve1.swaechter.ch
ceph-deploy osd create --data /dev/sdb pve2.swaechter.ch
ceph-deploy osd create --data /dev/sdb pve3.swaechter.ch

# Gesundheit und Status prüfen
ceph health
ceph status
 
 # Optional für Nodes
 ssh <IP> sudo ceph -s

# Metadata Server auf allen Nodes erstellen und starten
ceph-deploy mds create pve1.swaechter.ch pve2.swaechter.ch pve3.swaechter.ch

# Weitere Monitore erstellen, um High Availability zu erreichen
ceph-deploy mon add pve2.swaechter.ch
ceph-deploy mon add pve3.swaechter.ch

# Weitere Manager erstellen, um High Availability zu erreichen
ceph-deploy mgr create pve2.swaechter.ch
ceph-deploy mgr create pve3.swaechter.ch

Hinweis: Jetzt sind alle 3 Nodes à la HCI gleichwertig. Bei mehr als 3 Nodes würde man natürlich nicht auf jeder Node einen Monitor, Manager und OSD erstellen --> Overkill + gefährlich, da der Quorumnetzwerkverkehr Überhand nehmen kann

 
# ===== Auf VMs wechseln =====
sudo apt-get install bonnie++
sudo bonnie -u root
 
 
 
wget -q -O- 'https://download.ceph.com/keys/release.asc' | sudo apt-key add -
echo deb https://download.ceph.com/debian-{ceph-stable-release}/ $(lsb_release -sc) main | sudo tee /etc/apt/sources.list.d/ceph.list
sudo apt update
sudo apt install ceph-deploy