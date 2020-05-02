<template>
  <a-layout-header
    style="height: auto; display: flex; justify-content: space-between; align-items: center; padding: 0">

    <div style="flex: 0 0 auto">
      <a-icon
        @click="$emit('menu-toggle')"
        style="color: white; padding: 1.5rem"
        type="menu"
      />
      <span class="imis-title" style="color: white">IMIS</span>
    </div>
    <span style="flex: 1 1 auto"></span>
    <a-popover v-model="visible" :title="username" trigger="click" class="header-user-popover">
      <a slot="content">

        <a-menu
          mode="inline"
          :selectedKeys="[]"
          style="border: none">
          <a-menu-item
            key="1"
            class="menu-item"
          >
            <router-link
              :to="{ name: 'account' }"
              @click.native="routeClicked"
            >
              <a-icon type="user" />
              <span class="nav-text">Benutzerkonto</span>
            </router-link>
          </a-menu-item>
          <a-menu-item
            @click="onLogout"
            class="menu-item"
          >
            <a-icon type="logout" />
            <span class="nav-text">
          Abmelden
        </span>
          </a-menu-item>
        </a-menu>

      </a>
      <div style="flex: 0 0 auto; cursor: pointer">
        <a-icon
          style="color: #aaa; padding: 1.5rem"
          type="user"
        />
        <span class="username" style="margin-right: 15px; color: rgba(255, 255, 255, 0.87)">{{username}}</span>
        <a-icon
          style="color: #aaa; padding: 1.5rem 1.5rem 1.5rem 0"
          type="caret-down"
        />
      </div>
    </a-popover>

  </a-layout-header>
</template>
<script lang="ts">
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'

export default Vue.extend({
  name: 'Header',
  computed: {
    ...authMapper.mapState({
      user: 'user',
    }),
    username(): string {
      return this.user?.username || ''
    },
  },
  data() {
    return {
      form: this.$form.createForm(this),
      visible: false,
    }
  },
  methods: {
    ...authMapper.mapActions({
      logout: 'logout',
    }),
    onLogout() {
      this.logout()
    },
    routeClicked() {
      this.visible = false
      this.$emit('route-clicked')
    },
  },
})
</script>
<style scoped lang="scss">
  @media (max-width: 700px) {
    .imis-title {
      display: none;
    }

    .search-link {
      display: none;
    }

    .username {
      display: none;
    }
  }

  .menu-item:hover {
    background-color: rgba(0, 0, 0, 0.1);
  }

</style>
