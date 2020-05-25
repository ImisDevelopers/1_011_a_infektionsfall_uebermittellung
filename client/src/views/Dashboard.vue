<template>
  <div class="dashboard-container">
    <div class="greeting-container">
      <h1>Willkommen in</h1>
      <img src="../assets/logo.png" />
      <h2>Infektionsmelde- und Informationsystem</h2>
    </div>
    <div class="header">
      <div class="text">Angemeldet als:</div>
      <a-icon class="icon" type="user" />
      <div class="text">{{ username }}</div>
      <a-icon class="icon" type="bank" />
      <div class="text" v-if="institution">{{ institution.name }}</div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'

export default Vue.extend({
  name: 'Dashboard',
  computed: {
    ...authMapper.mapState({
      user: 'user',
      institution: 'institution',
    }),
    username(): string {
      return this.user?.username || ''
    },
  },
  data() {
    return {}
  },
  methods: {},
})
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.dashboard-container {
  padding: 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.greeting-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;

  h1 {
    font-size: 48px;
  }

  h2 {
    margin-top: 25px;
    font-size: 28px;
  }
}

.header {
  margin-top: 75px;
  display: flex;
  align-items: center;
  background: white;
  margin-bottom: 24px;
  padding: 16px;
  border-radius: 4px;
  border: 1px solid #e8e8e8;
  max-width: 800px;

  .icon {
    font-size: 28px;
    margin: 0 15px;
  }

  .text {
    color: rgba(0, 0, 0, 0.87);
    font-size: 18px;
  }
}
</style>
