<template>
  <a-layout-sider
    :collapsed="sideNavCollapsed"
    :collapsedWidth="0"
    :trigger="null"
    breakpoint="md"
  >
    <a-menu
      mode="inline"
      style="text-align: left;"
      theme="dark"
    > <!--TODO :defaultSelectedKeys="[asdf]"-->
      <a-menu-item
        :key="route.path"
        v-for="route in routes()"
      >
        <!--        v-bind:class="{'ant-menu-item-selected': route.name === $route.name}" TODO this is to slow-->
        <router-link
          :to="{ name: route.name }"
          @click.native="$emit('route-clicked')"
        >
          <a-icon :type="route.meta.navigationInfo.icon"/>
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
import {authMapper} from '@/store/modules/auth.module'

export default Vue.extend({
  name: 'Navigation',
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
    onLogout() {
      this.logout()
    },
  },
  props: {
    sideNavCollapsed: {
      type: Boolean,
    },
  },
})
</script>

<style>
</style>
