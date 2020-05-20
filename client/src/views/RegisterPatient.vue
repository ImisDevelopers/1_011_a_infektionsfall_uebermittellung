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
            <a-form-item
              :label="'Bitte zutreffendes ankreuzen:'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-checkbox-group
                @change="exposuresChanged"
                v-decorator="['exposures']"
              >
                <a-row>
                  <a-col
                    :key="exposure.value"
                    :span="24"
                    v-for="exposure in EXPOSURES_INTERNAL"
                  >
                    <a-checkbox :value="exposure.value">
                      {{ exposure.label }}
                    </a-checkbox>
                  </a-col>
                </a-row>
              </a-checkbox-group>
              <a-checkbox-group
                :disabled="disableExposureLocation"
                style="padding-left: 30px;"
                v-decorator="['exposureLocation']"
              >
                <a-row>
                  <a-col
                    :key="exposure.value"
                    :span="24"
                    v-for="exposure in EXPOSURE_LOCATIONS"
                  >
                    <a-checkbox :value="exposure.value">
                      {{ exposure.label }}
                    </a-checkbox>
                  </a-col>
                </a-row>
              </a-checkbox-group>
            </a-form-item>
          </a-collapse-panel>

          <!-- Symptoms -->
          <a-collapse-panel header="Symptome" key="3">
            <a-form-item
              class="no-double-colon-form-field"
              :label="'Welche Symptome weist der Patient/die Patientin auf?'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-checkbox-group v-decorator="['symptoms']">
                <a-row>
                  <a-col
                    :key="symptom.value"
                    :span="
                      symptom.value === 'LOSS_OF_SENSE_OF_SMELL_TASTE' ? 12 : 6
                    "
                    v-for="symptom in SYMPTOMS"
                  >
                    <a-checkbox :value="symptom.value">
                      {{ symptom.label }}
                    </a-checkbox>
                  </a-col>
                </a-row>
              </a-checkbox-group>
              <div
                style="display: flex; align-items: center; align-self: stretch;"
              >
                <a-checkbox
                  :checked="showOtherSymptoms"
                  @change="symptomsChanged"
                  style="flex: 0 0 auto;"
                >
                  Andere:
                </a-checkbox>
                <a-form-item
                  style="flex: 1 1 100%; margin-bottom: 0; max-width: 600px;"
                >
                  <a-input
                    :disabled="!showOtherSymptoms"
                    v-decorator="['symptomsOther']"
                  />
                </a-form-item>
              </div>
            </a-form-item>

            <a-form-item
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
              class="no-double-colon-form-field"
              label="Wie schnell sind die Beschwerden aufgetreten?"
            >
              <a-radio-group v-decorator="['speedOfSymptomsOutbreak']">
                <a-radio value="suddenly">
                  Plötzlich, innerhalb von einem Tag
                </a-radio>
                <a-radio value="slow">
                  Langsam, innerhalb von mehreren Tagen
                </a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item
              class="no-double-colon-form-field"
              :label="'Hat der Patient/die Patientin für diese Saison eine Influenza-Impfung erhalten?'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-radio-group v-decorator="['fluImmunization']">
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-collapse-panel>

          <!-- Risks / Pre Illnesses -->
          <a-collapse-panel header="Vorerkrankungen und Risikofaktoren" key="4">
            <a-form-item
              class="no-double-colon-form-field"
              :label="'Welche Vorerkrankungen und Risikofaktoren liegen vor?'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-checkbox-group v-decorator="['preIllnesses']">
                <a-row>
                  <a-col
                    :key="preIllness.value"
                    :span="8"
                    v-for="preIllness in PRE_ILLNESSES"
                  >
                    <a-checkbox :value="preIllness.value">
                      {{ preIllness.label }}
                    </a-checkbox>
                  </a-col>
                  <a-col
                    :key="preIllness.value"
                    :span="12"
                    v-for="preIllness in ADDITIONAL_PRE_ILLNESSES"
                  >
                    <a-checkbox :value="preIllness.value">
                      {{ preIllness.label }}
                    </a-checkbox>
                  </a-col>
                </a-row>
              </a-checkbox-group>
              <div
                style="display: flex; align-items: center; align-self: stretch;"
              >
                <a-checkbox
                  :checked="showOtherPreIllnesses"
                  @change="preIllnessesChanged"
                  style="flex: 0 0 auto;"
                >
                  Andere:
                </a-checkbox>
                <a-form-item
                  style="flex: 1 1 100%; margin-bottom: 0; max-width: 600px;"
                >
                  <a-input
                    :disabled="!showOtherPreIllnesses"
                    v-decorator="['preIllnessesOther']"
                  />
                </a-form-item>
              </div>
            </a-form-item>
          </a-collapse-panel>

          <!-- Krankheitsdetails -->
          <a-collapse-panel header="Krankheit und Zustand" key="5">
            <h4>Erkrankung</h4>
            <a-row :gutter="12">
              <a-col :md="12">
                <a-form-item label="Art der Erkrankung">
                  <a-select
                    placeholder="Bitte wählen..."
                    v-decorator="[
                      'illnessType',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bitte Erkrankung wählen',
                          },
                        ],
                        initialValue: 'CORONA',
                      },
                    ]"
                  >
                    <a-select-option value="CORONA">COVID-19</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="12">
                <a-form-item label="Fallstatus">
                  <a-select
                    placeholder="Status"
                    style="width: 250px;"
                    v-decorator="[
                      'patientStatus',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bitte Status wählen',
                          },
                        ],
                        initialValue: 'SUSPECTED',
                      },
                    ]"
                  >
                    <a-select-option
                      :key="eventType.id"
                      v-for="eventType in EVENT_TYPES"
                    >
                      <a-icon
                        :type="eventType.icon"
                        style="margin-right: 5px;"
                      />
                      {{ eventType.label }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row :gutter="12">
              <a-col :md="12">
                <a-form-item label="Erkrankungsdatum">
                  <DateInput
                    v-decorator="[
                      'dateOfIllness',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bitte Erkrankungsdatum wählen',
                          },
                        ],
                        initialValue: today,
                      },
                    ]"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="12">
                <a-form-item label="Meldedatum">
                  <DateInput
                    v-decorator="[
                      'dateOfReporting',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bitte Meldedatum wählen',
                          },
                        ],
                        initialValue: today,
                      },
                    ]"
                  />
                </a-form-item>
              </a-col>
            </a-row>

            <a-divider />

            <h4>Hospitalisierung</h4>
            <a-row :gutter="12">
              <a-col :md="12">
                <a-form-item>
                  <a-checkbox v-model="patientHospitalized">
                    Patient/in ist hospitalisiert
                  </a-checkbox>
                </a-form-item>
              </a-col>
              <a-col :md="12">
                <a-form-item label="Datum der Hospitalisierung">
                  <DateInput
                    v-decorator="[
                      'dateOfHospitalization',
                      {
                        rules: [
                          {
                            required: patientHospitalized,
                            message: 'Bitte Datum wählen',
                          },
                        ],
                        initialValue: today,
                      },
                    ]"
                    :disabled="!patientHospitalized"
                    label="Seit"
                    style="flex: 0 1 400px;"
                  />
                </a-form-item>
                <a-form-item>
                  <a-checkbox
                    v-decorator="['onIntensiveCareUnit']"
                    :disabled="!patientHospitalized"
                  >
                    Auf der Intensivstation
                  </a-checkbox>
                </a-form-item>
              </a-col>
            </a-row>
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
import { Patient } from '@/api/SwaggerApi'
import PatientStammdaten from '@/components/PatientStammdaten.vue'
import { SYMPTOMS } from '@/models/symptoms'
import { Option } from '@/models'
import { ADDITIONAL_PRE_ILLNESSES, PRE_ILLNESSES } from '@/models/pre-illnesses'
import { EXPOSURE_LOCATIONS, EXPOSURES_INTERNAL } from '@/models/exposures'
import DateInput from '@/components/DateInput.vue'
import { EventTypeItem, eventTypes } from '@/models/event-types'
import moment, { Moment } from 'moment'

