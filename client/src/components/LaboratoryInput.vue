<template>
  <a-form-item :label="label">
    <a-select ref="input"
      :disabled="disabled"
      @search="handleSearch"
      placeholder="Suche über ID, Name, Stadt"
      v-decorator="validation"
      defaultActiveFirstOption="false"
      :showSearch="true"
      :showArrow="false"
      filterOption="false"
      >
        <a-select-option :key="laboratory.id" v-for="laboratory in result">
          {{laboratory.name}} ({{laboratory.city}})
        </a-select-option>
    </a-select>
  </a-form-item>
</template>

<script lang="ts">

import Api from '@/api'
import Vue from 'vue'
import { Laboratory } from '@/api/SwaggerApi'

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
  props: ['initialLabs', 'validation', 'label', 'form', 'disabled'],
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
        result = await Api.queryAllLaboratoriesUsingGet({ id: value })
      }
      this.result = result
    },
  },
})
</script>

<style scoped lang="scss">

</style>
