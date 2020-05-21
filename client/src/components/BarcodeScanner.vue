<template>
  <div class="scanner-wrapper">
    <video
      height="100%"
      id="video"
      style="border: 1px solid gray;"
      width="100%"
    ></video>
    <div class="result" v-if="result.length > 0">
      <p>{{ result }}</p>
      <div class="buttons">
        <a-button type="secondary" v-on:click="useResult"
          >Scan new result</a-button
        >
        <a-button type="primary" v-on:click="useResult">Use</a-button>
      </div>
    </div>
    <div class="scanner-input">
      <a-select
        :value="device"
        @change="handleDeviceChange"
        labelInValue
        v-if="devices.length > 0"
      >
        <a-select-option :key="d.deviceId" v-for="d in devices">{{
          d.label
        }}</a-select-option>
      </a-select>
      <div class="cancel-button" v-if="result.length === 0">
        <a-button type="secondary" v-on:click="onCancel">Cancel</a-button>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'BarcodeScanner',
  data() {
    // const codeReader = new BrowserMultiFormatReader()
    return {
      //  codeReader,
      devices: [],
      device: '',
      result: '',
    }
  },
  methods: {
    handleDeviceChange(device: string) {
      this.device = device
      this.startScanning()
    },
    startScanning() {
      if (this.device) {
        try {
          // this.codeReader.decodeOnceFromVideoDevice(this.device.key, 'video')
          //   .then(result => {
          //     this.result = result.text
          //     // this.startScanning()  # TODO should it stop after first detection?
          //   })
        } catch (err) {
          console.log('notificaiton')
          console.log(err)
          this.$notification.error({
            message: 'No Camera',
            description: 'No Camera or no Camera Permissions',
          })
        }
      }
    },
    useResult() {
      this.$emit('result', this.result)
    },
    onCancel() {
      this.$emit('result')
    },
  },
  created: async function () {
    // try {
    //   if (!navigator.mediaDevices.enumerateDevices) {
    //     this.device = {
    //       key: undefined,
    //       label: 'Camera',
    //     }
    //   } else {
    //     this.devices = await this.codeReader.listVideoInputDevices()
    //     this.device = {
    //       key: this.devices[0].deviceId,
    //       label: this.devices[0].label,
    //     }
    //   }
    //   this.startScanning()
    // } catch (err) {
    //   console.log('notificaiton')
    //   console.log(err)
    //   this.$notification.error({
    //     title: 'No Camera',
    //     description: 'No Camera or no Camera Permissions',
    //   })
    // }
  },
})
</script>
<style scoped lang="scss">
.scanner-wrapper {
  top: 0;
  left: 0;
  background: white;
  position: fixed;
  z-index: 20;
  width: 100%;
  height: 100%;
}

.scanner-input {
  padding: 1em;
  position: absolute;
  bottom: 0;
  z-index: 22;
}

.result {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  flex-direction: column;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #000000aa;
  color: white;
  font-size: 30pt;
  margin: auto;
}

.buttons {
  display: flex;
  justify-content: space-around;
}

.buttons button {
  width: auto;
  margin: 0 1em;
}
</style>
