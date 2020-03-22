# IMIS Architecture Overview

## General 
### Services
The Application is split into two parts.
* Database
* Corecomponent

The Database and the Corecomponent are completly decoupled. It is possible to run these components on prem or on any Hyperscaler to easily scale up or down depending on use.

### Corecomponent

The Logic of the Corecomponent is a Bootspring Java Application. The sourcecode for this java component can be found [here](server). 

The Frontend is implemented as a responsive single page application in VueJs. The sourcecode for this component can be found [here](client).

### Serving
The application is served with Spring MVC. It serves the Singlepage Application as well as the REST Api for the Application.

### Building
General Builds are performed with Gradle. First both parts of the core component are build. Afterwards these Buildartifacts are pulled together into a single Application. By running `gradlew run bootrun` you can start you application as long as the database is already up.
