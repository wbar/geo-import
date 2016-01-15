__GoEURO__ - JAVA solution
======================

This repository contains simple solution for Java challenge.

Task specification: https://github.com/goeuro/dev-test


Maven tasks
-----------
Maven `assembly:single` job creates JAR file with all dependences needed.


Instalation
-----------

Simply steps:

1. execute `mvn clean install` in root directory
2. take `goeuro-1.0-SNAPSHOT-jar-with-dependencies.jar` whenewher You want.
3. copy `config.properties` from root directory if You want customize JAR behavior and place it with JAR in the same directory.
4. execute `java -jar <jar_name> <city_name>` where
   - `jar_name` name of JAR, i.e. `goeuro-1.0-SNAPSHOT-jar-with-dependencies.jar`
   - `city_name` is name of City, i.e. Berlin

 
