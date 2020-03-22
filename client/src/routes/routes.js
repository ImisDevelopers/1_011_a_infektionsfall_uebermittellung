 import ExampleComponent from '../components/ExampleComponent.vue';
import PatientDataComponent from '../components/PatientDataComponent.vue'; // Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new
import LabResultComponent from '../components/LabResultComponent.vue'; // Labs erstellen Berichte zu Tests/ProbeIDs
import ViewAllDataComponent from '../components/ViewAllDataComponent.vue';
import AddInstitutionComponent from "../components/AddInstitutionComponent";
import PublicStatisticsComponent from "../components/PublicStatisticsComponent";
import PatientOverviewComponent from '../components/PatientOverviewComponent.vue';
import ProbeComponent from "../components/ProbeComponent"; // TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu

export const routes = [
    { path: '*', redirect: '/patient-data' },
    {
        path: '/lab-result',
        component: LabResultComponent
    },
    {       
        path: '/add-institution',
        component: AddInstitutionComponent,
    },
    {
        path: '/public-statistics',
        component: PublicStatisticsComponent
    },
    {        
        path:'/patient-data',
        component: PatientDataComponent
    },
    {
        path:'/patient-overview',
        component: PatientOverviewComponent
    },
    {
        path: '/all-data',
        component: ViewAllDataComponent
    },
    {
        path: '/example-component',
        component: ExampleComponent
    },
    {
        path: '/probe-component',
        component: ProbeComponent
    },
]
