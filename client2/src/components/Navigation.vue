<template>
  <a-layout-sider
    breakpoint="md"
  >
    <a-menu theme="dark" mode="inline" style="text-align: left;">
      <a-menu-item
        v-for="route in routes"
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
        <a-icon type="logout"/>Logout
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script lang="ts">

import Vue from 'vue'
import Component from 'vue-class-component'
import { navigationRoutes, AppRoute } from '../router'
import { config } from '@/config'
import { mapActions, mapGetters } from 'vuex'
import { InstitutionType } from '@/store/modules/auth.module'

@Component({
  computed: {
    ...mapGetters('auth', ['roles']),
  },
  methods: {
    ...mapActions('auth', ['logout']),
  },
})
export default class Navigation extends Vue {
  logout!: () => void
  routes!: AppRoute[]
  roles!: InstitutionType[]

  data () {
    return {
      routes: navigationRoutes
        .filter(r => config.showAllViews || r.meta?.navigationInfo?.authorities.includes(this.roles[0])),
    }
  }

  onLogout () {
    this.logout()
  }
}
</script>

<style>
</style>
