Flashcard-REST Server
========================
REST-Server  based on the [Spring Framework](http://projects.spring.io/spring-framework/)

Prerequisites
-------------
JDK1.8 is required. Check with

    $ java -version

MongoDB will be used as database. It must be up and running.

    $ mongod

Building
--------
The server is a gradle project and can be built with the gradle wrapper

    $ ./gradlew installDist

Running
-------
Use the output from gradle task.

    $ ./build/install/flashcard-rest/bin/flashcard-rest

Test it with a browser, e.g. http://localhost:8080/flashcard-rest/questionnaires

Or use the following gradle task to launch the application

    $ ./gradlew bootRun

Deployment
----------

- File `application.properties`

    Copy this file into the project root folder and update all properties as needed.
    

