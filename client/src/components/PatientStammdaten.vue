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
          <a-form-item label="Geschlecht">
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

      <a-divider />
      <p style="text-align: center">Adresse:</p>
      <!-- Straße / Hausnummer -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Straße">
            <a-input
              v-decorator="['street', { rules: [{
                        required: true,
                        message: 'Bitte Straße eingeben',
                      }], initialValue: patientInput.street }]"
            />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Hausnummer">
            <a-input
              v-decorator="['houseNumber', { rules: [{
                        required: true,
                        message: 'Bitte Hausnummer eingeben',
                      }], initialValue: patientInput.houseNumber}]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- PLZ / Ort -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <PlzInput :decorator="['zip', { rules: [{
              required: true,
              message: 'Bitte PLZ eingeben',
            }], initialValue: patientInput.zip}]" @plzChanged="setPLZ" />
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Ort">
            <a-input v-decorator="['city', { rules: [{
              required: true,
              message: 'Bitte Ort eingeben',
            }], initialValue: patientInput.city }]" />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Aufenthaltsort -->
      <div v-if="showStay">
        <a-divider />
        <p style="text-align: center">Aufenthaltsort, falls von Adresse abweichend:</p>
        <!-- Straße / Hausnummer -->
        <a-row>
          <a-col :lg="12" :sm="24">
            <a-form-item label="Straße">
              <a-input
                v-decorator="['stayStreet', {initialValue: patientInput.stayStreet}]"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :sm="24">
            <a-form-item label="Hausnummer">
              <a-input
                v-decorator="['stayHouseNumber', {initialValue: patientInput.stayHouseNumber}]"
              />
            </a-form-item>
          </a-col>
        </a-row>

        <!-- PLZ / Ort -->
        <a-row>
          <a-col :lg="12" :sm="24">
            <PlzInput :decorator="['stayZip', {initialValue: patientInput.stayZip}]" @plzChanged="setStayPLZ" />
          </a-col>
          <a-col :lg="12" :sm="24">
            <a-form-item label="Ort">
              <a-input
                v-decorator="['stayCity', {initialValue: patientInput.stayCity}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </div>

      <!-- Email / Telefon -->
      <a-divider />
      <p style="text-align: center">Kontakt und Sonstiges:</p>
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
import { Plz } from '@/util/plz-service'
import { RiskOccupation } from '@/models'
import { RISK_OCCUPATIONS, RiskOccupationOption } from '@/models/risk-occupation'
import DateInput from '@/components/DateInput.vue'
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
  plzs: Plz[];
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
      plzs: [],
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
    PlzInput,
  },
  methods: {
    setStayPLZ(plz: Plz) {
      this.form.setFieldsValue({
        stayZip: plz.fields.plz,
        stayCity: plz.fields.note,
      })
      let nextInput = document.getElementById('coordinated_email')
      if (!nextInput) {
        nextInput = document.getElementById('email')
      }
      if (nextInput) {
        nextInput.focus()
      }
    },
    setPLZ(plz: Plz) {
      this.form.setFieldsValue({
        zip: plz.fields.plz,
        city: plz.fields.note,
      })
      let nextInput = document.getElementById('coordinated_email')
      if (!nextInput) {
        nextInput = document.getElementById('email')
      }
      if (nextInput) {
        nextInput.focus()
      }
    },
    riskOccupationSelected(value: RiskOccupation) {
      this.disableOccupation = value !== 'NO_RISK_OCCUPATION'
      let occupation
      if (this.disableOccupation) {
        occupation = this.riskOccupations.find(riskOccupation => riskOccupation.value === value)?.label || ''
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
