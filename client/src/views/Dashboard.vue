<template>
  <div style="padding: 24px">
    <div class="header">
      <a-icon class="icon" type="user" />
      <div class="text">{{username}}</div>
      <a-icon class="icon" type="bank" />
      <div class="text" v-if="institution">{{institution.name}}</div>
    </div>
    <a-row :gutter="8">
      <a-col
        :span="24"
        :md="24"
      >
        <UnprocessedCases />
      </a-col>
    </a-row>
  </div>

</template>

<script lang="ts">
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'
import UnprocessedCases from '@/components/UnprocessedCases.vue'

export default Vue.extend({
  name: 'Dashboard',
  components: {
    UnprocessedCases,
  },
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
  .header {
    display: flex;
    align-items: center;
    background: white;
    margin-bottom: 24px;
    padding: 16px;
    border-radius: 4px;
    border: 1px solid #e8e8e8;

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
