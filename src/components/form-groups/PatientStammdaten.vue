<template>
  <div class="data-form">
    <div>
      <!-- Vorname / Nachname -->
      <h4>Allgemeine Angaben</h4>

      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Vorname">
            <a-input
              v-decorator="[
                'firstName',
                {
                  rules: [
                    {
                      required: true,
                      message: 'Bitte Vornamen eingeben',
                    },
                  ],
                  initialValue: patientInput.firstName,
                },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Nachname">
            <a-input
              v-decorator="[
                'lastName',
                {
                  rules: [
                    {
                      required: true,
                      message: 'Bitte Nachnamen eingeben',
                    },
                  ],
                  initialValue: patientInput.lastName,
                },
              ]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Staatsangehörigkeit -->
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Staatsangehörigkeit">
            <a-input
              v-decorator="[
                'nationality',
                {
                  rules: [
                    {
                      required: true,
                      message: 'Bitte Staatsangehörigkeit angeben',
                    },
                  ],
                  initialValue: patientInput.nationality || 'deutsch',
                },
              ]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Geschlecht / Geburtsdatum -->
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Mediz. Geschlecht">
            <a-radio-group
              @change="genderSelected"
              buttonStyle="solid"
              v-decorator="[
                'gender',
                {
                  rules: [
                    {
                      required: true,
                      message: 'Bitte Geschlecht eingeben',
                    },
                  ],
                  initialValue: patientInput.gender,
                },
              ]"
            >
              <a-radio value="male">Männlich</a-radio>
              <a-radio value="female">Weiblich</a-radio>
              <a-radio value="divers">Divers</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Geburtsdatum">
            <DateInput
              v-decorator="[
                'dateOfBirth',
                {
                  rules: [
                    {
                      required: true,
                      message: 'Bitte Geburtsdatum eingeben',
                    },
                  ],
                  initialValue: initialDateOfBirth,
                },
              ]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <a-row :gutter="12" v-if="showDeath">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Verstorben">
            <a-radio-group
              @change="diedChanged"
              v-decorator="[
                'died',
                {
                  rules: [
                    {
                      required: true,
                      message: 'Bitte Status angeben',
                    },
                  ],
                  initialValue: !!patientInput.dateOfDeath,
                },
              ]"
            >
              <a-radio :value="true">Ja</a-radio>
              <a-radio :value="false">Nein</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Todesdatum">
            <DateInput
              v-decorator="[
                'dateOfDeath',
                { initialValue: initialDateOfDeath },
              ]"
              :disabled="!showDateOfDeath"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Wohnsitz -->
      <a-divider />
      <h4>Wohnort</h4>
      <location-form-group :form="form" :data="patient" :required="true" />

      <!-- Aufenthaltsort -->
      <div v-if="showStay">
        <a-divider />
        <h4>Aufenthaltsort, falls von Wohnort abweichend</h4>
        <location-form-group
          :data="patient"
          :required="false"
          fieldNamePrefix="stay"
        />
      </div>

      <!-- Email / Telefon -->
      <a-divider />
      <h4>Kommunikation und Beruf</h4>
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="E-mail">
            <a-input
              v-decorator="[
                'email',
                {
                  rules: [
                    {
                      required: false,
                      message: 'Bitte Email eingeben',
                    },
                  ],
                  initialValue: patientInput.email,
                },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Telefon">
            <a-input
              v-decorator="[
                'phoneNumber',
                {
                  rules: [
                    {
                      required: false,
                      message: 'Bitte Telefonnummer eingeben',
                    },
                  ],
                  initialValue: patientInput.phoneNumber,
                },
              ]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- Beruf / Arbeitgeber -->
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Beruf">
            <a-select
              @select="riskOccupationSelected"
              v-decorator="[
                'riskOccupation',
                {
                  rules: [
                    {
                      required: false,
                      message: 'Bitte Beruf eingeben',
                    },
                  ],
                  initialValue: initialRiskOccupation,
                },
              ]"
            >
              <a-select-option
                :key="riskOccupation.value"
                v-for="riskOccupation in riskOccupations"
              >
                {{ riskOccupation.label }}
              </a-select-option>
            </a-select>
            <transition name="fading">
              <a-input
                ref="occupation"
                style="margin-top: 12px;"
                :disabled="disableOccupation"
                v-show="!disableOccupation"
                placeholder="Berufsbezeichnung"
                v-decorator="[
                  'occupation',
                  {
                    rules: [
                      {
                        message: 'Bitte Beruf eingeben',
                      },
                    ],
                    initialValue: patientInput.occupation,
                  },
                ]"
              />
            </transition>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Arbeitgeber">
            <a-input
              v-decorator="[
                'employer',
                { initialValue: patientInput.employer },
              ]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <a-divider />

      <!-- Krankenkasse / Versichertennr -->
      <h4>Krankenkasse</h4>
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Krankenkasse (optional)">
            <a-auto-complete
              @search="searchInsuranceCompanies"
              :filterOption="false"
              v-decorator="[
                'insuranceCompany',
                { initialValue: patientInput.insuranceCompany },
              ]"
            >
              <template slot="dataSource">
                <a-select-option
                  :key="company"
                  v-for="company in insuranceCompanies.preDefined"
                  >{{ company }}</a-select-option
                >
                <a-select-option
                  :key="company"
                  v-for="company in insuranceCompanies.userDefined"
                  >{{ company }}</a-select-option
                >
              </template>
            </a-auto-complete>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Versichertennr.">
            <a-input
              v-decorator="[
                'insuranceMembershipNumber',
                { initialValue: patientInput.insuranceMembershipNumber },
              ]"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import { RiskOccupation } from '@/models'
