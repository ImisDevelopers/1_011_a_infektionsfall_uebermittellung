<template>
  <div class="wrapper">
    <div v-if="createdPatient" style="display: flex; justify-content: center; text-align: center">
      <a-card style="max-width: 600px; margin-bottom: 25px">
        <div style="display: flex; flex-direction: column; position: relative">
          <a-icon type="close" style="top: 0; right: 0; position: absolute; cursor: pointer"
                  @click="createdPatient = null" />
          <div style="display: flex; align-items: center; margin: 10px">
            <a-icon type="check-circle" :style="{ fontSize: '38px', color: '#08c' }" style="margin: 0 25px 0 0" />
            <h3 style="margin-bottom: 0">
              <span v-if="createdPatient.gender === 'female'">Patientin</span>
              <span v-else>Patient</span>
              wurde erfolgreich registriert.
            </h3>
          </div>
          <table style="border-collapse: separate; border-spacing: 15px 5px; text-align: left">
            <tbody>
              <tr>
                <td>Name:</td>
                <td>{{ createdPatient.firstName }} {{ createdPatient.lastName }}</td>
              </tr>
              <tr>
                <td>Patienten-ID:</td>
                <td>{{ createdPatient.id }}</td>
              </tr>
            </tbody>
          </table>
          <div style="margin-top: 15px">
            <router-link :to="{ name: 'patient-detail', params: { id: createdPatient.id } }" style="margin-right: 15px">
              <a-button type="primary">
                Patienten einsehen
              </a-button>
            </router-link>
            <router-link :to="{ name: 'register-test' }">
              <a-button type="primary">
                Probe zuordnen
              </a-button>
            </router-link>
          </div>
        </div>
      </a-card>
    </div>
    <div>
      <h3>
        Registrieren Sie hier neue Patienten in IMIS. Bitte füllen Sie den Bogen
        vollständig aus.
      </h3>

      <a-form
        :form="form"
        :layout="'horizontal'"
        :labelCol="{ span: 8 }"
        :wrapperCol="{ span: 16}"
        @submit.prevent="handleSubmit"
      >
        <a-collapse defaultActiveKey="1">
          <a-collapse-panel header="Allgemeines" key="1">
            <a-row>
              <a-col :lg="12">
                <a-form-item label="Vorname">
                  <a-input
                    v-decorator="['firstName', { rules: [{ required: true, message: 'Bitte Vornamen eingeben', }] }]"
                  />
                </a-form-item>
                <a-form-item label="Nachname">
                  <a-input
                    v-decorator="['lastName', { rules: [{ required: true, message: 'Bitte Nachnamen eingeben', }] }]"
                  />
                </a-form-item>
                <a-form-item label="Geschlecht">
                  <a-radio-group
                    v-decorator="['gender', { rules: [{ required: true, message: 'Bitte Geschlecht eingeben', }] }]"
                    buttonStyle="solid"
                  >
                    <a-radio value="male">Männl.</a-radio>
                    <a-radio value="female">Weibl.</a-radio>
                    <a-radio value="divers">Div.</a-radio>
                  </a-radio-group>
                </a-form-item>
                <a-form-item label="Geburtsdatum">
                  <a-date-picker
                    :format="dateFormat"
                    v-decorator="[
                      'dateOfBirth',
                      { rules: [{ required: true, message: 'Bitte Geburtsdatum eingeben', }] }
                    ]"
                    placeholder="Datum wählen"
                  />
                </a-form-item>
                <a-form-item label="Krankenkasse">
                  <a-input v-decorator="['insuranceCompany']" />
                </a-form-item>
                <a-form-item label="Versichertenr.">
                  <a-input v-decorator="['insuranceMembershipNumber']" />
                </a-form-item>
              </a-col>
              <a-col :lg="12">
                <a-form-item label="E-mail">
                  <a-input
                    v-decorator="['email', { rules: [{ required: true, message: 'Bitte Email eingeben', }] }]"
                  />
                </a-form-item>
                <a-form-item label="Telefon">
                  <a-input
                    v-decorator="[
                      'phoneNumber',
                      { rules: [{ required: true, message: 'Bitte Telefonnummer eingeben', }] }
                    ]"
                  />
                </a-form-item>
                <a-form-item label="Straße">
                  <a-input
                    v-decorator="['street', { rules: [{ required: true, message: 'Bitte Straße eingeben', }] }]"
                  />
                </a-form-item>
                <a-form-item label="Hausnr.">
                  <a-input
                    v-decorator="[
                      'houseNumber',
                      { rules: [{ required: true, message: 'Bitte Hausnummer eingeben', }] }
                    ]"
                  />
                </a-form-item>
                <a-form-item label="PLZ">
                  <a-input
                    v-decorator="['zip', { rules: [{ required: true, message: 'Bitte PLZ eingeben', }] }]"
                  />
                </a-form-item>
                <a-form-item label="Ort">
                  <a-input
                    v-decorator="['city', { rules: [{ required: true, message: 'Bitte Stadt eingeben', }] }]"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item
                  label="Üben sie einen systemrelevanten Beruf aus?"
                  required
                  :labelCol="{ lg: 12 }"
                  :wrapperCol="{ lg: 12 }"
                >
                  <a-select
                    labelInValue
                    v-model="selectedOccupation"
                  >
                    <a-select-option
                      v-for="riskOccupation in RISK_OCCUPATIONS" :key="riskOccupation.value"
                    >
                      {{ riskOccupation.label }}
                    </a-select-option>
                  </a-select>

                </a-form-item>
              </a-col>
            </a-row>
          </a-collapse-panel>
          <a-collapse-panel header="Infektionskette" key="2">
            <a-form-item
              label="Haben Sie Kontakt mit einem bestätigten Corona-Patienten gehabt?"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-radio-group
                v-decorator="[
                      'coronaContacts',
                      { rules: [{ required: false }] }
                    ]"
              >
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item
              label="Haben Sie sich in einem / mehreren der folgenden Risikogebiete für Coronavirus aufgehalten?"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-row>
                <a-col
                  v-for="(riskArea, idx) in RISK_AREAS" :key="idx"
                  :xs="24"
                  :sm="12"
                >
                  <a-checkbox
                    v-model="selectedRiskAreas[riskArea.key]">{{
                    riskArea.description
                    }}
                  </a-checkbox>
                </a-col>
              </a-row>
            </a-form-item>
            <!-- ggf. Aufenthaltszeitraum ergänzen -->
          </a-collapse-panel>

          <a-collapse-panel header="Symptome" key="3">
            <a-form-item
              label="Leiden sie unter einem oder mehreren der folgenden Symptome?"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-row>
                <a-col
                  v-for="(symptom, idx) in SYMPTOMS" :key="idx"
                  :xs="24"
                  :sm="12">
                  <a-checkbox v-model="selectedSymptoms[symptom.value]">{{symptom.label}}</a-checkbox>
                </a-col>
              </a-row>
            </a-form-item>

            <a-form-item
              label="Wie schnell sind die Beschwerden aufgetreten?"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-radio-group
                v-decorator="[
                  'speedOfSymptomsOutbreak',
                  { rules: [{ required: false }] }
                ]"
              >
                <a-radio value="suddenly"
                >Plötzlich, innerhalb von einem Tag
                </a-radio
                >
                <a-radio value="slow"
                >Langsam, innerhalb von mehreren Tagen
                </a-radio
                >
              </a-radio-group>
            </a-form-item>

            <a-form-item
              label="Haben Sie für diese Saison eine Influenza-Impfung erhalten?"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-radio-group
                v-decorator="[
                  'fluImmunization',
                  { rules: [{ required: false }] }
                ]"
              >
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-collapse-panel>
          <a-collapse-panel header="Vorerkrankungen" key="4">
            <a-form-item
              label="Haben Sie aufgrund einer Erkrankung ein geschwächtes Immunsystem?"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-radio-group
                v-decorator="[
                  'weakenedImmuneSystem',
                  { rules: [{ required: false }] }
                ]"
              >
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item
              label="Leiden Sie an einer / mehreren der folgenden Erkrankungen?"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-row>
                <a-col
                  v-for="(illness, idx) in PRE_ILLNESSES" :key="idx"
                  :xs="24"
                  :sm="12"
                >
                  <a-checkbox v-model="selectedPreIllnesses[illness.value]">{{illness.label}}</a-checkbox>
                </a-col>
              </a-row>
            </a-form-item>
          </a-collapse-panel>
        </a-collapse>

        <!-- Datenschutzerklärung Bestätigung-->
        <br />
        <a-form-item :wrapperCol="{ span: 24, offset: 0 }">
          <a-checkbox @change="onCheck">
            <span :class="dataProcessingClass">
              Ich erkläre mich mit der Übermittlung dieser Daten zur weiteren
              Verarbeitung einverstanden.
            </span>
          </a-checkbox>
        </a-form-item>

        <!-- Submit Button -->
        <a-form-item :wrapperCol="{ span: 24, offset: 0 }">
          <a-row :gutter="16" type="flex" justify="end">
            <a-col>
              <a-button type="primary" html-type="submit">
                Patient registrieren + Test anordnen
              </a-button>
            </a-col>
          </a-row>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<!-- Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new -->

