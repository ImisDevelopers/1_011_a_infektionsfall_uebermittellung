<template>
  <div class="data-form">
    <div>

      <!-- Vorname / Nachname -->
      <p style="text-align: center">Allgemeine Angaben:</p>
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Titel">
            <a-input v-decorator="['title']" />
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
                      }]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Nachname">
            <a-input
              v-decorator="['lastName', { rules: [{
                        required: true,
                        message: 'Bitte Nachnamen eingeben'
                      }]}]"
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
                      }]}]"
            >
              <a-radio value="male">Männl.</a-radio>
              <a-radio value="female">Weibl.</a-radio>
              <a-radio value="divers">Div.</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <DateInput :decorator="['dateOfBirth', { rules: [{
                        required: true,
                        message: 'Bitte Geburtsdatum eingeben',
                      }]}]" label="Geburtsdatum" />
        </a-col>
      </a-row>

      <a-row v-if="showDeath">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Verstorben">
            <a-radio-group @change="diedChanged" v-decorator="['died', { rules: [{
                        required: true,
                        message: 'Bitte Status angeben',
                      }], initialValue: false }]">
              <a-radio :value="true">Ja</a-radio>
              <a-radio :value="false">Nein</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <DateInput :decorator="['dateOfDeath']" :disabled="!showDateOfDeath" label="Todesdatum" />
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
                      }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Hausnummer">
            <a-input
              v-decorator="['houseNumber', { rules: [{
                        required: true,
                        message: 'Bitte Hausnummer eingeben',
                      }]}]"
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
            }]}]" @plzChanged="setPLZ" />
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Ort">
            <a-input v-decorator="['city', { rules: [{
              required: true,
              message: 'Bitte Ort eingeben',
            }] }]" />
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
                v-decorator="['stayStreet']"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :sm="24">
            <a-form-item label="Hausnummer">
              <a-input
                v-decorator="['stayHouseNumber']"
              />
            </a-form-item>
          </a-col>
        </a-row>

        <!-- PLZ / Ort -->
        <a-row>
          <a-col :lg="12" :sm="24">
            <PlzInput :decorator="['stayZip']" @plzChanged="setStayPLZ" />
          </a-col>
          <a-col :lg="12" :sm="24">
            <a-form-item label="Ort">
              <a-input
                v-decorator="['stayCity']"
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
                      }] }]"
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
                      }]}
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
                      }]}]">
                <a-select-option :key="riskOccupation.value" v-for="riskOccupation in riskOccupations">
                  {{riskOccupation.label}}
                </a-select-option>
              </a-select>
              <a-input
                :disabled="disableOccupation"
                v-decorator="['occupation', { rules: [{
                        required: true,
                        message: 'Bitte Beruf eingeben',
                      }]}]"
              />
            </div>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Arbeitgeber (optional)">
            <a-input v-decorator="['employer']" />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Krankenkasse / Versichertennr -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Krankenkasse (optional)">
            <a-input v-decorator="['insuranceCompany']" />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Versichertennr. (optional)">
            <a-input v-decorator="['insuranceMembershipNumber']" />
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
}

export default Vue.extend({
  name: 'PatientStammdaten',
  props: ['form', 'showStay', 'showDeath'],
  data(): State {
    return {
      plzs: [],
      disableOccupation: true,
      riskOccupations: RISK_OCCUPATIONS,
      showDateOfDeath: false,
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
