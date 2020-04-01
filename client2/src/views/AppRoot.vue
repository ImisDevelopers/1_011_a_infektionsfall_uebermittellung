<template>
  <div>
    <a-layout class="app-container">
      <a-layout>
        <Header />
        <a-layout>
          <Navigation />
          <a-layout-content
            style="margin: auto; box-sizing: border-box; width: 100%;"
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
  </div>
</template>
<script>

import Vue from 'vue'
import Component from 'vue-class-component'
import Header from '@/components/Header.vue'
import Navigation from '@/components/Navigation.vue'

@Component({
  components: {
    Header,
    Navigation,
  },
})
export default class AppRoot extends Vue {

}

</script>

<style>
  .app-container {
    min-height: 100vh;
  }
</style>
