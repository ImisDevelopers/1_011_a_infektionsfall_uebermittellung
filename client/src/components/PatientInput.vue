<template>
  <a-form-item :label="label">
    <a-auto-complete @search="handleSearch" placeholder="Suche über ID, Name, Telefon, Mail" v-decorator="validation"
                     @select="emitSelection" :defaultActiveFirstOption="false">
      <template slot="dataSource">
        <a-select-option v-for="patient in result" :key="patient.id">
          {{patient.firstName}} {{patient.lastName}} ({{patient.id}})
        </a-select-option>
      </template>
    </a-auto-complete>
  </a-form-item>
</template>

<script lang="ts">

import Api from '@/api'
import Vue from 'vue'
import { Patient } from '@/api/SwaggerApi'

/**
 * Autocomplete for Patients
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  result: Patient[];
}

export default Vue.extend({
  name: 'PatientInput',
  props: ['validation', 'label', 'form'],
  data(): State {
    return {
      result: [],
    }
  },
  methods: {
    async handleSearch(value: string) {
      let result: Patient[]
      if (!value || value.length < 2) {
        result = []
      } else {
        result = await Api.patients.queryPatientsSimpleUsingPost({
          query: value,
          offsetPage: 0,
          pageSize: 10,
          order: 'asc',
          orderBy: 'lastName',
        })
      }
      this.result = result
    },
    emitSelection(value: string) {
      this.$emit('select', value)
    },
  },
})
</script>

<style>

</style>
