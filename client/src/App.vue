<template>
  <div id="app">
    <div class="prototype-warning">
      Dies ist ein Prototyp - Eingetragene Daten werden vor dem Absenden
      randomisiert.
    </div>

    <a-layout id="components-layout-demo-responsive">
      <a-layout>
        <a-layout-header style="height: auto">
          <Navigation />
        </a-layout-header>
        <a-layout-content
          style="margin: auto; box-sizing: border-box; padding: 0rem 4rem; width: 100%;"
        >
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
import Navigation from "./components/Navigation";

export default {
  components: {
    Navigation
  },
  name: "App"
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.prototype-warning {
  border: 2px solid red;
  font-size: 20px;
  height: 35px;
  background-color: #ffdddd;
}

  #components-layout-demo-responsive {
    height: calc(100vh - 35px);
  }
</style>
