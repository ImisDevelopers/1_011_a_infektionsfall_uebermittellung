<template>
  <div class="location-form-group">
    <slot>
      <!-- Straße / Hausnummer -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Straße, Hausnummer">
            <a-input-group compact>
              <a-input
                ref="street"
                class="custom-input"
                style="width: calc(100% - 40pt);"
                placeholder="Straße"
                v-decorator="[keys.street, {
                  rules: [{
                    required: $props.required!==false,
                    message: 'Bitte Straße eingeben',
                  }],
                  initialValue: initialData('street'),
                }]"
              />
              <a-input
                class="custom-input"
                style="width: 40pt;"
                placeholder="HNr."
                v-decorator="[keys.houseNumber, {
                  rules: [{
                    message: 'Bitte Hausnummer eingeben',
                  }],
                  initialValue: initialData('houseNumber'),
                }]"
              />
            </a-input-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="PLZ, Ort">
            <a-input-group compact>
              <a-auto-complete
                ref="zip"
                class="custom-input"
                style="width: 50pt;"
                placeholder="PLZ"
                :dataSource="zips"
                optionLabelProp="value"
                @search="handleZipSearch"
                @select="(val) => handleZipSelection(val)"
                :dropdownMenuStyle="{
                  width: 'max-content'
                }"
                v-decorator="[keys.zip, {
                  rules: [{
                    required: $props.required!==false,
                    message: 'Bitte PLZ eingeben',
                  }],
                  initialValue: initialData('zip'),
                }]" />
              <a-input
                ref="city"
                class="custom-input"
                style="width: calc(100% - 60pt);"
                placeholder="Ort"
                v-decorator="[keys.city, {
                  rules: [{
                    required: $props.required!==false,
                    message: 'Bitte Ort eingeben',
                  }],
                  initialValue: initialData('city'),
                }]" />
            </a-input-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Land">
            <a-input
              ref="country"
              class="custom-input"
              v-decorator="[keys.country, {
                rules: [
                  { required: $props.required!==false, message: 'Bitte Land eingeben' }
                ],
                initialValue: initialCountry(),
              }]" />
          </a-form-item>
        </a-col>
      </a-row>
    </slot>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import { getPlzs, Plz } from '@/util/plz-service'

interface Input extends Vue {
  value: string;
  focus: () => void;
}

interface ZipEntry {
  text: string;
  value: string;
  zipData: Plz;
}

export default Vue.extend({
  name: 'LocationFormGroup',
  props: [
    'form',
    'required',
    'data',
    'streetInputKey',
    'houseNumberInputKey',
    'zipInputKey',
    'cityInputKey',
    'countryInputKey',
    'inputKeyPrefix',
    'useInputKeysForData',
  ],
  data() {
    const keynames = [
      'street',
      'houseNumber',
      'zip',
      'city',
      'country',
    ]
    const prefixed = (val: string) => {
      if (this.$props.inputKeyPrefix) {
        const capitalized = val.charAt(0).toUpperCase() + val.substring(1)
        return this.$props.inputKeyPrefix + capitalized
      } else {
        return val
      }
    }

    const keys = Object.fromEntries(Object.values(keynames).map(keyname => {
      return [keyname, this.$props[keyname + 'InputKey'] || prefixed(keyname)]
    }))
    const dataKeys = Object.fromEntries(Object.values(keynames).map(keyname => {
      return [keyname, this.$props.useInputKeysForData ? keys[keyname] : keyname]
    }))

    return {
      keys,
      dataKeys,
      zips: [] as ZipEntry[],
      currentZipSearch: '' as string,
    }
  },
  methods: {
    initialCountry() {
      const initialData = this.initialData('country')
      if (!initialData && this.required) {
        return 'Deutschland'
      } else {
        return initialData
      }
    },
    initialData(keyname: string) {
      return this.$props.data ? this.$props.data[this.keys[keyname]] : null
    },
    updateByZipData(plzData: Plz) {
      this.$props.form.setFieldsValue({
        [this.keys.zips]: plzData.fields.plz,
        [this.keys.city]: plzData.fields.note,
        [this.keys.country]: 'Deutschland',
      })
    },
    async handleZipSearch(value: string) {
      this.currentZipSearch = value
      if (!value || value.length < 2) {
        this.zips = []
      } else {
        const result: Plz[] = await getPlzs(value)
        if (this.currentZipSearch !== value) {
          // If a request takes longer, this request might be outdated since the user already changed the input
          return
        }
        if (result.length === 0) {
          return
        }
        if (result.length === 1) {
          setTimeout(() => {
            this.updateByZipData(result[0]);
            (this.$refs.city as Input).focus()
          }, 0)
          this.zips = []
        } else {
          this.zips = result.map((elem: Plz) => ({
            text: `${elem.fields.plz} ${elem.fields.note}`,
            value: elem.fields.plz,
            zipData: elem,
          })) as ZipEntry[]

          const tweakDropdown = () => {
            // Adjust width of PLZ dropdown
            const ddRootId = document.evaluate(
              'string(./descendant-or-self::*[@aria-controls and contains(@class, "ant-select-selection")]/@aria-controls)',
              (this.$refs.zip as Vue).$el,
              null, XPathResult.STRING_TYPE).stringValue

            let ddRoot = document.getElementById(ddRootId)
            if (ddRoot) {
              ddRoot = ddRoot.parentElement as HTMLElement
              ddRoot.style.width = 'max-content'
            } else {
              Vue.nextTick(tweakDropdown)
            }
          }

          Vue.nextTick(tweakDropdown)
        }
      }
    },
    handleZipSelection(value: string) {
      const zipEntry = this.zips.find((entry: ZipEntry) => entry.value === value) as ZipEntry
      if (zipEntry) {
        setTimeout(() => {
          this.updateByZipData(zipEntry.zipData);
          (this.$refs.city as Input).focus()
        }, 0)
      }
    },
  },
})
</script>

<style lang="scss">
  .ant-select-dropdown-menu-item {
    overflow: unset;
    text-overflow: unset;
  }

  .location-form-group {
    input.custom-input, .custom-input input {
      text-align: left;
    }
  }
</style>
