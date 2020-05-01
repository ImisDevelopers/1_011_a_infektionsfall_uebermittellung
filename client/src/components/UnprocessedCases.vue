<template>
  <a-card title="Unbearbeitete Fälle" :extra="count" align="left">
    <a-table
      :columns="columnsSchema"
      :customRow="customRow"
      :dataSource="unprocessedCases"
      :pagination="{ pageSize: 500 }"
      :scroll="{x: 0, y: 0}"
      @change="handleTableChange"
      class="imis-table-no-pagination"
      rowKey="id"
    >
      <div slot="patientStatus" slot-scope="patientStatus">
        <a-icon :type="eventTypes.find(type => type.id === patientStatus).icon" style="margin-right: 5px" />
        {{eventTypes.find(type => type.id === patientStatus).label}}
      </div>
      <div slot="operation" slot-scope="nothing, patient" style="cursor: pointer">
        <a-icon @click="() => handlePatientClick(patient)" style="margin-right: 5px; cursor: pointer" type="search" />
      </div>
    </a-table>
    <div style="display: flex; width: 100%; margin: 15px 0; justify-content: flex-end; align-items: center">
      <span style="margin-right: 10px">{{count}} Patienten</span>
      <a-pagination
        :pageSize.sync="pageSize"
        :total="count"
        @showSizeChange="onShowSizeChange"
        showSizeChanger
        v-model="currentPage"
      />
    </div>
  </a-card>
</template>

<script lang="ts">
import Vue from 'vue'
import { Patient, PatientSearchParamsDTO } from '@/api/SwaggerApi'
import Api from '@/api'
import { Column } from 'ant-design-vue/types/table/column'
import { EventTypeItem, eventTypes } from '@/models/event-types'
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
    dataIndex: 'lastName',
    key: 'lastName',
    sorter: true,
    defaultSortOrder: 'ascend',
  },
  {
    title: 'Vorname',
    dataIndex: 'firstName',
    key: 'firstName',
    sorter: true,
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
    title: 'Seit',
    dataIndex: 'lastEventTimestamp',
    key: 'lastEventTimestamp',
  },
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    sorter: true,
  },
]

interface State {
  unprocessedCases: PatientWithTimestamp[];
  currentPage: number;
  pageSize: number;
  columnsSchema: Partial<Column>[];
  order: string;
  orderBy: string;
  count: number;
  eventTypes: EventTypeItem[];
}

interface PatientWithTimestamp extends Patient {
  lastEventTimestamp: string;
}

export default Vue.extend({
  name: 'UnprocessedCases',
  data(): State {
    return {
      currentPage: 1,
      pageSize: 10,
      unprocessedCases: [],
      columnsSchema,
      eventTypes: eventTypes,
      order: 'asc',
      orderBy: 'lastName',
      count: 10,
    }
  },
  async created() {
    this.loadPage()
  },
  watch: {
    currentPage() {
      this.loadPage()
    },
  },
  methods: {
    onShowSizeChange(current: number, pageSize: number) {
      console.log('Page Size changed to ' + current + ' - ' + pageSize)
      this.currentPage = current
      this.pageSize = pageSize
      this.loadPage()
    },
    loadPage() {
      const request: PatientSearchParamsDTO = {
        offsetPage: this.currentPage - 1,
        order: this.order,
        orderBy: this.orderBy,
        pageSize: this.pageSize,
        patientStatus: 'SUSPECTED',
      }

      const countPromise = Api.countQueryPatientsUsingPost(request)
      const queryPromise = Api.queryPatientsUsingPost(request)

      countPromise.then(count => {
        this.count = count
      })
      queryPromise.then((result: Patient[]) => {
        this.unprocessedCases = result.map(patient => {
          let lastTimestamp: any
          if (patient.events && patient.events.length > 0) {
            lastTimestamp = moment(patient.events[patient.events.length - 1].eventTimestamp).format('DD.MM.YYYY')
          }
          return {
            ...patient,
            lastEventTimestamp: lastTimestamp,
          }
        })
      }).catch(error => {
        console.error(error)
        const notification = {
          message: 'Fehler beim Laden der Patientendaten.',
          description: error.message,
        }
        this.$notification.error(notification)
      })
    },
    handleTableChange(pagination: any, filters: any, sorter: any) {
      console.log('Table Changed to ' + this.currentPage)
      const sortKey = sorter.field ? sorter.field : 'lastName'
      let sortOrder = 'asc'
      if (sorter.order === 'descend') {
        sortOrder = 'desc'
      }
      this.order = sortOrder
      this.orderBy = sortKey
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

  .imis-table-no-pagination {
    .ant-table-pagination {
      display: none;
    }
  }

  .header {
    display: flex;
    align-items: center;
    background: white;
    margin-bottom: 24px;
    padding: 16px;
    border-radius: 4px;
    border: 1px solid #e8e8e8;

    .icon {
      font-size: 32px;
      margin: 0 15px;
    }

    .text {
      font-size: 22px;
    }
  }
</style>
