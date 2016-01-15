__GoEURO__ - JAVA solution
==========================

This repository contains simple solution for Java challenge.

Task specification: https://github.com/goeuro/dev-test


Maven tasks
===========
Maven `assembly:single` job creates JAR file with all dependences needed.


Instalation
===========

Simply steps:

1. execute `mvn clean install` in root directory
2. take `goeuro-1.0-SNAPSHOT-jar-with-dependencies.jar` whenewher You want.
3. copy `config.properties` from root directory if You want customize JAR behavior and place it with JAR in the same directory.
4. execute `java -jar <jar_name> <city_name>` where
   - `jar_name` name of JAR, i.e. `goeuro-1.0-SNAPSHOT-jar-with-dependencies.jar`
   - `city_name` is name of City, i.e. Berlin


Properties
==========


`goeuro.config.api.position.suggest.url`
----------------------------------------
__Default:__ `http://api.goeuro.com/api/v2/position/suggest/en/{CITY_NAME}`

GoEuro API endpoint.



`goeuro.config.out.filename`
----------------------------
__Default:__ `export.csv`

Target filename


`goeuro.config.out.csv.format`
----------------------------
__Default:__ `_id;key;name;fullName;iata_airport_code;type;country;geo_position/latitude;geo_position/longitude;locationId;inEurope;countryCode;coreCountry;distance`

List of CSV fields, comma-separated list of JSON Path values.


`goeuro.config.out.csv.null`
----------------------------
__Default:__ ``

Null fields will be exported as empty string by default.


`goeuro.config.out.csv.separator`
----------------------------
__Default:__ `;`

Fields will be separated by comma by default.
