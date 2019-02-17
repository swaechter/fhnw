Flashcard-MVC Server
========================
Server App based on the [SpringMVC Framework](https://spring.io/projects/spring-framework/)

Prerequisites
-------------
JDK1.8 is required. Check with

    $ java -version

MongoDB will be used as database. It must be up and running.

    $ mongod
    
On Windows, running the mongodb server is done through the command prompt,
and must include a --dbpath "C:\Path\to\DB-Folder" option.

Example:
    
    C:\mongodb>bin\mongod.exe --dbpath "C:\mongodb\db"

Building
--------
The server is a gradle project and can be built with the gradle wrapper

    $ ./gradlew installBootDist

Running
-------
Use the output from gradle task.

    $ ./build/install/flashcard-mvc/bin/flashcard-mvc

and access the webapp with `http://localhost:8080/flashcard-mvc/questionnaires` or
use the following gradle task to launch the application

    $ ./gradlew bootRun

Deployment
----------

- File `application.properties`

    Copy this file into the project root folder and update all properties as needed.
    
- Thymeleaf Caching

    To disable reloading of static resources add `spring.thymeleaf.cache=true` to the configuration file "application.properties"

- Build war

    To build a war use the `./gradlew bootWar` task.
