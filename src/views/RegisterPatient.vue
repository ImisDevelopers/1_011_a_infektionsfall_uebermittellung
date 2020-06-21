<template>
  <div class="wrapper register-patient-container">
    <!-- Patient successfully created info -->

    <a-page-header
      title="Patient wurde erfolgreich registriert"
      :ghost="false"
      v-if="createdPatient"
    >
      <template slot="extra">
        <router-link :to="{ name: 'register-test' }">
          <a-button>
            Probe zuordnen
          </a-button>
        </router-link>
        <router-link
          :to="{
            name: 'patient-detail',
            params: { id: createdPatient.id },
          }"
        >
          <a-button>
            Patienten/in einsehen
          </a-button>
        </router-link>
        <a-button type="primary" @click="createdPatient = null">
          Neuen Patienten registrieren
        </a-button>
      </template>
      <a-descriptions size="small" :column="1">
        <a-descriptions-item label="Patienten-ID">
          {{ createdPatient.id }}
        </a-descriptions-item>
        <a-descriptions-item label="Name">
          {{ createdPatient.firstName }} {{ createdPatient.lastName }}
        </a-descriptions-item>
      </a-descriptions>
    </a-page-header>

    <!-- Patient Registration Form -->
    <div v-if="!createdPatient">
      <a-page-header
        title="Patient Registrieren"
        sub-title="Bitte erfassen Sie
      die nachfolgenden Daten so vollständig wie möglich. Pflichtangaben sind
      mit '*' markiert."
        style="padding-left: 0; padding-right: 0;"
      />

      <a-form :form="form" layout="vertical" @submit.prevent="handleSubmit">
        <a-collapse defaultActiveKey="1">
          <!-- Stammdaten -->
          <a-collapse-panel header="Personendaten" key="1">
            <PatientStammdaten
              :form="form"
              :show-death="true"
              :show-stay="true"
            />
          </a-collapse-panel>

          <!-- Infektionskette / Exposure -->
          <a-collapse-panel header="Exposition" key="2">
            <ExposureForm :form="form" />
          </a-collapse-panel>

          <!-- Symptoms -->
          <a-collapse-panel header="Symptome" key="3">
            <SymptomsForm :form="form" />

            <a-form-item
              class="no-double-colon-form-field"
              :label="'Hat der Patient/die Patientin für diese Saison eine Influenza-Impfung erhalten?'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-radio-group v-decorator="['fluImmunization']">
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
                <a-radio value="">Nicht bekannt</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-collapse-panel>

          <!-- Risks / Pre Illnesses -->
          <a-collapse-panel header="Vorerkrankungen und Risikofaktoren" key="4">
            <PreIllnessesForm :form="form" />
          </a-collapse-panel>

          <!-- Krankheitsdetails -->
          <a-collapse-panel header="Krankheit und Zustand" key="5">
            <IllnessStatusForm :form="form" />
          </a-collapse-panel>
        </a-collapse>

        <!-- Submit Button -->
        <a-form-item
          :wrapperCol="{ span: 24, offset: 0 }"
          style="margin-top: 15px;"
        >
          <a-row :gutter="16" justify="end" type="flex">
            <a-col>
              <a-button html-type="submit" size="large" type="primary">
                <a-icon type="save" />
                Patient registrieren
              </a-button>
            </a-col>
          </a-row>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import Api from '@/api'
import { Patient } from '@/api/ImisSwaggerApi'
import PatientStammdaten from '@/components/form-groups/PatientStammdaten.vue'
import moment from 'moment'
import SymptomsForm from '@/components/form-groups/SymptomsForm.vue'
import ExposureForm from '@/components/form-groups/ExposureForm.vue'
import PreIllnessesForm from '@/components/form-groups/PreIllnessesForm.vue'
import IllnessStatusForm from '@/components/form-groups/IllnessStatusForm.vue'

interface State {
  form: any
  createdPatient: Patient | null
  showSpeedOfSymptoms: boolean
}

export default Vue.extend({
  components: {
    PatientStammdaten,
    SymptomsForm,
    ExposureForm,
    PreIllnessesForm,
    IllnessStatusForm,
  },
  name: 'RegisterPatient',
  data(): State {
    return {
      form: this.$form.createForm(this, { name: 'coordinated' }),
      createdPatient: null,
      showSpeedOfSymptoms: false,
    }
  },
  methods: {
    handleSubmit() {
      this.form.validateFields(async (err: Error, values: any) => {
        if (err) {
          return
        }
        const request = {
          ...values,
          dateOfBirth: values.dateOfBirth.format('YYYY-MM-DD'),
          riskAreas: [],
        }

        values.dateOfIllness = (values.dateOfIllness || moment()).format(
          'YYYY-MM-DD'
        )
        values.dateOfReporting = (values.dateOfReporting || moment()).format(
          'YYYY-MM-DD'
        )

        if (!request.symptoms) {
          request.symptoms = []
        }
        if (!request.preIllnesses) {
          request.preIllnesses = []
        }

        if (request.dateOfDeath) {
          request.dateOfDeath = request.dateOfDeath.format('YYYY-MM-DD')
        }

        if (request.patientHospitalized) {
          request.dateOfHospitalization = values.dateOfHospitalization.format(
            'YYYY-MM-DD'
          )
          request.onIntensiveCareUnit = values.onIntensiveCareUnit
        } else {
          request.dateOfHospitalization = null
          request.onIntensiveCareUnit = null
        }

        if (values.exposures) {
          request.riskAreas = request.riskAreas.concat(values.exposures)
        }
        if (values.showOtherSymptoms && values.symptomsOther) {
          request.symptoms.push(values.symptomsOther)
        }
        if (values.showOtherPreIllnesses && values.preIllnessesOther) {
          request.preIllnesses.push(values.preIllnessesOther)
        }
        if (values.exposureLocation) {
          request.riskAreas = request.riskAreas.concat(
            values.exposureLocation.map(
              (location: string) => 'CONTACT_WITH_CORONA_' + location
            )
          )
        }

        Api.addPatientUsingPost(request)
          .then((patient: Patient) => {
            this.form.resetFields()
            this.createdPatient = patient as any
            this.form.setFieldsValue({
              symptomsOther: undefined,
              symptomsOtherActivated: undefined,
              preIllnessesOther: undefined,
              preIllnessesActivated: undefined,
            })
            const notification = {
              message: 'Patient/in registriert.',
              description: 'Patient/in wurde erfolgreich registriert.',
            }
            this.$notification.success(notification)
            window.scrollTo(0, 0)
          })
          .catch((error: Error) => {
            console.error(error)
            const notification = {
              message: 'Patient/in nicht registriert.',
              description: 'Patient/in konnte nicht registriert werden.',
            }
            this.$notification.error(notification)
          })
      })
    },
  },
})
</script>

<style lang="scss" scoped>
.register-patient-container {
  .no-double-colon-form-field {
    .ant-form-item-label {
      label::after {
        display: none;
      }
    }
  }

  .ant-row.ant-form-item {
    margin-bottom: 8px;
  }

  .ant-divider {
    margin: 1rem 0;
  }
}

.wrapper {
  margin: 0 auto;
  width: 100%;
  max-width: 1000px;
}
</style>
