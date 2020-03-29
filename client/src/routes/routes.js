import RegisterInstitutionPage from "../components/page/RegisterInstitutionPage";
import RegisterPatientPage from "../components/page/RegisterPatientPage";

import ViewAllDataComponent from "../components/ViewAllDataComponent.vue";
import PublicStatisticsComponent from "../components/PublicStatisticsComponent";
import PatientOverviewComponent from "../components/PatientOverviewComponent.vue";
import LandingPage from "../components/LandingPage";
import Login from "../components/Login";
import LinkTestAndPatient from "../components/laboratory/LinkTestAndPatient";
import LinkTestResultAndPatient from "../components/laboratory/LinkTestResultAndPatient";

// Available Roles
const LABORATORY = "LABORATORY";
const DOCTORS_OFFICE = "DOCTORS_OFFICE";
const CLINIC = "CLINIC";
const TEST_SITE = "TEST_SITE";
const GOVERNMENT_AGENCY = "GOVERNMENT_AGENCY";


/*
    Info on rules:
    To restrict access to a route, add the corresponding roles to the route.
    If no roles are provided, the page will be public.
 */

export const routes = [
    {path: "*", redirect: "/"},
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
        component: RegisterPatientPage,
        roles: [LABORATORY, DOCTORS_OFFICE, CLINIC, TEST_SITE]
    },
    {
        path: "/prototype/link-test-result",
        component: LinkTestResultAndPatient,
        roles: [LABORATORY, DOCTORS_OFFICE, CLINIC, TEST_SITE]
    },
    {
        path: "/prototype/register-institution",
        component: RegisterInstitutionPage,
        // TODO: This is unprotected for testing purposes. Allow only certain roles to create institutions in the future
    },
    {
        path: "/prototype/public-statistics",
        component: PublicStatisticsComponent
    },
    {
        path: "/prototype/register-patient",
        component: RegisterPatientPage,
        roles: [LABORATORY, DOCTORS_OFFICE, CLINIC, TEST_SITE]
    },
    {
        path: "/prototype/patient-overview",
        component: PatientOverviewComponent,
        roles: [LABORATORY, DOCTORS_OFFICE, CLINIC, TEST_SITE, GOVERNMENT_AGENCY]
    },
    {
        path: "/prototype/all-data",
        component: ViewAllDataComponent,
        roles: [LABORATORY, DOCTORS_OFFICE, CLINIC, TEST_SITE, GOVERNMENT_AGENCY]
    },
    {
        path: "/prototype/link-test-and-patient",
        component: LinkTestAndPatient,
        roles: [LABORATORY, DOCTORS_OFFICE, CLINIC, TEST_SITE]
    }
];
