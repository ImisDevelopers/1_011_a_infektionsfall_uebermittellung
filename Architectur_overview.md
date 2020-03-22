# IMIS Architecture Overview

## General 
### Services
The Application is split into two parts.
* Database
* Server Application (Backend)

The Database and the Server Application are completly decoupled. It is possible to run these components on prem or on any Hyperscaler to easily scale up or down depending on use.

### Backend

The Logic of the Backend is a Spring Boot 2 Java 11 Application. The sourcecode for this java component can be found [here](server). 

The Frontend is implemented as a responsive single page application in Vue.js. The sourcecode for this component can be found [here](client).

### Serving
The application is served with Spring MVC and Spring Data JPA using Hibernate. It serves the Singlepage Application as well as the REST Api for the Application.

### Building
General Builds are performed with Gradle. First both parts of the core component are build. Afterwards these Buildartifacts are pulled together into a single Application. By running `gradlew run bootRun` you can start you application as long as the database is already up.
