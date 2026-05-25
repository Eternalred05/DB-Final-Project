If you're having any trouble after reading through this `README`, feel free to ask for help.
This is a school project made for learning, any fix, idea or way to make it better is always welcome

# Necessary Programs

### 1. At least JDK 23 installed and fully working (It can be downloaded from https://www.oracle.com/java/technologies/downloads/)
### 2. PostreSQL Version 13.8 or higher (Download at https://www.postgresql.org/download/)
### 3. For reading .form files and modify GUI more comfortably Netbeans 23 or newer (https://netbeans.apache.org/front/main/download/)

# How to use
### After all necessary programs are installed, create a server called Gestion-Hospitalaria with a database, then run the GestionHospitalaria.sql file included via PGAdmin 4 in the Query Tool to create all the necessary tables. And Later run the other with the Login Tables and some users to test.
### Go to src\Connection\configuracion.properties and edit both the password, user and port to connect the Database to java, Database name can also be changed as well, there is a class called `TestConexion.java` you can always run that to check wether the changes are right.
### Simply run the class at Runner package to start the program and don't forget to use the correct info to Login.

# Project Overview
This is a database final project made for CUJAE University, for the theme Hospitalary Management in Software Engineering Faculty. For Connecting The DB JBDC Drivers 42.7.11 are being used (https://jdbc.postgresql.org/download/)

# Building
If you want to manually compile it, check nbproject\build-impl.xml, at least ant 1.8.0 is required.
