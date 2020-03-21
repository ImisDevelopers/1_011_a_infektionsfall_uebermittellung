<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <!-- <ExampleComponent/> -->
    <a-layout id="components-layout-demo-responsive">
        <div class="logo" />
      <a-layout>
        <a-layout-header :style="{ background: '#fff', padding: 0}">
          <a-menu theme="dark" mode="horizontal"  :defaultSelectedKeys="['1']">
            <a-menu-item key="1" v-on:click="menumode=1">
              <a-icon type="user" />
              <span class="nav-text">Instutition Hinzufügen</span>
            </a-menu-item>
            <a-menu-item key="2" v-on:click="menumode=2">
              <a-icon type="video-camera" />
              <span class="nav-text">ExampleComponent</span>
            </a-menu-item>
            <a-menu-item key="3" v-on:click="menumode=3">
              <a-icon type="upload" />
              <span class="nav-text">Laborresultate</span>
            </a-menu-item>
            <a-menu-item key="4" v-on:click="menumode=4">
              <a-icon type="user" />
              <span class="nav-text">Patientendaten</span>
            </a-menu-item>
            <a-menu-item key="5" v-on:click="menumode=5">
              <a-icon type="video-camera" />
              <span class="nav-text">Test-Komponente</span>
            </a-menu-item>
            <a-menu-item key="6" v-on:click="menumode=6">
              <a-icon type="user" />
              <span class="nav-text">Alle Daten einsehen</span>
            </a-menu-item>
          </a-menu>
        </a-layout-header>
        <a-layout-content :style="{ margin: '0px auto', width:'100%'  }">

          <a-div v-if="menumode==1" >
            <div :style="{ padding: '0px', background: '#fff', minHeight: '360px' }">
              <AddInstitutionComponent/>
            </div>
          </a-div>
          <a-div v-else-if="menumode==2" >
            <div :style="{ padding: '0px', background: '#fff', minHeight: '360px' }">
              <ExampleComponent/>
            </div>
          </a-div>
          <a-div v-else-if="menumode==3" >
            <div :style="{ padding: '0px', background: '#fff', minHeight: '360px' }">
              <LabResultComponent/>
            </div>
          </a-div>
          <a-div v-else-if="menumode==4" >
            <div :style="{ padding: '0px', background: '#fff', minHeight: '360px' }">
              <PatientDataComponent/>
            </div>
          </a-div>
          <a-div v-else-if="menumode==5" >
            <div :style="{ padding: '0px', background: '#fff', minHeight: '360px' }">
              <TestingComponent/>
            </div>
          </a-div>
          <a-div v-else-if="menumode==6" >
            <div :style="{ padding: '0px', background: '#fff', minHeight: '360px' }">
              <ViewAllDataComponent/>
            </div>
          </a-div>
        </a-layout-content>
        <a-layout-footer style="textAlign: center">
          IMIS ©2020 Created by WeVsVirus
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>

import ExampleComponent from './components/ExampleComponent.vue';
import PatientDataComponent from './components/PatientDataComponent.vue'; // Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new
import TestingComponent from './components/TestingComponent.vue'; // TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu
import LabResultComponent from './components/LabResultComponent.vue'; // Labs erstellen Berichte zu Tests/ProbeIDs
import ViewAllDataComponent from './components/ViewAllDataComponent.vue';
import AddInstitutionComponent from "./components/AddInstitutionComponent";

export default {
  name: 'App',
  components: {
    PatientDataComponent,
    LabResultComponent,
    ExampleComponent,
    AddInstitutionComponent,
    TestingComponent,
    ViewAllDataComponent,
  },
  methods: {
    onCollapse(collapsed, type) {
      console.log(collapsed, type);
    },
    onBreakpoint(broken) {
      console.log(broken);
    },
  },
  data: function() {
    return {
      menumode: 1
    };
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
  /* margin-top: 60px; */
}
</style>
