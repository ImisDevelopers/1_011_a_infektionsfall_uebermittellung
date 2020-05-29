<template>
  <div>
    <a-page-header
      title="Alle Patienten"
      style="padding-left: 0; padding-right: 0;"
    >
      <template slot="extra">
        <a-button
          @click="
            $router.push({
              name: 'register-patient',
            })
          "
        >
          Patient registrieren
        </a-button>
      </template>
    </a-page-header>

    <a-card class="table-container">
      <!-- TODO refactor search in own component -->
      <a-form :model="form" class="search-container">
        <a-form-item style="width: 600px; max-width: 100%;">
          <a-input
            :disabled="showAdvancedSearch"
            placeholder="Suche über ID, Name, Stadt, Email oder Telefon (z.B. 'Max Mustermann Berlin')"
            v-model="form.query"
          >
            <a-icon slot="prefix" type="search" />
          </a-input>
        </a-form-item>
        <a-button @click="toggleAdvancedSearch">
          <a-icon :type="showAdvancedSearch ? 'down' : 'right'" />
          Erweiterte Suche
        </a-button>
        <a-button
          @click="handleSearch"
          html-type="submit"
          icon="search"
          type="primary"
        >
          Suche
        </a-button>
      </a-form>
      <a-form
        :model="advancedForm"
        class="search-container"
        v-if="showAdvancedSearch"
      >
        <a-form-item label="Vorname">
          <a-input
            placeholder="Vorname"
            v-model="advancedForm.firstName"
            autocomplete="no"
          >
            <a-icon slot="prefix" type="user" />
          </a-input>
        </a-form-item>
        <a-form-item label="Nachname">
          <a-input
            placeholder="Nachname"
            v-model="advancedForm.lastName"
            autocomplete="no"
          >
            <a-icon slot="prefix" type="user" />
          </a-input>
        </a-form-item>
        <a-form-item label="ID">
          <a-input placeholder="ID" v-model="advancedForm.id" autocomplete="no">
            <a-icon slot="prefix" type="hdd" />
          </a-input>
        </a-form-item>
        <a-form-item label="Geschlecht">
          <a-select
            placeholder="Geschlecht"
            style="width: 120px;"
            v-model="advancedForm.gender"
          >
            <a-select-option value="">Alle</a-select-option>
            <a-select-option value="female">
              <a-icon style="margin-right: 5px;" type="woman" />
              Weiblich
            </a-select-option>
            <a-select-option value="male">
              <a-icon style="margin-right: 5px;" type="man" />
              Männlich
            </a-select-option>
            <a-select-option value="divers">Sonstiges</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="Stadt">
          <a-input
            placeholder="Stadt"
            v-model="advancedForm.city"
            autocomplete="no"
          >
            <a-icon slot="prefix" type="home" />
          </a-input>
        </a-form-item>
        <a-form-item label="Straße">
          <a-input
            placeholder="Straße"
            v-model="advancedForm.street"
            autocomplete="no"
          >
            <a-icon slot="prefix" type="home" />
          </a-input>
        </a-form-item>
        <a-form-item label="Hausnummer" style="width: 100px;">
          <a-input
            placeholder="Hausnr."
            v-model="advancedForm.houseNumber"
            autocomplete="no"
          >
            <a-icon slot="prefix" type="home" />
          </a-input>
        </a-form-item>
        <a-form-item label="Postleitzahl" style="width: 100px;">
          <a-input
            placeholder="PLZ"
            v-model="advancedForm.zip"
            autocomplete="no"
          >
            <a-icon slot="prefix" type="home" />
          </a-input>
        </a-form-item>
        <a-form-item label="Versicherung">
          <a-input
            placeholder="Versicherung"
            v-model="advancedForm.insuranceCompany"
            autocomplete="no"
          >
            <a-icon slot="prefix" type="hdd" />
          </a-input>
        </a-form-item>
        <a-form-item label="Quarantänestatus">
          <a-select
            placeholder="Quarantänestatus"
            style="width: 250px;"
            v-model="quarantineSelection"
          >
            <a-select-option value="ALL">Alle</a-select-option>
            <a-select-option key="QUARANTINE_MANDATED"
              >Quarantäne angeordnet
            </a-select-option>
            <a-select-option key="QUARANTINE_SELECTED"
              >Quarantäne vorgemerkt
            </a-select-option>
            <a-select-option key="NO_SELECTION"
              >Keine Anordnung
            </a-select-option>
          </a-select>
        </a-form-item>
        <!-- Invisible Button so user can use enter to search -->
        <a-button
          @click="handleSearch"
          html-type="submit"
          style="visibility: hidden;"
        />
      </a-form>
      <a-table
        :columns="columnsSchema"
        :customRow="customRow"
        :dataSource="currentPatients"
        :pagination="{ pageSize: 500 }"
        :scroll="{ x: 0, y: 0 }"
        @change="handleTableChange"
        class="imis-table-no-pagination"
        rowKey="id"
      >
        <div slot="gender" slot-scope="gender">
          {{
            gender === 'male'
              ? 'männlich'
              : gender === 'female'
              ? 'weiblich'
              : 'divers'
          }}
        </div>
        <div slot="patientStatus" slot-scope="patientStatus">
          <a-icon
            :type="eventTypes.find((type) => type.id === patientStatus).icon"
            style="margin-right: 5px;"
          />
          {{ eventTypes.find((type) => type.id === patientStatus).label }}
        </div>
        <div
          slot="operation"
          slot-scope="nothing, patient"
          style="cursor: pointer;"
        >
          <a-icon
            @click="() => handlePatientClick(patient)"
            style="margin-right: 5px; cursor: pointer;"
            type="import"
          />
        </div>
        <div slot="name" slot-scope="patient">
          {{ patient.lastName }}, {{ patient.firstName }}
        </div>
        <div slot="city" slot-scope="patient">
          {{ patient.zip }} {{ patient.city }}
        </div>
        <div slot="age" slot-scope="dateOfBirth">
          {{ moment().diff(moment(dateOfBirth), 'years') }}
        </div>
        <div slot="indexpatient" slot-scope="id">
          <index-patient-table-cell :patient-id="id"></index-patient-table-cell>
        </div>
      </a-table>
      <div
        style="
          display: flex;
          width: 100%;
          margin: 15px 0;
          justify-content: flex-end;
          align-items: center;
        "
      >
        <a-button
          @click="downloadPatients"
          style="margin-right: 50px;"
          type="primary"
          >CSV exportieren
        </a-button>
        <span style="margin-right: 10px;">{{ count }} Patienten</span>
        <a-pagination
          :pageSize.sync="form.pageSize"
          :total="count"
          @showSizeChange="onShowSizeChange"
          showSizeChanger
          v-model="currentPage"
        />
      </div>
    </a-card>
  </div>
