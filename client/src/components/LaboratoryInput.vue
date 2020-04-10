<template>
  <a-form-item :label="label">
    <a-auto-complete @search="handleSearch" placeholder="Suche über ID, Name, Stadt" v-decorator="validation">
      <template slot="dataSource">
        <a-select-option v-for="laboratory in result" :key="laboratory.id">
          {{laboratory.name}} ({{laboratory.city}})
        </a-select-option>
      </template>
    </a-auto-complete>
  </a-form-item>
</template>

<script lang="ts">

import Api from '@/api'
import Vue from 'vue'
import { Laboratory } from '@/api/SwaggerApi'
import { updateLocale } from 'moment'

/**
 * Input that supports 1d and 2d Barcodes
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  result: Laboratory[];
}

export default Vue.extend({
  name: 'LaboratoryInput',
  props: ['initialLabs', 'validation', 'label', 'form'],
  data(): State {
    return {
      result: [],
    }
  },
  watch: {
    initialLabs: function(labs) {
      if (labs && labs.length > 0) {
        this.result = labs
      }
    },
  },
  methods: {
    async handleSearch(value: string) {
      let result: Laboratory[]
      if (!value || value.length < 2) {
        result = []
      } else {
        result = await Api.institutions.queryAllLaboratoriesUsingGet({ id: value })
      }
      this.result = result
    },
  },
})
</script>

<style>

</style>
