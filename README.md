[![IMIS Cover](client/public/web-imis.png)](http://www.youtube.com/watch?v=XIIlMh3Lbsc "Pitch")

  * [Demo](https://imis-prototyp.de)
  * [Video pitch](https://www.youtube.com/watch?v=XIIlMh3Lbsc)
 
Dieses Projekt entstand im Rahmen des [#WirvsVirus](https://wirvsvirushackathon.org/)-Hackathon. 
 
  * [Organization - Google Docs](https://docs.google.com/document/d/1nEf7WGs6BJ9qcHcuUoVzV1i01kIPH0ENQihb6B7yiI4/edit?usp=sharing)
  * [DevPost submission](https://devpost.com/software/imis-infektions-melde-und-informations-system)
  * Mit freundlicher Unterstützung von [https://covidmeldeprozess.de/](https://covidmeldeprozess.de/)
  
# Development
### [Prod](https://imis-prototyp.de) ![Test](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Build%20and%20Test/badge.svg?branch=master&event=push) ![Deploy](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Deploy/badge.svg) ![GitHub release (latest by date)](https://img.shields.io/github/v/release/imisDevelopers/1_011_a_infektionsfall_uebermittellung?color=green)
### [Dev](https://staing.imis-prototyp.de) ![Test](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Build%20and%20Test/badge.svg?branch=dev&event=push) ![Deploy](https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung/workflows/Deploy/badge.svg?branch=dev&event=push)

## General Guidelines

Development happens in `master` using feature branches and PR. 
`master` branch is deployed at:

   * [Staging Deployment](https://staging.imis-prototyp.de)

## Tech Stack

We are using:
 - Vue.js + Typescript
 - VueX + [vuex-smart-module](https://github.com/ktsn/vuex-smart-module)
 - Vue Router
 - [Ant Design](https://www.antdv.com/) 
 - swagger-typescript-api
 - Deployment: Google Kubernetes Engine (GKE)

### Requirements
1. YARN 
   - https://classic.yarnpkg.com/en/docs/install

### Frontend
1. Start local development server for vue.js development: 
```yarn serve```

## CI system
All commits to `master`, `feature/*` and all PRs will be CI checked.

New commit to `master` will result in new release to `staging.imis-prototyp.de`.

A new release to `imis-prototyp.de` is not triggerd by commit on `master` but by a new release tag. 