</template>

<!--// TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu-->

<script lang="ts">
import { Column } from 'ant-design-vue/types/table/column'
import Vue from 'vue'
import {
  Patient,
  PatientSimpleSearchParamsDTO,
  PatientSearchParamsDTO,
} from '@/api/SwaggerApi'
import { eventTypes } from '@/models/event-types'
import { PatientStatus } from '@/models'
import { downloadCsv } from '@/util/export-service'
import Api from '@/api'
import moment from 'moment'
import IndexPatientTableCell from '@/components/other/IndexPatientTableCell.vue'

const columnsSchema: Partial<Column>[] = [
  {
    title: '',
    dataIndex: 'operation',
    scopedSlots: {
      customRender: ['operation'],
    },
  },
  {
    title: 'Name',
    key: 'lastName',
    scopedSlots: {
      customRender: ['name'],
    },
  },
  {
    title: 'Geschlecht',
    dataIndex: 'gender',
    key: 'gender',
    scopedSlots: {
      customRender: 'gender',
    },
  },
  {
    title: 'Status',
    dataIndex: 'patientStatus',
    key: 'patientStatus',
    scopedSlots: {
      customRender: 'patientStatus',
    },
    sorter: true,
  },
  {
    title: 'Stadt',
    key: 'city',
    scopedSlots: {
      customRender: 'city',
    },
  },
  {
    title: 'Alter',
    dataIndex: 'dateOfBirth',
    key: 'age',
    scopedSlots: {
      customRender: 'age',
    },
  },
  {
    title: 'Indexpatient',
    dataIndex: 'id',
    key: 'indexpatient',
    scopedSlots: {
      customRender: 'indexpatient',
    },
  },
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    defaultSortOrder: 'ascend',
  },
]

interface SimpleForm {
  query: string
  order: string
  orderBy: string
  offsetPage: number
  pageSize: number
}

interface State {
  form: SimpleForm
  advancedForm: PatientSearchParamsDTO
  quarantineSelection:
    | 'ALL'
    | 'QUARANTINE_MANDATED'
    | 'QUARANTINE_SELECTED'
    | 'NO_SELECTION'
  currentPatients: Patient[]

  [key: string]: any
}