<script lang="ts">
import Vue from 'vue'
import { patientMapper } from '../store/modules/patients.module'
import { SYMPTOMS } from '@/models/symptoms'
import { PRE_ILLNESSES } from '@/models/pre-illnesses'
import Api from '@/api'
import { Patient } from '@/api/SwaggerApi'
import { RISK_OCCUPATIONS } from '@/models/risk-occupation'

type KeyDescription = { key: string; description: string };

const RISK_AREAS: KeyDescription[] = [
  { key: 'IRAN', description: 'Iran' },
  { key: 'ITALY', description: 'Italien' },
  { key: 'CHINA', description: 'China' },
  { key: 'SOUTHCOREA', description: 'Südkorea' },
  { key: 'FRACE', description: 'Frankreich' },
  { key: 'AUSTRIA', description: 'Österreich' },
  { key: 'SPAIN', description: 'Spanien' },
  { key: 'USA', description: 'USA: Kalofornien, Washington oder New York' },
]

export default Vue.extend({
  name: 'RegisterPatient',
  data() {
    const selectedSymptoms: any = {}
    SYMPTOMS.forEach(symptom => {
      selectedSymptoms[symptom.value] = false
    })

    const selectedPreIllnesses: any = {}
    PRE_ILLNESSES.forEach(illness => {
      selectedPreIllnesses[illness.value] = false
    })

    const selectedRiskAreas: any = {}
    RISK_AREAS.forEach(riskArea => {
      selectedRiskAreas[riskArea.key] = false
    })

    const selectedOccupation = { key: 'NO_RISK_OCCUPATION', label: 'Kein Risiko Beruf' }

    return {
      dateFormat: 'DD/MM/YYYY',
      form: this.$form.createForm(this, { name: 'coordinated' }),
      SYMPTOMS,
      selectedSymptoms,
      PRE_ILLNESSES,
      selectedPreIllnesses,
      RISK_AREAS,
      selectedRiskAreas,
      RISK_OCCUPATIONS,
      selectedOccupation,
      createdPatient: null,
      dataProcessingClass: '',
      checked: false,
    }
  },
  methods: {
    ...patientMapper.mapActions({
      registerPatient: 'registerPatient',
    }),
    onCheck(e: any) {
      this.checked = e.target.checked
    },
    handleSubmit() {
      this.form.validateFields(async(err: Error, values: any) => {
        if (!this.checked) {
          this.dataProcessingClass = 'data-processing-not-selected'
          return
        } else if (err) {
          return
        }

        const symptoms = SYMPTOMS.filter(
          symptom => this.selectedSymptoms[symptom.value],
        ).map(symptom => symptom.value)
        const preIllnesses = PRE_ILLNESSES.filter(
          illness => this.selectedPreIllnesses[illness.value],
        ).map(illness => illness.value)
        const riskAreas = RISK_AREAS.filter(
          riskArea => this.selectedRiskAreas[riskArea.key],
        ).map(riskArea => riskArea.key)
        const riskOccupation = this.selectedOccupation.key
        console.log(riskOccupation)
        console.log(this.selectedOccupation)
        const request = {
          ...values,
          dateOfBirth: values.dateOfBirth.format('YYYY-MM-DD') + ' 00',
          symptoms,
          preIllnesses,
          riskAreas,
          riskOccupation,
        }

        const patientString = request.gender === 'female' ? 'Patientin' : 'Patient'

        Api.patients.addPatientUsingPost(request).then((patient: Patient) => {
          this.form.resetFields()
          this.createdPatient = patient as any
          const notification = {
            message: patientString + ' registriert.',
            description: 'Der ' + patientString + ' wurde erfolgreich registriert.',
          }
          this.$notification.success(notification)
          window.scrollTo(0, 0)
        }).catch((error: Error) => {
          console.error(error)
          const notification = {
            message: patientString + ' nicht registriert.',
            description: 'Der ' + patientString + ' konnte nicht registriert werden.',
          }
          this.$notification.error(notification)
        })
      })
    },
  },
})
</script>

<style scoped>
  .wrapper {
    text-align: left;
    padding: 2%;
    width: 100%;
  }

  .data-processing-not-selected {
    color: red;
  }
</style>
