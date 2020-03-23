import PatientDataComponent from "../components/PatientDataComponent.vue"; // Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new
import LinkTestResultAndPatientPage from "../components/LinkTestResultAndPatientPage.vue"; // Labs erstellen Berichte zu Tests/ProbeIDs
import ViewAllDataComponent from "../components/ViewAllDataComponent.vue";
import AddInstitutionComponent from "../components/AddInstitutionComponent";
import PublicStatisticsComponent from "../components/PublicStatisticsComponent";
import PatientOverviewComponent from "../components/PatientOverviewComponent.vue";
import LinkSampleAndPatientPage from "../components/LinkSampleAndPatientPage";

export const routes = [
  { path: "*", redirect: "/patient-data" },
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
    path: "/patient-data",
    component: PatientDataComponent
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
