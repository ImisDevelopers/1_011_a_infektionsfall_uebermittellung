<template>
  <a-form-item :label="label">
    <a-input v-decorator="validation">
<!--      <a-tooltip slot="suffix" title="Scan Barcode/QR using your Camera">-->
<!--        <a-icon type="camera" style="color: rgba(0,0,0,.45)" v-on:click="showScanner = true" />-->
<!--      </a-tooltip>-->
    </a-input>
<!--    <BarcodeScanner v-if="showScanner" @result="onResult" />-->
  </a-form-item>
</template>

<script>
/**
   * Input that supports 1d and 2d Barcodes
   *
   * props:
   * - validation: put in v-decorator object
   */
// import BarcodeScanner from './BarcodeScanner'

export default {
  name: 'BarcodeInput',
  props: ['placeholder', 'validation', 'label', 'form'],
  // components: { BarcodeScanner },
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
