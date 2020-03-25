<!--
<template>
  <div id="app">
    <Navigation />
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
  background-color: #ffdddd;
}
</style>
-->

<template>
  <div id="app">
    <div v-if="currentUrl=='/home'"> <!-- this is quite unreliable-->
      <LandingPage/>
    </div>
    <div v-else>
      <a-layout id="components-layout-demo-responsive">
        <div class="logo" />
        <Navigation />
        <a-layout>
          <a-layout-header
                  :style="{ background: '#fff', padding: 0, height: 'auto' }"
          >
          </a-layout-header>
          <a-layout-content
                  style="margin: '0px auto'; box-sizing: border-box; padding: 2rem 4rem; width: 100%; min-height: calc(100vh - 46px - 69px);"
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
  </div>
</template>

<script>
import Navigation from "./components/Navigation";
import LandingPage from "./components/LandingPage";
const currentUrl= window.location.pathname;

  export default {
    components: {
      Navigation,
      LandingPage,
    },
    data() {
      return{
        currentUrl
      }
    },
    name: "App"
  }
</script>

<style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }
</style>