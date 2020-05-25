<template>
  <div>
    <router-link
      :to="{ name: 'patient-detail', params: { id: indexPatient.id } }"
      v-if="indexPatient"
    >
      {{ indexPatient.lastName }}, {{ indexPatient.firstName }}
    </router-link>
    <div v-else>-</div>
  </div>
</template>
<script lang="ts">
import Vue from 'vue'
import { ExposureContactContactView } from '@/api/SwaggerApi'
import Api from '@/api'

interface State {
  indexPatient: ExposureContactContactView | undefined
}

export default Vue.extend({
  name: 'IndexPatientTableCell',
  props: ['patientId'],
  data(): State {
    return {
      indexPatient: undefined,
    }
  },
  created() {
    Api.getExposureSourceContactsForPatientUsingGet(this.patientId).then(
      (infectionSources) => {
        if (infectionSources.length > 0) {
          const contact = infectionSources[0].source
          if (contact) {
            this.indexPatient = contact
            console.log('Has source: ' + this.patientId)
          }
        }
      }
    )
  },
  methods: {},
})
</script>
<style scoped lang="scss"></style>
