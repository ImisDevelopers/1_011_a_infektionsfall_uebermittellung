import RegisterPatientPage from "../components/page/RegisterPatientPage.vue";
import LinkTestResultAndPatientPage from "../components/page/LinkTestResultAndPatientPage.vue";
import LinkSampleAndPatientPage from "../components/page/LinkSampleAndPatientPage";

import ViewAllDataComponent from "../components/ViewAllDataComponent.vue";
import AddInstitutionComponent from "../components/AddInstitutionComponent";
import PublicStatisticsComponent from "../components/PublicStatisticsComponent";
import PatientOverviewComponent from "../components/PatientOverviewComponent.vue";

export const routes = [
  { path: "*", redirect: "/register-patient" },
  {
    path: "/link-test-result",
    component: LinkTestResultAndPatientPage
  },
  {
    path: "/add-institution",
    component: AddInstitutionComponent
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
