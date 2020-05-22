<template>
    <div>
      
      <a-row :gutter="8">
        <TestIncidentsCard :testIncidents='test' />
      </a-row>
      <a-row :gutter="8" style="margin-top: 8px;">

        <a-col :md="8" :span="24">
          <OtherIncidentsCard :quarantineIncidents='quarantine' :hospitalizationIncidents='hospitalization'/>
        </a-col>
      
        <a-col :md="8" :span="24">
          <a-card align="left" title="Prädisposition">
            <div v-if='preIllnesses[0]===""'>Keine Prädispositionsinformationen.</div>
            <div v-bind:key="illness" v-for="illness in preIllnesses">
              {{ illness }}
            </div>
          </a-card>
        </a-col>

        <a-col :md="8" :span="24">
          <a-card align="left" title="Symptome">
            <div v-if='symptoms[0]===""'>Keine Symptome erfasst.</div>
            <div v-bind:key="symptom" v-for="symptom in symptoms">
              {{ symptom }}
            </div>
          </a-card>
        </a-col>
        
      </a-row>
    </div>
</template>
<script lang="ts">
import Vue from 'vue'
import { Incident } from '../api/SwaggerApi'
import TestIncidentsCard from '../components/TestIncidentsCard.vue'
import OtherIncidentsCard from '../components/OtherIncidentsCard.vue'

interface State {
  administrative: any[]
  test: any[]
  quarantine: any[]
  hospitalization: any[]
}

export default Vue.extend({
  name: 'Incidents',
  components: {
      TestIncidentsCard,
      OtherIncidentsCard,
  },
  created() {

      let incidents :any[] = [...this.allIncidents]
      
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
  props: ['allIncidents', 'preIllnesses', 'symptoms'],
  data(): State {
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
