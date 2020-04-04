<template>
  <div>
    <a-layout class="app-container">
      <a-layout>
        <Header />
        <a-layout>
          <Navigation />
          <a-layout-content
            style="box-sizing: border-box; width: 100%; display: flex; justify-content: space-between; flex-direction: column;"
          >
            <div :style="{ padding: '0px', minHeight: '360px', width: '100%' }">
              <router-view></router-view>
            </div>
            <a-layout-footer style="textAlign: center">
              IMIS ©2020 with <a-icon type="heart" style="color:red;">❤</a-icon> by
              <a-button type="link" href="https://wirvsvirushackathon.org" target="_blank" style="padding-left: 0">#WeVsVirus</a-button>
            </a-layout-footer>
          </a-layout-content>
        </a-layout>
      </a-layout>
    </a-layout>
    <a-spin
      v-if="isLoading"
      size="large"
      style="position: fixed; bottom: 3rem; margin: 0 auto;"
    />
  </div>
</template>
<script lang="ts">

import Vue from 'vue'
import Component from 'vue-class-component'
import Header from '@/components/Header.vue'
import Navigation from '@/components/Navigation.vue'
import { mapGetters } from 'vuex'

@Component({
  components: {
    Header,
    Navigation,
  },
})
export default class AppRoot extends Vue {
  // TODO Connect to state and use http interceptor
  data () {
    return {
      isLoading: false,
    }
  }
}

</script>

<style>
  .app-container {
    min-height: 100vh;
  }
</style>
