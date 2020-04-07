<template>
  <a-card style="max-width: 500px; margin: 2rem auto; min-height: 300px">
    <div>
      <a-form
        :form="form"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
        @submit="handleSubmit"
      >

        <!-- TestId -->
        <BarcodeInput
          label="Test-ID"
          placeholder="z.B 1337-4237-9438"
          :form="form"
          :validation="['testId', { rules: [{
            required: true,
            message: 'Bitte geben Sie Ihre Test-ID ein.'
          }]}]"
        />

        <!-- TestResult -->
        <a-form-item label="Testresultat">
          <a-radio-group
            class="test-result-group"
            v-decorator="['testResult', { rules: [{
              required: true,
              message: 'Bitte geben Sie das Testresultat an.'
            }]}]">
            <a-radio v-for="testResult in testResults" :value="testResult.id" :key="testResult.id">
              <a-icon :type="testResult.icon"/>
              {{testResult.label}}
            </a-radio>
          </a-radio-group>
        </a-form-item>

        <!-- Kommentar -->
        <a-form-item label="Kommentar">
          <a-textarea
            placeholder="Kommentar hinzufügen"
            :autoSize="{ minRows: 3, maxRows: 5 }"
            v-decorator="['comment']"
          />
        </a-form-item>

        <!-- Hochladen -->
        <a-form-item :wrapper-col="{ span: 24 }">
          <a-button v-on:click="uploadHint()">
            <a-icon type="upload"/>
            Test Report hochladen
          </a-button>
        </a-form-item>
        <a-divider/>

        <!-- Speichern -->
        <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
          <a-button type="primary" html-type="submit">
            Speichern
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </a-card>
</template>

<script>
import Api from '@/api'
import BarcodeInput from '../components/BarcodeInput'
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'
import { testResults } from '@/util/event-types'

export default Vue.extend({
  name: 'LinkTestResultAndPatient',
  computed: {
    ...authMapper.mapGetters({ institution: 'institution' }),
  },
  components: { BarcodeInput },
  props: {},
  data() {
    return {
      form: this.$form.createForm(this),
      fileBytes: null,
      testResults: testResults,
    }
  },
  async created() {
    if (!this.institution()) {
      console.log('Loading institution')
      await this.getAuthenticatedInstitution()
    }
  },
  methods: {
    ...authMapper.mapActions({
      getAuthenticatedInstitution: 'getAuthenticatedInstitution',
    }),
    uploadHint() {
      const notification = {
        message: 'Das Labor kann hier den Bericht mit hochladen. Aus Sicherheitsgründen ist diese Funktion im Prototyp deaktiviert.',
        description: '',
      }
      this.$notification.info(notification)
    },
    beforeUpload(file) {
      const setFileBytes = fileBytes => {
        this.fileBytes = fileBytes
      }

      const reader = new FileReader()
      reader.onload = e => {
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
    handleSubmit(e) {
      e.preventDefault()

      this.form.validateFields((err, values) => {
        if (err) {
          return
        }

        const { testId } = values
        const request = {
          testId,
          status: values.testResult,
          comment: values.comment,
          file: this.fileBytes,
        }

        const testResult = this.testResults.find(testResult => values.testResult === testResult.id)
        Api.labtests.updateTestStatusUsingPut(this.institution().id, request).then(labTest => {
          const notification = {
            message: 'Test ' + labTest.testId + ' aktualisiert.',
            description: 'Status geändert auf "' + testResult.label + '"',
          }
          this.$notification.success(notification)
        }).catch(err => {
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

<style>
  .test-result-group {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    padding-left: 15px;
  }

  .test-result-group > label {
    padding: 10px 0 10px 15px;
    display: flex;
    align-items: center;
  }

  .test-result-group > label:hover {
    background: rgba(0, 0, 0, 0.1);
  }

  .test-result-group .ant-radio {
    margin-right: 10px;
  }

  .test-result-group i {
    margin-right: 10px;
  }
</style>
