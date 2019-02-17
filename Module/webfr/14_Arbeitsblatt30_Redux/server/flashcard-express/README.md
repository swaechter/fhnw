Flashcard-Express Server
========================
Server App based on the [Express Framework](http://expressjs.com/)

Prerequisites
-------------
[Node](https://nodejs.org/en/) must be installed. Check with command

    $ node -v
    v6.9.1

and also [NPM](https://www.npmjs.com/).

    $ npm -v
    3.10.8

On top of that, mongodb needs to be installed and running.

On Windows, running the mongodb server is done through the command prompt,
and must include a --dbpath "C:\Path\to\DB-Folder" option.

Example:

    C:\mongodb>bin\mongod.exe --dbpath "C:\mongodb\db"

Installation
------------
Install all node packages based on the configuration file 'package.json'.  
Run command

    $ npm install


Debugging
---------

With node v.6.9.1 the debugging functionality is built in. Run

    $ node --inspect app.js

and use the provided url to open the chrome devtools including the debugger.

Development
-----------

To run the server with live-reloading, use

    $ npm start

To enable logging on express itself, use

    $ npm run dev-debug

Installation-Eclipse (optional)
-------------------------------

Install nodeclipse plugin for Eclipse from Eclipse Marketplace.

Add nodeclipse package globally

    $ sudo npm i -g nodeclipse

Run following command to add the eclipse files to the project

    $ cd $PROJECTROOT
    $ nodeclipse -p

Import project into eclipse using "Existing Projects into Workspace"
To remove JSHint warnings while use es6 syntax add entry

     "esversion": 6

to the project properties over "Properties -> JSHint -> Configuration"
