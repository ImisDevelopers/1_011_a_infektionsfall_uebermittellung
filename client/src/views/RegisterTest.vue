<template>
  <a-card style="max-width: 500px; margin: 2rem auto; min-height: 300px">
    <div v-if="!createdLabTest">
      <a-form
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
        :colon="false"
        :form="form"
        @submit.prevent="handleSubmit"
      >
        <a-form-item label="Labor">
          <a-auto-complete
            :dataSource="laboratories"
            @search="onSearch"
            @focus="onSearch"
            placeholder="z.B. WirVsVirus Labor"
            v-decorator="[
                    'laboratoryId',
                    {
                      rules: [
                        {
                          required: true,
                          message: 'Bitte wählen Sie ein Labor aus.'
                        }
                      ]
                    }
                  ]"
          />
          <!--                  @select="onSelect"-->
        </a-form-item>
        <BarcodeInput
          label="Patienten-ID"
          placeholder="z.B 43A78F0B"
          :form="form"
          :validation="[
            'patientId',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie die Patienten-ID ein.'
                }
              ]
            }
          ]"
        />
        <BarcodeInput
          placeholder="z.B ae48-hr43-sk97"
          label="Test-ID"
          :form="form"
          :validation="[
            'testId',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie die Test-ID ein.'
                }
              ]
            }
          ]"
        />
        <a-form-item label="Kommentar">
          <a-textarea
            v-decorator="['comment']"
            placeholder="Kommentar hinzufügen"
            :autoSize="{ minRows: 3, maxRows: 5 }"
          />
        </a-form-item>
        <a-divider />
        <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
          <a-button type="primary" html-type="submit">
            Speichern
          </a-button>
        </a-form-item>
      </a-form>
    </div>
    <div v-else>
      <div>Der Test wurde erfolgreich angelegt.</div>
      <br />
      <div>Test ID: {{ createdLabTest.id }}</div>
      <div>Test Status: {{ createdLabTest.testStatus }}</div>
    </div>
  </a-card>
</template>

<script>
import Vue from 'vue'

import Api from '@/api'
import BarcodeInput from '../components/BarcodeInput'
import Component from 'vue-class-component'

@Component({
  components: {
    BarcodeInput,
  },
})
export default class RegisterTest extends Vue {
  data() {
    return {
      form: this.$form.createForm(this),
      createdLabTest: null,
      fetchedLaboratories: [],
      laboratories: [],
    }
  }

  handleSubmit(e) {
    this.form.validateFields((err, values) => {
      if (err) {
        return
      }
      const request = {
        ...values,
      }

      Api.labtests.createTestForPatientUsingPost(request)
        .then(labTest => {
          this.createdLabTest = labTest

          const notification = {
            message: 'Test angelegt und verknüpft.',
            description:
              'Der Test wurde erfolgreich angelegt und mit dem Patienten verknüpft.',
          }
          this.$notification.success(notification)
        })
        .catch(err => {
          const notification = {
            message: 'Fehler beim Anlegen des Tests.',
            description: err.message,
          }
          this.$notification.error(notification)
        })
    })
  }

  onSearch(str) {
    if (!str) {
      this.laboratories = this.fetchedLaboratories
        .map(l => ({
          value: l.id,
          text: l.name,
        }))
    } else {
      this.laboratories = this.fetchedLaboratories
        .filter(l => l.name.toLowerCase().startsWith(str.toLowerCase()))
        .map(l => ({
          value: l.id,
          text: l.name,
        }))
    }
  }

  async created() {
    this.fetchedLaboratories = await Api.institutions.getAllLaboratoriesUsingGet()
  }
}
</script>

<style></style>
