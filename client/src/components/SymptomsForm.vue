<template>
  <a-form-item
    class="no-double-colon-form-field"
    :label="'Welche Symptome weist der Patient/die Patientin auf?'"
    :labelCol="{ div: 24 }"
    :wrapperCol="{ div: 24 }"
  >
    <a-checkbox-group v-decorator="['symptoms', { initialValue: symptoms }]">
      <a-row>
        <a-col
          :key="symptom.value"
          :span="symptom.value === 'LOSS_OF_SENSE_OF_SMELL_TASTE' ? 12 : 6"
          v-for="symptom in SYMPTOMS"
        >
          <a-checkbox :value="symptom.value">
            {{ symptom.label }}
          </a-checkbox>
        </a-col>
      </a-row>
    </a-checkbox-group>
    <div style="display: flex; align-items: center; align-self: stretch;">
      <a-checkbox
        v-model="showOtherSymptoms"
        v-decorator="['showOtherSymptoms', { initialValue: showOtherSymptoms }]"
        style="flex: 0 0 auto;"
      >
        Andere:
      </a-checkbox>
      <a-form-item style="flex: 1 1 100%; margin-bottom: 0; max-width: 600px;">
        <a-input
          :disabled="!showOtherSymptoms"
          v-decorator="['symptomsOther', { initialValue: symptomsOther }]"
        />
      </a-form-item>
    </div>
  </a-form-item>
</template>

<script lang="ts">
import Vue from 'vue'
import { Option } from '@/models'
import { SYMPTOMS } from '@/models/symptoms'

export interface State {
  SYMPTOMS: Option[]
  symptoms: string[]
  symptomsOther: string
  showOtherSymptoms: boolean
}

export default Vue.extend({
  name: 'SymptomsForm',
  props: ['form', 'patient'],
  created() {
    if (this.patient) {
      for (const symptom of this.patient.symptoms) {
        if (symptom) {
          if (SYMPTOMS.some((item) => item.value === symptom)) {
            this.symptoms.push(symptom)
          } else {
            this.showOtherSymptoms = true
            this.symptomsOther = symptom
          }
        }
      }
    }
  },
  data(): State {
    return {
      SYMPTOMS,
      symptoms: [],
      symptomsOther: '',
      showOtherSymptoms: false,
    }
  },
})
</script>

<style scoped lang="scss"></style>
