# Achtung: Bitte Milestone Simulation mit oberster Priorität abarbeiten!!
# Für Simulations-Features (sofern nicht generell gültig) Branch 'Pilot' nutzen.
[![IMIS Cover](client/public/web-imis.png)](http://www.youtube.com/watch?v=XIIlMh3Lbsc "Pitch")

  * [Demo](https://imis-prototyp.de)
  * [Swagger API](https://imis-prototyp.de/swagger-ui.html)
  * [Video pitch](https://www.youtube.com/watch?v=XIIlMh3Lbsc)
 
Dieses Projekt entstand im Rahmen des [#WirvsVirus](https://wirvsvirushackathon.org/)-Hackathon. 
 
  * [Organization - Google Docs](https://docs.google.com/document/d/1nEf7WGs6BJ9qcHcuUoVzV1i01kIPH0ENQihb6B7yiI4/edit?usp=sharing)
  * [DevPost submission](https://devpost.com/software/imis-infektions-melde-und-informations-system)
  * Mit freundlicher Unterstützung von [https://covidmeldeprozess.de/](https://covidmeldeprozess.de/)
  
# Development
### [Prod](https://imis-prototyp.de) ![Test](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Build%20and%20Test/badge.svg?branch=master&event=push) ![Deploy](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Deploy/badge.svg) ![GitHub release (latest by date)](https://img.shields.io/github/v/release/imisDevelopers/1_011_a_infektionsfall_uebermittellung?color=green)
### [Dev](https://staing.imis-prototyp.de) ![Test](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Build%20and%20Test/badge.svg?branch=dev&event=push) ![Deploy](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Deploy/badge.svg?branch=dev&event=push)
### [Pilot](https://pilot.imis-prototyp.de) ![Test](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Build%20and%20Test/badge.svg?branch=pilot&event=push) ![Deploy](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Deploy/badge.svg?branch=pilot&event=push)

## General Guidelines
_DO NOT COMMIT TO MASTER_ 

Development happens in `dev` using feature branches and PR. `dev` branch is deployed at:

   * [Staging Deployment](https://staging.imis-prototyp.de)
   * [Staging API](https://staging.imis-prototyp.de/swagger-ui.html)

## Tech Stack
 - Backend: Spring Boot
 - Frontend: Vue.js
 - Persistence: PostgreSQL
 - Deployment: Google Kubernetes Engine (GKE)


## How to run this server?
1. Install Docker
   - Linux: https://docs.docker.com/install/
       - Docker Compose has to be installed as well; see https://docs.docker.com/compose/install/
   - Mac : https://docs.docker.com/docker-for-mac/install/
   - Windows:
       - Pro/Enterprise: https://docs.docker.com/docker-for-windows/install/
       - Home: https://docs.docker.com/toolbox/toolbox_install_windows/
1. Run `docker-compose up`

## Local development setup

<!--
### Quickstart for frontend development
```docker-compose up -d postgres server```
### Quickstart for server development
```docker-compose up -d postgres client``` 
-->

### Requirements
1. JDK 11 (Java)
   - https://adoptopenjdk.net/
   - **Make sure your `$JAVA_PATH` environment variable is set correctly!**
1. Gradle
   - https://gradle.org/install/
1. Python 3
   - https://www.python.org/downloads/
   - **Make sure `python3` can be found on `$PATH`!**
1. YARN (Frontend-only)
   - https://classic.yarnpkg.com/en/docs/install

### Preconditions
- Start PostgreSQL:
    - Using docker container: ```docker-compose up -d postgres```
    - Alternatively: Manually start `postgres` and add a user as specified
  in `server/src/main/resources/application.yml`


### Run full application
1. Make sure all [preconditions](#preconditions) have been met
1. Run `./gradlew run`


#### Spring Boot Server
Make sure all [preconditions](#preconditions) have been met
- Using Gradle 
    - Download Gradle wrapper
```gradle wrapper```
    - Build application
```./gradlew build```
    - Test application
```./gradlew test```
    - Run server application
```./gradlew run```
- Using Intellij: 
    - Import Project by opening the `build.gradle` file. Intellij resolves all dependencies and you can run the application by pressing the run button.

### Frontend ([more](client/README.md))
1. Make sure all [preconditions](#preconditions) have been met
1. Start local development server for vue.js development: 
```./gradlew runFrontendDev```
    - The local development server still needs the server application to be running for REST API
     calls!

## CI system
All commits to `dev`, `master`, `feature/*` and all PRs will be CI checked.

New commit to `dev` will result in new release to `staging.imis-prototyp.de`.

A new release to `imis-prototyp.de` is not triggerd by commit on `master` but by a new release tag. 
