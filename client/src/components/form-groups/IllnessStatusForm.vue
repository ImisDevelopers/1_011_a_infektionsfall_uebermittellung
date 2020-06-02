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
          <a-checkbox
            v-model="patientHospitalized"
            v-decorator="['patientHospitalized']"
          >
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
            v-model="onIntensiveCareUnit"
            v-decorator="['onIntensiveCareUnit']"
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
import DateInput from '@/components/inputs/DateInput.vue'

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  today: Moment
  EVENT_TYPES: EventTypeItem[]
  patientHospitalized: boolean
  onIntensiveCareUnit: boolean
}

export default Vue.extend({
  name: 'IllnessStatusForm',
  props: ['form', 'patient'],
  components: {
    DateInput,
  },
  watch: {
    patient: {
      immediate: true,
      handler(newP, oldP) {
        this.patientHospitalized = !!(newP && newP.dateOfHospitalization)
        this.onIntensiveCareUnit = newP && newP.onIntensiveCareUnit
      },
    },
  },
  computed: {
    initialDateOfHospitalization(): moment.Moment {
      return this.patient && this.patient.dateOfHospitalization
        ? moment(this.patient.dateOfHospitalization)
        : moment()
    },
    initialDateOfIllness(): moment.Moment {
      return this.patient && this.patient.dateOfIllness
        ? moment(this.patient.dateOfIllness)
        : moment()
    },
    initialDateOfReporting(): moment.Moment {
      return this.patient && this.patient.dateOfReporting
        ? moment(this.patient.dateOfReporting)
        : moment()
    },
  },
  data(): State {
    return {
      today: moment(),
      EVENT_TYPES: eventTypes,
      patientHospitalized: false,
      onIntensiveCareUnit: false,
    }
  },
  methods: {},
})
</script>

<style scoped lang="scss"></style>
