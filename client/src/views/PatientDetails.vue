<template style="margin: auto;">
  <div>
    <ChangePatientStammdatenForm
      @cancel="
        () => {
          showChangePatientStammdatenForm = false;
        }
      "
      @create="
        () => {
          showChangePatientStammdatenForm = false;
          this.loadData();
        }
      "
      :visible="showChangePatientStammdatenForm"
      :patient="patient"
    />
    <div style="max-width: 1020px; margin: 0 auto; padding: 1rem 1rem; text-align: left;">
      <a-page-header
        :title="`${patient.lastName}, ${patient.firstName}`"
        :sub-title="patient.id"
        @back="() => $router.go(-1)"
        style="padding: 1rem 0;"
      >
        <template slot="tags">
          <a-tag :color="patientStatus.color" v-if="patientStatus">
            {{ patientStatus.label }}
          </a-tag>
        </template>
        <template slot="extra">
          <a-dropdown>
            <a-menu slot="overlay" @click="handleActionClick">
              <a-menu-item key="ARRANGE_TEST"> <a-icon type="user" />Neuen Test anordnen </a-menu-item>
              <a-menu-item key="SEND_TO_QUARANTINE">
                <a-icon type="user" />Patienten in Quarantäne schicken
              </a-menu-item>
              <!--                <a-menu-item key="HOSPITALIZATION"><a-icon type="user" />Krankenhaus einweisung</a-menu-item>-->
            </a-menu>
            <a-button style="margin-left: 8px;">
              Aktionen
              <a-icon type="down" />
            </a-button>
          </a-dropdown>
        </template>
      </a-page-header>

      <a-tabs defaultActiveKey="1" v-if="patient">
        <a-tab-pane key="1" tab="Stammdaten">
          <div>
            <a-row type="flex" justify="end" style="margin-bottom: 1rem;">
              <a-col>
                <a-button icon="edit" @click="editPatientStammdaten">Daten ändern</a-button>
              </a-col>
            </a-row>
            <a-row :gutter="8">
              <a-col :md="12" :span="24">
                <a-card :extra="patient.id" align="left" title="Allgemein">
                  <table>
                    <tr>
                      <td>Geburtsdatum:</td>
                      <td>{{ dateOfBirth }}</td>
                    </tr>
                    <tr>
                      <td>Geschlecht:</td>
                      <td>{{ gender }}</td>
                    </tr>
                    <tr>
                      <td>Staatsangehörigkeit:</td>
                      <td>{{ patient.nationality }}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
              <a-col :md="12" :span="24">
                <a-card align="left" title="Adresse">
                  <table>
                    <tr>
                      <td>Straße/Hausnr.:</td>
                      <td>{{ patient.street }} {{ patient.houseNumber }}</td>
                    </tr>
                    <tr>
                      <td>PLZ/Ort:</td>
                      <td>{{ patient.zip }} {{ patient.city }}</td>
                    </tr>
                    <tr>
                      <td>Land:</td>
                      <td>{{ patient.country }}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
            </a-row>

            <!-- Kontakt & Versicherung & Arbeitgeber -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col :md="8" :span="24">
                <a-card align="left" title="Kontakt">
                  <table>
                    <tr>
                      <td>Telefonnummer:</td>
                      <td>{{ patient.phoneNumber }}</td>
                    </tr>
                    <tr>
                      <td>Email:</td>
                      <td>
                        <a :href="`mailto:${patient.email}`">{{ patient.email }}</a>
                      </td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
              <a-col :md="8" :span="24">
                <a-card align="left" title="Versicherung">
                  <table>
                    <tr>
                      <td>Versicherung:</td>
                      <td>{{ patient.insuranceCompany }}</td>
                    </tr>
                    <tr>
                      <td>V-Nr:</td>
                      <td>{{ patient.insuranceMembershipNumber }}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
              <a-col :md="8" :span="24">
                <a-card align="left" title="Arbeit">
                  <table>
                    <tr>
                      <td>Beruf:</td>
                      <td>{{ patient.occupation || "Keine Angabe" }}</td>
                    </tr>
                    <tr>
                      <td>Arbeitgeber:</td>
                      <td>{{ patient.employer || "Keine Angabe" }}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
            </a-row>
          </div>
        </a-tab-pane>
        <a-tab-pane key="2" tab="Falldaten">
          <!-- display user data here-->
          <div>
            <!-- Tests -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col span="24">
                <div style="background: white; border: 1px solid #e8e8e8;">
                  <div class="card-header">
                    <div>
                      Fall-Status: {{ patientStatus ? patientStatus.label : 'Unbekannt' }}
                      {{patient.quarantineUntil ? ", Quarantäne angeordnet bis " + patient.quarantineUntil : "" }}
                    </div>
                    <div class="card-header-subtitle">Erkrankungsdatum: {{ dateOfIllness }}</div>
                    <div class="card-header-subtitle">Meldedatum: {{ dateOfReporting }}</div>
                  </div>
                  <a-table
                    :columns="columnsTests"
                    :dataSource="tests"
                    :scroll="{ x: 0, y: 0 }"
                    class="imis-table-no-pagination"
                    rowKey="id"
                  >
                    <div slot="lastUpdate" slot-scope="lastUpdate">{{ getDate(lastUpdate) }}</div>
                    <div slot="testStatus" slot-scope="testStatus">
                      <a-icon
                        :type="testResults.find((type) => type.id === testStatus).icon"
                        style="margin-right: 5px;"
                      />
                      {{ testResults.find((type) => type.id === testStatus).label }}
                    </div>
                    <div slot="testType" slot-scope="testType">
                      <a-icon :type="testTypes.find((type) => type.id === testType).icon" style="margin-right: 5px;" />
                      {{ testTypes.find((type) => type.id === testType).label }}
                    </div>
                  </a-table>
                </div>
              </a-col>
            </a-row>

            <!-- Symptome und Risiken -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col :md="12" :span="24">
                <a-card align="left" title="Vorerkrankungen und Risikofaktoren">
                  <div v-bind:key="illness" v-for="illness in preIllnesses">{{ illness }}</div>
                </a-card>
              </a-col>
              <a-col :md="12" :span="24">
                <a-card :extra="'Stand: ' + formatTimestamp(patient.creationTimestamp)" align="left" title="Symptome">
                  <div v-bind:key="symptom" v-for="symptom in symptoms">{{ symptom }}</div>
                </a-card>
              </a-col>
            </a-row>
          </div>
          <br />
        </a-tab-pane>
        <a-tab-pane forceRender key="3" tab="Verlauf">
          <a-card>
            <a-timeline style="text-align: left; margin-left: 40px;" v-if="patient.events.length">
              <!-- List all the events recorded corresponding to the patient over time -->
              <a-timeline-item
                :color="timelineColor(event.eventType)"
                :key="event.id"
                v-for="event in this.patient.events"
              >
                {{ formatTimestamp(event.eventTimestamp) }},
                {{ eventTypes.find((type) => type.id === event.eventType).label }}
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
import ChangePatientStammdatenForm from '@/components/ChangePatientStammdatenForm.vue'

