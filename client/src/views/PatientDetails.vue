<template style="margin: auto">
  <div>
    <div style="max-width: 1020px; margin: 0 auto; padding: 0 1rem">
      <a-tabs
        defaultActiveKey="1"
        v-if="patient"
      >
        <a-tab-pane
          key="1"
          tab="Stammdaten"
        >
          <!-- display user data here-->
          <div>

            <!-- Allgemein & Adresse -->
            <a-row :gutter="8">
              <a-col
                :md="12"
                :span="24"
              >
                <a-card
                  :extra="this.patient.id"
                  align="left"
                  title="Allgemein"
                >
                  <table>
                    <tr>
                      <td>Vorname:</td>
                      <td>{{patient.firstName}}</td>
                    </tr>
                    <tr>
                      <td>Nachname:</td>
                      <td>{{patient.lastName}}</td>
                    </tr>
                    <tr>
                      <td>Geburtsdatum:</td>
                      <td>{{dateOfBirth}}</td>
                    </tr>
                    <tr>
                      <td>Geschlecht:</td>
                      <td>{{gender}}</td>
                    </tr>
                    <tr>
                      <td>Staatsangehörigkeit:</td>
                      <td>{{patient.nationality}}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
              <a-col
                :md="12"
                :span="24"
              >
                <a-card
                  align="left"
                  title="Adresse"
                >
                  <table>
                    <tr>
                      <td>Straße:</td>
                      <td>{{patient.street}}</td>
                    </tr>
                    <tr>
                      <td>Hausnummer:</td>
                      <td>{{patient.houseNumber}}</td>
                    </tr>
                    <tr>
                      <td>PLZ:</td>
                      <td>{{patient.zip}}</td>
                    </tr>
                    <tr>
                      <td>Ort:</td>
                      <td>{{patient.city}}</td>
                    </tr>
                    <tr>
                      <td>Land:</td>
                      <td>{{patient.country}}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
            </a-row>

            <!-- Kontakt & Versicherung & Arbeitgeber -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col :md="8" :span="24">
                <a-card
                  align="left"
                  title="Kontakt"
                >
                  <table>
                    <tr>
                      <td>Telefonnummer:</td>
                      <td>{{patient.phoneNumber}}</td>
                    </tr>
                    <tr>
                      <td>Email:</td>
                      <td><a href="">{{patient.email}}</a></td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
              <a-col :md="8" :span="24">
                <a-card
                  align="left"
                  title="Versicherung"
                >
                  <table>
                    <tr>
                      <td>Versicherung:</td>
                      <td>{{patient.insuranceCompany}}</td>
                    </tr>
                    <tr>
                      <td>V-Nr:</td>
                      <td>{{patient.insuranceMembershipNumber}}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
              <a-col :md="8" :span="24">
                <a-card
                  align="left"
                  title="Arbeit"
                >
                  <table>
                    <tr>
                      <td>Beruf:</td>
                      <td>{{patient.occupation || 'Keine Angabe'}}</td>
                    </tr>
                    <tr>
                      <td>Arbeitgeber:</td>
                      <td>{{patient.employer || 'Keine Angabe'}}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
            </a-row>

            <!-- Symptome und Risiken -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col
                :md="12"
                :span="24"
              >
                <a-card
                  align="left"
                  title="Vorerkrankungen und Risikofaktoren"
                >
                  <div v-bind:key="illness" v-for="illness in preIllnesses">{{illness}}</div>
                </a-card>
              </a-col>
              <a-col
                :md="12"
                :span="24"
              >
                <a-card
                  :extra="'Stand: ' + formatTimestamp(patient.creationTimestamp)"
                  align="left"
                  title="Symptome"
                >
                  <div v-bind:key="symptom" v-for="symptom in symptoms">{{symptom}}</div>
                </a-card>
              </a-col>
            </a-row>

            <!-- Tests -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col span="24">
                <a-card
                  :title="'Status: ' + (patientStatus ? patientStatus.label : 'Unbekannt') + (patient.quarantineUntil ? (', Quarantäne angeordnet bis ' + patient.quarantineUntil) : '')"
                  align="left"
                >
                  <a-table
                    :columns="columnsTests"
                    :dataSource="tests"
                    :scroll="{x: 0, y: 0}"
                    class="imis-table-no-pagination"
                    rowKey="id"
                  >
                    <div slot="testStatus" slot-scope="testStatus">
                      <a-icon :type="testResults.find(type => type.id === testStatus).icon" style="margin-right: 5px" />
                      {{testResults.find(type => type.id === testStatus).label}}
                    </div>
                    <div slot="testType" slot-scope="testType">
                      <a-icon :type="testTypes.find(type => type.id === testType).icon" style="margin-right: 5px" />
                      {{testTypes.find(type => type.id === testType).label}}
                    </div>
                  </a-table>
                </a-card>
              </a-col>
            </a-row>
          </div>
          <br>
        </a-tab-pane>
        <a-tab-pane
          forceRender
          key="2"
          tab="Verlauf"
        >
          <a-card>
            <a-timeline
              style="text-align: left; margin-left: 40px"
              v-if="patient.events.length"
            >
              <!-- List all the events recorded corresponding to the patient over time -->
              <a-timeline-item
                :color="timelineColor(event.eventType)"
                :key="event.id"
                v-for="event in this.patient.events"
              >
                {{ formatTimestamp(event.eventTimestamp) }},
                {{ eventTypes.find(type => type.id === event.eventType).label }}
              </a-timeline-item>
            </a-timeline>
          </a-card>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import moment from 'moment'
