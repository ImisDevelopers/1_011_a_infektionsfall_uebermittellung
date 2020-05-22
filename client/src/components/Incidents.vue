<template>
    <p>{{incidents[0].id}}</p>
</template>
<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'Incidents',
  created() {
      console.log(this.incidents)
  },
  props: ['incidents'],
  data() {
    return {}
  },
  methods: {},
})
</script>
<style scoped lang="scss">
</style>
