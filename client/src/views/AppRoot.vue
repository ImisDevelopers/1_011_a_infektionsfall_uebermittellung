<template>
  <div>
    <a-layout class="app-container">
      <a-layout>
        <Header
          @menu-toggle="sideNavCollapsed = !sideNavCollapsed"
        />
        <a-layout>
          <Navigation
            :sideNavCollapsed="sideNavCollapsed"
            @route-clicked="handleRouteClicked"
          />
          <a-layout-content
            style="box-sizing: border-box; width: 100%; display: flex; justify-content: space-between; flex-direction: column;"
          >
            <div :style="{ padding: '0px', minHeight: '360px', width: '100%' }">
              <router-view></router-view>
            </div>
            <!--
            <a-layout-footer style="textAlign: center">
              IMIS ©2020 with
              <a-icon style="color: red;" type="heart">❤</a-icon>
              by
              <a-button href="https://wirvsvirushackathon.org" style="padding-left: 0" target="_blank" type="link">
                #WeVsVirus
              </a-button>
            </a-layout-footer>
            -->
          </a-layout-content>
        </a-layout>
      </a-layout>
    </a-layout>
    <a-spin
      size="large"
      style="position: fixed; bottom: 3rem; margin: 0 auto;"
      v-if="isLoading"
    />
  </div>
</template>
<script lang="ts">

import Vue from 'vue'
import Header from '@/components/Header.vue'
import Navigation from '@/components/Navigation.vue'

// used for the navigation bar
const mobileWidth = 700

export default Vue.extend({
  name: 'AppRoot',
  components: {
    Header,
    Navigation,
  },
  data() {
    return {
      isLoading: false,
      sideNavCollapsed: window.innerWidth < mobileWidth,
    }
  },
  methods: {
    handleRouteClicked() {
      if (window.innerWidth < mobileWidth) {
        this.sideNavCollapsed = true
      }
    },
  },
})
</script>

<style scoped lang="scss">
  .app-container {
    min-height: 100vh;
  }
</style>
