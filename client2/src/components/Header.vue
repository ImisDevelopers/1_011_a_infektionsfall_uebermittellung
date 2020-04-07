<template>
  <a-layout-header
    style="height: auto; display: flex; justify-content: space-between; align-items: baseline; padding: 0">

    <div>
      <a-icon
        type="menu"
        style="color: white; padding: 1.5rem"
        @click="$emit('menu-toggle')"
      />
    </div>
    <span :style="{color: 'white', }">IMIS</span>
    <div>
      <a-icon
        type="user"
        style="color: #aaa; padding: 1.5rem"
      />
    </div>
  </a-layout-header>
</template>
<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'Header',
})
</script>
