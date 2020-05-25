<template>
  <div class="location-form-group">
    <slot>
      <!-- Straße / Hausnummer -->
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Straße, Hausnummer" :itemSelfUpdate="true">
            <a-input-group compact>
              <a-input
                ref="street"
                class="custom-input"
                style="width: calc(100%);"
                placeholder="Straße und Hausnummer"
                v-decorator="[
                  formFieldName('street'),
                  {
                    rules: [
                      {
                        required: $props.required !== false,
                        message: 'Bitte Straße und Hausnummer eingeben',
                      },
                    ],
                    initialValue: initialData('street'),
                  },
                ]"
              />
            </a-input-group>
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <a-form-item label="PLZ, Ort" :itemSelfUpdate="true">
            <a-input-group compact>
              <a-auto-complete
                ref="zip"
                class="custom-input"
                style="width: 60pt;"
                placeholder="PLZ"
                :dataSource="zips"
                optionLabelProp="value"
                @search="handleZipSearch"
                @select="(val) => handleZipSelection(val)"
                :dropdownMenuStyle="{
                  width: 'max-content',
                }"
                v-decorator="[
                  formFieldName('zip'),
                  {
                    rules: [
                      {
                        required: $props.required !== false,
                        message: 'Bitte PLZ eingeben',
                      },
                    ],
                    initialValue: initialData('zip'),
                  },
                ]"
              />
              <a-input
                ref="city"
                class="custom-input"
                style="width: calc(100% - 60pt);"
                placeholder="Ort"
                v-decorator="[
                  formFieldName('city'),
                  {
                    rules: [
                      {
                        required: $props.required !== false,
                        message: 'Bitte Ort eingeben',
                      },
                    ],
                    initialValue: initialData('city'),
                  },
                ]"
              />
            </a-input-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Land" :itemSelfUpdate="true">
            <a-select
              ref="country"
              class="custom-input"
              placeholder="Bitte wählen..."
              v-decorator="[
                formFieldName('country'),
                {
                  rules: [
                    {
                      required: $props.required !== false,
                      message: 'Bitte Land auswählen',
                    },
                  ],
                  initialValue: initialCountry(),
                },
              ]"
            >
              <a-select-option value="BE">Belgien</a-select-option>
              <a-select-option value="DK">Dänemark</a-select-option>
              <a-select-option value="DE">Deutschland</a-select-option>
              <a-select-option value="FR">Frankreich</a-select-option>
              <a-select-option value="GR">Griechenland</a-select-option>
              <a-select-option value="UK">Großbritannien</a-select-option>
              <a-select-option value="IT">Italien</a-select-option>
              <a-select-option value="LU">Luxemburg</a-select-option>
              <a-select-option value="NL">Niederlande</a-select-option>
              <a-select-option value="AT">Österreich</a-select-option>
              <a-select-option value="PL">Polen</a-select-option>
              <a-select-option value="PT">Portugal</a-select-option>
              <a-select-option value="RU">Russland</a-select-option>
              <a-select-option value="CH">Schweiz</a-select-option>
              <a-select-option value="ES">Spanien</a-select-option>
              <a-select-option value="CZ">Tschechien</a-select-option>
              <a-select-option value="TR">Türkei</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </slot>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import mixins from 'vue-typed-mixins'
import * as typing from '@/util/typing'
import { getPlzs, Plz } from '@/util/plz-service'
import { FormGroupMixin } from '@/util/forms'

interface Input extends Vue {
  value: string
  focus: () => void
}

interface ZipEntry {
  text: string
  value: string
  zipData: Plz
}

export default mixins(FormGroupMixin).extend({
  name: 'LocationFormGroup',
  fieldIdentifiers: ['street', 'zip', 'city', 'country'],
  props: ['required', 'data'],
  data() {
    return {
      zips: [] as ZipEntry[],
      currentZipSearch: '' as string,
    }
  },
  methods: {
    withExts() {
      return typing.extended(this, typing.TypeArg<FormGroupMixin>())
    },
    initialCountry() {
      const initialData = this.initialData('country')
      if (!initialData && this.required) {
        return 'Deutschland'
      } else {
        return initialData
      }
    },
    initialData(fieldId: string) {
      return this.$props.data
        ? this.$props.data[this.withExts().formFieldName(fieldId)]
        : null
    },
    updateByZipData(plzData: Plz) {
      this.withExts().setData({
        zip: plzData.fields.plz,
        city: plzData.fields.note,
        country: 'Deutschland',
      })
    },
    async handleZipSearch(value: string) {
      this.currentZipSearch = value
      if (!value || value.length < 4) {
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
            this.updateByZipData(result[0])
            ;(this.$refs.city as Input).focus()
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
              null,
              XPathResult.STRING_TYPE
            ).stringValue

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
      const zipEntry = this.zips.find(
        (entry: ZipEntry) => entry.value === value
      ) as ZipEntry
      if (zipEntry) {
        setTimeout(() => {
          this.updateByZipData(zipEntry.zipData)
          ;(this.$refs.city as Input).focus()
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
  input.custom-input,
  .custom-input input {
    text-align: left;
  }
}
</style>
