<template>
  <div id="app">
    <div v-if="this.$route.path === '/'">
      <LandingPage />
    </div>
    <div v-else>
      <a-layout id="components-layout-demo-responsive">
        <a-layout>
          <a-layout-header style="height: auto">
            <Navigation />
          </a-layout-header>
          <a-layout-content
            style="margin: auto; box-sizing: border-box; padding: 0 4rem; width: 100%;"
          >
            <div :style="{ padding: '0px', minHeight: '360px', width: '100%' }">
              <router-view></router-view>
            </div>
          </a-layout-content>
          <a-layout-footer style="textAlign: center">
            IMIS ©2020 with <a-icon type="heart" style="color:red;">❤</a-icon> by
            <a-button type="link" href="https://wirvsvirushackathon.org" target="_blank" style="padding-left: 0">#WeVsVirus</a-button>
          </a-layout-footer>
        </a-layout>
      </a-layout>
    </div>
  </div>
</template>

<script>
import Navigation from "./components/Navigation";
import LandingPage from "./components/LandingPage";

export default {
  components: {
    Navigation,
    LandingPage
  },
  data() {
    return {};
  },
  computed: {
    currentPath: function() {
      return location.pathname;
    }
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
  min-height: 100vh;
}
</style>
