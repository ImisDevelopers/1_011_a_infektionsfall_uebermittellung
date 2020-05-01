<template>
  <div class="data-form">
    <div>

      <!-- Vorname / Nachname -->
      <p style="text-align: center">Allgemeine Angaben:</p>
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Titel">
            <a-input v-decorator="['title', {initialValue: patientInput.title}]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Vorname">
            <a-input
              v-decorator="['firstName', { rules: [{
                        required: true,
                        message: 'Bitte Vornamen eingeben',
                      }], initialValue: patientInput.firstName}]"
            />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Nachname">
            <a-input
              v-decorator="['lastName', { rules: [{
                        required: true,
                        message: 'Bitte Nachnamen eingeben'
                      }], initialValue: patientInput.lastName}]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Geschlecht / Geburtsdatum -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Mediz. Geschlecht">
            <a-radio-group
              @change="genderSelected"
              buttonStyle="solid"
              v-decorator="['gender', { rules: [{
                        required: true,
                        message: 'Bitte Geschlecht eingeben',
                      }], initialValue: patientInput.gender}]"
            >
              <a-radio value="male">Männlich</a-radio>
              <a-radio value="female">Weiblich</a-radio>
              <a-radio value="divers">Divers</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <DateInput :decorator="['dateOfBirth', { rules: [{
                        required: true,
                        message: 'Bitte Geburtsdatum eingeben',
                      }], initialValue: initialDateOfBirth}]" label="Geburtsdatum" />
        </a-col>
      </a-row>

      <!-- Staatsangehörigkeit -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Staatsangehörigkeit">
            <a-input
              v-decorator="['nationality', { rules: [
                { required: true, message: 'Bitte Staatsangehörigkeit angeben' }
                ]}]"/>
          </a-form-item>
        </a-col>
      </a-row>

      <a-row v-if="showDeath">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Verstorben">
            <a-radio-group @change="diedChanged" v-decorator="['died', { rules: [{
                        required: true,
                        message: 'Bitte Status angeben',
                      }], initialValue: !!patientInput.dateOfDeath }]">
              <a-radio :value="true">Ja</a-radio>
              <a-radio :value="false">Nein</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <DateInput :decorator="['dateOfDeath', {initialValue: initialDateOfDeath}]"
                     :disabled="!showDateOfDeath"
                     label="Todesdatum" />
        </a-col>
      </a-row>

      <!-- Wohnsitz -->
      <a-divider />
      <p style="text-align: center">Wohnort:</p>
      <location-form-group
        :form="form"
        :data="patient"
        :required="true" />

      <!-- Aufenthaltsort -->
      <div v-if="showStay">
        <a-divider />
        <p style="text-align: center">Aufenthaltsort, falls von Wohnort abweichend:</p>
        <location-form-group
          :form="form"
          :data="patient"
          :required="false"
          inputKeyPrefix="stay"
          :useInputKeysForData="true"/>
      </div>

      <!-- Email / Telefon -->
      <a-divider />
      <p style="text-align: center">Kommunikation und Sonstiges:</p>
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="E-mail">
            <a-input
              v-decorator="['email', { rules: [{
                        required: true,
                        message: 'Bitte Email eingeben',
                      }], initialValue: patientInput.email }]"
            />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Telefon">
            <a-input
              v-decorator="[
                      'phoneNumber',
                      { rules: [{
                        required: true,
                        message: 'Bitte Telefonnummer eingeben',
                      }], initialValue: patientInput.phoneNumber}
                    ]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Beruf / Arbeitgeber -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Beruf">
            <div>
              <a-select @select="riskOccupationSelected" v-decorator="['riskOccupation', { rules: [{
                        required: true,
                        message: 'Bitte Beruf eingeben',
                      }], initialValue: initialRiskOccupation}]">
                <a-select-option :key="riskOccupation.value" v-for="riskOccupation in riskOccupations">
                  {{riskOccupation.label}}
                </a-select-option>
              </a-select>
              <a-input
                ref="occupation"
                :disabled="disableOccupation"
                v-decorator="['occupation', { rules: [{
                        required: true,
                        message: 'Bitte Beruf eingeben',
                      }], initialValue: patientInput.occupation}]"
              />
            </div>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Arbeitgeber (optional)">
            <a-input v-decorator="['employer', {initialValue: patientInput.employer}]" />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Krankenkasse / Versichertennr -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Krankenkasse (optional)">
            <a-input v-decorator="['insuranceCompany', {initialValue: patientInput.insuranceCompany}]" />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Versichertennr. (optional)">
            <a-input
              v-decorator="['insuranceMembershipNumber', {initialValue: patientInput.insuranceMembershipNumber}]" />
          </a-form-item>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script lang="ts">

import Vue from 'vue'
import { RiskOccupation } from '@/models'
import { RISK_OCCUPATIONS, RiskOccupationOption } from '@/models/risk-occupation'
import DateInput from '@/components/DateInput.vue'
import LocationFormGroup from '@/components/LocationFormGroup.vue'
import PlzInput from '@/components/PlzInput.vue'
import { Patient } from '@/api/SwaggerApi'
import moment, { Moment } from 'moment'

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  disableOccupation: boolean;
  riskOccupations: RiskOccupationOption[];
  showDateOfDeath: boolean;
  patientInput: Patient;
  initialDateOfBirth: Moment | undefined;
  initialDateOfDeath: Moment | undefined;
  initialRiskOccupation: string | undefined;
}

export default Vue.extend({
  name: 'PatientStammdaten',
  props: ['form', 'showStay', 'showDeath', 'patient'],
  created() {
    if (this.patient) {
      this.patientInput = this.patient
      this.initialDateOfBirth = moment(this.patientInput.dateOfBirth)
      if (this.patientInput.dateOfDeath) {
        this.initialDateOfBirth = moment(this.patientInput.dateOfDeath)
      }
      this.initialRiskOccupation = this.patientInput.riskOccupation || 'NO_RISK_OCCUPATION'
      this.disableOccupation = false
    }
  },
  data(): State {
    return {
      disableOccupation: true,
      riskOccupations: RISK_OCCUPATIONS,
      showDateOfDeath: false,
      patientInput: {},
      initialDateOfBirth: undefined,
      initialDateOfDeath: undefined,
      initialRiskOccupation: undefined,
    }
  },
  components: {
    DateInput,
    LocationFormGroup,
  },
  methods: {
    riskOccupationSelected(value: RiskOccupation) {
      this.disableOccupation = value !== 'NO_RISK_OCCUPATION'
      let occupation
      if (this.disableOccupation) {
        occupation = this.riskOccupations.find(riskOccupation => riskOccupation.value === value)?.label || ''
      } else {
        const occupationInput = ((this.$refs.occupation as Vue).$el as HTMLElement)
        setTimeout(() => {
          occupationInput.focus()
        }, 0)
      }
      this.form.setFieldsValue({
        occupation: occupation,
      })
    },
    genderSelected(event: Event) {
      const gender = (event.target as any).value
      this.$emit('gender', gender)
    },
    diedChanged(event: Event) {
      this.showDateOfDeath = (event.target as any).value
    },
  },
})
</script>

<style scoped lang="scss">
</style>
