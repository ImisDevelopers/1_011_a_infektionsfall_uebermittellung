import LinkTestResultAndPatientPage from "../components/page/LinkTestResultAndPatientPage";
import LinkTestAndPatientPage from "../components/page/LinkTestAndPatientPage";
import RegisterInstitutionPage from "../components/page/RegisterInstitutionPage";
import RegisterPatientPage from "../components/page/RegisterPatientPage";

import ViewAllDataComponent from "../components/ViewAllDataComponent.vue";
import PublicStatisticsComponent from "../components/PublicStatisticsComponent";
import PatientOverviewComponent from "../components/PatientOverviewComponent.vue";
import SelfRegisterComponent from "../components/SelfRegisterComponent";

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
    path: "/link-test-and-patient",
    component: LinkTestAndPatientPage
  },
  {
    path: "/self-register",
    component: SelfRegisterComponent
  }
];