const columnsTests: Partial<Column>[] = [
  {
    title: 'Test ID',
    dataIndex: 'testId',
    key: 'testId',
  },
  {
    title: 'Test Typ',
    dataIndex: 'testType',
    key: 'testType',
    scopedSlots: {
      customRender: 'testType',
    },
  },
  {
    title: 'Test Status',
    dataIndex: 'testStatus',
    key: 'testStatus',
    scopedSlots: {
      customRender: 'testStatus',
    },
  },
  {
    title: 'Aktualisiert',
    dataIndex: 'lastUpdate',
    key: 'lastUpdate',
    scopedSlots: {
      customRender: 'lastUpdate',
    },
  },
  {
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
  showChangePatientStammdatenForm: boolean;
  gender: string;
  tests: LabTest[];
  columnsTests: Partial<Column>[];
  testResults: TestResultType[];
  testTypes: TestTypeItem[];
  dateOfReporting: string;
  dateOfIllness: string;
  dateFormat: string;
}

export default Vue.extend({
  name: 'PatientDetails',
  components: {
    ChangePatientStammdatenForm,
  },
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
      dateFormat: 'DD.MM.YYYY',
      patient: undefined,
      patientStatus: undefined,
      eventTypes: eventTypes,
      testResults: testResults,
      testTypes: testTypes,
      symptoms: [],
      showChangePatientStammdatenForm: false,
      preIllnesses: [],
      dateOfBirth: '',
      gender: '',
      tests: [],
      columnsTests,
      dateOfReporting: '',
      dateOfIllness: '',
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

      if (this.patient.events) {
        const event = this.patient.events.find(event => event.eventType === 'REGISTERED' || event.eventType === 'SUSPECTED')
        if (event) {
          this.dateOfReporting = moment(event.eventTimestamp).format(this.dateFormat)
        }
      }

      if (this.patient.dateOfIllness) {
        this.dateOfIllness = moment(this.patient.dateOfIllness).format(this.dateFormat)
      } else {
        this.dateOfIllness = this.dateOfReporting
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
      this.dateOfBirth = moment(this.patient.dateOfBirth).format(this.dateFormat)
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
    editPatientStammdaten(): void {
      this.showChangePatientStammdatenForm = true
    },
    handleActionClick(e: { key: string }) {
      switch (e.key) {
        case 'SEND_TO_QUARANTINE':
          this.$router.push({ name: 'send-to-quarantine', params: { patientId: this.patient?.id || '' } })
          break
        case 'ARRANGE_TEST':
          this.scheduleTest()
          break
      }
    },
    scheduleTest() {
      if (this.patient) {
        Api.createOrderTestEventUsingPost({
          patientId: this.patient.id,
        }).then(() => {
          this.$notification.success({
            message: 'Test angefordert',
            description: '',
          })
          this.loadData()
        }).catch(() => {
          this.$notification.error({
            message: 'Es ist ein Fehler aufgetreten',
            description: '',
          })
        })
      }
    },
    getDate(date: string) {
      return moment(date).format(this.dateFormat)
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

  .card-header {
    padding: 16px 24px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: rgba(0, 0, 0, 0.85);
    font-weight: 500;
    font-size: 16px;
    border-bottom: 1px solid #e8e8e8
  }

  .card-header-subtitle {
    font-size: 14px;
    font-weight: normal;
    color: rgba(0, 0, 0, 0.65);
  }
</style>
