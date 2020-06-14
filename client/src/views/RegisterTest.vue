<template>
  <div style="max-width: 500px; margin: 0 auto;">
    <a-page-header
      title="Probe zuordnen"
      sub-title=""
      style="padding-left: 0; padding-right: 0;"
    />

    <a-card>
      <a-form
        :colon="false"
        :form="form"
        :layout="'vertical'"
        @submit.prevent="handleSubmit"
      >
        <LaboratoryInput
          :form="form"
          :validation="[
            'laboratoryId',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte wählen Sie ein Labor aus.',
                },
              ],
            },
          ]"
          label="Labor"
        />

        <a-form-item label="Patienten-ID">
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
          />
        </a-form-item>

        <!-- Test ID -->
        <a-form-item label="Test-ID">
          <a-input
            placeholder="Neue Test ID"
            v-decorator="[
              'testId',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte geben Sie die Test-ID ein.',
                  },
                ],
              },
            ]"
          />
        </a-form-item>

        <!-- TestType -->
        <a-form-item label="Testtyp">
          <a-radio-group
            class="imis-radio-group"
            default-value="PCR"
            v-decorator="[
              'type',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte geben Sie den Typen des Tests an.',
                  },
                ],
              },
            ]"
          >
            <a-radio
              :key="testTypeItem.id"
              :value="testTypeItem.id"
              v-for="testTypeItem in testTypes"
            >
              {{ testTypeItem.label }}
            </a-radio>
          </a-radio-group>
        </a-form-item>
        <!-- Test Material -->
        <a-form-item label="Probenmaterial">
          <a-radio-group
            class="imis-radio-group"
            default-value="RACHENABSTRICH"
            v-decorator="[
              'material',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte geben Sie das Material des Tests an.',
                  },
                ],
              },
            ]"
          >
            <a-radio
              :key="testMaterialItem.id"
              :value="testMaterialItem.id"
              v-for="testMaterialItem in testMaterials"
            >
              {{ testMaterialItem.label }}
            </a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item label="Probendatum">
          <DateInput
            :value="today"
            v-decorator="[
              'eventDate',
              {
                initialValue: today,
                rules: [
                  {
                    required: true,
                    message: 'Datum der Probenabnahme',
                  },
                ],
              },
            ]"
          />
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
    </a-card>
  </div>
</template>

<script lang="ts">
import { TestIncident } from '../api/SwaggerApi'
import Vue from 'vue'
import DateInput from '@/components/inputs/DateInput.vue'
import Api from '../api'
import PatientInput from '@/components/inputs/PatientInput.vue'
import LaboratoryInput from '@/components/inputs/LaboratoryInput.vue'
import { TestTypeItem, testTypes } from '../models/test-types'
import { testResults } from '../models/event-types'
import { TestMaterialItem, testMaterials } from '../models/test-materials'
import moment from 'moment'

interface State {
  form: any
  testTypes: TestTypeItem[]
  testMaterials: TestMaterialItem[]
  today: moment.Moment
}

export default Vue.extend({
  name: 'RegisterTest',
  components: {
    PatientInput,
    LaboratoryInput,
    DateInput,
  },
  data(): State {
    return {
      form: this.$form.createForm(this),
      testTypes: testTypes,
      testMaterials: testMaterials,
      today: moment(),
    }
  },
  methods: {
    handleSubmit() {
      this.form.validateFields((err: Error, values: any) => {
        if (err) {
          return
        }
        const request: TestIncident = {
          ...values,
          laboratory: {
            id: values.laboratoryId,
          },
          patient: {
            id: values.patientId,
          },
          eventType: 'TEST_SUBMITTED',
          status: 'TEST_SUBMITTED',
        }
        Api.setTestUsingPost(request)
          .then((incident: TestIncident) => {
            this.form.resetFields()
            const h = this.$createElement
            this.$success({
              title: 'Der Test wurde erfolgreich angelegt.',
              content: h('div', {}, [
                h('div', `Test ID: ${incident.testId}`),
                h(
                  'div',
                  `Patient: ${incident.patient!.firstName} ${
                    incident.patient!.lastName
                  }`
                ),
              ]),
            })
          })
          .catch((err) => {
            const notification = {
              message: 'Fehler beim Registrieren des Tests.',
              description: err.message,
            }

            if (err.message === 'CONSTRAINT_VIOLATION') {
              notification.description =
                'Dieser Test bei diesem Labor ist bereits einem anderen Patienten zugeordnet.'
            }
            this.$notification.error(notification)
          })
      })
    },
  },
})
</script>

<style scoped lang="scss"></style>
