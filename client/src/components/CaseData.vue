<template>
  <div>

    <!-- Test Table -->
    <a-row :gutter="8" style="margin-top: 8px;">
      <TestIncidentsCard :testIncidents="test" />
    </a-row>

    <a-row :gutter="8" style="margin-top: 8px;">

      <!-- Basisinformationen -->
      <a-col :md="8" :span="24">
        <a-card align="left" title="Basisinformationen">
          <table>
            <tr>
              <td>Krankheit:</td>
              <td>{{ administrative.illness }}</td>
            </tr>
            <tr>
              <td>Erkrankungsdatum:</td>
              <td>{{ getDate(administrative.dateOfIllness) }}</td>
            </tr>
            <tr>
              <td>Meldedatum:</td>
              <td>{{ getDate(administrative.dateOfReporting) }}</td>
            </tr>
            <tr>
              <td>Behandelnder Artzt:</td>
              <td>{{ administrative.responsibleDoctor || 'keine Angabe' }}</td>
            </tr>
          </table>
        </a-card>
      </a-col>

      <!-- Symptoms, Prädisposition -->
      <a-col :md="8" :span="24">

        <a-card align="left" title="Symptome, Prädisposition">

          <div v-if="administrative.symptoms[0] === ''">Keine Symptome erfasst.</div>
          <div v-else>
            Symptome:
            <ul>
              <li v-bind:key="symptom" v-for="symptom in administrative.symptoms">
                {{ symptom }}
              </li>
            </ul>
          </div>

          <br />

          <div v-if="preIllnesses[0] === ''">
            Keine Prädispositionsinformationen.
          </div>
          <div v-else>
            Prädisposition:
            <ul>
              <li v-bind:key="illness" v-for="illness in preIllnesses">
                {{ illness }}
              </li>
            </ul>
          </div>

        </a-card>

      </a-col>
      
      <!-- Quarantine & Hospital -->
      <a-col :md="8" :span="24">
        <QuarantineHospitalizationCard
          :quarantineIncidents="quarantine"
          :hospitalizationIncidents="hospitalization"
        />
      </a-col>

    </a-row>

  </div>
</template>
<script lang="ts">
import Vue from 'vue'
import { Incident } from '../api/SwaggerApi'
import TestIncidentsCard from '../components/TestIncidentsCard.vue'
import QuarantineHospitalizationCard from '../components/QuarantineHospitalizationCard.vue'
import { getDate } from '../util/helper-functions'

interface State {
  administrative: any // There's only one.
  test: any[]
  quarantine: any[]
  hospitalization: any[]
}

export default Vue.extend({
  name: 'Incidents',
  components: {
    TestIncidentsCard,
    QuarantineHospitalizationCard,
  },
  created() {
    let incidents: any[] = [...this.allIncidents]

    // Sort by ID and Version
    incidents.sort((a: Incident, b: Incident) => {
      return (
        a.id!.localeCompare(b.id!) ||
        a.versionTimestamp!.localeCompare(b.versionTimestamp!)
      )
    })

    // Remove historic entries (keep latest version only)
    for (let i = 0; i < incidents.length - 1; i++) {
      if (incidents[i].id === incidents[i + 1].id) incidents[i] = undefined
    }
    incidents = incidents.filter((c: any) => c !== undefined)
    console.log(incidents)
    // Categorize Incidents
    this.administrative = incidents.filter((incident: any) =>
      incident.id.startsWith('administrative')
    )[0] // There's only one (exactly one) per Case. This needs to be adapted once case support is enabled.
    this.test = incidents.filter((incident: any) =>
      incident.id.startsWith('test')
    )
    this.quarantine = incidents.filter((incident: any) =>
      incident.id.startsWith('quarantine')
    )
    this.hospitalization = incidents.filter((incident: any) =>
      incident.id.startsWith('hospitalization')
    )
  },
  props: ['allIncidents', 'preIllnesses'],
  data(): State {
    return {
      administrative: undefined,
      test: [],
      quarantine: [],
      hospitalization: [],
    }
  },
  methods: {
    getDate,
  },
})
</script>

<style scoped lang="scss">
.list-item {
  border-left: 5em;
}
table {
  border-collapse: separate;
  border-spacing: 5px;

  tr {
    padding: 15px;

    td:first-of-type {
      padding-right: 15px;
    }
  }
}
table.compact {
  border-spacing: 15px 3px;
}
</style>

