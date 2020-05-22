<template>
  <a-form-item
    :label="'Bitte zutreffendes ankreuzen:'"
    :labelCol="{ div: 24 }"
    :wrapperCol="{ div: 24 }"
  >
    <a-checkbox-group @change="exposuresChanged" v-decorator="['exposures']">
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
</template>

<script lang="ts">
import Vue from 'vue'
import { Option } from '@/models'
import { EXPOSURES_INTERNAL } from '@/models/exposures'
import { EXPOSURE_LOCATIONS } from '@/models/exposures'

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  EXPOSURES_INTERNAL: Option[]
  EXPOSURE_LOCATIONS: Option[]
  disableExposureLocation: boolean
}

export default Vue.extend({
  name: 'ExpositionForm',
  props: ['form'],
  data(): State {
    return {
      EXPOSURES_INTERNAL,
      EXPOSURE_LOCATIONS,
      disableExposureLocation: true,
    }
  },
  methods: {
    exposuresChanged(checkedValues: string[]) {
      this.disableExposureLocation = !checkedValues.includes(
        'CONTACT_WITH_CORONA_CASE'
      )
    },
  },
})
</script>

<style scoped lang="scss"></style>
