<template>
  <a-form-item :label="label">
    <a-auto-complete @search="handleSearch" :placeholder="placeholder" v-decorator="validation">
      <template slot="dataSource">
        <a-select-option v-for="testId in result" :key="testId">{{testId}}</a-select-option>
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
  showScanner: boolean;
}

export default Vue.extend({
  name: 'BarcodeInput',
  props: ['placeholder', 'validation', 'label', 'form'],
  data(): State {
    return {
      result: [],
      showScanner: false,
    }
  },
  methods: {
    async handleSearch(value: string) {
      let result: string[]
      if (!value || value.length < 2) {
        result = []
      } else {
        const labTests = await Api.labtests.queryLabTestsByIdUsingGet({ labTestIdQuery: value })
        result = labTests.map(labTest => labTest.testId)
      }
      this.result = result
    },
    // TODO: Do we need Barcode Scanner? Or will users always use the keyboard to type the test id?
    // onResult(result) {
    //   if (result != null) {
    //     this.form.setFieldsValue({
    //       [this.validation[0]]: result,
    //     })
    //   }
    //   this.showScanner = false
    // },
  },
})
</script>

<style>

</style>
