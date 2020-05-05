<template style="margin: auto">
  <div>
    <ChangePatientStammdatenForm
      @cancel="() => { showChangePatientStammdatenForm = false }"
      @create="() => { showChangePatientStammdatenForm = false; this.loadData() }"
      :visible="showChangePatientStammdatenForm"
      :patient="patient"
    />
    <div style="max-width: 1020px; margin: 0 auto; padding: 0 1rem">
      <a-tabs
        defaultActiveKey="overview"
        v-if="patient"
      >
        <a-tab-pane
          key="overview"
          tab="Stammdaten"
        >
          <div style="display: flex; justify-content: flex-end; padding-bottom: 10px">
            <div style="padding-right: 1rem">
              <a-dropdown>
                <a-menu slot="overlay" @click="handleActionClick">
                  <a-menu-item key="ARRANGE_TEST"><a-icon type="user" />Neuen Test anordnen</a-menu-item>
                  <a-menu-item key="SEND_TO_QUARANTINE"><a-icon type="user" />Patienten in Quarantäne schicken</a-menu-item>
  <!--                <a-menu-item key="HOSPITALIZATION"><a-icon type="user" />Krankenhaus einweisung</a-menu-item>-->
                </a-menu>
                <a-button style="margin-left: 8px" type="primary"> Aktionen <a-icon type="down" /> </a-button>
              </a-dropdown>
            </div>
            <a-button type="primary" icon="edit" @click="editPatientStammdaten">
              Stammdaten editieren
            </a-button>
          </div>
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
          key="timeline"
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
        <a-tab-pane
          forceRender
          key="infection-chain"
          tab="Infektionskette">

          <a-row :gutter="8" style="margin-top: 8px;">
            <a-card
              align="left"
              title="Infektionsquelle"
            >
              <table>
                <tr v-if="!!patientInfectionSource">
                  <td>
                    <a-icon type="user"/> Kontakt mit infizierter Person
                  </td>
                  <td>
                    <a @click="showPatient(patientInfectionSource.source.id)">
                      {{ patientInfectionSource.source.firstName }} {{ patientInfectionSource.source.lastName }},
                    </a>
                  </td>
                  <td>{{ moment(patientInfectionSource.dateOfContact).format('DD.MM.YYYY') }};</td>
                  <td>{{ patientInfectionSource.context }}</td>
                </tr>
                <tr v-else>
                  Unbekannt
                </tr>
              </table>
            </a-card>
          </a-row>

          <!-- exposed contacts -->
          <a-row :gutter="8" style="margin-top: 8px;">
            <a-card
              title="Kontaktpersonen"
              align="left"
            >
              <div slot="extra">
                <a-button
                  icon="plus"
                  type="primary"
                  @click="addExposureContact">
                  Hinzufügen
                </a-button>
              </div>

              <a-table
                :columns="columnsExposureContacts"
                :dataSource="exposureContacts"
                class="imis-table-no-pagination"
                :rowKey="contact => contact.contact.id"
                :loading="exposureContactsLoading"
                :customRow="contact => ({
                  on: { dblclick: () => showExposureContact(contact.id) }
                })">
                <template slot="gotoPatient" slot-scope="contact">
                  <a-button ghost
                    icon="user"
                    type="primary"
                    title="Patientendaten anzeigen"
                    @click="showPatient(contact.contact.id)"/>
                </template>
                <template slot="dateOfContact" slot-scope="contact">
                  {{ moment(contact.dateOfContact).format('DD.MM.YYYY') }}
                </template>
                <template slot="infectionState" slot-scope="contact">
                  <span v-if="contact.contact.infected"
                    style="color: red;">
                    Infiziert
                  </span>
                  <span v-else>
                    Unbekannt
                  </span>
                </template>
                <template slot="quarantineState" slot-scope="contact">
                  <span v-if="contact.contact.inQuarantine">
                    In Quarantäne
                  </span>
                  <span v-else
                    :style="`color: ${contact.contact.infected ? 'red' : 'unset'};`">
                    Keine Quarantäne
                  </span>
                </template>
                <template slot="actions" slot-scope="contact">
                  <div class="inline-buttons">
                    <a-button ghost
                      icon="edit"
                      type="primary"
                      title="Bearbeiten"
                      @click="showExposureContact(contact.id)"/>
                    <a-button ghost
                      icon="close"
                      type="danger"
                      title="Entfernen"
                      @click="removeExposureContact(contact.id)"/>
                  </div>
                </template>
              </a-table>
            </a-card>
          </a-row>
          <a-modal title="Kontaktperson bearbeiten"
            ref="exposureContactModal"
            :visible="!!exposureContactInEditing"
            @ok="persistExposureContact()
              .then((success) => { if (success) exposureContactInEditing = null })"
            @cancel="exposureContactInEditing = null">
            <a-form :form="exposureContactForm" :selfUpdate="true">
              <EditExposureContact
                :disableOriginatorPatient="true"/>
            </a-form>
          </a-modal>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import moment, { Moment } from 'moment'
