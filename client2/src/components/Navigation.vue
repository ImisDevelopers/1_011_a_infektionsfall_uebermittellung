<template>
  <a-layout-sider
    breakpoint="md"
  >
    <a-menu
      theme="dark"
      mode="inline"
      style="text-align: left;"
    > <!--TODO :defaultSelectedKeys="[asdf]"-->
      <a-menu-item
        v-for="route in routes()"
        :key="route.path"
      >
<!--        v-bind:class="{'ant-menu-item-selected': route.name === $route.name}" TODO this is to slow-->
        <router-link :to="{ name: route.name }">
          <a-icon :type="route.meta.navigationInfo.icon" />
          <span class="nav-text">{{ route.meta.navigationInfo.title }}</span>
        </router-link>
      </a-menu-item>
      <a-menu-item
        @click="onLogout"
      >
        <a-icon type="logout"/>
        <span class="nav-text">
          Logout
        </span>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script lang="ts">

import Vue from 'vue'
import Component from 'vue-class-component'
import { authMapper, authModule } from '@/store/modules/auth.module'

const Base = Vue.extend({
  computed: {
    ...authMapper.mapGetters({
      roles: 'roles',
      routes: 'routes',
    }),
  },
  methods: {
    ...authMapper.mapActions({
      logout: 'logout',
    }),
  },
})
@Component
export default class Navigation extends Base {
  onLogout() {
    this.logout()
  }
}
</script>

<style>
</style>
