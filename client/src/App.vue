<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <!-- <ExampleComponent/> -->
    <!-- <router-link to="/lab-results">Go to RESULTS</router-link>
    <router-link to="/all-data">All data</router-link>
    <router-view></router-view>
    <h1>1337</h1> -->
    <a-layout id="components-layout-demo-responsive">
        <div class="logo" />

      <a-layout>
        <a-layout-header :style="{ background: '#fff', padding: 0 }">
          <a-menu theme="dark" mode="horizontal"  :defaultSelectedKeys="['1']">
            <a-menu-item key="1" v-on:click="menumode=1">
              <a-icon type="user" />
              <span class="nav-text">
                <router-link to="add-institution">Instutition Hinzufügen</router-link>
              </span>
            </a-menu-item>
            <a-menu-item key="2" v-on:click="menumode=2">
              <a-icon type="video-camera" />
              <span class="nav-text">
                <router-link to="example-component" class="nav-text">ExampleComponent</router-link>
              </span>
            </a-menu-item>
            <a-menu-item key="3" v-on:click="menumode=3">
              <a-icon type="upload" />
              <span class="nav-text">
                <router-link to="lab-results" class="nav-text">Laborresultate</router-link>
              </span>
            </a-menu-item>
            <a-menu-item key="4" v-on:click="menumode=4">
              <a-icon type="user" />
              <span class="nav-text">
                <router-link to="patient-data" class="nav-text">Patientendaten</router-link>
              </span>
            </a-menu-item>
            <a-menu-item key="5" v-on:click="menumode=5">
              <a-icon type="video-camera" />
              <span class="nav-text">
                <router-link to="test-components" class="nav-text">Test-Komponente</router-link>
              </span>
            </a-menu-item>
            <a-menu-item key="6" v-on:click="menumode=6">
              <a-icon type="account-group" />
              <span class="nav-text">
                <router-link to="all-data" class="nav-text">Alle Daten einsehen</router-link>
              </span>
            </a-menu-item>
          </a-menu>
        </a-layout-header>
        <a-layout-content :style="{ margin: '0px auto' }">

          <a-div>
            <div :style="{ padding: '0px', background: '#fff', minHeight: '360px' }">
              <router-view></router-view>
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

export default {
  name: 'App',
  components: {
    // Components
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
      menumode: 0
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
