<template>
  <div style="max-width: 500px; margin: 0 auto;">
    <a-page-header
      title="Testresultat zuordnen"
      sub-title=""
      style="padding-left: 0; padding-right: 0;"
    />

    <a-card>
      <a-form :form="form" layout="vertical" @submit.prevent="handleSubmit">
        <!-- Labor -->
        <LaboratoryInput
          :form="form"
          :initial-labs="laboratories"
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

        <!-- TestId -->
        <TestInput
          :form="form"
          :validation="[
            'testId',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie Ihre Test-ID ein.',
                },
              ],
            },
          ]"
          label="Test-ID"
          placeholder="z.B 1337-4237-9438"
        />

        <!-- TestResult -->
        <a-form-item label="Testresultat">
          <a-radio-group
            class="imis-radio-group"
            v-decorator="[
              'testResult',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte geben Sie das Testresultat an.',
                  },
                ],
              },
            ]"
          >
            <a-radio
              :key="testResult.id"
              :value="testResult.id"
              v-for="testResult in testResults"
            >
              <a-icon :type="testResult.icon" />
              {{ testResult.label }}
            </a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item label="Ergebnisdatum">
          <DateInput
            :defaultValue="today"
            v-decorator="[
              'eventDate',
              {
                rules: [
                  {
                    required: false,
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

        <!-- Hochladen -->
        <a-form-item :wrapper-col="{ span: 24 }">
          <a-button v-on:click="uploadHint()">
            <a-icon type="upload" />
            Test Report hochladen
          </a-button>
        </a-form-item>
        <a-divider />

        <!-- Speichern -->
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
import { Institution, LabTest } from '@/api/SwaggerApi'
import Vue from 'vue'
import Api from '@/api'
import TestInput from '@/components/TestInput.vue'
import LaboratoryInput from '@/components/LaboratoryInput.vue'
import DateInput from '@/components/DateInput.vue'
import { authMapper } from '@/store/modules/auth.module'
import { testResults, TestResultType } from '@/models/event-types'
import moment from 'moment'

interface State {
  form: any
  fileBytes?: any
  testResults: TestResultType[]
  laboratories: Institution[]
  updatedLabTest?: LabTest
  updatedLabTestStatus: string
  today: moment.Moment
}

export default Vue.extend({
  name: 'SubmitTestResult',
  computed: {
    ...authMapper.mapGetters({ institution: 'institution' }),
  },
  components: {
    TestInput,
    LaboratoryInput,
    DateInput,
  },
  props: {},
  data(): State {
    return {
      form: this.$form.createForm(this),
      fileBytes: undefined,
      // TODO: After simulation, remove the filter
      testResults: testResults.filter(
        (testResult) =>
          testResult.id === 'TEST_POSITIVE' || testResult.id === 'TEST_NEGATIVE'
      ),
      laboratories: [],
      updatedLabTest: undefined,
      updatedLabTestStatus: '',
      today: moment(),
    }
  },
  async mounted() {
    if (!this.institution()) {
      console.log('Loading institution')
      await this.getAuthenticatedInstitution()
    }
    const lab = this.institution()
    if (lab && lab.type === 'LABORATORY') {
      this.laboratories = [lab]
      this.form.setFieldsValue({
        laboratoryId: lab.id,
      })
    } else {
      this.form.setFieldsValue({
        laboratoryId: '',
      })
    }
  },
  methods: {
    ...authMapper.mapActions({
      getAuthenticatedInstitution: 'getAuthenticatedInstitution',
    }),
    uploadHint() {
      const notification = {
        message:
          'Das Labor kann hier den Bericht mit hochladen. Aus Sicherheitsgründen ist diese Funktion im Prototyp deaktiviert.',
        description: '',
      }
      this.$notification.info(notification)
    },
    beforeUpload(file: File) {
      const setFileBytes = (fileBytes: any) => {
        this.fileBytes = fileBytes
      }

      const reader = new FileReader()
      reader.onload = (e: any) => {
        const utf8 = unescape(encodeURIComponent(e.target.result))
        const array = []
        for (let i = 0; i < utf8.length; i++) {
          array.push(utf8.charCodeAt(i))
        }
        setFileBytes(array)
      }
      reader.readAsDataURL(file)

      return false
    },
    handleSubmit() {
      this.form.validateFields((err: Error, values: any) => {
        if (err) {
          return
        }

        const { testId } = values
        const request = {
          testId,
          status: values.testResult,
          comment: values.comment,
          file: this.fileBytes,
          eventDate: values.eventDate,
        }

        Api.updateTestStatusUsingPut(values.laboratoryId, request)
          .then((labTest) => {
            this.form.resetFields(['testId', 'testResult', 'comment'])
            this.fileBytes = null
            const updatedLabTest = labTest
            const updatedLabTestStatus =
              testResults.find(
                (testResult) => testResult.id === labTest.testStatus
              )?.label || ''
            const h = this.$createElement
            this.$success({
              title: 'Der Test wurde erfolgreich aktualisiert.',
              content: h('div', {}, [
                h('div', `Test ID: ${updatedLabTest.testId}`),
                h('div', `Neuer Test Status: ${updatedLabTestStatus}`),
              ]),
            })
          })
          .catch((err) => {
            const notification = {
              message: 'Fehler beim Hinzufügen des Testergebnisses.',
              description: err.message,
            }
            this.$notification.error(notification)
          })
      })
    },
  },
})
</script>

<style scoped lang="scss"></style>
