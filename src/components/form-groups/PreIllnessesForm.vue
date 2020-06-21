<template>
  <a-form-item class="no-double-colon-form-field" :wrapperCol="{ div: 24 }">
    <a-checkbox-group
      v-decorator="['preIllnesses', { initialValue: preIllnesses }]"
    >
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
        v-decorator="[
          'showOtherPreIllnesses',
          { initialValue: showOtherPreIllnesses },
        ]"
        v-model="showOtherPreIllnesses"
        style="flex: 0 0 auto;"
      >
        Andere:
      </a-checkbox>
      <a-form-item style="flex: 1 1 100%; margin-bottom: 0; max-width: 600px;">
        <a-input
          :disabled="!showOtherPreIllnesses"
          v-decorator="[
            'preIllnessesOther',
            { initialValue: preIllnessesOther },
          ]"
        />
      </a-form-item>
    </div>
  </a-form-item>
</template>

<script lang="ts">
import Vue from 'vue'
import { ADDITIONAL_PRE_ILLNESSES, PRE_ILLNESSES } from '@/models/pre-illnesses'
import { Option } from '@/models'

export interface State {
  PRE_ILLNESSES: Option[]
  ADDITIONAL_PRE_ILLNESSES: Option[]
  preIllnesses: string[]
  preIllnessesOther: string
  showOtherPreIllnesses: boolean
}

export default Vue.extend({
  name: 'PreIllnessesForm',
  props: ['form', 'patient'],
  created() {
    if (this.patient) {
      const ALL_ILLNESSES = [...PRE_ILLNESSES, ...ADDITIONAL_PRE_ILLNESSES]
      for (const preIllness of this.patient.preIllnesses) {
        if (preIllness) {
          if (ALL_ILLNESSES.some((item) => item.value === preIllness)) {
            this.preIllnesses.push(preIllness)
          } else {
            this.showOtherPreIllnesses = true
            this.preIllnessesOther = preIllness
          }
        }
      }
    }
  },
  data(): State {
    return {
      PRE_ILLNESSES,
      ADDITIONAL_PRE_ILLNESSES,
      preIllnesses: [],
      preIllnessesOther: '',
      showOtherPreIllnesses: false,
    }
  },
})
</script>

<style scoped lang="scss"></style>
