<template>
  <a-date-picker
    v-bind="$attrs"
    v-on="$listeners"
    :value="value"
    :format="format"
    :open="open || dateOfBirthPickerOpen"
    @focus="datePickerFocused"
    @openChange="openChanged"
    :placeholder="placeholder"
  />
</template>

<script lang="ts">
import mixins from 'vue-typed-mixins'
import { FormControlMixin } from '@/util/forms'

/**
 * Input that supports 1d and 2d Barcodes
 *
 * props:
 * - validation: put in v-decorator object
 */

export interface State {
  dateOfBirthPickerOpen: boolean
  wasJustFocused: boolean
}

export default mixins(FormControlMixin).extend({
  name: 'DateInput',
  mixins: [FormControlMixin],
  model: {
    prop: 'value',
    event: 'change',
  },
  props: {
    value: { default: undefined },
    open: { default: false },
    placeholder: { default: 'z.B. 28.02.1986' },
    format: { default: () => ['DD.MM.YYYY', 'D.M.YYYY'] },
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

<style scoped lang="scss"></style>
