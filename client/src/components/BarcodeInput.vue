<template>
  <a-form-item :label="label">
    <a-input v-decorator="validation"></a-input>
  </a-form-item>
</template>

<script>
/**
 * Input that supports 1d and 2d Barcodes
 *
 * props:
 * - validation: put in v-decorator object
 */

export default {
  name: 'BarcodeInput',
  props: ['placeholder', 'validation', 'label', 'form'],
  data() {
    return {
      showScanner: false,
    }
  },
  methods: {
    onResult(result) {
      if (result != null) {
        this.form.setFieldsValue({
          [this.validation[0]]: result,
        })
      }
      this.showScanner = false
    },
  },
}
</script>

<style>

</style>