import Api from '@/api'
import { LabTest, Patient, Timestamp } from '@/api/SwaggerApi'
import { patientMapper } from '@/store/modules/patients.module'
import { EventTypeItem, eventTypes, testResults, TestResultType } from '@/models/event-types'
import { SYMPTOMS } from '@/models/symptoms'
import { PRE_ILLNESSES } from '@/models/pre-illnesses'
import { Column } from 'ant-design-vue/types/table/column'
import { TestTypeItem, testTypes } from '@/models/test-types'

const columnsTests: Partial<Column>[] = [
  {
    title: 'Test ID',
    dataIndex: 'testId',
    key: 'testId',
  }, {
    title: 'Test Typ',
    dataIndex: 'testType',
    key: 'testType',
    scopedSlots: {
      customRender: 'testType',
    },
  }, {
    title: 'Test Status',
    dataIndex: 'testStatus',
    key: 'testStatus',
    scopedSlots: {
      customRender: 'testStatus',
    },
  }, {
    title: 'Kommentar',
    dataIndex: 'comment',
    key: 'comment',
  },
]

interface State {
  patient: undefined | Patient;
  patientStatus: EventTypeItem | undefined;
  eventTypes: any[];
  symptoms: string[];
  preIllnesses: string[];
  dateOfBirth: string;
  gender: string;
  tests: LabTest[];
  columnsTests: Partial<Column>[];
  testResults: TestResultType[];
  testTypes: TestTypeItem[];
}

export default Vue.extend({
  name: 'PatientDetails',
  computed: {
    ...patientMapper.mapGetters({
      patientById: 'patientById',
    }),
  },

  async created() {
    this.loadData()
  },

  data(): State {
    return {
      patient: undefined,
      patientStatus: undefined,
      eventTypes: eventTypes,
      testResults: testResults,
      testTypes: testTypes,
      symptoms: [],
      preIllnesses: [],
      dateOfBirth: '',
      gender: '',
      tests: [],
      columnsTests,
    }
  },

  watch: {
    '$route.params.id'() {
      this.loadData()
    },
  },

  methods: {
    ...patientMapper.mapMutations({
      setPatient: 'setPatient',
    }),
    async loadData() {
      // Load Patient
      const patientId = this.$route.params.id
      this.patient = this.patientById(this.$route.params.id)
      if (!this.patient) {
        const patient = await Api.getPatientForIdUsingGet(patientId)
        this.setPatient(patient)
        this.patient = patient
      }

      // Map patient attributes to their display representation
      this.patientStatus = eventTypes.find(type => type.id === this.patient?.patientStatus)
      this.symptoms = this.patient.symptoms?.map(symptom => {
        const patientSymptom = SYMPTOMS.find(symptomFind => symptomFind.value === symptom)
        return patientSymptom ? patientSymptom.label : symptom
      }) || []
      this.preIllnesses = this.patient.preIllnesses?.map(preIllness => {
        const patientIllness = PRE_ILLNESSES.find(illness => illness.value === preIllness)
        return patientIllness ? patientIllness.label : preIllness
      }) || []
      this.dateOfBirth = moment(this.patient.dateOfBirth).format('DD.MM.YYYY')
      const patientGender = this.patient.gender || ''
      this.gender = patientGender === 'male' ? 'männlich' : patientGender === 'female' ? 'weiblich' : 'divers'

      // Tests
      this.tests = await Api.getLabTestForPatientUsingGet(patientId)
    },
    timelineColor(eventType: any) {
      switch (eventType) {
        case 'TEST_FINISHED_POSITIVE':
          return 'red'
        case 'TEST_FINISHED_NEGATIVE':
          return 'green'
        default:
          return 'grey'
      }
    },
    formatTimestamp(timestamp: Timestamp): string {
      const momentTimestamp = moment(timestamp)
      if (momentTimestamp.isValid()) {
        return moment(timestamp).format('DD.MM.YYYY HH:mm')
      } else {
        return 'Unbekannt'
      }
    },
  },
})
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  table {
    border-collapse: separate;
    border-spacing: 15px
  }
</style>
