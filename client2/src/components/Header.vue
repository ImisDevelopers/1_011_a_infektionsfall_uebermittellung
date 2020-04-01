<template>
  <a-layout-header style="height: auto; display: flex; justify-content: space-between; align-items: baseline; padding: 0">

    <div>
<!--        v-if="authenticationStore.user"-->
<!--        @click="showNavbar = !showNavbar"-->
      <a-icon
        type="menu"
        style="color: white; padding: 1.5rem"
      />
    </div>
    <span :style="{color: 'white', }">IMIS</span>
    <div>
<!--        v-if="authenticationStore.user"-->
      <a-icon
        type="user"
        style="color: #aaa; padding: 1.5rem"
      />
    </div>
  </a-layout-header>
</template>
<script>
import Vue from 'vue'
import Component from 'vue-class-component'

@Component
export default class Header extends Vue {

}
</script>