interface State {
  form: any
  createdPatient: Patient | null
  SYMPTOMS: Option[]
  PRE_ILLNESSES: Option[]
  ADDITIONAL_PRE_ILLNESSES: Option[]
  EXPOSURES_INTERNAL: Option[]
  EXPOSURE_LOCATIONS: Option[]
  EVENT_TYPES: EventTypeItem[]
  showOtherSymptoms: boolean
  showOtherPreIllnesses: boolean
  disableExposureLocation: boolean
  patientHospitalized: boolean
  today: Moment
}

export default Vue.extend({
  components: {
    PatientStammdaten,
    DateInput,
  },
  name: 'RegisterPatient',
  data(): State {
    return {
      form: this.$form.createForm(this, { name: 'coordinated' }),
      createdPatient: null,
      SYMPTOMS,
      ADDITIONAL_PRE_ILLNESSES,
      PRE_ILLNESSES,
      EXPOSURES_INTERNAL,
      EXPOSURE_LOCATIONS,
      EVENT_TYPES: eventTypes,
      disableExposureLocation: true,
      showOtherSymptoms: false,
      showOtherPreIllnesses: false,
      patientHospitalized: false,
      today: moment(),
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

        if (values.dateofIllness) {
          request.dateOfIllness = values.dateOfIllness.format('YYYY-MM-DD')
        } else {
          request.dateOfIllness = moment().format('YYYY-MM-DD')
        }

        if (values.dateOfReporting) {
          request.dateOfReporting = values.dateOfReporting.format('YYYY-MM-DD')
        } else {
          request.dateOfReporting = moment().format('YYYY-MM-DD')
        }

        if (!request.symptoms) {
          request.symptoms = []
        }
        if (!request.preIllnesses) {
          request.preIllnesses = []
        }

        if (request.dateOfDeath) {
          request.dateOfDeath = request.dateOfDeath.format('YYYY-MM-DD')
        }

        if (this.patientHospitalized) {
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
        if (this.showOtherSymptoms) {
          request.symptoms.push(values.symptomsOther)
        }
        if (this.showOtherPreIllnesses) {
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
            this.patientHospitalized = false
            this.showOtherSymptoms = false
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
    symptomsChanged(event: Event) {
      const target = event.target as any
      this.showOtherSymptoms = target.checked
    },
    preIllnessesChanged(event: Event) {
      const target = event.target as any
      this.showOtherPreIllnesses = target.checked
    },
    exposuresChanged(checkedValues: string[]) {
      this.disableExposureLocation = !checkedValues.includes(
        'CONTACT_WITH_CORONA_CASE'
      )
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
