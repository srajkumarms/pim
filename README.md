# pim
A sample web application developed in Java Struts 2 and Handlebar JS

## Overview

This sample application has a authentication module (Login, Logout) and a product information management module. For authentication, the application uses credential details stored in a properties file. The following credentials will gain you access to the application.
1. **admin\admin**
2. **admin2\admin2**

## Technology Stack
1. J2EE
2. Java Struts 2
3. Handlebar JS
4. jQuery
5. Bootstrap
6. Flexbox
7. Apache Tomcat-8.0
8. Web Fonts

## Depedencies
### Browser
Browser used to develop this application is **Google Chrome** and tested in **Mozilla Firefox** too. This site is broken in IE for now and plans to implement fix soon.
### Apache
This project uses Apache Tomcat-8.0.20 and it can be downloaded from [here](https://archive.apache.org/dist/tomcat/tomcat-8/v8.0.20/ "Apache Tomcat-8.0.20") and is compatible with higher version. Download and extract the file for later use.
### Struts and Log4j
This project uses Struts 2 framework and you can download the binaries from [here](http://struts.apache.org/ "Struts 2")
Though Struts 2 have many binaries from the distribution package, this sample project uses only the below listed binaries. This project references an *User Library* variable which points to the folder that has only these libraries:

1. commons-fileupload-1.3.2.jar
2. commons-io-2.4.jar
3. commons-lang-2.4.jar
4. commons-lang3-3.4.jar
5. commons-logging-1.1.3.jar
6. freemarker-2.3.23.jar
7. javassist-3.20.0-GA.jar
8. log4j-api-2.7.jar
9. ognl-3.1.12.jar
10. struts2-core-2.5.10.1.jar
11. struts2-json-plugin-2.5.10.1.jar

This project uses another *User Library* variable for log4j which points to the folder containing log4j-1.2.17.jar. This can be downloaded from [here](https://logging.apache.org/log4j/1.2/download.html "log4j-1.2.17")

This project has all UI related dependencies in it's respective folders and doesn't use UI package managers like NPM, Bower for now.

## Setup Instructions:
Eclipse is used to develop this sample application and the instructions are to setup the codebase in the same IDE.

1. Import this into Eclipse
2. Create an User Library variable named Struts2 and add the above listed JARs as External JARs. [Reference](http://tutoringcenter.cs.usfca.edu/resources/adding-user-libraries-in-eclipse.html)
3. Create another User Library variable named log4j and add log4j JAR as External JARs.
4. Add Tomcat Server from the extracted folder as a project server. Refer [here](https://stackoverflow.com/questions/8046871/how-to-add-tomcat-server-in-eclipse) for adding server.
5. Right click on the project and select *Run As > Run on Server* | *Debug As > Debug on Server*. 
6. Select the newly setup Tomcat Server and proceed.

Now the project setup is done, server should be up and running and good for execution/debugging.

You can access the application from http://localhost:8080/pim/