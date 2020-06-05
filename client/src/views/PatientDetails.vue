<template style="margin: auto;">
  <div>
    <ChangePatientStammdatenForm
      @cancel="
        () => {
          showChangePatientStammdatenForm = false
        }
      "
      @create="
        () => {
          showChangePatientStammdatenForm = false
          this.loadData()
        }
      "
      v-if="showChangePatientStammdatenForm"
      :visible="showChangePatientStammdatenForm"
      :patient="patient"
    />
    <ChangePatientFalldatenForm
      @cancel="
        () => {
          showChangePatientFalldatenForm = false
        }
      "
      @create="
        () => {
          showChangePatientFalldatenForm = false
          this.loadData()
        }
      "
      v-if="showChangePatientFalldatenForm"
      :visible="showChangePatientFalldatenForm"
      :patient="patient"
    />
    <div
      v-if="patient"
      style="max-width: 1020px; margin: 0 auto; padding: 0 1rem;"
    >
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
              <a-menu-item
                key="SEND_TO_QUARANTINE"
                v-if="permissions.sendToQuarantine"
              >
                <a-icon type="user" />
                Quarantäne vormerken
              </a-menu-item>
            </a-menu>
            <a-button style="margin-left: 8px;">
              Aktionen
              <a-icon type="down" />
            </a-button>
          </a-dropdown>
        </template>
      </a-page-header>

      <a-tabs
        defaultActiveKey="master-data"
        v-if="patient"
        style="text-align: left;"
      >
        <a-tab-pane key="master-data" tab="Stammdaten">
          <div class="tool-row">
            <a-button icon="edit" @click="editPatientStammdaten">
              Stammdaten ändern
            </a-button>
          </div>
          <!-- Allgemein & Adresse -->
          <a-row :gutter="8" type="flex">
            <a-col :md="12" :span="24">
              <a-card :extra="this.patient.id" align="left" title="Allgemein">
                <table>
                  <tr>
                    <td>Name:</td>
                    <td>{{ patient.lastName }}, {{ patient.firstName }}</td>
                  </tr>
                  <tr>
                    <td>Staatsangehörigkeit:</td>
                    <td>{{ patient.nationality }}</td>
                  </tr>
                  <tr>
                    <td>Geschlecht:</td>
                    <td>{{ gender }}</td>
                  </tr>
                  <tr>
                    <td>Geburtsdatum:</td>
                    <td>{{ dateOfBirth }}</td>
                  </tr>
                  <tr>
                    <td>Todesdatum:</td>
                    <td>{{ dateOfDeath }}</td>
                  </tr>
                </table>
              </a-card>
            </a-col>
            <a-col :md="12" :span="24">
              <a-card align="left" title="Adresse" style="height: 100%;">
                <table>
                  <tr v-if="patient.stayCity">
                    <td colspan="2">Wohnort:</td>
                  </tr>
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
                <table v-if="patient.stayCity" style="margin-top: 10px;">
                  <tr>
                    <td colspan="2">Aufenthaltsort:</td>
                  </tr>
                  <tr>
                    <td>Straße/Hausnr.:</td>
                    <td>
                      {{ patient.stayStreet }} {{ patient.stayHouseNumber }}
                    </td>
                  </tr>
                  <tr>
                    <td>PLZ/Ort:</td>
                    <td>{{ patient.stayZip }} {{ patient.stayCity }}</td>
                  </tr>
                  <tr>
                    <td>Land:</td>
                    <td>{{ patient.stayCountry }}</td>
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
                    <td>Telefon:</td>
                    <td>{{ patient.phoneNumber }}</td>
                  </tr>
                  <tr>
                    <td>Email:</td>
                    <td>
                      <a href="">{{ patient.email }}</a>
                    </td>
                  </tr>
                </table>
              </a-card>
            </a-col>
            <a-col :md="8" :span="24">
              <a-card align="left" title="Arbeit">
                <table>
                  <tr>
                    <td>Beruf:</td>
                    <td>{{ patient.occupation || 'Keine Angabe' }}</td>
                  </tr>
                  <tr>
                    <td>Arbeitgeber:</td>
                    <td>{{ patient.employer || 'Keine Angabe' }}</td>
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
          </a-row>
        </a-tab-pane>
        <a-tab-pane key="Cases" tab="Falldaten">
          <div class="tool-row">
            <div style="font-size: 18px; padding-left: 16px;" />
            <span style="flex: 1 1 auto;"></span>
            <a-button icon="edit" @click="editPatientFalldaten">
              Falldaten ändern
            </a-button>
          </div>
          <CaseData
            :allIncidents="incidents"
            :preIllnesses="preIllnesses"
            :patientInfectionSources="patientInfectionSources"
            :exposureContacts="exposureContacts"
          />
        </a-tab-pane>
        <a-tab-pane forceRender key="timeline" tab="Verlauf">
          <History :allIncidents="incidents" />
        </a-tab-pane>
        <a-tab-pane forceRender key="infection-chain" tab="Infektionskette">
          <a-row :gutter="8" style="margin-top: 8px;">
            <a-card align="left" title="Infektionsquellen">
              <a-descriptions
                layout="vertical"
                :column="{ xs: 1, sm: 1, md: 2, lg: 2 }"
              >
                <a-descriptions-item>
                  <span slot="label"
                    ><a-icon type="user" style="margin-right: 5px;" /> Kontakte
                    mit Indexpatienten
                    <span v-if="patientInfectionSources.length > 0"
                      >({{ patientInfectionSources.length }})</span
                    ></span
                  >
                  <div v-if="patientInfectionSources.length > 0">
                    <a-table
                      class="table-index-patients"
                      :columns="columnsIndexPatients"
                      :dataSource="patientInfectionSources"
                      :rowKey="(contact) => contact.id"
                      :showHeader="false"
                      :pagination="false"
                    >
                      <template slot="name" slot-scope="contact">
                        <a @click="showPatient(contact.source.id)"
                          >{{ contact.source.firstName }}
                          {{ contact.source.lastName }}</a
                        >,
                      </template>
                      <template slot="dateOfContact" slot-scope="contact">
                        am
                        {{
                          moment(contact.dateOfContact).format('DD.MM.YYYY')
                        }},
                      </template>
                    </a-table>
                  </div>
                  <div v-else>
                    Keine bekannt
                  </div>
                </a-descriptions-item>
                <a-descriptions-item label="Sonstige">
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
                  @click="addExposureContact"
                >
                  Hinzufügen
                </a-button>
              </div>

              <a-table
                :columns="columnsExposureContacts"
                :pagination="false"
                :dataSource="exposureContacts"
                class="imis-table-no-pagination"
                :rowKey="(contact) => contact.contact.id"
                :loading="exposureContactsLoading"
                :customRow="
                  (contact) => ({
                    on: { dblclick: () => showExposureContact(contact.id) },
                  })
                "
              >
                <template slot="gotoPatient" slot-scope="contact">
                  <a-button
                    ghost
                    icon="user"
                    type="primary"
                    title="Patientendaten anzeigen"
                    @click="showPatient(contact.contact.id)"
                  />
                </template>
                <template slot="dateOfContact" slot-scope="contact">
                  {{ moment(contact.dateOfContact).format('DD.MM.YYYY') }}
                </template>
                <template slot="infectionState" slot-scope="contact">
                  <span v-if="contact.contact.infected" style="color: red;">
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
                  <span
                    v-else
                    :style="`color: ${
                      contact.contact.infected ? 'red' : 'unset'
                    };`"
                  >
                    Keine Quarantäne
                  </span>
                </template>
                <template slot="actions" slot-scope="contact">
                  <div class="inline-buttons">
                    <a-button
                      ghost
                      icon="edit"
                      type="primary"
                      title="Bearbeiten"
                      @click="showExposureContact(contact.id)"
                    />
                    <a-button
                      ghost
                      icon="close"
                      type="danger"
                      title="Entfernen"
                      @click="removeExposureContact(contact.id)"
                    />
                  </div>
                </template>
              </a-table>
            </a-card>
          </a-row>
          <a-modal
            title="Kontaktperson bearbeiten"
            ref="exposureContactModal"
            :visible="!!exposureContactInEditing"
            @ok="persistExposureContact()"
            @cancel="exposureContactInEditing = null"
          >
            <a-form
              :form="exposureContactForm"
              :selfUpdate="true"
              layout="vertical"
            >
              <EditExposureContact
                :disableOriginatorPatient="true"
                :showOriginatorPatient="false"
                @showPatient="showPatient"
                :contactPatient="
                  exposureContactInEditing
                    ? exposureContactInEditing.contact
                    : null
                "
              />
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
import {
  ExposureContactFromServer,
  Patient,
  Timestamp,
  PatientLogDto,
} from '@/api/SwaggerApi'
import { authMapper } from '@/store/modules/auth.module'
import { patientMapper } from '@/store/modules/patients.module'
import {
  EventTypeItem,
  eventTypes,
  testResults,
  TestResultType,
} from '@/models/event-types'
import { SYMPTOMS } from '@/models/symptoms'
import { PRE_ILLNESSES } from '@/models/pre-illnesses'
import { Column } from 'ant-design-vue/types/table/column'
import { TestTypeItem, testTypes } from '@/models/test-types'
import CaseData from '@/components/other/CaseData.vue'
import History from '@/components/other/History.vue'
import ChangePatientStammdatenForm from '@/components/modals/ChangePatientStammdatenForm.vue'
import EditExposureContact from '@/components/form-groups/EditExposureContact.vue'
import { map } from '@/util/mapping'
import { Modal } from 'ant-design-vue'
import ChangePatientFalldatenForm from '@/components/modals/ChangePatientFalldatenForm.vue'
import { EXPOSURE_LOCATIONS, EXPOSURES_INTERNAL } from '@/models/exposures'

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
    sendToQuarantine: boolean
  }
  patient: undefined | Patient
  patientInfectionSources: ExposureContactFromServer[]
  exposureContacts: ExposureContactFromServer[]
  exposureContactsLoading: boolean
  exposureContactForm: any
  exposureContactInEditing: any
  patientStatus: EventTypeItem | undefined
  eventTypes: any[]
  symptoms: string[]
  exposures: string[]
  preIllnesses: string[]
  dateOfBirth: string
  dateOfDeath: string
  dateOfHospitalization: string
  showChangePatientStammdatenForm: boolean
  showChangePatientFalldatenForm: boolean
  gender: string
  columnsExposureContacts: Partial<Column>[]
  columnsIndexPatients: Partial<Column>[]
  testResults: TestResultType[]
  testTypes: TestTypeItem[]
  dateOfReporting: string
  dateOfIllness: string
  dateFormat: string
  incidents: PatientLogDto | undefined
}

export default Vue.extend({
  name: 'PatientDetails',
  components: {
    ChangePatientStammdatenForm,
    ChangePatientFalldatenForm,
    EditExposureContact,
    CaseData,
    History,
  },
  computed: {
    ...authMapper.mapGetters({
      myRoles: 'roles',
    }),
    ...patientMapper.mapGetters({
      patientById: 'patientById',
    }),
  },

  async created() {
    this.loadData()
    this.exposureContactForm = this.$form.createForm(
      this.$refs.exposureContactModal as Vue,
      {
        name: 'exposure-contact',
      }
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
      exposures: [],
      showChangePatientStammdatenForm: false,
      showChangePatientFalldatenForm: false,
      preIllnesses: [],
      dateOfBirth: '',
      dateOfDeath: '',
      dateOfHospitalization: '',
      gender: '',
      columnsExposureContacts,
      columnsIndexPatients,
      dateOfReporting: '',
      dateOfIllness: '',
      incidents: undefined,
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

      this.incidents = await Api.getPatientLogUsingGet(patientId)

      if (this.patient.dateOfIllness) {
        this.dateOfIllness = moment(this.patient.dateOfIllness).format(
          this.dateFormat
        )
      } else {
        this.dateOfIllness = this.dateOfReporting
      }
      if (this.patient.dateOfHospitalization) {
        this.dateOfHospitalization = moment(
          this.patient.dateOfHospitalization
        ).format(this.dateFormat)
      }

      // Map patient attributes to their display representation
      this.patientStatus = eventTypes.find(
        (type) => type.id === this.patient?.patientStatus
      )
      this.symptoms =
        this.patient.symptoms?.map((symptom) => {
          const patientSymptom = SYMPTOMS.find(
            (symptomFind) => symptomFind.value === symptom
          )
          return patientSymptom ? patientSymptom.label : symptom
        }) || []
      this.exposures =
        this.patient.riskAreas?.map((exposure) => {
          let patientExposure = EXPOSURES_INTERNAL.find(
            (exposureFind) => exposureFind.value === exposure
          )
          if (!patientExposure) {
            patientExposure = EXPOSURE_LOCATIONS.find(
              (exposureFind) =>
                'CONTACT_WITH_CORONA_' + exposureFind.value === exposure
            )
          }
          return patientExposure ? patientExposure.label : exposure
        }) || []
      this.preIllnesses =
        this.patient.preIllnesses?.map((preIllness) => {
          const patientIllness = PRE_ILLNESSES.find(
            (illness) => illness.value === preIllness
          )
          return patientIllness ? patientIllness.label : preIllness
        }) || []
      this.dateOfBirth = moment(this.patient.dateOfBirth).format(
        this.dateFormat
      )
      this.dateOfDeath = this.patient.dateOfDeath
        ? moment(this.patient.dateOfDeath).format(this.dateFormat)
        : '-'
      const patientGender = this.patient.gender || ''
      this.gender =
        patientGender === 'male'
          ? 'männlich'
          : patientGender === 'female'
          ? 'weiblich'
          : 'divers'

      // Source of Infection
      try {
        this.patientInfectionSources = await Api.getExposureSourceContactsForPatientUsingGet(
          patientId
        )
      } catch (e) {
        this.patientInfectionSources = []
      }

      // Retrieve exposure contacts data
      this.exposureContacts = await Api.getExposureContactsForPatientUsingGet(
        patientId
      )
      this.exposureContactsLoading = false
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
    editPatientFalldaten(): void {
      this.showChangePatientFalldatenForm = true
    },
    handleActionClick(e: { key: string }) {
      switch (e.key) {
        case 'SEND_TO_QUARANTINE':
          this.$router.push({
            name: 'request-quarantine',
            params: {
              patientId: this.patient?.id || '',
              patientFirstName: this.patient?.firstName || '',
              patientLastName: this.patient?.lastName || '',
            },
          })
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
        })
          .then(() => {
            this.$notification.success({
              message: 'Test angefordert',
              description: '',
            })
            this.loadData()
          })
          .catch(() => {
            this.$notification.error({
              message: 'Es ist ein Fehler aufgetreten',
              description: '',
            })
          })
      }
    },
    addExposureContact() {
      this.exposureContactInEditing = { contact: { id: undefined } }

      Vue.nextTick(() => {
        this.exposureContactForm.resetFields()
        this.exposureContactForm.setFieldsValue({
          source: this.patient,
        })
      })
    },
    showExposureContact(contactId: number) {
      const contact = this.exposureContacts.find(
        (contact: any) => contact.id === contactId
      )

      this.exposureContactInEditing = contact

      Vue.nextTick(() => {
        this.exposureContactForm.setFieldsValue(
          map(contact as { [x: string]: any }, {
            // source: patient => patient.id,
            // contact: patient => patient.id,
            contact: (contact) => contact.id,
            dateOfContact: moment,
          })
        )
      })
    },
    persistExposureContact() {
      const stringFromMoment = (value: Moment): string =>
        value.format('YYYY-MM-DD')

      this.exposureContactForm.validateFields().then(async (values: any) => {
        // Convert values to transport format
        values = map(values, {
          id: parseInt,
          dateOfContact: stringFromMoment,
        })

        // send initial patient data in contact field as string
        if (!values.contact) {
          values.contact = JSON.stringify({
            firstName: values.contactFirstName,
            lastName: values.contactLastName,
            gender: values.contactGender,
            dateOfBirth: values.contactDateOfBirth
              ? stringFromMoment(values.contactDateOfBirth)
              : undefined,
          })
        }

        if (values.id) {
          Object.assign(
            this.exposureContactInEditing,
            await Api.updateExposureContactUsingPut(values)
          )
        } else {
          this.exposureContacts.push(
            await Api.createExposureContactUsingPost(values)
          )
        }

        this.exposureContactInEditing = null
      })
    },
    async removeExposureContact(contactId: number) {
      await Api.removeExposureContactUsingDelete(contactId)
      this.exposureContacts = this.exposureContacts.filter(
        (contact) => contact.id !== contactId
      )
    },
    showPatient(patientId: string) {
      (this.$refs.exposureContactModal as Modal).$emit('cancel')
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
  border-bottom: 1px solid #e8e8e8;
}

.card-header-subtitle {
  font-size: 14px;
  font-weight: normal;
  color: rgba(0, 0, 0, 0.65);
}

.tool-row {
  display: flex;
  padding-bottom: 10px;
  align-items: center;
  justify-content: flex-end;
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
