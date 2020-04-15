<template>
  <a-card style="max-width: 500px; margin: 2rem auto; min-height: 300px">
    <a-form
      :colon="false"
      :form="form"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
      @submit.prevent="handleSubmit"
    >

      <LaboratoryInput
        :form="form"
        :validation="['laboratoryId',{ rules: [{
            required: true,
            message: 'Bitte wählen Sie ein Labor aus.'
          }]}]"
        label="Labor"
      />

      <PatientInput
        :form="form"
        :validation="['patientId',{ rules: [{
          required: true,
          message: 'Bitte geben Sie die Patienten-ID ein.'
        }]}]"
        label="Patienten-ID"
      />

      <!-- Test ID -->
      <a-form-item label="Test-ID">
        <a-input placeholder="Neue Test ID" v-decorator="['testId', { rules: [{
          required: true,
          message: 'Bitte geben Sie die Test-ID ein.'
        }]}]" />
      </a-form-item>

      <!-- TestType -->
      <a-form-item label="Testtyp">
        <a-radio-group
          class="imis-radio-group"
          v-decorator="['testType', { rules: [{
              required: true,
              message: 'Bitte geben Sie den Typen des Tests an.'
            }]}]">
          <a-radio :key="testTypeItem.id" :value="testTypeItem.id" v-for="testTypeItem in testTypes">
            <a-icon :type="testTypeItem.icon" />
            {{testTypeItem.label}}
          </a-radio>
        </a-radio-group>
      </a-form-item>

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

    <!-- Confirmation after creation -->
    <div v-if="createdLabTest">
      <a-icon :style="{ fontSize: '38px', color: '#08c' }" style="margin-bottom: 20px" type="check-circle" />
      <div>
        <div>Der Test wurde erfolgreich angelegt.</div>
        <br />
        <div>Test ID: {{ createdLabTest.testId }}</div>
        <div>Test Status: {{ createdLabTestStatus }}</div>
      </div>
    </div>
  </a-card>
</template>

<script>
import Vue from 'vue'

import Api from '@/api'
import BarcodeInput from '../components/TestInput'
import PatientInput from '../components/PatientInput'
import LaboratoryInput from '../components/LaboratoryInput'
import { testTypes } from '@/models/test-types'
import Component from 'vue-class-component'
import { testResults } from '@/models/event-types'

@Component({
  components: {
    BarcodeInput,
    PatientInput,
    LaboratoryInput,
  },
})
export default class RegisterTest extends Vue {
  data() {
    return {
      form: this.$form.createForm(this),
      createdLabTest: null,
      createdLabTestStatus: '',
      testTypes: testTypes,
    }
  }

  handleSubmit() {
    this.form.validateFields((err, values) => {
      if (err) {
        return
      }
      const request = {
        ...values,
      }

      Api.api.createTestForPatientUsingPost(request).then(labTest => {
        this.createdLabTest = labTest
        this.createdLabTestStatus = testResults
          .find(testResult => testResult.id === labTest.testStatus)
          .label

        const notification = {
          message: 'Test angelegt und verknüpft.',
          description:
            'Der Test ' + labTest.testId + ' wurde erfolgreich angelegt und mit dem Patienten verknüpft.',
        }
        this.$notification.success(notification)
        this.form.resetFields()
      }).catch(err => {
        const notification = {
          message: 'Fehler beim Anlegen des Tests.',
          description: err.message,
        }
        this.$notification.error(notification)
      })
    })
  }
}
</script>

<style></style>
