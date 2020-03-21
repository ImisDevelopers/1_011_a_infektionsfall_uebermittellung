<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <!-- <ExampleComponent/> -->
    <a-layout id="components-layout-demo-responsive">
      <a-layout-sider
        breakpoint="lg"
        collapsedWidth="0"
        @collapse="onCollapse"
        @breakpoint="onBreakpoint"
      >
        <div class="logo" />
        <a-menu theme="dark" mode="inline" :defaultSelectedKeys="['1']">
          <a-menu-item key="1">
            <a-icon type="user" />
            <span class="nav-text">Verdachtsfälle</span>
          </a-menu-item>
          <a-menu-item key="2">
            <a-icon type="video-camera" />
            <span class="nav-text">Coronatests</span>
          </a-menu-item>
          <a-menu-item key="3">
            <a-icon type="upload" />
            <span class="nav-text">Befundupload</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header :style="{ background: '#fff', padding: 0 }" />
        <a-layout-content :style="{ margin: '0px auto' }">
          <div :style="{ padding: '0px', background: '#fff', minHeight: '360px' }">
            <TestingComponent/>
          </div>
        </a-layout-content>
        <a-layout-footer style="textAlign: center">
          IMIS ©2020 Created by WeVsVirus
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>

// import ExampleComponent from './components/ExampleComponent.vue';
// import PatientDataComponent from './components/PatientDataComponent.vue'; // Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new
import TestingComponent from './components/TestingComponent.vue'; // TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu
// import LabResultComponent from './components/LabResultComponent.vue'; // Labs erstellen Berichte zu Tests/ProbeIDs
// import ViewAllDataComponent from './components/ViewAllDataComponent.vue'; // Admin Übersicht über alle Daten (RKI? Bundesamt?)

export default {
  name: 'App',
  components: {
    // ExampleComponent,
    // PatientDataComponent,
    TestingComponent,
    // LabResultComponent,
    // ViewAllDataComponent,
  },
  methods: {
    onCollapse(collapsed, type) {
      console.log(collapsed, type);
    },
    onBreakpoint(broken) {
      console.log(broken);
    },
  },
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
