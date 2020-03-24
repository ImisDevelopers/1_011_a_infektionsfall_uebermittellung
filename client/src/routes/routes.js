import LinkTestResultAndPatientPage from "../components/page/LinkTestResultAndPatientPage";
import LinkSampleAndPatientPage from "../components/page/LinkSampleAndPatientPage";
import RegisterInstitutionPage from "../components/page/RegisterInstitutionPage";
import RegisterPatientPage from "../components/page/RegisterPatientPage";

import ViewAllDataComponent from "../components/ViewAllDataComponent.vue";
import PublicStatisticsComponent from "../components/PublicStatisticsComponent";
import PatientOverviewComponent from "../components/PatientOverviewComponent.vue";

export const routes = [
  { path: "*", redirect: "/register-patient" },
  {
    path: "/link-test-result",
    component: LinkTestResultAndPatientPage
  },
  {
    path: "/register-institution",
    component: RegisterInstitutionPage
  },
  {
    path: "/public-statistics",
    component: PublicStatisticsComponent
  },
  {
    path: "/register-patient",
    component: RegisterPatientPage
  },
  {
    path: "/patient-overview",
    component: PatientOverviewComponent
  },
  {
    path: "/all-data",
    component: ViewAllDataComponent
  },
  {
    path: "/link-sample-and-patient",
    component: LinkSampleAndPatientPage
  }
];
