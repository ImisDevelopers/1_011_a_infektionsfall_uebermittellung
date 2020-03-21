# 1_011_a_infektionsfall_uebermittellung

## General Guidelines
_DO NOT COMMIT TO MASTER_ 

## Tech Stack
 - Backend: Spring Boot
 - Frontend: Vue.js
 - Persistence: PostgreSQL
 
## Local development setup
#### Start PostgreSQL in docker container (before starting application): 
```docker-compose up```

#### Initialize gradle wrapper: 
```gradle wrapper```

#### Run application
```./gradlew bootrun```

#### Test application
```./gradlew test```

#### Build application
```./gradlew clean build```

#### Start local development server for vue.js development
```./gradlew serve```