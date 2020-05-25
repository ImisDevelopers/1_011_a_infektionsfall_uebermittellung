<template>
  <a-card
    style="max-width: 500px; margin: 2rem auto; min-height: 300px;"
    align="center"
  >
    <a-form
      :form="form"
      :label-col="{ span: 7 }"
      :wrapper-col="{ span: 17 }"
      @submit.prevent="handleSubmit"
    >
      <!-- :colon="false" -->
      <a-form-item label="Patienten-ID" v-if="this.givenPatientId">
        {{ this.$route.params.patientFirstName }}
        {{ this.$route.params.patientLastName }} ({{
          this.$route.params.patientId
        }})
      </a-form-item>
      <a-form-item label="Patienten-ID" v-else>
        <PatientInput
          v-decorator="[
            'patientId',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie die Patienten-ID ein.',
                },
              ],
            },
          ]"
          v-on:select="onPatientSwitch"
        />
      </a-form-item>

      <a-form-item label="Quarantäne bis">
        <DateInput
          v-decorator="[
            'dateUntil',
            {
              rules: [
                {
                  required: true,
                  message: 'Bis wann soll der Patient in Quarantäne?',
                },
              ],
              initialValue: today,
            },
          ]"
        />
      </a-form-item>

      <a-form-item label="Vorgemerkt am">
        <DateInput
          v-decorator="[
            'eventDate',
            {
              rules: [
                {
                  required: false,
                  message:
                    'Datum, für welches der Vermerk erfasst werden soll.',
                },
              ],
              initialValue: today,
            },
          ]"
        />
      </a-form-item>

      <a-checkbox
        :disabled="contacts.length === 0"
        :checked="sendContactsToQuarantine"
        @change="sendContactsToQuarantineChanged"
        style="margin-bottom: 15px;"
      >
        Quarantäne auch für alle Kontaktpersonen vormerken
      </a-checkbox>

      <div style="margin-bottom: 15px;">
        <div v-if="contacts.length === 0" style="margin-bottom: 15px;">
          Keine Kontaktpersonen hinterlegt
        </div>
        <div
          v-for="contact in contacts"
          :key="contact.id"
          style="
            padding: 10px;
            display: flex;
            text-align: left;
            align-items: center;
          "
          v-bind:class="
            sendContactsToQuarantine ? '' : 'send-to-quarantine-disabled'
          "
        >
          <a-button
            icon="user"
            :type="sendContactsToQuarantine ? 'primary' : 'dashed'"
            title="Patientendaten anzeigen"
            @click="showPatient(contact.contact.id)"
            style="margin-right: 15px;"
          />
          <div>
            <div>
              {{ contact.contact.firstName }} {{ contact.contact.lastName }}
            </div>
            <div>Datum Kontakt: 21.04.2020</div>
          </div>
          <div style="margin-left: 15px;">
            <div>
              <span v-if="contact.contact.infected" style="color: red;">
                Infiziert
              </span>
              <span v-else>Infektionsstatus unbekannt</span>
            </div>
            <div>
              <span v-if="contact.contact.inQuarantine">
                In Quarantäne
              </span>
              <span
                v-else
                :style="`color: ${contact.contact.infected ? 'red' : 'unset'};`"
              >
                Keine Quarantäne
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- Kommentar -->
      <a-form-item label="Kommentar">
        <a-textarea
          :autoSize="{ minRows: 3, maxRows: 5 }"
          placeholder="Kommentar hinzufügen"
          v-decorator="['comment']"
        />
      </a-form-item>

      <!-- Submit -->
      <a-divider />
      <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
        <a-button html-type="submit" type="primary">
          Speichern
        </a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script lang="ts">
import { ExposureContactFromServer, Patient } from '@/api/SwaggerApi'
import Api from '@/api'
import Vue from 'vue'
import DateInput from '@/components/inputs/DateInput.vue'
import PatientInput from '@/components/inputs/PatientInput.vue'
import moment from 'moment'

interface State {
  form: any
  patient?: Patient
  today: moment.Moment
  contacts: ExposureContactFromServer[]
  sendContactsToQuarantine: boolean
}

