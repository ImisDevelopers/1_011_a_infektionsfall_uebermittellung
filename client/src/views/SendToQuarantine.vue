<template>
  <div class="send-to-quarantine">
    <a-button class="download-all-button" type="primary" @click="downloadAll" icon="download" size="large">
      Alle Herunterladen
    </a-button>
    <h2 style="margin-top: 30px">Es wurden {{quarantinesByZip.length}} Patienten für eine Quarantäne vorgemerkt.</h2>
    <a-card style="max-width: 500px; margin: 2rem auto;"
            v-for="quarantinesByZip of quarantinesByZip"
            :title="'PLZ ' + quarantinesByZip.zip + (quarantinesByZip.cityName ? ' - ' + quarantinesByZip.cityName : '')"
            align="left"
            :key="quarantinesByZip.zip">

      <a-table :columns="columnsQuarantines"
               :dataSource="quarantinesByZip.quarantines"
               :rowKey="contact => contact.id"
               :pagination="false"
      >
        <template slot="until" slot-scope="until">
          {{ moment(until).format('DD.MM.YYYY') }}
        </template>
        <template slot="name" slot-scope="patient">
          <a @click="showPatient(patient.id)">{{ patient.firstName }} {{ patient.lastName }}</a>
        </template>
        <template slot="timestamp" slot-scope="timestamp">
          {{ moment(timestamp).format('DD.MM.YYYY HH:mm') }}
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<script lang="ts">
import Api from '@/api'
import Vue from 'vue'
import { QuarantineIncident } from '@/api/SwaggerApi'
import { Column } from 'ant-design-vue/types/table/column'
import moment from 'moment'
import { getPlzs } from '@/util/plz-service'
import { downloadCsv } from '@/util/export-service'

const columnsQuarantines = [
  {
    title: 'Quarantäne bis',
    key: 'until',
    scopedSlots: {
      customRender: 'until',
    },
  },
  {
    title: 'Name',
    key: 'name',
    dataIndex: 'patient',
    scopedSlots: {
      customRender: 'name',
    },
  },
  {
    title: 'Eingereicht um',
    key: 'timestmap',
    dataIndex: 'versionTimestamp',
    scopedSlots: {
      customRender: 'timestamp',
    },
  },
]

interface QuarantinesForZip {
  zip: string;
  quarantines: QuarantineIncident[];
  cityName: string;
}

interface State {
  quarantinesByZip: QuarantinesForZip[];
  columnsQuarantines: Partial<Column>[];
}

export default Vue.extend({
  name: 'SendToQuarantine',
  async created() {
    const quarantineIncidents = await Api.getSelectedForQuarantineUsingGet()
    const quarantinesByZip: QuarantinesForZip[] = []
    for (const quarantineIncident of quarantineIncidents) {
      const zip = quarantineIncident?.patient?.zip || 'Unbekannt'
      let byZip = quarantinesByZip.find(quarantine => quarantine.zip === zip)
      if (!byZip) {
        byZip = {
          zip: zip,
          quarantines: [],
          cityName: '',
        }
        quarantinesByZip.push(byZip)
      }
      byZip.quarantines.push(quarantineIncident)
    }
    this.quarantinesByZip = quarantinesByZip
    for (const quarantinesForZip of quarantinesByZip) {
      getPlzs(quarantinesForZip.zip).then(plzs => {
        if (plzs && plzs.length > 0) {
          quarantinesForZip.cityName = plzs[0].fields.note
        }
      })
    }
  },
  data(): State {
    return {
      quarantinesByZip: [],
      columnsQuarantines: columnsQuarantines,
    }
  },
  methods: {
    moment,
    showPatient(patientId: string) {
      this.$router.push({ name: 'patient-detail', params: { id: patientId } })
    },
    downloadAll() {
      const header = 'PLZ;Quarantäne bis;Vorname;Nachname;Adresse'
      let content = ''
      for (const quarantineIncidents of this.quarantinesByZip) {
        content += quarantineIncidents.quarantines.map(quarantine => {
          const patient = quarantine.patient
          if (patient) {
            const address = `${patient.street} ${patient.houseNumber} ${patient.zip} ${patient.city}`
            return `${quarantineIncidents.zip};${moment(quarantine.until).format('DD.MM.YYYY')};${patient?.firstName};${patient?.lastName};${address}`
          } else {
            console.warn('Quarantine without patient')
            return ''
          }
        }).join('\n') + '\n'
      }
      const filename = moment().format('YYYY_MM_DD') + '_quarantaene_anordnung.csv'
      downloadCsv(header + '\n' + content, filename)
    },
  },
})
</script>

<style lang="scss">
  .send-to-quarantine {
    .ant-card-body {
      padding: 0;
    }
  }
</style>

<style lang="scss" scoped>
  .download-all-button {
    position: absolute;
    top: 90px;
    right: 25px;
  }
</style>
