<template>
  <a-form-item label="PLZ">
    <a-auto-complete @search="handlePlzSearch" v-decorator="decorator" @select="handlePlzSelection">
      <template slot="dataSource">
        <a-select-option v-for="plz in plzs" :key="plz.fields.plz">
          {{plz.fields.plz}} {{plz.fields.note}}
        </a-select-option>
      </template>
    </a-auto-complete>
  </a-form-item>
</template>

<script lang="ts">

import Vue from 'vue'
import { getPlzs, Plz } from '@/util/plz-service'

export interface State {
  plzs: Plz[];
}

export default Vue.extend({
  name: 'PlzInput',
  props: ['decorator', 'form'],
  data(): State {
    return {
      plzs: [],
    }
  },
  methods: {
    async handlePlzSearch(value: string) {
      let result: Plz[]
      if (!value || value.length < 2) {
        result = []
      } else {
        result = await getPlzs(value)
        if (result.length === 1) {
          this.setPLZ(result[0])
        }
      }
      this.plzs = result
    },
    handlePlzSelection(value: string) {
      const plz = this.plzs.find(plz => plz.fields.plz === value)
      if (plz) {
        this.setPLZ(plz)
      }
    },
    setPLZ(plz: Plz) {
      this.$emit('plzChanged', plz)
      plz.fields.note = '' // So city does not display in plz input
    },
  },
})
</script>

<style>

</style>
