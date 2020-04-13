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
    <a-form :form="form" style="flex: 0 1 700px; display: flex; align-items: center">
      <a-button @click="searchPatient" class="search-link" html-type="submit" type="link">
        <a-icon type="search"/>
      </a-button>
      <PatientInput
        :form="form"
        :validation="['patientQuery']"
        @select="selectPatient"
        style="margin: 0; flex: 1 1 auto"
      />
    </a-form>
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
import PatientInput from '@/components/PatientInput.vue'
import {authMapper} from '@/store/modules/auth.module'

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
  components: {
    PatientInput,
  },
  data() {
    return {
      form: this.$form.createForm(this),
    }
  },
  methods: {
    searchPatient() {
      this.$router.push({name: 'patient-list', query: {query: this.form.getFieldValue('patientQuery')}}).catch(err => {
        console.warn('Received ' + err.name + ' while navigating to patient list')
      })
    },
    selectPatient(patientId: string) {
      this.$router.push({name: 'patient-detail', params: {id: patientId}})
    },
  },
})
</script>
<style scoped>
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