import Api from '@/api'
import { LabTest, Patient, Timestamp, ExposureContactFromServer } from '@/api/SwaggerApi'
import { patientMapper } from '@/store/modules/patients.module'
import { EventTypeItem, eventTypes, testResults, TestResultType } from '@/models/event-types'
import { SYMPTOMS } from '@/models/symptoms'
import { PRE_ILLNESSES } from '@/models/pre-illnesses'
import { Column } from 'ant-design-vue/types/table/column'
import { TestTypeItem, testTypes } from '@/models/test-types'
import ChangePatientStammdatenForm from '@/components/ChangePatientStammdatenForm.vue'
import EditExposureContact from '@/components/EditExposureContact.vue'
import { map } from '@/util/mapping'

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

const columnsExposureContacts: Partial<Column>[] = [
  {
    key: 'gotoPatient',
    width: '40pt',
    align: 'center',
    scopedSlots: {
      customRender: 'gotoPatient',
    },
  },
  {
    title: 'Nachname',
    key: 'lastName',
    dataIndex: 'contact.lastName',
  },
  {
    title: 'Vorname',
    key: 'firstName',
    dataIndex: 'contact.firstName',
  },
  {
    title: 'Datum des Kontakts',
    key: 'dateOfContact',
    scopedSlots: {
      customRender: 'dateOfContact',
    },
  },
  {
    title: 'Infektionsstatus',
    key: 'infected',
    scopedSlots: {
      customRender: 'infectionState',
    },
  },
  {
    title: 'Quarantänestatus',
    key: 'quarantine',
    scopedSlots: {
      customRender: 'quarantineState',
    },
  },
  {
    title: 'Aktionen',
    key: 'actions',
    width: '120pt',
    align: 'center',
    scopedSlots: {
      customRender: 'actions',
    },
  },
]

interface State {
  patient: undefined | Patient;
  patientInfectionSource: undefined | ExposureContactFromServer;
  exposureContacts: ExposureContactFromServer[];
  exposureContactsLoading: boolean;
  exposureContactForm: any;
  exposureContactInEditing: any;
  patientStatus: EventTypeItem | undefined;
  eventTypes: any[];
  symptoms: string[];
  preIllnesses: string[];
  dateOfBirth: string;
  showChangePatientStammdatenForm: boolean;
  gender: string;
  tests: LabTest[];
  columnsTests: Partial<Column>[];
  columnsExposureContacts: Partial<Column>[];
  testResults: TestResultType[];
  testTypes: TestTypeItem[];
}

export default Vue.extend({
  name: 'PatientDetails',
  components: {
    ChangePatientStammdatenForm,
    EditExposureContact,
  },
  computed: {
    ...patientMapper.mapGetters({
      patientById: 'patientById',
    }),
  },

  async created() {
    this.loadData()
    this.exposureContactForm = this.$form.createForm(
      this.$refs.exposureContactModal as Vue, {
        name: 'exposure-contact',
      },
    )
  },

  data(): State {
    return {
      patient: undefined,
      patientInfectionSource: undefined,
      exposureContacts: [],
      exposureContactsLoading: false,
      exposureContactForm: undefined,
      exposureContactInEditing: null,
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
      columnsExposureContacts,
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
      this.exposureContactsLoading = true

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

      // Source of Infection
      try {
        this.patientInfectionSource = await Api.getExposureSourceContactForPatientUsingGet(patientId)
      } catch (e) {
        this.patientInfectionSource = undefined
      }

      // Tests
      this.tests = await Api.getLabTestForPatientUsingGet(patientId)

      // Retrieve exposure contacts data
      this.exposureContacts = await Api.getExposureContactsForPatientUsingGet(patientId)
      this.exposureContactsLoading = false
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
    handleActionClick(e: { key: string}) {
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
    addExposureContact() {
      const patientId = this.patient?.id

      this.exposureContactInEditing = {}

      Vue.nextTick(() => {
        this.exposureContactForm.resetFields()
        this.exposureContactForm.setFieldsValue({
          source: this.patient,
        })
      })
    },
    showExposureContact(contactId: number) {
      const contact = this.exposureContacts.find((contact: any) => contact.id === contactId)

      this.exposureContactInEditing = contact

      Vue.nextTick(() => {
        this.exposureContactForm.setFieldsValue(map(contact as {[x: string]: any}, {
          // source: patient => patient.id,
          // contact: patient => patient.id,
          dateOfContact: moment,
        }))
      })
    },
    persistExposureContact(): Promise<boolean> {
      const stringFromMoment = (value: Moment): string => value.format('YYYY-MM-DD')

      return new Promise((resolve: (success: boolean) => void) => {
        this.exposureContactForm.validateFields(async(err: Error[], values: {[x: string]: any}) => {
          if (err) {
            resolve(false)
            return
          }

          // Convert values to transport format
          values = map(values, {
            id: parseInt,
            dateOfContact: stringFromMoment,
          })

          if (values.id) {
            Object.assign(this.exposureContactInEditing, await Api.updateExposureContactUsingPut(values))
          } else {
            this.exposureContacts.push(await Api.createExposureContactUsingPost(values))
          }

          resolve(true)
        })
      })
    },
    async removeExposureContact(contactId: number) {
      await Api.removeExposureContactUsingDelete(contactId)
      this.exposureContacts = this.exposureContacts.filter(contact => contact.id !== contactId)
    },
    showPatient(patientId: string) {
      this.$router.push({ name: 'patient-detail', params: { id: patientId } })
    },
    moment,
  },
})
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
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

  .inline-buttons > button {
    margin-left: 2px;
    margin-right: 2px;
  }
</style>
