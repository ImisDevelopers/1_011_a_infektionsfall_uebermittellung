<template>
  <div style="max-width: 1000px; margin: 0 auto;">
    <a-page-header
      title="In Quarantäne senden"
      sub-title=""
      style="padding-left: 0; padding-right: 0;"
    >
      <template slot="extra">
        <a-button
          class="download-all-button"
          @click="downloadAll"
          icon="download"
        >
          Alle Herunterladen
        </a-button>
        <a-button
          class="clear-all-button"
          @click="showModal"
          type="primary"
          icon="play-circle"
        >
          Quarantäne anordnen
        </a-button>
      </template>

      <a-row type="flex" v-if="quarantinesByZip.length > 0">
        <a-statistic
          title="Vorgemerkt für Quarantäne"
          suffix="Personen"
          :value="quarantinesByZip.length"
        />
      </a-row>

      <div v-else style="background: white; padding: 20px;">
        <a-empty
          description="Aktuell wurden keine Patienten für Quarantäne vorgemerkt."
        />
      </div>
    </a-page-header>

    <a-card
      style="max-width: 500px; margin: 2rem auto;"
      :bodyStyle="{ padding: 0 }"
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
            v-decorator="[
              'eventDate',
              {
                rules: [
                  {
                    required: false,
                    message: 'Datum der Anordnung',
                  },
                ],
                initialValue: today,
              },
            ]"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script lang="ts">
import Api from '@/api'
import Vue from 'vue'
import { QuarantineIncident, ExposureContactFromServer } from '@/api/SwaggerApi'
import { Column } from 'ant-design-vue/types/table/column'
import moment from 'moment'
import { getPlzs } from '@/util/plz-service'
import { downloadCsv } from '@/util/export-service'
import DateInput from '@/components/inputs/DateInput.vue'
import { testResults } from '@/models/event-types'

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
  form: any
  today: moment.Moment
  patientInfectionSources: Map<string, ExposureContactFromServer[]> // Patient-ID - ExposureContactFromServer list
  patientTestResults: Map<string, string[]> // Patient-ID - Test result list
  quarantineIncidents: QuarantineIncident[]
}

export default Vue.extend({
  name: 'SendToQuarantine',
  components: {
    DateInput,
  },
  data(): State {
    return {
      quarantinesByZip: [],
      columnsQuarantines: columnsQuarantines,
      confirmVisible: false,
      form: this.$form.createForm(this),
      today: moment(),
      patientInfectionSources: new Map<string, ExposureContactFromServer[]>(),
      patientTestResults: new Map<string, string[]>(),
      quarantineIncidents: [],
    }
  },
  async created() {
    this.quarantineIncidents = await Api.getSelectedForQuarantineUsingGet()
    const quarantinesByZip: QuarantinesForZip[] = []
    for (const quarantineIncident of this.quarantineIncidents) {
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
    // Involved Patients
    const patientIDs: string[] = []
    for (const quarantineIncidents of this.quarantinesByZip) {
      for (const incident of quarantineIncidents.quarantines) {
        if (patientIDs.indexOf(incident.patient!.id!) < 0)
          patientIDs.push(incident.patient!.id!)
      }
    }
    // Infections Sources for CSV download
    const exposures = await Api.getExposureSourceContactsForPatientsUsingPost(
      patientIDs
    )
    for (const patientId in exposures) {
      const sources = exposures[patientId].filter(Boolean)
      this.patientInfectionSources.set(patientId, sources)
    }
    // Test Results for CSV download
    const testIncidents = await Api.getPatientsCurrentByTypeUsingPost(
      patientIDs
    )
    for (const patientId in testIncidents) {
      this.patientTestResults.set(
        patientId,
        testIncidents[patientId].map((incident: any) => incident.status)
      )
    }
  },
  methods: {
    moment,
    showPatient(patientId: string) {
      this.$router.push({ name: 'patient-detail', params: { id: patientId } })
    },
    downloadAll() {
      let content = ''
      let maxInfectionSources = 0 // Needed for #Cols in Header
      for (const quarantineIncidents of this.quarantinesByZip) {
        content +=
          quarantineIncidents.quarantines
            .map((quarantine) => {
              const patient = quarantine.patient
              if (patient) {
                const address = `${patient.street} ${patient.houseNumber} ${patient.zip} ${patient.city}`
                let stayaddress = `${patient.stayStreet} ${patient.stayHouseNumber} ${patient.stayZip} ${patient.stayCity}`
                stayaddress =
                  stayaddress === 'null null null null' ? '' : stayaddress
                const comment = quarantine.comment ? quarantine.comment : ''

                const sourcesObjects = this.patientInfectionSources!.get(
                  patient.id!
                )
                let sources = ''
                if (sourcesObjects) {
                  sources = sourcesObjects!
                    .map((source) => {
                      return `${source.source!.firstName} ${
                        source.source!.lastName
                      };${source.dateOfContact};${source.context}`
                    })
                    .join(';')
                  maxInfectionSources =
                    maxInfectionSources < sourcesObjects!.length
                      ? sourcesObjects!.length
                      : maxInfectionSources
                }

                const patientTestResults = this.patientTestResults!.get(
                  patient.id!
                )
                let tests = ''
                if (patientTestResults) {
                  tests = patientTestResults
                    .map((result) => {
                      const type = testResults.find((tr) => tr.id === result)
                      if (type) return type.label
                      else return result
                    })
                    .join(' - ') // One patient can have multiple test results.
                }

                return `${quarantineIncidents.zip};${moment(
                  quarantine.until
                ).format('DD.MM.YYYY')};${patient?.firstName};${
                  patient?.lastName
                };${address};${patient.gender};${
                  patient?.dateOfBirth
                };${comment};${tests};${stayaddress};${sources}`
              } else {
                console.warn('Quarantine without patient')
                return ''
              }
            })
            .join('\n') + '\n'
      }
      let header =
        'PLZ;Quarantäne bis;Vorname;Nachname;Adresse;Geschlecht;Geburtsdatum;Quarantänekommentar;Testresultat;Aufenthaltsort'
      if (maxInfectionSources === 1)
        header +=
          ';Indexpatient Name;Indexpatient Kontaktdatum;Indexpatient Kontext'
      else
        for (let i = 1; i < maxInfectionSources + 1; i++)
          header += `;Indexpatient ${i} Name;Indexpatient ${i} Kontaktdatum;Indexpatient ${i} Kontext`
      const filename =
        moment().format('YYYY_MM_DD') + '_quarantaene_anordnung.csv'
      downloadCsv(header + '\n' + content, filename)
    },
    updatePatients() {
      this.confirmVisible = false

      const eventDate = this.form.getFieldValue('eventDate')
        ? this.form.getFieldValue('eventDate').format('YYYY-MM-DD')
        : undefined

      const incidentsForPost = this.quarantineIncidents.map((incident) => ({
        ...incident,
        patient: { id: incident.patient?.id },
        versionUser: { id: incident.versionUser?.id },
        eventType: 'QUARANTINE_MANDATED',
        eventDate,
      }))

      Api.addOrUpdateMultipleQuarantineIncidentsUsingPost(incidentsForPost)
        .then(() => {
          const h = this.$createElement
          this.$success({
            title: 'Quarantäne angeordnet.',
            content: h('div', {}, [
              h(
                'div',
                `Der Quarantänestatus wurde für ${incidentsForPost.length} Patienten aktualisiert.`
              ),
            ]),
          })
          this.quarantinesByZip = []
        })
        .catch((error: Error) => {
          const notification = {
            message: 'Fehler beim Aktualisieren des Quarantänestatus',
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
