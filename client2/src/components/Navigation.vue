<template>
  <a-layout-sider
    breakpoint="md"
  >
    <a-menu theme="dark" mode="inline" style="text-align: left;">
      <a-menu-item
        v-for="route in routes"
        :key="route.path"
      >
        <router-link :to="{ name: route.name }">
          <a-icon :type="route.meta.icon" />
          <span class="nav-text">{{ route.meta.title }}</span>
        </router-link>
      </a-menu-item>
      <a-menu-item
        @click="onLogout"
      >
        <a-icon type="logout"/>Logout
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script>

import Vue from 'vue'
import Component from 'vue-class-component'
import { navigationRoutes } from '../router'

@Component
export default class Navigation extends Vue {
  data () {
    return {
      routes: navigationRoutes.filter(r => r.meta),
    }
  }

  onLogout () {
    this.$store.dispatch('auth/logout')
  }
}
</script>

<style>
</style>