export default Vue.extend({
  name: 'RequestQuarantine',
  components: {
    PatientInput,
    DateInput,
  },
  created() {
    if (this.givenPatientId) {
      this.onPatientSwitch(this.givenPatientId)
    }
  },
  data(): State {
    return {
      form: this.$form.createForm(this),
      patient: undefined,
      today: moment(),
      contacts: [],
      sendContactsToQuarantine: false,
    }
  },
  computed: {
    givenPatientId(): string | undefined {
      return this.$route.params.patientId
    },
  },
  methods: {
    moment,
    async onPatientSwitch(patientId: string) {
      this.contacts = await Api.getExposureContactsForPatientUsingGet(patientId)
    },
    showPatient(patientId: string) {
      this.$router.push({ name: 'patient-detail', params: { id: patientId } })
    },
    sendContactsToQuarantineChanged() {
      this.sendContactsToQuarantine = !this.sendContactsToQuarantine
    },
    handleSubmit() {
      this.form.validateFields(async (err: Error, values: any) => {
        // eslint-disable-next-line
        if (err) {
          return
        }
        const request = {
          dateUntil: values.dateUntil.format('YYYY-MM-DD'),
          eventDate: values.eventDate
            ? values.eventDate.format('YYYY-MM-DD')
            : undefined,
          comment: values.comment,
        }
        const patientId = this.givenPatientId
          ? this.givenPatientId
          : values.patientId

        let quarantineUntil = ''
        try {
          const patient = await Api.requestQuarantineUsingPost(
            patientId,
            request
          )
          quarantineUntil = moment(patient.quarantineUntil).format('DD.MM.YYYY')
          if (!this.sendContactsToQuarantine) {
            const h = this.$createElement
            this.$success({
              title: 'Der Quarantänevermerk wurde erfasst.',
              content: h('div', {}, [
                h('div', `Patient: ${patient.firstName} ${patient.lastName}`),
                h('div', `In Quarantäne bis: ${quarantineUntil}`),
              ]),
            })
            this.resetForm()
          }
        } catch (err) {
          console.error(err)
          const notification = {
            message: 'Fehler beim hinterlegen des Quarantänevermerks',
            description: err.message,
          }
          this.$notification.error(notification)
        }

        if (this.sendContactsToQuarantine) {
          const failedPatients = []
          for (const contactItem of this.contacts) {
            const contact = contactItem.contact
            if (!contact) {
              continue
            }
            const patientId = contact.id
            if (!patientId) {
              continue
            }
            try {
              await Api.requestQuarantineUsingPost(patientId, request)
            } catch (e) {
              console.error('Could not send ' + patientId + ' to quarantine:')
              console.error(e)
              const patientStr =
                contact.firstName +
                ' ' +
                contact.lastName +
                ' (ID: ' +
                patientId +
                ')'
              failedPatients.push(patientStr)
            }
          }
          if (failedPatients.length > 0) {
            const notification = {
              message: 'Fehler beim hinterlegen des Quarantänevermerks',
              description: `${failedPatients.length} von ${
                this.contacts.length + 1
              } Quarantänen konnten nicht vorgemerkt werden. Fehler bei: ${failedPatients}`,
            }
            this.$notification.error(notification)
          } else {
            const h = this.$createElement
            this.$success({
              title: 'Der Quarantänevermerk wurde vermerkt.',
              content: h('div', {}, [
                h(
                  'div',
                  `Quarantäne für ${
                    this.contacts.length + 1
                  } Patienten wurde vorgemerkt`
                ),
                h('div', `In Quarantäne bis: ${quarantineUntil}`),
              ]),
            })
            this.resetForm()
          }
        }
      })
    },
    resetForm() {
      this.form.resetFields()
      this.contacts = []
      if (this.givenPatientId) {
        this.$router.push({
          name: 'patient-detail',
          params: { id: this.givenPatientId },
        })
      }
    },
  },
})
</script>

<style>
.send-to-quarantine-disabled {
  color: rgba(0, 0, 0, 0.45);
}
</style>
