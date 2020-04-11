<template>
  <div>
    <a-card class="table-container">
      <!-- TODO refactor search in own component -->
      <a-form class="search-container" :model="form">
        <a-form-item style="width: 600px; max-width: 100%">
          <a-input v-model="form.query"
                   placeholder="Suche über ID, Name, Stadt, Email oder Telefon (z.B. 'Max Mustermann Berlin')"
                   :disabled="showAdvancedSearch">
            <a-icon slot="prefix" type="search" />
          </a-input>
        </a-form-item>
        <a-button @click="toggleAdvancedSearch">
          <a-icon :type="showAdvancedSearch ? 'down' : 'right'" />
          Erweiterte Suche
        </a-button>
        <a-button icon="search" type="primary" html-type="submit"
                  @click="handleSearch">
          Suche
        </a-button>
      </a-form>
      <a-form class="search-container" :model="advancedForm" v-if="showAdvancedSearch">
        <a-form-item label="Vorname">
          <a-input v-model="advancedForm.firstName" placeholder="Vorname">
            <a-icon slot="prefix" type="user" />
          </a-input>
        </a-form-item>
        <a-form-item label="Nachname">
          <a-input v-model="advancedForm.lastName" placeholder="Nachname">
            <a-icon slot="prefix" type="user" />
          </a-input>
        </a-form-item>
        <a-form-item label="Status">
          <a-select style="width: 250px" placeholder="Status" v-model="advancedForm.patientStatus">
            <a-select-option value="">Alle</a-select-option>
            <a-select-option v-for="eventType in eventTypes" :key="eventType.id">
              <a-icon :type="eventType.icon" style="margin-right: 5px" />
              {{eventType.label}}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="ID">
          <a-input v-model="advancedForm.id" placeholder="ID">
            <a-icon slot="prefix" type="hdd" />
          </a-input>
        </a-form-item>
        <a-form-item label="Geschlecht">
          <a-select v-model="advancedForm.gender" style="width: 120px" placeholder="Geschlecht">
            <a-select-option value="">Alle</a-select-option>
            <a-select-option value="weiblich">
              <a-icon type="woman" style="margin-right: 5px" />
              Weiblich
            </a-select-option>
            <a-select-option value="männlich">
              <a-icon type="man" style="margin-right: 5px" />
              Männlich
            </a-select-option>
            <a-select-option value="sonstiges">Sonstiges</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="Stadt">
          <a-input v-model="advancedForm.city" placeholder="Stadt">
            <a-icon slot="prefix" type="home" />
          </a-input>
        </a-form-item>
        <a-form-item label="E-Mail">
          <a-input v-model="advancedForm.email" placeholder="E-Mail">
            <a-icon slot="prefix" type="mail" />
          </a-input>
        </a-form-item>
        <a-form-item label="Telefonnummer">
          <a-input v-model="advancedForm.phoneNumber" placeholder="Telefonnummer">
            <a-icon slot="prefix" type="phone" />
          </a-input>
        </a-form-item>
        <a-form-item label="Straße">
          <a-input v-model="advancedForm.street" placeholder="Straße">
            <a-icon slot="prefix" type="home" />
          </a-input>
        </a-form-item>
        <a-form-item label="Hausnummer" style="width: 100px">
          <a-input v-model="advancedForm.houseNumber" placeholder="Hausnr.">
            <a-icon slot="prefix" type="home" />
          </a-input>
        </a-form-item>
        <a-form-item label="Postleitzahl" style="width: 100px">
          <a-input v-model="advancedForm.zip" placeholder="PLZ">
            <a-icon slot="prefix" type="home" />
          </a-input>
        </a-form-item>
        <a-form-item label="Versicherung">
          <a-input v-model="advancedForm.insuranceCompany" placeholder="Versicherung">
            <a-icon slot="prefix" type="hdd" />
          </a-input>
        </a-form-item>
        <a-form-item label="Versicherungsnummer">
          <a-input v-model="advancedForm.insuranceMembershipNumber" placeholder="Versicherungsnummer">
            <a-icon slot="prefix" type="idcard" />
          </a-input>
        </a-form-item>
        <a-form-item label="Arzt-ID">
          <a-input v-model="advancedForm.doctorId" placeholder="Arzt-ID">
            <a-icon slot="prefix" type="hdd" />
          </a-input>
        </a-form-item>
        <a-form-item label="Labor-ID">
          <a-input v-model="advancedForm.laboratoryId" placeholder="Labor-ID">
            <a-icon slot="prefix" type="hdd" />
          </a-input>
        </a-form-item>
        <!-- Invisible Button so user can use enter to search -->
        <a-button style="visibility: hidden" html-type="submit"
                  @click="handleSearch" />
      </a-form>
      <a-table
        class="imis-table-no-pagination"
        :columns="columnsSchema"
        :dataSource="actualPatients"
        :scroll="{x: 0, y: 0}"
        :pagination="{ pageSize: 500 }"
        rowKey="id"
        :customRow="customRow"
        @change="handleTableChange"
      >
        <div slot="patientStatus" slot-scope="patientStatus">
          <a-icon :type="eventTypes.find(type => type.id === patientStatus).icon" style="margin-right: 5px" />
          {{eventTypes.find(type => type.id === patientStatus).label}}
        </div>
        <div slot="operation" slot-scope="nothing, patient" style="cursor: pointer">
          <a-icon type="search" style="margin-right: 5px; cursor: pointer" @click="() => handlePatientClick(patient)" />
        </div>
      </a-table>
      <div style="display: flex; width: 100%; margin: 15px 0; justify-content: flex-end; align-items: center">
        <a-button type="primary" style="margin-right: 50px" @click="downloadPatients">CSV exportieren</a-button>
        <span style="margin-right: 10px">{{count}} Patienten</span>
        <a-pagination
          showSizeChanger
          :pageSize.sync="form.pageSize"
          @showSizeChange="onShowSizeChange"
          :total="count"
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
import { Patient, PatientSearchParamsDTO } from '@/api/SwaggerApi'
import { eventTypes } from '@/models/event-types'
import { downloadCsv } from '@/util/export-service'
import Api from '@/api'
import moment from 'moment'

