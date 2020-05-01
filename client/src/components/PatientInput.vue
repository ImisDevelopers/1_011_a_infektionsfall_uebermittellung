<template>
  <a-select ref="input" v-bind="$attrs" v-on="externListeners"
                   :defaultActiveFirstOption="false"
                   :placeholder="placeholder"
                   :showSearch="true"
                   :showArrow="false"
                   :filterOption="false"
                   :value="value"
                   @search="handleSearch">
    <a-select-option v-for="entry in result"
      :key="entry.value">
      {{ entry.label }}
    </a-select-option>
  </a-select>
</template>

<script lang="ts">
import Api from '@/api'
import Vue from 'vue'
import { Select } from 'ant-design-vue'
import { Patient } from '@/api/SwaggerApi'

declare interface State {
  result: {
    label?: string;
    value?: string;
  }[];
}

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export default Vue.extend({
  name: 'PatientInput',
  inheritAttrs: false,
  props: {
    placeholder: { default: 'Suche über ID, Name, Telefon, Mail' },
    value: { default: '' },
  },
  model: {
    prop: 'value',
    event: 'change',
  },
  inject: {
    FormContext: { default: () => ({}) },
  },
  data(): State {
    return {
      result: [],
    }
  },
  computed: {
    externListeners() {
      const { search, ...listeners } = this.$listeners
      return listeners
    },
  },
  watch: {
    value() {
      const hasProp = (obj: Record<string, any>, prop: string): boolean => Object.prototype.hasOwnProperty.call(obj, prop)
      if (typeof this.value === 'object' && hasProp(this.value, 'id')) {
        const patient = this.value as Patient
        this.result = [{
          label: this.getPatientLabel(patient),
          value: patient.id,
        }]
        if (hasProp(this.$attrs, 'data-__meta')) {
          this.FormContext.form.setFieldsValue({
            [this.$attrs['data-__meta'].name]: patient.id,
          })
        }
      }
    },
  },
  methods: {
    getPatientLabel(patientData: Patient) {
      return `${patientData.firstName} ${patientData.lastName} (${patientData.id})`
    },
    async handleSearch(value: string) {
      let result: Patient[]
      if (!value || value.length < 2) {
        result = []
      } else {
        result = await Api.queryPatientsSimpleUsingPost({
          query: value,
          offsetPage: 0,
          pageSize: 10,
          order: 'asc',
          orderBy: 'lastName',
        })
      }
      this.result = result.map((patient: Patient) => ({
        label: this.getPatientLabel(patient),
        value: patient.id,
      }))
    },
  },
})
</script>

<style scoped lang="scss">

</style>
