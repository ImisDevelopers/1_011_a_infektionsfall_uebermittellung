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
      :defaultSelectedKeys="[defaultPath]"
    >
      <a-menu-item
        v-for="route in routes()"
        :key="route.path"
      >
        <router-link
          :to="{ name: route.name }"
          @click.native="$emit('route-clicked')"
        >
          <a-icon :type="route.meta.navigationInfo.icon" />
          <span class="nav-text">{{ route.meta.navigationInfo.title }}</span>
        </router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script lang="ts">

import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'

export default Vue.extend({
  name: 'Navigation',
  computed: {
    ...authMapper.mapGetters({
      roles: 'roles',
      routes: 'routes',
    }),
  },
  created() {
    console.log(location.pathname)
  },
  data() {
    return {
      // we need to match the location path to the app route path
      // e.g. /app/account becomes account
      defaultPath: location.pathname.substring(5),
    }
  },
  props: {
    sideNavCollapsed: {
      type: Boolean,
    },
  },
})
</script>

<style scoped lang="scss">
</style>
