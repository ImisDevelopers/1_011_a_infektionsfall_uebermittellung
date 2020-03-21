<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <!-- <ExampleComponent/> -->
    <PatientDataComponent/>
  </div>
</template>

<script>

// import ExampleComponent from './components/ExampleComponent.vue';
import PatientDataComponent from './components/PatientDataComponent.vue'; // Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new
// import TestingComponent from './components/LabResultComponent.vue'; // TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu
// import LabResultComponent from './components/LabResultComponent.vue'; // Labs erstellen Berichte zu Tests/ProbeIDs
// import ViewAllDataComponent from './components/LabResultComponent.vue'; // Admin Übersicht über alle Daten (RKI? Bundesamt?)

export default {
  name: 'App',
  components: {
    // ExampleComponent,
    PatientDataComponent,
    // TestingComponent,
    // LabResultComponent,
    // ViewAllDataComponent,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
