[![IMIS Cover](client/public/web-imis.png)](http://www.youtube.com/watch?v=XIIlMh3Lbsc "Pitch")

  * [Demo](http://imis-prototyp.de)
  * [Swagger API](http://api.imis-prototyp.de/)
  * [Video pitch](https://www.youtube.com/watch?v=XIIlMh3Lbsc)
 
Dieses Projekt entstand im Rahmen des [#WirvsVirus](https://wirvsvirushackathon.org/)-Hackathon. 
 
  * [Organization - Google Docs](https://docs.google.com/document/d/1nEf7WGs6BJ9qcHcuUoVzV1i01kIPH0ENQihb6B7yiI4/edit?usp=sharing)
  * [DevPost submission](https://devpost.com/software/imis-infektions-melde-und-informations-system)
  * Mit freundlicher Unterstützung von [https://covidmeldeprozess.de/](https://covidmeldeprozess.de/)
  
# Development
![Build](https://github.com/imis-Infektionsfall-uebermittellung/1_011_a_infektionsfall_uebermittellung/workflows/Build%20and%20Test/badge.svg?branch=dev) and ![Deployment](https://github.com/imis-Infektionsfall-uebermittellung/1_011_a_infektionsfall_uebermittellung/workflows/Build%20and%20Deploy%20to%20GKE/badge.svg?branch=dev)

## General Guidelines
_DO NOT COMMIT TO MASTER_ 

Development happens in `dev` using feature branches and PR. `dev` branch is deployed at:

   * [Staging Deployment](https://staging.imis-prototyp.de)
   * [Staging API](https://api.staging.imis-prototyp.de)

## Tech Stack
 - Backend: Spring Boot
 - Frontend: Vue.js
 - Persistence: PostgreSQL
 - Deployment: Google Kubernetes Engine (GKE)
 
## Benötigte Tools
1. Docker
  - Linux: https://docs.docker.com/install/
  - Mac : https://docs.docker.com/docker-for-mac/install/
  - Windows:
    - Pro/Enterprise: https://docs.docker.com/docker-for-windows/install/
    - Home: https://docs.docker.com/toolbox/toolbox_install_windows/
2. JDK 11 (Java)
  - https://adoptopenjdk.net/ 
3. YARN
  - https://classic.yarnpkg.com/en/docs/install#mac-stable 

## How to run this server?
Install Docker and run: `docker-compose up`

## Quickstart for frontend development
```docker-compose up -d postgres server```
## Quickstart for server development
```docker-compose up -d postgres client``` 

## Local development setup

### Run full application
Run application (including frontend) 
```./gradlew bootRun```
### Server
1. Shell öffnen 
1. Make sure your $JAVA_PATH is set correctly (java11)
#### Postgres
- Start PostgreSQL in docker container (before starting application): 
```docker-compose up -d postgres```
- When you are not using docker you have to setup postgres manually and add a user as specified in `server/src/main/resources/application.yml`

#### Spring Boot Server
- Using Gradle 
    - Download Gradle wrapper
```gradle wrapper```
    - Build application
```./gradlew clean build```
    - Test application
```./gradlew test lint```
    - Run server application
```./gradlew bootRun```
    - Start local development server for vue.js development
```./gradlew serve```
- Using Intellij: 
    - Import Project by opening the `build.gradle` file. Intellij resolves all dependencies and you can run the application by pressing the run button.

But you have to run Postgres in docker or install it and run it locally manually

### Frontend ([more](client/README.md))
- Start local development server for vue.js development
```./gradlew serve```
- Alternatively you can also us `yarn serve` within the client folder, make sure you installed all dependencies using `yarn install`
- Update TypeScript API client for frontend using `generate:api-client`, make sure the server is running on `localhost:80`. Do not manually change the `SwaggerApi.ts` file.

## API communication

An API developed by the backend is served via swagger on the server: http://localhost/swagger-ui.html
We are using the swagger docs to autogenerate the typescript api client.
```yarn generate:api-client```
Make sure the server is running locally, the command updates the ts api client

When you make changes to the backend, make sure to generate a new client. 

## CI system
All commits to `dev`, `master`, `feature/*` and all PRs will be CI checked.

New commit to `dev` will result in new release to `staging.imis-prototyp.de`.

A new release to `imis-prototyp.de` is not triggerd by commit on `master` but by a new release tag. 
