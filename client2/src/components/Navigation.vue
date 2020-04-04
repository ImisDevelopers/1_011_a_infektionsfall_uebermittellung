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
import { navigationRoutes, AppRoute } from '../router'
import { config } from '@/config'
import { mapActions, mapGetters } from 'vuex'
import { authModule } from '@/store/modules/auth.module'
import { InstitutionType } from '@/models'
import store from '@/store'

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
  authActions = authModule.context(store).actions

  data () {
    return {
      routes: navigationRoutes
        .filter(r => (config.showAllViews ||
          this.roles.some(a => r.meta?.navigationInfo?.authorities.includes(a)))),
    }
  }

  onLogout () {
    this.authActions.logout()
  }
}
</script>

<style>
</style>
