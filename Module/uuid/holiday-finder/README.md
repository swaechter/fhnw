# Holiday Finder

## Introduction

Holiday Finder is a web application for the FHNW module uuid.

## Installation

The application runs on any Apache based system with a PHP and enabled .htaccess.

### Clone the repository

	cd /var/www/
	git clone https://github.com/swaechter/fhnw-holiday-finder.git holidayfinder.com

### Enable Apache mod_rewrite

	Edit the file /etc/apache2/apache2.conf and allow rewrite
	sudo a2enmod rewrite

### Create a virtuel host

	Create an Apache virtual host for www.holidayfinder.com that points to /var/www/holidayfinder.com
	sudo a2ensite holidayfinder.com

### Install all dependencies

	php composer.phar install

### Edit the installation

	nano index.php

## License

The application is licensed under the GNU GPL v3 or later. For more information see the LICENSE.md file.
