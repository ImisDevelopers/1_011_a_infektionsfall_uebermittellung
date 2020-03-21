import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import Antd from "ant-design-vue";
import "ant-design-vue/dist/antd.css";

import AddInstitutionComponent from "./components/AddInstitutionComponent";
import ExampleComponent from './components/ExampleComponent.vue';
import LabResultComponent from './components/LabResultComponent.vue'; // Labs erstellen Berichte zu Tests/ProbeIDs
import PatientDataComponent from './components/PatientDataComponent.vue'; // Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new
import TestingComponent from './components/TestingComponent.vue'; // TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu
import ViewAllDataComponent from './components/ViewAllDataComponent.vue';

const router = new VueRouter({
  mode: 'history',
  routes: [
    { path: '/add-institution', component: AddInstitutionComponent },
    { path: '/example-component', component: ExampleComponent },
    { path: '/lab-results', component: LabResultComponent },
    { path: '/patient-data', component: PatientDataComponent },
    { path: '/test-components', component: TestingComponent },
    { path: '/all-data', component: ViewAllDataComponent },
  ]
})

Vue.use(Antd);
Vue.use(VueRouter)
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
