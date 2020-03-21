# 1_011_a_infektionsfall_uebermittellung

## General Guidelines
_DO NOT COMMIT TO MASTER_ 

## Tech Stack
 - Backend: Spring Boot
 - Frontend: Vue.js
 - Persistence: PostgreSQL
 
## Local development setup
1.  Start PostgreSQL in docker container (before starting application): 
```docker-compose up```
2. Initialize gradle wrapper: 
```gradle wrapper```
3. Build application
```./gradlew clean build```
4. Test application
```./gradlew test```
5. Run application
```./gradlew bootrun```
6. Start local development server for vue.js development
```./gradlew serve```