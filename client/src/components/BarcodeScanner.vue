<template>
	<div class="scanner-wrapper">
		<video
			id="video"
			width="100%"
			height="100%"
			style="border: 1px solid gray"
		></video>
		<div class="result" v-if="result.length > 0">
			<p>{{result}}</p>
			<div class="buttons">
				<a-button type="secondary" v-on:click="useResult">Scan new result</a-button>
				<a-button type="primary" v-on:click="useResult">Use</a-button>
			</div>
		</div>
		<div class="scanner-input">
			<a-select @change="handleDeviceChange" labelInValue :value="device">
				<a-select-option v-for="d in devices" :key="d.deviceId">{{d.label}}</a-select-option>
			</a-select>
			<div class="cancel-button" v-if="result.length === 0">
				<a-button type="secondary" v-on:click="onCancel">Cancel</a-button>
			</div>
		</div>

	</div>
</template>
<script>
import { BrowserMultiFormatReader } from "@zxing/library";
export default {
  name: "BarcodeScanner",
  data(){
    const codeReader = new BrowserMultiFormatReader();
    return {
      codeReader,
      devices: [],
			device: '',
			result: '',
    }
	},
  methods: {
    handleDeviceChange(device) {
			this.device = device
			this.startScanning()
		},
		startScanning() {
      if (this.device) {
				this.codeReader.decodeOnceFromVideoDevice(this.device.key, 'video')
					.then(result => {
						this.result = result.text;
						// this.startScanning()  # TODO should it stop after first detection?
					})
      }
		},
		useResult() {
      this.$emit('result', this.result);
		},
		onCancel() {
      this.$emit('result');
		}
	},
  created: async function() {
    try {
			this.devices = await this.codeReader.listVideoInputDevices();
			this.device = {
				key: this.devices[0].deviceId,
				label: this.devices[0].label,
      };
			this.startScanning();
		} catch (err) {
      console.log(err);
		}


  },
};
</script>
<style>
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
		width: auto	;
		margin: 0 1em;
	}
</style>
