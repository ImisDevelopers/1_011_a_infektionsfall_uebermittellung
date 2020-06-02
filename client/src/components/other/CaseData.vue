<template>
  <div>
    <!-- Test Table -->
    <a-row :gutter="8">
      <TestIncidentsCard :testIncidents="test" style="margin-top: 8px;" />
    </a-row>

    <a-row :gutter="8">
      <!-- Basisinformationen -->
      <a-col :md="8" :span="24">
        <a-card
          align="left"
          title="Basisinformationen"
          style="margin-top: 8px;"
        >
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

        <!-- Infektionskette -->
        <a-card align="left" title="Infektionskette" style="margin-top: 8px;">
          <a-descriptions layout="vertical" :column="1">
            <a-descriptions-item>
              <span slot="label"
                ><a-icon type="arrow-right" /><a-icon
                  type="user"
                  style="margin-right: 5px;"
                />
                Kontakte mit Indexpatienten</span
              >
              <span v-if="patientInfectionSources.length > 0">{{
                patientInfectionSources.length
              }}</span>
              <span v-else>Keine</span>
              bekannt
            </a-descriptions-item>
            <a-descriptions-item>
              <span slot="label"
                ><a-icon type="user" /><a-icon
                  type="arrow-right"
                  style="margin-right: 5px;"
                />
                Eigene Kontaktpersonen</span
              >
              <span v-if="exposureContacts.length > 0">{{
                exposureContacts.length
              }}</span>
              <span v-else>Keine</span>
              angegeben
            </a-descriptions-item>
          </a-descriptions>
        </a-card>
      </a-col>

      <!-- Symptoms, Prädisposition -->
      <a-col :md="8" :span="24">
        <a-card
          align="left"
          title="Symptome, Prädisposition"
          style="margin-top: 8px;"
        >
          <div v-if="administrative.symptoms[0] === ''">
            Keine Symptome erfasst.
          </div>
          <div v-else>
            Symptome:
            <ul>
              <li
                v-bind:key="symptom"
                v-for="symptom in administrative.symptoms"
              >
                {{
                  SYMPTOMS.find((symptomFind) => symptomFind.value === symptom) ?
                    SYMPTOMS.find((symptomFind) => symptomFind.value === symptom).label
                    : symptom
                }}
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
          style="margin-top: 8px;"
          :quarantineIncidents="quarantine"
          :hospitalizationIncidents="hospitalization"
        />
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts">
import Vue from 'vue'
import {
  PatientLogDto,
  HospitalizationIncident,
  QuarantineIncident,
  AdministrativeIncident,
  TestIncident,
} from '../../api/SwaggerApi'
import TestIncidentsCard from '../../components/other/TestIncidentsCard.vue'
import QuarantineHospitalizationCard from '../../components/other/QuarantineHospitalizationCard.vue'
import { getDate } from '../../util/helper-functions'
import { SYMPTOMS } from '../../models/symptoms'

interface State {
  administrative: AdministrativeIncident | undefined // There's only one.
  test: TestIncident[]
  quarantine: QuarantineIncident[]
  hospitalization: HospitalizationIncident[]
  SYMPTOMS: any[]
}

const removeHistoric = (
  incidents: Array<
    | TestIncident
    | HospitalizationIncident
    | QuarantineIncident
    | AdministrativeIncident
  >
) =>
  incidents
    .sort((a, b) => {
      return (
        b.id!.localeCompare(a.id!) ||
        b.versionTimestamp!.localeCompare(a.versionTimestamp!)
      )
    })
    .filter((cA, i) => incidents.findIndex((cB) => cB.id === cA.id) === i)

export default Vue.extend({
  name: 'Incidents',
  components: {
    TestIncidentsCard,
    QuarantineHospitalizationCard,
  },
  watch: {
    allIncidents: {
      immediate: true,
      handler(newI: PatientLogDto) {
        const administrativeIncidents = [...newI.administrativeIncidents!]
        const quarantineIncidents = [...newI.quarantineIncidents!]
        const hospitalizationIncidents = [...newI.hospitalizationIncidents!]
        const testIncidents = [...newI.testIncidents!]
        this.administrative = removeHistoric(administrativeIncidents)[0]
        this.quarantine = removeHistoric(quarantineIncidents)
        this.hospitalization = removeHistoric(hospitalizationIncidents)
        this.test = removeHistoric(testIncidents)
      },
    },
  },
  props: {
    allIncidents: {
      type: Object as () => PatientLogDto,
    },
    preIllnesses: Array, // Array<String> not supported
    patientInfectionSources: Array, // Api returns any
    exposureContacts: Array,
  },
  data(): State {
    return {
      administrative: undefined,
      test: [],
      quarantine: [],
      hospitalization: [],
      SYMPTOMS,
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
