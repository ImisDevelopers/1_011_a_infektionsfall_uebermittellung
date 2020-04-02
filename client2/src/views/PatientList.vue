<template>
  <div>
    <a-card class="table-container">
      <a-input-search placeholder="Suchen" style="width: 100%; margin: 10px 0" />
      <a-table :columns="columns" :dataSource="patients" :scroll="{x: 1, y: 0}">
        <!--        <span slot="riskAreas" slot-scope="riskAreas">-->
        <!--          <a-tag v-for="riskArea in riskAreas" :key="riskArea">-->
        <!--            {{ riskArea }}-->
        <!--          </a-tag>-->
        <!--        </span>-->
        <!--        <span slot="preIllnesses" slot-scope="preIllnesses">-->
        <!--          <a-tag v-for="preIllness in preIllnesses" :key="preIllness">-->
        <!--            {{ preIllness }}-->
        <!--          </a-tag>-->
        <!--        </span>-->
      </a-table>
      <div
        style="display: flex; width: 100%; justify-content: flex-end; margin-bottom: 1rem;"
      >
        <a-button type="primary">CSV exportieren</a-button>
      </div>
    </a-card>
  </div>
</template>

<!--// TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu-->

<script>
import Vue from 'vue'
import Component from 'vue-class-component'

const columns = [
  {
    title: 'Nachname',
    sorter: (a, b) => a.lastName.localeCompare(b.lastName),
    dataIndex: 'lastName',
    key: 'lastName',
  },
  {
    title: 'Vorname',
    sorter: (a, b) => a.firstName.localeCompare(b.firstName),
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
    sorter: (a, b) => a.status.localeCompare(b.status),
    dataIndex: 'status',
    key: 'status',
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

@Component
export default class PatientList extends Vue {
  data () {
    return {
      content: '',
      columns,
    }
  }

  get patients () {
    return this.$store.state.patient.patients
  }

  created () {
    this.$store.dispatch('patient/fetchPatients')
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h3 {
    margin: 20px 10px;
  }

  span {
    margin: 10px;
  }

  .table-container {
    width: 100%;
    /* overflow-x: scroll;
    scroll-behavior: smooth; */
  }
</style>
