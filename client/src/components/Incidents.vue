<template>
    <div>
        <TestIncidentsCard :testIncidents='test' />
        <OtherIncidentsCard />
    </div>
</template>
<script lang="ts">
import Vue from 'vue'
import { Incident } from '../api/SwaggerApi'
import TestIncidentsCard from '../components/TestIncidentsCard.vue'
import OtherIncidentsCard from '../components/OtherIncidentsCard.vue'

export default Vue.extend({
  name: 'Incidents',
  components: {
      TestIncidentsCard,
      OtherIncidentsCard,
  },
  created() {

      let incidents = this.allIncidents
      
      // Sort by ID and Version
      incidents.sort((a: Incident, b: Incident) => {
        return (
          a.id!.localeCompare(b.id!) ||
          a.versionTimestamp!.localeCompare(b.versionTimestamp!)
        )
      })

      // Remove historic entries (keep latest version only)
      for (let i=0; i<incidents.length-1; i++) {
          if (incidents[i].id === incidents[i+1].id)
            incidents[i] = undefined
      }
      incidents = incidents.filter( (c:any) => c !== undefined )

      // Categorize Incidents
      this.administrative = incidents.filter( (incident:any) => incident.id.startsWith("administrative"))
      this.test = incidents.filter( (incident:any) => incident.id.startsWith("test"))
      this.quarantine = incidents.filter( (incident:any) => incident.id.startsWith("quarantine"))
      this.hospitalization = incidents.filter( (incident:any) => incident.id.startsWith("hospitalization"))

  },
  props: ['allIncidents'],
  data() {
    return {
        administrative: [],
        test: [],
        quarantine: [],
        hospitalization: [],
    }
  },
  methods: {},
})
</script>
<style scoped lang="scss">
</style>
