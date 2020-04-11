<template>
  <a-form-item :label="label">
    <a-date-picker
      @focus="datePickerFocused"
      @openChange="openChanged"
      style="width: 100%"
      :open="dateOfBirthPickerOpen"
      :format="dateFormat"
      v-decorator="decorator"
      placeholder="TT.MM.JJJJ (z.B. 28.02.1986)"
    />
  </a-form-item>
</template>

<script lang="ts">

import Vue from 'vue'

/**
 * Input that supports 1d and 2d Barcodes
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  dateOfBirthPickerOpen: boolean;
  wasJustFocused: boolean;
  dateFormat: string;
}

export default Vue.extend({
  name: 'LaboratoryInput',
  props: ['decorator', 'label'],
  data(): State {
    return {
      dateOfBirthPickerOpen: false,
      wasJustFocused: false,
      dateFormat: 'DD.MM.YYYY',
    }
  },
  methods: {
    datePickerFocused() {
      this.dateOfBirthPickerOpen = true
      this.wasJustFocused = true
      setTimeout(() => {
        this.wasJustFocused = false
      }, 500)
    },
    openChanged(state: boolean) {
      if (!this.wasJustFocused) {
        this.dateOfBirthPickerOpen = state
      }
    },
  },
})
</script>

<style>

</style>
