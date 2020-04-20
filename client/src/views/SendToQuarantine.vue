<template>
  <a-card style="max-width: 500px; margin: 2rem auto; min-height: 300px">
    <a-form
      :form="form"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
      @submit.prevent="handleSubmit"
    > <!-- :colon="false" -->

      <PatientInput
        :form="form"
        :validation="['patientId',{ rules: [{
          required: true,
          message: 'Bitte geben Sie die Patienten-ID ein.'
        }]}]"
        label="Patienten-ID"
      />

      <DateInput
        :form="form"
        :decorator="['dateUntil', { rules: [{
          required: true,
          message: 'Bis wann soll der Patient in Quarantäne?',
        }]}]"
        label="Quarantäne bis"
      />

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

    <!-- TODO AB HIER -->
    <!-- Confirmation after creation -->
    <div v-if="patient">
      <a-icon :style="{ fontSize: '38px', color: '#08c' }" style="margin-bottom: 20px" type="check-circle" />
      <div>
        <div>Der Quarantänevermerk wurde erfasst.</div>
        <br />
        <div>Patient: {{ patient.firstName }} {{ patient.lastName }}</div>
        <div>In Quarantäne bis: {{ patient.quarantineUntil }}</div>
      </div>
    </div>
  </a-card>
</template>

<script>
import Vue from 'vue'

import Api from '@/api'
import DateInput from '../components/DateInput'
import PatientInput from '../components/PatientInput'
import Component from 'vue-class-component'

@Component({
  components: {
    PatientInput,
    DateInput,
  },
})
export default class RegisterTest extends Vue {
  data() {
    return {
      form: this.$form.createForm(this),
      patient: null,
    }
  }

  handleSubmit() {
    this.form.validateFields((err, values) => {
      if (err) {
        return
      }
      const request = {
        dateUntil: values.dateUntil.format('YYYY-MM-DD'),
      }

      Api.api.sendToQuarantineUsingPost(values.patientId, request).then(patient => {
        this.patient = patient

        const notification = {
          message: 'Quarantäne vermerkt',
          description:
            'Der Quarantänevermerk für ' + patient.firstName + ' ' + patient.lastName + ' wurde erfolgreich hinterlegt.',
        }
        this.$notification.success(notification)
        this.form.resetFields()
      }).catch(err => {
        const notification = {
          message: 'Fehler beim hinterlegen des Quarantänevermerks',
          description: err.message,
        }
        this.$notification.error(notification)
      })
    })
  }
}
</script>

<style></style>