import {
  RISK_OCCUPATIONS,
  RiskOccupationOption,
} from '@/models/risk-occupation'
import Api from '@/api'
import DateInput from '@/components/inputs/DateInput.vue'
import LocationFormGroup from '@/components/form-groups/LocationFormGroup.vue'
import { Patient, HealthInsuranceCompanies } from '@/api/ImisSwaggerApi'
import moment, { Moment } from 'moment'
import { TextMatcher, TextMatcherResult } from '@/util/search'

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  // TODO disableOccupation should be a computed property and not be set on change
  disableOccupation: boolean
  riskOccupations: RiskOccupationOption[]
  showDateOfDeath: boolean
  patientInput: Patient
  initialDateOfBirth: Moment | undefined
  initialDateOfDeath: Moment | undefined
  initialRiskOccupation: string | undefined
  insuranceCompanies: HealthInsuranceCompanies
  availableInsuranceCompanies: HealthInsuranceCompanies
}

export default Vue.extend({
  name: 'PatientStammdaten',
  props: ['form', 'showStay', 'showDeath', 'patient'],
  created() {
    Api.getHealthInsuranceCompaniesUsingGet().then(
      (companies) => (this.availableInsuranceCompanies = companies)
    )

    if (this.patient) {
      this.patientInput = this.patient
      this.initialDateOfBirth = moment(this.patientInput.dateOfBirth)
      if (this.patientInput.dateOfDeath) {
        this.initialDateOfDeath = moment(this.patientInput.dateOfDeath)
      }
      this.initialRiskOccupation =
        this.patientInput.riskOccupation || 'NO_RISK_OCCUPATION'
      this.disableOccupation = false
    }
  },
  data(): State {
    const initialInsuranceCompanies = {
      preDefined: [],
      userDefined: [],
    }

    return {
      disableOccupation: true,
      riskOccupations: RISK_OCCUPATIONS,
      showDateOfDeath: false,
      patientInput: {
        patientStatus: 'REGISTERED',
      },
      initialDateOfBirth: undefined,
      initialDateOfDeath: undefined,
      initialRiskOccupation: undefined,
      insuranceCompanies: initialInsuranceCompanies,
      availableInsuranceCompanies: initialInsuranceCompanies,
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
        occupation =
          this.riskOccupations.find(
            (riskOccupation) => riskOccupation.value === value
          )?.label || ''
      } else {
        const occupationInput = (this.$refs.occupation as Vue)
          .$el as HTMLElement
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
    searchInsuranceCompanies(search: string) {
      const matcher = new TextMatcher(search, { withScore: true })

      const [preDefined, userDefined] = [
        this.availableInsuranceCompanies.preDefined as string[],
        this.availableInsuranceCompanies.userDefined as string[],
      ].map((companies) =>
        companies
          .map(
            (company) =>
              [company, matcher.match(company)] as [string, TextMatcherResult]
          )
          .filter(([company, matchResult]) => matchResult.matches)
          // Modify score to match density (score per word)
          .map(([company, matchResult]) => {
            matchResult.score /= company.split(/\s+/g).length
            return [company, matchResult] as [string, TextMatcherResult]
          })
          // Sort by score, descending
          .sort((o1, o2) => -1 * (o1[1].score - o2[1].score))
          .map(([company, matchResult]) => company)
      )

      this.insuranceCompanies = {
        preDefined,
        userDefined,
      }
    },
  },
})
</script>

<style scoped lang="scss"></style>
