<template>
  <div id="app">
    <div v-if="this.$route.path === '/'">
      <LandingPage />
    </div>
    <div v-else>
      <a-layout id="components-layout-demo-responsive">
        <a-layout>
          <a-layout-header style="height: auto; display: flex; justify-content: space-between; align-items: baseline; padding: 0">

            <div>
              <a-icon
                v-if="authenticationStore.user"
                @click="showNavbar = !showNavbar"
                type="menu"
                style="color: white; padding: 1.5rem"
              />
            </div>
            <span :style="{color: 'white', }">IMIS</span>
            <div>
              <a-icon
                v-if="authenticationStore.user"
                type="user"
                style="color: #aaa; padding: 1.5rem"
              />
            </div>
          </a-layout-header>
          <a-layout>
            <a-layout-sider
              breakpoint="lg"
              collapsed-width="0"
              v-model="showNavbar"
              :trigger="null"
            >
              <Navigation />
            </a-layout-sider>
            <div style="display: flex; flex-direction: column; justify-content: space-between;">
              <a-layout-content
                style="margin: 0 auto; box-sizing: border-box; width: 100%;"
              >
                <div :style="{ padding: '0px', minHeight: '360px', width: '100%' }">
                  <router-view></router-view>
                </div>
              </a-layout-content>
              <a-layout-footer style="textAlign: center">
                IMIS ©2020 with <a-icon type="heart" style="color:red;">❤</a-icon> by
                <a-button type="link" href="https://wirvsvirushackathon.org" target="_blank" style="padding-left: 0">#WeVsVirus</a-button>
              </a-layout-footer>
            </div>
          </a-layout>
        </a-layout>
      </a-layout>
    </div>
  </div>
</template>

<script>
import Navigation from "./components/Navigation";
import LandingPage from "./components/LandingPage";
import ALayoutSider from "ant-design-vue/es/layout/Sider";
import {authenticationStore} from "./util/auth";

export default {
  components: {
    ALayoutSider,
    Navigation,
    LandingPage
  },
  data() {
    return {
      showNavbar: false,
      authenticationStore: authenticationStore,
    };
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
