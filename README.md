#Task Management API Suite

This is a maven project for Task management which uses an embedded derby database to store tasks in a table.

## Pre-requisites
1. Java 8 or higher needs to be installed on the system and environment variable JAVA_HOME should be set correctly to the JDK path.  
   Check by running below command in command prompt  
   `java -version`  
2. Maven needs to be installed on the system.  
   Check by running below command in command prompt  
   `mvn -v`  

## Run the application
This project uses Tomcat as an embedded container to host the web application.  
Goto the base folder of the application and execute the following command to launch the application.  
`mvn spring-boot:run`  

## Compile and run the application via command line
`mvn clean package` to compile and generate the artifact. Also, it creates a code coverage report in `target/site/jacoco/index.html`   
`java -jar target/task-api.jar` to run the application via command line.

For both ways to run the application above, the application will be available at [http://localhost:8080/task-management/api/v1](http://localhost:8080/task-management/api/v1)

## Test
`mvn test` to run unit tests.

  
 
## API Documentation - Swagger UI

To download the yaml file
[http://localhost:8080/task-management/api/v1/api-docs](http://localhost:8080/task-management/api/v1/api-docs)

swagger-ui
[http://localhost:8080/task-management/api/v1/swagger-ui/index.html](http://localhost:8080/task-management/api/v1/swagger-ui/index.html)
