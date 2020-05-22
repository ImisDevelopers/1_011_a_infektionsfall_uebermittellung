<template>
  <a-form-item
    class="no-double-colon-form-field"
    :label="'Welche Vorerkrankungen und Risikofaktoren liegen vor?'"
    :labelCol="{ div: 24 }"
    :wrapperCol="{ div: 24 }"
  >
    <a-checkbox-group v-decorator="['preIllnesses']">
      <a-row>
        <a-col
          :key="preIllness.value"
          :span="8"
          v-for="preIllness in PRE_ILLNESSES"
        >
          <a-checkbox :value="preIllness.value">
            {{ preIllness.label }}
          </a-checkbox>
        </a-col>
        <a-col
          :key="preIllness.value"
          :span="12"
          v-for="preIllness in ADDITIONAL_PRE_ILLNESSES"
        >
          <a-checkbox :value="preIllness.value">
            {{ preIllness.label }}
          </a-checkbox>
        </a-col>
      </a-row>
    </a-checkbox-group>
    <div style="display: flex; align-items: center; align-self: stretch;">
      <a-checkbox
        :checked="showOtherPreIllnesses"
        v-decorator="['showOtherPreIllnesses']"
        @change="preIllnessesChanged"
        style="flex: 0 0 auto;"
      >
        Andere:
      </a-checkbox>
      <a-form-item style="flex: 1 1 100%; margin-bottom: 0; max-width: 600px;">
        <a-input
          :disabled="!showOtherPreIllnesses"
          v-decorator="['preIllnessesOther']"
        />
      </a-form-item>
    </div>
  </a-form-item>
</template>

<script lang="ts">
import Vue from 'vue'
import { ADDITIONAL_PRE_ILLNESSES, PRE_ILLNESSES } from '@/models/pre-illnesses'
import { Option } from '@/models'

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  showOtherPreIllnesses: boolean
  PRE_ILLNESSES: Option[]
  ADDITIONAL_PRE_ILLNESSES: Option[]
}

export default Vue.extend({
  name: 'ExpositionForm',
  props: ['form'],
  data(): State {
    return {
      PRE_ILLNESSES,
      ADDITIONAL_PRE_ILLNESSES,
      showOtherPreIllnesses: false,
    }
  },
  methods: {
    preIllnessesChanged(event: Event) {
      const target = event.target as any
      this.showOtherPreIllnesses = target.checked
    },
  },
})
</script>

<style scoped lang="scss"></style>
