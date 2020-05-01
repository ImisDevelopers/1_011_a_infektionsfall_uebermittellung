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
    <div style="flex: 0 0 auto">
      <a-icon
        style="color: #aaa; padding: 1.5rem"
        type="user"
      />
      <span class="username" style="margin-right: 15px; color: rgba(255, 255, 255, 0.87)">{{username}}</span>
    </div>
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
    }
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

</style>