const columnsSchema: Partial<Column>[] = [
  {
    title: '',
    dataIndex: 'operation',
    scopedSlots: {
      customRender: ['operation'],
    },
  },
  {
    title: 'Nachname',
    // sorter: (a, b) => a.lastName.localeCompare(b.lastName),
    dataIndex: 'lastName',
    key: 'lastName',
  },
  {
    title: 'Vorname',
    // sorter: (a, b) => a.firstName.localeCompare(b.firstName),
    dataIndex: 'firstName',
    key: 'firstName',
  },
  {
    title: 'Geschlecht',
    dataIndex: 'gender',
    key: 'gender',
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
    dataIndex: 'city',
    key: 'city',
  },
  {
    title: 'E-Mail',
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    defaultSortOrder: 'ascend',
  },
]

interface SimpleForm {
  query: string;
  order: string;
  orderBy: string;
  offsetPage: number;
  pageSize: number;
}

interface State {
  form: SimpleForm;
  advancedForm: Partial<PatientSearchParamsDTO>;

  [key: string]: any;
}

export default Vue.extend({
  name: 'PatientList',
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
        email: '',
        phoneNumber: '',
        street: '',
        houseNumber: '',
        zip: '',
        insuranceCompany: '',
        insuranceMembershipNumber: '',
        doctorId: '',
        laboratoryId: '',
        firstName: '',
        lastName: '',
        patientStatus: undefined,
        id: '',
      },
      content: '',
      count: 0,
      currentPage: 1, // Starts at 1
      columnsSchema,
      data: [], // data
      showAdvancedSearch: false,
      eventTypes: eventTypes,
      actualPatients: [],
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
        const formValues = { ...this.form, ...this.advancedForm }

        if (formValues.patientStatus) {
          // Backend fails on empty string
          formValues.patientStatus = undefined
        }

        countPromise = Api.api.countQueryPatientsUsingPost(formValues)
        queryPromise = Api.api.queryPatientsUsingPost(formValues)
      } else {
        const query = this.form.query
        countPromise = Api.api.countQueryPatientsSimpleUsingGet({ query })
        queryPromise = Api.api.queryPatientsSimpleUsingPost({ ...this.form })
      }

      countPromise.then(count => {
        this.count = count
      })
      queryPromise.then((result: Patient[]) => {
        this.actualPatients = result
      }).catch(error => {
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
        if (formValues.patientStatus) {
          // Backend fails on empty string
          formValues.patientStatus = undefined
        }
        countPromise = Api.api.countQueryPatientsUsingPost(formValues)
      } else {
        formValues = { ...this.form }
        const query = this.form.query
        countPromise = Api.api.countQueryPatientsSimpleUsingGet({ query })
      }

      countPromise.then(count => {
        // Download all data that applies to the current filter
        formValues.offsetPage = 0
        formValues.pageSize = count

        let queryPromise: Promise<Patient[]>
        if (this.showAdvancedSearch) {
          queryPromise = Api.api.queryPatientsUsingPost(formValues)
        } else {
          queryPromise = Api.api.queryPatientsSimpleUsingPost(formValues)
        }

        queryPromise.then(result => {
          const header = 'ID,Vorname,Nachname,Geschlecht,Status,Geburtsdatum,Stadt,E-Mail;Telefonnummer;' +
            'Straße;Hausnummer;Stadt;Versicherung;Versichertennummer;'
          const patients = result.map((patient: Patient) =>
            `${patient.id};${patient.firstName};${patient.lastName};${patient.gender};${patient.patientStatus};` +
            `${patient.dateOfBirth};${patient.city};${patient.email};${patient.phoneNumber};${patient.street};` +
            `${patient.houseNumber};${patient.city};${patient.insuranceCompany};${patient.insuranceMembershipNumber}`,
          ).join('\n')
          const filename = moment().format('YYYY_MM_DD') + '_patienten_export.csv'
          downloadCsv(header + '\n' + patients, filename)
        }).catch((error: Error) => {
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
        this.$router.push({ name: 'patient-detail', params: { id: patient.id } })
      }
    },
    customRow(record: Patient) {
      return {
        on: {
          dblclick: () => this.handlePatientClick(record),
        },
      }
    },
  },
})

</script>

<style lang="scss">
  .imis-table-no-pagination {
    .ant-table-pagination {
      display: none;
    }
  }
</style>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

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
