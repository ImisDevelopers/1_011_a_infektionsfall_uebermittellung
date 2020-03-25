import LinkTestResultAndPatientPage from "../components/page/LinkTestResultAndPatientPage";
import LinkSampleAndPatientPage from "../components/page/LinkSampleAndPatientPage";
import RegisterInstitutionPage from "../components/page/RegisterInstitutionPage";
import RegisterPatientPage from "../components/page/RegisterPatientPage";

import ViewAllDataComponent from "../components/ViewAllDataComponent.vue";
import PublicStatisticsComponent from "../components/PublicStatisticsComponent";
import PatientOverviewComponent from "../components/PatientOverviewComponent.vue";
import LandingPage from "../components/LandingPage";

export const routes = [
  { path: "*", redirect: "/home" },
  {
    path: "/home",
    component: LandingPage
  },
  {
    path: "/prototype",
    component: RegisterPatientPage
  },
  {
    path: "/prototype/link-test-result",
    component: LinkTestResultAndPatientPage
  },
  {
    path: "/prototype/register-institution",
    component: RegisterInstitutionPage
  },
  {
    path: "/prototype/public-statistics",
    component: PublicStatisticsComponent
  },
  {
    path: "/prototype/register-patient",
    component: RegisterPatientPage
  },
  {
    path: "/prototype/patient-overview",
    component: PatientOverviewComponent
  },
  {
    path: "/prototype/all-data",
    component: ViewAllDataComponent
  },
  {
    path: "/prototype/link-sample-and-patient",
    component: LinkSampleAndPatientPage
  }
];
