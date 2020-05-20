<template>
  <div class="send-to-quarantine">
    <a-modal
      v-model="confirmVisible"
      title="Bitte bestätigen"
      ok-text="Ja"
      cancel-text="Abbrechen"
      @ok="updatePatients"
    >
      <p>
        Sollen die Quarantänen von {{ quarantinesByZip.length }} Patienten in
        den Status 'Quarantäne angeordnet' überführt werden?
      </p>
      <a-form :form="form">
        <a-form-item label="Datum der Anordnung (optional):">
          <DateInput
            :defaultValue="today"
            v-decorator="[
              'eventDate',
              {
                rules: [
                  {
                    required: false,
                    message: 'Datum der Anordnung',
                  },
                ],
              },
            ]"
          />
        </a-form-item>
      </a-form>
    </a-modal>
    <div style="display: flex; justify-content: flex-end;">
      <a-button
        class="download-all-button"
        type="primary"
        @click="downloadAll"
        icon="download"
        size="large"
      >
        Alle Herunterladen
      </a-button>
      <a-button
        class="clear-all-button"
        type="primary"
        @click="showModal"
        icon="play-circle"
        size="large"
      >
        Quarantäne anordnen
      </a-button>
    </div>
    <h2 style="margin-top: 30px; text-align: center;">
      Es wurden {{ quarantinesByZip.length }} Patienten für eine Quarantäne
      vorgemerkt.
    </h2>
    <a-card
      style="max-width: 500px; margin: 2rem auto;"
      v-for="quarantinesByZip of quarantinesByZip"
      :title="
        'PLZ ' +
        quarantinesByZip.zip +
        (quarantinesByZip.cityName ? ' - ' + quarantinesByZip.cityName : '')
      "
      align="left"
      :key="quarantinesByZip.zip"
    >
      <a-table
        :columns="columnsQuarantines"
        :dataSource="quarantinesByZip.quarantines"
        :rowKey="(contact) => contact.id"
        :pagination="false"
      >
        <template slot="until" slot-scope="until">
          {{ moment(until).format('DD.MM.YYYY') }}
        </template>
        <template slot="name" slot-scope="patient">
          <a @click="showPatient(patient.id)"
            >{{ patient.firstName }} {{ patient.lastName }}</a
          >
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
import DateInput from '../components/DateInput.vue'

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
  zip: string
  quarantines: QuarantineIncident[]
  cityName: string
}

interface State {
  quarantinesByZip: QuarantinesForZip[]
  columnsQuarantines: Partial<Column>[]
  confirmVisible: boolean // eslint-disable-next-line
  form: any;
  today: moment.Moment
}

export default Vue.extend({
  name: 'SendToQuarantine',
  components: {
    DateInput,
  },
  async created() {
    const quarantineIncidents = await Api.getSelectedForQuarantineUsingGet()
    const quarantinesByZip: QuarantinesForZip[] = []
    for (const quarantineIncident of quarantineIncidents) {
      const zip = quarantineIncident?.patient?.zip || 'Unbekannt'
      let byZip = quarantinesByZip.find((quarantine) => quarantine.zip === zip)
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
      getPlzs(quarantinesForZip.zip).then((plzs) => {
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
      confirmVisible: false,
      form: this.$form.createForm(this),
      today: moment(),
    }
  },
  methods: {
    moment,
    showPatient(patientId: string) {
      this.$router.push({ name: 'patient-detail', params: { id: patientId } })
    },
    downloadAll() {
      const header = 'PLZ;Quarantäne bis;Vorname;Nachname;Adresse;Geschlecht;Geburtsdatum;Quarantäne Kommentar;Aufenthaltsort'
      let content = ''
      for (const quarantineIncidents of this.quarantinesByZip) {
        content +=
          quarantineIncidents.quarantines
            .map((quarantine) => {
              const patient = quarantine.patient
              if (patient) {
                const address = `${patient.street} ${patient.houseNumber} ${patient.zip} ${patient.city}`
                let stayaddress = `${patient.stayStreet} ${patient.stayHouseNumber} ${patient.stayZip} ${patient.stayCity}`
                stayaddress = stayaddress === 'null null null null' ? '' : stayaddress
                const comment = quarantine.comment ? quarantine.comment : ''
                return `${quarantineIncidents.zip};${moment(
                  quarantine.until
                ).format('DD.MM.YYYY')};${patient?.firstName};${
                  patient?.lastName
                };${address};${patient.gender};${patient?.dateOfBirth};${comment};${stayaddress}`
              } else {
                console.warn('Quarantine without patient')
                return ''
              }
            })
            .join('\n') + '\n'
      }
      const filename =
        moment().format('YYYY_MM_DD') + '_quarantaene_anordnung.csv'
      downloadCsv(header + '\n' + content, filename)
    },
    updatePatients() {
      this.confirmVisible = false
      const patientIds: string[] = []
      for (const quarantinesByZip of this.quarantinesByZip) {
        patientIds.push(
          ...quarantinesByZip.quarantines.map(
            (quarantine) => quarantine.patient?.id || ''
          )
        )
      }
      const request = {
        patientIds: patientIds,
        eventDate: this.form.getFieldValue('eventDate')
          ? this.form.getFieldValue('eventDate').format('YYYY-MM-DD')
          : undefined,
      }
      Api.sendToQuarantineUsingPost(request)
        .then(() => {
          const h = this.$createElement
          this.$success({
            title: 'Quarantänen aktualisiert.',
            content: h('div', {}, [
              h(
                'div',
                `Die Quarantänen von ${patientIds.length} Patienten wurden aktualisiert.`
              ),
            ]),
          })
          this.quarantinesByZip = []
        })
        .catch((error: Error) => {
          const notification = {
            message: 'Fehler beim Aktualisieren der Quarantänen',
            description: error.message,
          }
          this.$notification.error(notification)
        })
    },
    showModal() {
      this.confirmVisible = true
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

.clear-all-button {
  position: absolute;
  top: 150px;
  right: 25px;
}

@media (max-width: 1300px) {
  .download-all-button {
    position: inherit;
    margin: 10px;
  }

  .clear-all-button {
    position: inherit;
    margin: 10px;
  }
}
</style>
