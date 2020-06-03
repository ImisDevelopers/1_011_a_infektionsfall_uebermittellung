<template>
  <a-select
    ref="input"
    v-bind="$attrs"
    v-on="externListeners"
    :defaultActiveFirstOption="false"
    :placeholder="placeholder"
    :showSearch="true"
    :showArrow="false"
    :filterOption="filterOption"
    :value="value"
    @select="emitPatient"
    @search="handleSearch"
  >
    <a-select-option v-for="patient in patients" :key="patient.id">
      {{ patient.firstName }} {{ patient.lastName }} ({{ patient.id }})
    </a-select-option>
  </a-select>
</template>

<script lang="ts">
import Api from '@/api'
import mixins from 'vue-typed-mixins'
import { Patient } from '@/api/SwaggerApi'
import { FormControlMixin } from '@/util/forms'

declare interface State {
  patients: Patient[]
}

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export default mixins(FormControlMixin).extend({
  name: 'PatientInput',
  inheritAttrs: false,
  props: {
    placeholder: { default: 'Suche über ID, Name, Telefon, Mail' },
    value: { default: '' },
    filterOption: { default: false },
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
      patients: [],
    }
  },
  computed: {
    externListeners() {
      const { search, ...listeners } = this.$listeners
      return listeners
    },
  },
  methods: {
    getPatientLabel(patientData: Patient) {
      return `${patientData.firstName} ${patientData.lastName} (${patientData.id})`
    },
    emitPatient(patientId: string) {
      this.$emit(
        'selectPatient',
        this.patients.filter((p) => p.id === patientId)[0]
      )
    },
    async handleSearch(value: string) {
      if (!value || value.length < 2) {
        this.patients = []
      } else {
        this.patients = await Api.queryPatientsSimpleUsingPost({
          query: value,
          offsetPage: 0,
          pageSize: 10,
          order: 'asc',
          orderBy: 'lastName',
        })
      }
    },
  },
})
</script>

<style scoped lang="scss"></style>
