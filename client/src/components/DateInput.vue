<template>
  <a-form-item :label="label">
    <a-date-picker
      :format="$props.dateFormat"
      :open="dateOfBirthPickerOpen"
      @focus="datePickerFocused"
      @openChange="openChanged"
      placeholder="TT.MM.JJJJ (z.B. 28.02.1986)"
      style="width: 100%"
      v-bind="$attrs"
      v-decorator="decorator"
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
}

export default Vue.extend({
  name: 'LaboratoryInput',
  props: {
    label: { default: undefined },
    dateFormat: { default: 'DD.MM.YYYY' },
    decorator: { default: undefined },
  },
  data(): State {
    return {
      dateOfBirthPickerOpen: false,
      wasJustFocused: false,
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

<style scoped lang="scss">

</style>
