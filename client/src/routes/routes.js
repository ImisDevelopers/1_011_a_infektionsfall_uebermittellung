import RegisterInstitutionPage from "../components/page/RegisterInstitutionPage";
import RegisterPatientPage from "../components/page/RegisterPatientPage";

import ViewAllDataComponent from "../components/ViewAllDataComponent.vue";
import PublicStatisticsComponent from "../components/PublicStatisticsComponent";
import PatientOverviewComponent from "../components/PatientOverviewComponent.vue";
import LandingPage from "../components/LandingPage";
import Login from "../components/Login";
import LinkTestAndPatient from "../components/laboratory/LinkTestAndPatient";
import LinkTestResultAndPatient from "../components/laboratory/LinkTestResultAndPatient";

// These routes do not require authentication
export const publicRoutes = [
  "/",
  "/prototype/login",
  "/prototype/public-statistics",
  // For demo purposes:
  "/prototype/register-institution"
];

export const routes = [
  { path: "*", redirect: "/" },
  {
    path: "/",
    component: LandingPage
  },
  {
    path: "/prototype/login",
    component: Login
  },
  {
    path: "/prototype",
    component: RegisterPatientPage
  },
  {
    path: "/prototype/link-test-result",
    component: LinkTestResultAndPatient
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
    path: "/prototype/link-test-and-patient",
    component: LinkTestAndPatient
  }
];
