<template>
  <a-form-item :label="label">
    <a-auto-complete @search="handleSearch" placeholder="Suche über ID" v-decorator="validation">
      <template slot="dataSource">
        <a-select-option :key="testId" v-for="testId in result">{{testId}}</a-select-option>
      </template>
    </a-auto-complete>
  </a-form-item>
</template>

<script lang="ts">

import Api from '@/api'
import Vue from 'vue'

/**
 * Input that supports 1d and 2d Barcodes
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  result: string[];
}

export default Vue.extend({
  name: 'TestInput',
  props: ['validation', 'label', 'form'],
  data(): State {
    return {
      result: [],
    }
  },
  methods: {
    async handleSearch(value: string) {
      let result: string[]
      if (!value || value.length < 2) {
        result = []
      } else {
        const labTests = await Api.api.queryLabTestsByIdUsingGet({ labTestIdQuery: value })
        result = labTests.map(labTest => labTest.testId)
      }
      this.result = result
    },
  },
})
</script>

<style>

</style>
