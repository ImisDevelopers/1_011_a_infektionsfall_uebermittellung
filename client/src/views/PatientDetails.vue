<template style="margin: auto">
  <div>
    <ChangePatientStammdatenForm
      @cancel="() => { showChangePatientStammdatenForm = false }"
      @create="() => { showChangePatientStammdatenForm = false; this.loadData() }"
      :visible="showChangePatientStammdatenForm"
      :patient="patient"
    />
    <div v-if="patient" style="max-width: 1020px; margin: 0 auto; padding: 0 1rem">

      <a-page-header
        :title="`${patient.lastName}, ${patient.firstName}`"
        :sub-title="patient.id"
        @back="() => $router.go(-1)"
        style="padding: 1rem 0;"
      >
        <template slot="tags">
          <a-tag v-if="patientStatus">
            {{ patientStatus.label }}
          </a-tag>
        </template>
        <template slot="extra">
              <a-dropdown>
                <a-menu slot="overlay" @click="handleActionClick">
                  <a-menu-item key="ARRANGE_TEST">
                    <a-icon type="user" />
                    Neuen Test anordnen
                  </a-menu-item>
                  <a-menu-item key="SEND_TO_QUARANTINE" v-if="permissions.sendToQuarantine">
                    <a-icon type="user" />
                    Quarantäne vormerken
                  </a-menu-item>
                </a-menu>
                <a-button style="margin-left: 8px"> Aktionen
                  <a-icon type="down" />
                </a-button>
              </a-dropdown>
        </template>
      </a-page-header>

      <a-tabs
        defaultActiveKey="master-data"
        v-if="patient"
        style="text-align: left"
      >
        <a-tab-pane
          key="master-data"
          tab="Stammdaten"
        >
          <div style="display: flex; justify-content: flex-end; padding-bottom: 10px">
            <a-button icon="edit" @click="editPatientStammdaten">
              Daten ändern
            </a-button>
          </div>
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
                      <td>Name:</td>
                      <td>{{patient.lastName}}, {{patient.firstName}}</td>
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
                      <td>Straße/Hausnr.:</td>
                      <td>{{patient.street}} {{patient.houseNumber}}</td>
                    </tr>
                    <tr>
                      <td>PLZ/Ort:</td>
                      <td>{{patient.zip}} {{patient.city}}</td>
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
        </a-tab-pane>

        <a-tab-pane
          key="overview"
          tab="Falldaten"
        >
            <!-- Tests -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col span="24">
                <div style="background: white; border: 1px solid #e8e8e8">
                  <div class="card-header">
                    <div>
                      Fall-Status: {{(patientStatus ? patientStatus.label : 'Unbekannt') + (patient.quarantineUntil ?
                      (', Quarantäne angeordnet bis ' + patient.quarantineUntil) : '')}}
                    </div>
                    <div class="card-header-subtitle">Erkrankungsdatum: {{dateOfIllness}}</div>
                    <div class="card-header-subtitle">Meldedatum: {{dateOfReporting}}</div>
                  </div>
                  <a-table
                    :columns="columnsTests"
                    :dataSource="tests"
                    :scroll="{x: 0, y: 0}"
                    class="imis-table-no-pagination"
                    rowKey="id"
                  >
                    <div slot="lastUpdate" slot-scope="lastUpdate">
                      {{getDate(lastUpdate)}}
                    </div>
                    <div slot="testStatus" slot-scope="testStatus">
                      <a-icon :type="testResults.find(type => type.id === testStatus).icon" style="margin-right: 5px" />
                      {{testResults.find(type => type.id === testStatus).label}}
                    </div>
                    <div slot="testType" slot-scope="testType">
                      <a-icon :type="testTypes.find(type => type.id === testType).icon" style="margin-right: 5px" />
                      {{testTypes.find(type => type.id === testType).label}}
                    </div>
                  </a-table>
                </div>
              </a-col>
            </a-row>

            <!-- Symptome und Risiken -->
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col
                :md="8"
                :span="24"
              >
                <a-card
                  align="left"
                  title="Infektionskette"
                >
                  <a-descriptions layout="vertical" :column="1">
                    <a-descriptions-item>
                      <span slot="label"><a-icon type="arrow-right"/><a-icon type="user" style="margin-right: 5px;"/> Kontakte mit Indexpatienten</span>
                      <span v-if="patientInfectionSources.length > 0">{{ patientInfectionSources.length }}</span>
                      <span v-else>Keine</span>
                      bekannt
                    </a-descriptions-item>
                    <a-descriptions-item>
                      <span slot="label"><a-icon type="user"/><a-icon type="arrow-right" style="margin-right: 5px;"/> Eigene Kontaktpersonen</span>
                      <span v-if="exposureContacts.length > 0">{{ exposureContacts.length }}</span>
                      <span v-else>Keine</span>
                      angegeben
                    </a-descriptions-item>
                  </a-descriptions>
                </a-card>
              </a-col>

              <a-col
                :md="8"
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
                :md="8"
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
              title="Infektionsquellen"
            >
              <a-descriptions layout="vertical" :column="{ xs: 1, sm: 1, md: 2, lg: 2 }">
                <a-descriptions-item>
                  <span slot="label"><a-icon type="user" style="margin-right: 5px;"/> Kontakte mit Indexpatienten <span v-if="patientInfectionSources.length > 0">({{ patientInfectionSources.length }})</span></span>
                  <div v-if="patientInfectionSources.length > 0">
                    <a-table class="table-index-patients"
                      :columns="columnsIndexPatients"
                      :dataSource="patientInfectionSources"
                      :rowKey="contact => contact.id"
                      :showHeader="false"
                      :pagination="false"
                    >
                      <template slot="name" slot-scope="contact">
                        <a @click="showPatient(contact.source.id)">{{ contact.source.firstName }} {{ contact.source.lastName }}</a>,
                      </template>
                      <template slot="dateOfContact" slot-scope="contact">
                        am {{ moment(contact.dateOfContact).format('DD.MM.YYYY') }},
                      </template>
                    </a-table>
                  </div>
                  <div v-else>
                    Keine bekannt
                  </div>
                </a-descriptions-item>
                <a-descriptions-item
                  label="Sonstige">
                  Keine bekannt
                </a-descriptions-item>
              </a-descriptions>
            </a-card>
          </a-row>

          <!-- exposed contacts -->
          <a-row :gutter="8" style="margin-top: 8px;">
            <a-card
              title="Kontaktpersonen"
              align="left"
              :bodyStyle="{ padding: 0 }"
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
                :pagination="false"
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
            <a-form :form="exposureContactForm" :selfUpdate="true" layout="vertical">
              <EditExposureContact
                :disableOriginatorPatient="true" :showOriginatorPatient="false" />
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
import * as permissions from '@/util/permissions'
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
    title: 'Aktualisiert',
    dataIndex: 'lastUpdate',
    key: 'lastUpdate',
    scopedSlots: {
      customRender: 'lastUpdate',
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
    title: 'Kontaktart',
    key: 'context',
    dataIndex: 'context',
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

const columnsIndexPatients = [
  {
    title: 'Name',
    key: 'name',
    scopedSlots: {
      customRender: 'name',
    },
  },
  {
    title: 'Kontaktdatum',
    key: 'dateOfContact',
    scopedSlots: {
      customRender: 'dateOfContact',
    },
  },
  {
    title: 'Kontaktart',
    key: 'context',
    dataIndex: 'context',
  },
]

interface State {
  permissions: {
    sendToQuarantine: boolean;
  };
  patient: undefined | Patient;
  patientInfectionSources: ExposureContactFromServer[];
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
  columnsIndexPatients: Partial<Column>[];
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
      permissions: {
        sendToQuarantine: false,
      },
      dateFormat: 'DD.MM.YYYY',
      patient: undefined,
      patientInfectionSources: [],
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
      columnsIndexPatients,
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
      this.exposureContactsLoading = true
      try {
        this.permissions = await permissions.checkAllowed({
          sendToQuarantine: Api.sendToQuarantineUsingPost,
        })
      } catch (err) {
        console.log(err)
      }

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

      // Source of Infection
      try {
        this.patientInfectionSources = await Api.getExposureSourceContactsForPatientUsingGet(patientId)
      } catch (e) {
        this.patientInfectionSources = []
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
    handleActionClick(e: { key: string }) {
      switch (e.key) {
        case 'SEND_TO_QUARANTINE':
          this.$router.push({ name: 'request-quarantine', params: { patientId: this.patient?.id || '' } })
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

          if (!values.contact) {
            values.contact = JSON.stringify({
              firstName: values.contactFirstName,
              lastName: values.contactLastName,
              dateOfBirth: stringFromMoment(values.contactDateOfBirth),
            })
          }

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

<style lang="scss">
  .table-index-patients {
    table {
      width: unset;
    }
    .ant-table-tbody > tr > td {
      border-bottom: none;
      padding: 4px;
    }
  }
  .ant-descriptions-item {
    vertical-align: top;
  }
</style>
