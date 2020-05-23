<template>
  <div>
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
                initialValue: patient ? patient.patientStatus : 'SUSPECTED',
              },
            ]"
          >
            <a-select-option
              :key="eventType.id"
              v-for="eventType in EVENT_TYPES"
            >
              <a-icon :type="eventType.icon" style="margin-right: 5px;" />
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
                initialValue: initialDateOfIllness,
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
                initialValue: initialDateOfReporting,
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
          <a-checkbox v-model="patientHospitalized" v-decorator="['patientHospitalized']">
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
                initialValue: initialDateOfHospitalization,
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
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import moment, { Moment } from 'moment'
import { EventTypeItem, eventTypes } from '@/models/event-types'
import DateInput from '@/components/DateInput.vue'

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  patientHospitalized: boolean
  today: Moment
  EVENT_TYPES: EventTypeItem[]
  initialDateOfIllness: Moment | undefined
  initialDateOfReporting: Moment | undefined
  initialDateOfHospitalization: Moment | undefined
}

export default Vue.extend({
  name: 'IllnessStatusForm',
  props: ['form', 'patient'],
  components: {
    DateInput,
  },
  created() {
    if (this.patient) {
      if (this.patient.dateOfHospitalization) {
        this.patientHospitalized = true
        this.initialDateOfHospitalization = moment(this.patient.dateOfHospitalization)
      } else {
        this.initialDateOfHospitalization = this.today
      }
      if (this.patient.dateOfReporting) {
        this.initialDateOfReporting = moment(this.patient.dateOfReporting)
      } else {
        this.initialDateOfReporting = this.today
      }
      if (this.patient.dateOfIllness) {
        this.initialDateOfIllness = moment(this.patient.dateOfIllness)
      } else {
        this.initialDateOfIllness = this.today
      }
    } else {
      this.initialDateOfHospitalization = this.today
      this.initialDateOfIllness = this.today
      this.initialDateOfReporting = this.today
    }
  },
  data(): State {
    return {
      patientHospitalized: false,
      today: moment(),
      EVENT_TYPES: eventTypes,
      initialDateOfIllness: undefined,
      initialDateOfReporting: undefined,
      initialDateOfHospitalization: undefined,
    }
  },
  methods: {},
})
</script>

<style scoped lang="scss"></style>
