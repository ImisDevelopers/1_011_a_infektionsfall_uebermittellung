<template>
  <a-form-item label="PLZ">
    <a-auto-complete @search="handlePlzSearch" @select="handlePlzSelection" v-decorator="decorator">
      <template slot="dataSource">
        <a-select-option :key="plz.fields.plz" v-for="plz in plzs">
          {{plz.fields.plz}} {{plz.fields.note}}
        </a-select-option>
      </template>
    </a-auto-complete>
  </a-form-item>
</template>

<script lang="ts">

import Vue from 'vue'
import {getPlzs, Plz} from '@/util/plz-service'

export interface State {
  plzs: Plz[];
  currentSearch: string;
}

export default Vue.extend({
  name: 'PlzInput',
  props: ['decorator', 'form'],
  data(): State {
    return {
      plzs: [],
      currentSearch: '',
    }
  },
  methods: {
    async handlePlzSearch(value: string) {
      this.currentSearch = value
      if (!value || value.length < 2) {
        this.plzs = []
      } else {
        const result = await getPlzs(value)
        if (this.currentSearch !== value) {
          // If a request takes longer, this request might be outdated since the user already changed the input
          return
        }
        if (result.length === 1) {
          this.setPLZ(result[0])
          this.plzs = []
        } else {
          this.plzs = result
        }
      }
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
