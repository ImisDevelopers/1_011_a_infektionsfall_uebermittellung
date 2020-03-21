# 1_011_a_infektionsfall_uebermittellung

## General Guidelines
_DO NOT COMMIT TO MASTER_ 

## Tech Stack
 - Backend: Spring Boot
 - Frontend: Vue.js
 - Persistence: PostgreSQL
 
## Benötigte Tools
1. Docker
  - Linux: https://docs.docker.com/install/
  - Mac : https://docs.docker.com/docker-for-mac/install/
  - Windows:
    - Pro/Enterprise: https://docs.docker.com/docker-for-windows/install/
    - Home: https://docs.docker.com/toolbox/toolbox_install_windows/
2. JDK 11 (Java)
  - https://adoptopenjdk.net/ 
3. NPM/YARN
  - https://classic.yarnpkg.com/en/docs/install#mac-stable 
 

## How to run this server?
Install Docker and run: `docker-compose up`

## Local development setup
1. Shell öffnen 
1. Make sure your $JAVA_PATH is set correctly
1. Start PostgreSQL in docker container (before starting application): 
```docker-compose up -d ```
1. Download Gradle wrapper
```gradle wrapper```
1. Build application
```./gradlew clean build```
1. Test application
```./gradlew test```
1. Run application (including frontend) 
```./gradlew bootRun```
1. Start local development server for vue.js development
```./gradlew serve```

## API communication

An API developed by the backend is served via swagger on the server: http://localhost/swagger-ui.html
If you have a new API that should be created add them into the api-frontend folder.
