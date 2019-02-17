Flashcard-React Client
======================
Client App based on the [React Framework](https://facebook.github.io/react/)

Prerequisites
-------------
[Node LTS](https://nodejs.org/en/) >6 must be installed. Check with command

    $ node -v
    v6.11.4

[NPM](https://www.npmjs.com/) will be installed with Node. Check it with:

    $ npm -v
    3.10.10

Installation
------------
Install all node packages based on the configuration file 'package.json'.  
Run command

    $ npm install

Development
-----------
Run 'webpack-server' with hot module replacement using the command

    $ npm start
    
App will be deployed into the webpack-server and the browser will be opened using the url 'localhost:3000'.

Open an editor of your choice and edit the files. On save the the hot module replacment
loads the new version automatically.

Production
----------
Create App using the command

    $ npm run build

This will build the project as production-ready App. The files will be copied into the folder 'build'.

Run the App e.g. with the "simplehttpserver" (npm package!)

    $ cd build
    $ simplehttpserver

and open URL on 'localhost:8000'
