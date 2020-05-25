<template>
  <a-form-item
    :label="'Bitte zutreffendes ankreuzen:'"
    :labelCol="{ div: 24 }"
    :wrapperCol="{ div: 24 }"
  >
    <a-checkbox-group
      v-model="exposures"
      v-decorator="['exposures', { initialValue: exposures }]"
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
      v-decorator="['exposureLocation', { initialValue: exposureLocations }]"
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
</template>

<script lang="ts">
import Vue from 'vue'
import { Option } from '@/models'
import { EXPOSURE_LOCATIONS, EXPOSURES_INTERNAL } from '@/models/exposures'

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  EXPOSURES_INTERNAL: Option[]
  EXPOSURE_LOCATIONS: Option[]
  exposures: string[]
  exposureLocations: string[]
}

export default Vue.extend({
  name: 'ExposureForm',
  props: ['form', 'patient'],
  created() {
    if (this.patient) {
      const prefix = 'CONTACT_WITH_CORONA'
      this.patient.riskAreas.forEach((exposure: string) => {
        let item = EXPOSURES_INTERNAL.find(
          (exposureInternal) => exposureInternal.value === exposure
        )
        if (item) {
          this.exposures.push(item.value)
        } else if (exposure.startsWith(prefix)) {
          item = EXPOSURE_LOCATIONS.find(
            (exposureLocation) =>
              prefix + '_' + exposureLocation.value === exposure
          )
          if (item) {
            this.exposureLocations.push(item.value)
          }
        }
      })
    }
  },
  computed: {
    disableExposureLocation(): boolean {
      return !this.exposures.includes('CONTACT_WITH_CORONA_CASE')
    },
  },
  data(): State {
    return {
      EXPOSURES_INTERNAL,
      EXPOSURE_LOCATIONS,
      exposures: [],
      exposureLocations: [],
    }
  },
})
</script>

<style scoped lang="scss"></style>
