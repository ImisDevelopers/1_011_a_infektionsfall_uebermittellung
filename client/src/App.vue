<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <!-- <ExampleComponent/> -->
    <a-layout id="components-layout-demo-responsive">
        <div class="logo" />

      <a-layout>
        <a-layout-header :style="{ background: '#fff', padding: 0, header: 'auto' }">
          <a-menu theme="dark" mode="horizontal"  :defaultSelectedKeys="['1']">
            <a-menu-item key="1" v-on:click="menumode=1">
              <router-link to="/patient-data">
                <a-icon type="user" />
                <span class="nav-text">Patient Aufnehmen</span>
              </router-link>
            </a-menu-item>

   
              <a-menu-item key="2" v-on:click="menumode=2">
                <router-link to="/probe-component">
                  <a-icon type="video-camera" />
                  <span class="nav-text">Probe Zuordnen</span>
                </router-link>
              </a-menu-item>

            
              <a-menu-item key="3" v-on:click="menumode=3">
                <router-link to="/lab-result">
                  <a-icon type="upload" />
                  <span class="nav-text">Laborresultate</span>
                </router-link>
              </a-menu-item>



              <a-menu-item key="4" v-on:click="menumode=4">
                <router-link to="/patient-overview">
                  <a-icon type="user" />
                  <span class="nav-text">Patienten-Daten</span>
                </router-link>
              </a-menu-item>

          

              <a-menu-item key="5" v-on:click="menumode=6">
                <router-link to="/all-data">
                  <a-icon type="user" />
                  <span class="nav-text">Alle Daten einsehen</span>
                </router-link>
              </a-menu-item>

            

              <a-menu-item key="6" v-on:click="menumode=7">
                <router-link to="/public-statistics">
                  <a-icon type="user" />
                  <span class="nav-text">Öffentliche Statistiken</span>
                </router-link>
              </a-menu-item>
          </a-menu>
        </a-layout-header>
        <a-layout-content style="margin: '0px auto'; box-sizing: border-box; padding: 2rem 4rem; width: 100%; min-height: calc(100vh - 46px - 69px);">

          <div :style="{ padding: '0px', minHeight: '360px', width: '100%' }">
            <router-view></router-view>
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


export default {
  name: 'App',

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
