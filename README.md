# Contacts

### Description
This is a phone book written on Java with command line interface. 
learning project from [JetBrains Academy](https://hyperskill.org/projects/43 "Project page"). 


### How to use
Requires Java 8 or greater. 

To compile run from "src" directory command: 

`javac ./contacts/Main.java`


To start application run from "src" directory command: 

`java contacts.Main`

In this state application creates empty phone book and stores all records in memory. 


To store phone book on your drive start application with parameter: 

`java contacts.Main filename` 

Where "filename" is a name of file (or path to it) in which you want to store phone book. <br>
If file doesn't exist application creates it on start and save all records on exit. 
