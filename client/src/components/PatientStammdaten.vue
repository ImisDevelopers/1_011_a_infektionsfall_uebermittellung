<template>
  <div class="data-form">
    <div>

      <!-- Vorname / Nachname -->
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
              v-decorator="['gender', { rules: [{
                        required: true,
                        message: 'Bitte Geschlecht eingeben',
                      }]}]"
              buttonStyle="solid"
            >
              <a-radio value="male">Männl.</a-radio>
              <a-radio value="female">Weibl.</a-radio>
              <a-radio value="divers">Div.</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <DateInput label="Geburtsdatum" :decorator="['dateOfBirth', { rules: [{
                        required: true,
                        message: 'Bitte Geburtsdatum eingeben',
                      }]}]" />
        </a-col>
      </a-row>

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

      <!-- PLZ Ort -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="PLZ">
            <a-auto-complete @search="handlePlzSearch" v-decorator="['zip', { rules: [{
                        required: true,
                        message: 'Bitte PLZ eingeben',
                      }] }]" @select="handlePlzSelection">
              <template slot="dataSource">
                <a-select-option v-for="plz in plzs" :key="plz.fields.plz">
                  {{plz.fields.plz}} {{plz.fields.note}}
                </a-select-option>
              </template>
            </a-auto-complete>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Ort">
            <a-input
              v-decorator="['city', { rules: [{
                        required: true,
                        message: 'Bitte Ort eingeben',
                      }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Email / Telefon -->
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
              <a-select v-decorator="['riskOccupation', { rules: [{
                        required: true,
                        message: 'Bitte Beruf eingeben',
                      }]}]" @select="riskOccupationSelected">
                <a-select-option v-for="riskOccupation in riskOccupations" :key="riskOccupation.value">
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
          <a-form-item label="Versichertenr. (optional)">
            <a-input v-decorator="['insuranceMembershipNumber']" />
          </a-form-item>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script lang="ts">

import Vue from 'vue'
import { getPlzs, Plz } from '@/util/plz-service'
import { RiskOccupation } from '@/models'
import { RISK_OCCUPATIONS, RiskOccupationOption } from '@/models/risk-occupation'
import DateInput from '@/components/DateInput.vue'

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
}

export default Vue.extend({
  name: 'PatientStammdaten',
  props: ['form'],
  data(): State {
    return {
      plzs: [],
      disableOccupation: true,
      riskOccupations: RISK_OCCUPATIONS,
    }
  },
  components: {
    DateInput,
  },
  methods: {
    async handlePlzSearch(value: string) {
      let result: Plz[]
      if (!value || value.length < 2) {
        result = []
      } else {
        result = await getPlzs(value)
        if (result.length === 1) {
          this.setPLZ(result[0])
        }
      }
      this.plzs = result
    },
    handlePlzSelection(value: string) {
      const plz = this.plzs.find(plz => plz.fields.plz === value)
      if (plz) {
        this.setPLZ(plz)
      }
    },
    setPLZ(plz: Plz) {
      this.form.setFieldsValue({
        zip: plz.fields.plz,
        city: plz.fields.note,
      })
      plz.fields.note = '' // So city does not display in plz input
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
  },
})
</script>

<style>

</style>
