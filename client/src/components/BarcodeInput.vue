<template>
	<div>
		<a-input v-bind="$props" v-model="testId">
			<a-tooltip slot="suffix" title="Scan using your Camera">
				<a-icon type="camera" style="color: rgba(0,0,0,.45)" v-on:click="showScanner = true" />
			</a-tooltip>
		</a-input>
		<BarcodeScanner v-if="showScanner" @result="onResult" />
	</div>

</template>

<script>

import BarcodeScanner from "./BarcodeScanner";

export default {
  name: 'BarcodeInput',
	props: ['placeholder', 'v-decorators'],
  components: {BarcodeScanner},
  data() {
		return {
			testId: '',
			showScanner: false,
		}
	},
	methods: {
		onResult(result) {
			if (result != null) {
				this.testId = result;
			}
			this.showScanner = false;
		},
	}
}
</script>

<style>

</style>