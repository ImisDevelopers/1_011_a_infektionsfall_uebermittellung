<template>
  <div>
    <slot>
      <!-- Straße / Hausnummer -->
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Straße, Hausnummer">
            <a-input-group compact>
              <a-input
                ref="street"
                style="width: calc(100% - 40pt);"
                placeholder="Straße"
                v-decorator="[keys.street, { rules: [{
                          required: $props.required === false ? false : true,
                          message: 'Bitte Straße eingeben',
                        }] }]"
              />
              <a-input
                style="width: 40pt;"
                placeholder="HNr."
                v-decorator="[keys.houseNum, { rules: [{
                          required: $props.required === false ? false : true,
                          message: 'Bitte Hausnummer eingeben',
                        }]}]"
              />
            </a-input-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="PLZ, Ort">
            <a-input-group compact>
              <a-auto-complete
                ref="plz"
                style="width: 50pt;"
                placeholder="PLZ"
                :dataSource="plzs"
                optionLabelProp="value"
                @search="handlePlzSearch"
                @select="(val) => handlePlzSelection(val)"
                :dropdownMenuStyle="{
                  width: 'max-content'
                }"
                v-decorator="[keys.plz, { rules: [{
                  required: $props.required === false ? false : true,
                  message: 'Bitte PLZ eingeben',
                }]}]" />
              <a-input
                ref="city"
                style="width: calc(100% - 60pt);"
                placeholder="Ort"
                v-decorator="[keys.city, { rules: [{
                required: $props.required === false ? false : true,
                message: 'Bitte Ort eingeben',
              }] }]" />
            </a-input-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :lg="12" :sm="24">
          <a-form-item label="Land">
            <a-input
              ref="country"
              v-decorator="[keys.country, { rules: [
                { required: $props.required === false ? false : true, message: 'Bitte Land eingeben' }
                ]}]"/>
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

interface PlzEntry {
  text: string;
  value: string;
  plzData: Plz;
}

/*
export interface State {
  keys: {
    street: string;
    houseNum: string;
    plz: string;
    city: string;
    country: string;
  };
  plzs: PlzEntry[];
  currentPlzSearch: string;
}
*/

export default Vue.extend({
  name: 'LocationFormGroup',
  props: [
    'form',
    'required',
    'streetInputKey',
    'houseNumInputKey',
    'zipInputKey',
    'cityInputKey',
    'countryInputKey',
    'inputKeyPrefix',
  ],
  data() {
    const prefixed = (val: string) => {
      if (this.$props.inputKeyPrefix) {
        const capitalized = val.charAt(0).toUpperCase() + val.substring(1)
        return this.$props.inputKeyPrefix + capitalized
      } else {
        return val
      }
    }

    return {
      keys: {
        street: this.$props.streetInputKey || prefixed('street') as string,
        houseNum: this.$props.houseNumInputKey || prefixed('houseNumber') as string,
        plz: this.$props.zipInputKey || prefixed('zip') as string,
        city: this.$props.cityInputKey || prefixed('city') as string,
        country: this.$props.countryInputKey || prefixed('country') as string,
      },
      plzs: [] as PlzEntry[],
      currentPlzSearch: '' as string,
    }
  },
  methods: {
    updateByPlzData(plzData: Plz) {
      this.$props.form.setFieldsValue({
        [this.keys.plz]: plzData.fields.plz,
        [this.keys.city]: plzData.fields.note,
        [this.keys.country]: 'Deutschland',
      })
    },
    async handlePlzSearch(value: string) {
      this.currentPlzSearch = value
      if (!value || value.length < 2) {
        this.plzs = []
      } else {
        const result: Plz[] = await getPlzs(value)
        if (this.currentPlzSearch !== value) {
          // If a request takes longer, this request might be outdated since the user already changed the input
          return
        }
        if (result.length === 1) {
          setTimeout(() => {
            this.updateByPlzData(result[0]);
            (this.$refs.city as Input).focus()
          }, 0)
          this.plzs = []
        } else {
          this.plzs = result.map((elem: Plz) => ({
            text: `${elem.fields.plz} ${elem.fields.note}`,
            value: elem.fields.plz,
            plzData: elem,
          })) as PlzEntry[]

          const tweakDropdown = () => {
            // Adjust width of PLZ dropdown
            const ddRootId = document.evaluate(
              'string(./descendant-or-self::*[@aria-controls and contains(@class, "ant-select-selection")]/@aria-controls)',
              (this.$refs.plz as Vue).$el,
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
    handlePlzSelection(value: string) {
      const plzEntry = this.plzs.find((entry: PlzEntry) => entry.value === value) as PlzEntry
      if (plzEntry) {
        setTimeout(() => {
          this.updateByPlzData(plzEntry.plzData);
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
</style>