export default Vue.extend({
  name: 'PatientList',
  components: {
    IndexPatientTableCell,
  },
  data(): State {
    return {
      form: {
        query: '',
        order: 'asc',
        orderBy: 'lastName',
        offsetPage: 0,
        pageSize: 10,
      },
      advancedForm: {
        gender: '',
        city: '',
        street: '',
        houseNumber: '',
        zip: '',
        insuranceCompany: '',
        firstName: '',
        lastName: '',
        quarantineStatus: undefined,
        id: '',
      },
      quarantineSelection: 'ALL',
      content: '',
      count: 10,
      currentPage: 1, // Starts at 1
      columnsSchema,
      data: [], // data
      showAdvancedSearch: false,
      eventTypes: eventTypes,
      currentPatients: [],
    }
  },
  watch: {
    currentPage() {
      this.loadPage()
    },
    '$route.query.query'() {
      this.loadAfterUrlChange()
    },
  },
  created() {
    this.indexPatientMap = new Map()
    this.loadAfterUrlChange()
  },
  methods: {
    loadAfterUrlChange() {
      const query = this.$route.query.query
      if (query) {
        this.form.query = query.toString()
      }
      this.loadPage()
    },
    handleSearch() {
      this.currentPage = 1
      this.loadPage()
    },
    onShowSizeChange(current: number, pageSize: number) {
      this.currentPage = current
      this.advancedForm.pageSize = pageSize
      this.loadPage()
    },
    loadPage() {
      this.form.offsetPage = this.currentPage - 1
      let countPromise
      let queryPromise
      if (this.showAdvancedSearch) {

        const formValues: PatientSearchParamsDTO = {
          ...this.form,
          ...this.advancedForm,
        }

        if (!formValues.patientStatus) {
          // Backend fails on empty string
          formValues.patientStatus = undefined
        }

        formValues.quarantineStatus = this.getQuarantineSelection() as Array<PatientStatus>

        countPromise = Api.countQueryPatientsUsingPost(formValues)
        queryPromise = Api.queryPatientsUsingPost(formValues)
      } else {
        const query = this.form.query
        countPromise = Api.countQueryPatientsSimpleUsingGet({ query })
        queryPromise = Api.queryPatientsSimpleUsingPost({ ...this.form })
      }

      countPromise.then((count) => {
        this.count = count
      })
      queryPromise
        .then((result: Patient[]) => {
          this.currentPatients = result
        })
        .catch((error) => {
          console.error(error)
          const notification = {
            message: 'Fehler beim Laden der Patientendaten.',
            description: error.message,
          }
          this.$notification.error(notification)
        })
    },
    toggleAdvancedSearch() {
      this.showAdvancedSearch = !this.showAdvancedSearch
    },
    downloadPatients() {
      this.form.offsetPage = this.currentPage - 1
      let countPromise
      let formValues: any
      if (this.showAdvancedSearch) {
        formValues = { ...this.form, ...this.advancedForm }
        if (!formValues.patientStatus) {
          // Backend fails on empty string
          delete formValues.patientStatus
        }
        formValues.quarantineStatus = this.getQuarantineSelection()
        countPromise = Api.countQueryPatientsUsingPost(formValues)
      } else {
        formValues = { ...this.form }
        const query = this.form.query
        countPromise = Api.countQueryPatientsSimpleUsingGet({ query })
      }

      countPromise.then((count) => {
        // Download all data that applies to the current filter
        formValues.offsetPage = 0
        formValues.pageSize = count

        let queryPromise: Promise<Patient[]>
        if (this.showAdvancedSearch) {
          queryPromise = Api.queryPatientsUsingPost(formValues)
        } else {
          queryPromise = Api.queryPatientsSimpleUsingPost(formValues)
        }

        queryPromise
          .then((result) => {
            const header =
              'ID;Vorname;Nachname;Geschlecht;Status;Geburtsdatum;E-Mail;Telefonnummer;' +
              'Straße;Hausnummer;PLZ;Stadt;Versicherung;Versichertennummer'
            const patients = result
              .map(
                (patient: Patient) =>
                  `${patient.id};${patient.firstName};${patient.lastName};${patient.gender};` +
                  `${patient.patientStatus};${patient.dateOfBirth};${patient.email};` +
                  `${patient.phoneNumber};${patient.street};${patient.houseNumber};` +
                  `${patient.zip};${patient.city};${patient.insuranceCompany};` +
                  `${patient.insuranceMembershipNumber}`
              )
              .join('\n')
            const filename =
              moment().format('YYYY_MM_DD') + '_patienten_export.csv'
            downloadCsv(header + '\n' + patients, filename)
          })
          .catch((error: Error) => {
            console.error(error)
            const notification = {
              message: 'Fehler beim Laden der Patientendaten.',
              description: error.message,
            }
            this.$notification.error(notification)
          })
      })
    },
    handleTableChange(pagination: any, filters: any, sorter: any) {
      const sortKey = sorter.field ? sorter.field : 'lastName'
      let sortOrder = 'asc'
      if (sorter.order === 'descend') {
        sortOrder = 'desc'
      }
      this.form.order = sortOrder
      this.form.orderBy = sortKey
      this.loadPage()
    },
    handlePatientClick(patient: Patient) {
      if (patient.id) {
        this.$router.push({
          name: 'patient-detail',
          params: { id: patient.id },
        })
      }
    },
    customRow(record: Patient) {
      return {
        on: {
          dblclick: () => this.handlePatientClick(record),
        },
      }
    },
    getQuarantineSelection(): PatientStatus[] {
      if (this.quarantineSelection === 'NO_SELECTION') {
        return ['QUARANTINE_RELEASED', 'QUARANTINE_PROFESSIONBAN_RELEASED']
      }

      if (this.quarantineSelection === 'QUARANTINE_SELECTED') {
        return ['QUARANTINE_SELECTED']
      }

      if (this.quarantineSelection === 'QUARANTINE_MANDATED') {
        return ['QUARANTINE_MANDATED']
      }

      return []
    },
    moment,
  },
})
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 20px 10px;
}

.table-container {
  width: 100%;
}

.search-container {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 15px;
  align-items: flex-end;
}

.search-container > * {
  margin: 0 10px;
  max-width: 250px;
}

.search-container > button {
  margin-bottom: 5px;
  margin-top: 5px;
}
</style>
