<template style="margin: auto">
  <div>
    <div style="max-width: 1020px; margin: 0 auto; padding: 0 1rem">
      <a-tabs
        defaultActiveKey="1"
        v-if="patient"
      >
        <a-tab-pane
          tab="Stammdaten"
          key="1"
        >
          <!-- display user data here-->
          <div>

            <!-- Allgemein & Adresse -->
            <a-row :gutter="8">
              <a-col
                :span="24"
                :md="12"
              >
                <a-card
                  title="Allgemein"
                  align="left"
                  :extra="this.patient.id"
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
                  </table>
                </a-card>
              </a-col>
              <a-col
                :span="24"
                :md="12"
              >
                <a-card
                  title="Adresse"
                  align="left"
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
                  </table>
                </a-card>
              </a-col>
            </a-row>

            <!-- Kontakt & Versicherung -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col :span="24" :md="12">
                <a-card
                  title="Kontakt"
                  align="left"
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
              <a-col :span="24" :md="12">
                <a-card
                  title="Versicherung"
                  align="left"
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
            </a-row>

            <!-- Symptome und Risiken -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col
                :span="24"
                :md="12"
              >
                <a-card
                  title="Vorerkrankungen und Risikofaktoren"
                  align="left"
                >
                  <div v-for="illness in preIllnesses" v-bind:key="illness">{{illness}}</div>
                </a-card>
              </a-col>
              <a-col
                :span="24"
                :md="12"
              >
                <a-card
                  title="Symptome"
                  align="left"
                  :extra="'Stand: ' + formatTimestamp(patient.creationTimestamp)"
                >
                  <div v-for="symptom in symptoms" v-bind:key="symptom">{{symptom}}</div>
                </a-card>
              </a-col>
            </a-row>

            <!-- Tests -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col span="24">
                <a-card
                  :title="'Status: ' + (patientStatus ? patientStatus.label : 'Unbekannt')"
                  align="left"
                >
                  <a-table
                    class="imis-table-no-pagination"
                    :columns="columnsTests"
                    :dataSource="tests"
                    :scroll="{x: 0, y: 0}"
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
                  <a-divider />
                  <a-row
                    type="flex"
                    justify="end"
                    :gutter="8"
                    style="margin-top: 8px;"
                  >
                    <a-col>
                      <a-button
                        type="primary"
                        @click="requestTestAgain"
                      >
                        Neuen Test anfordern
                      </a-button>
                    </a-col>
                  </a-row>
                </a-card>
              </a-col>
            </a-row>
          </div>
          <br>
        </a-tab-pane>
        <a-tab-pane
          tab="Verlauf"
          key="2"
          forceRender
        >
          <a-card>
            <a-timeline
              style="text-align: left; margin-left: 40px"
              v-if="patient.events.length"
            >
              <!-- List all the events recorded corresponding to the patient over time -->
              <a-timeline-item
                v-for="event in this.patient.events"
                :key="event.id"
                :color="timelineColor(event.eventType)"
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
    // Load Patient
    const patientId = this.$route.params.id
    this.patient = this.patientById(this.$route.params.id)
    if (!this.patient) {
      const patient = await Api.patients.getPatientForIdUsingGet(patientId)
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
    this.tests = await Api.labtests.getLabTestForPatientUsingGet(patientId)
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

  methods: {
    ...patientMapper.mapMutations({
      setPatient: 'setPatient',
    }),
    requestTestAgain() {
      // TODO: Test anfordern
      const notification = {
        message: 'Der Test wurde angefordert.',
        description: '',
      }
      this.$notification.success(notification)
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
<style scoped>
  table {
    border-collapse: separate;
    border-spacing: 15px
  }
</style>
