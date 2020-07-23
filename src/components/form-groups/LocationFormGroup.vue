<template>
  <div class="location-form-group">
    <slot>
      <!-- Straße / Hausnummer -->
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Straße, Hausnummer" :itemSelfUpdate="true">
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
          </a-form-item>
        </a-col>
        <a-col :lg="12" :sm="24">
          <div style="display: flex;">
            <!-- #438 We cant put plz and city into a combined input because then errors on city won't be displayed -->
            <a-form-item
              label="PLZ"
              :itemSelfUpdate="true"
              style="flex: 0 0 60pt;"
              class="imis-plz-form-item"
            >
              <a-auto-complete
                ref="zip"
                class="custom-input"
                placeholder="PLZ"
                :dataSource="zips"
                optionLabelProp="value"
                @search="handleZipSearch"
                @select="(val) => handleZipSelection(val)"
                :dropdownMenuStyle="{
                  width: 'max-content',
                }"
                :tabIndex="-1"
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
            </a-form-item>
            <a-form-item
              label="Ort"
              :itemSelfUpdate="true"
              style="flex: 1 1 auto;"
              class="imis-city-form-item"
            >
              <a-input
                ref="city"
                class="custom-input"
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
            </a-form-item>
          </div>
        </a-col>
      </a-row>
      <a-row :gutter="12">
        <a-col :lg="12" :sm="24">
          <a-form-item label="Land" :selfUpdate="true">
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
              <a-select-opt-group>
                <span slot="label">Schnellzugriff</span>
                <a-select-option value="" v-if="!required">
                  Unbekannt
                </a-select-option>
                <a-select-option value="DE">
                  Deutschland
                </a-select-option>
              </a-select-opt-group>
              <a-select-opt-group>
                <span slot="label">Alle ({{ countries.length }})</span>
                <a-select-option
                  v-for="country of countries"
                  :value="country.code"
                  :key="country.code"
                >
                  {{ country.name }}
                </a-select-option>
              </a-select-opt-group>
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
import { CountryDto } from '@/api/ImisSwaggerApi'
import { getCountries } from '@/util/country-service'

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
  created() {
    getCountries().then((countries) => {
      this.countries = countries
    })
  },
  data() {
    return {
      zips: [] as ZipEntry[],
      currentZipSearch: '' as string,
      countries: [] as CountryDto[],
    }
  },
  methods: {
    withExts() {
      return typing.extended(this, typing.TypeArg<FormGroupMixin>())
    },
    initialCountry() {
      const initialData = this.initialData('country')
      if (!initialData && this.required) {
        return 'DE'
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
        country: 'DE',
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

.imis-plz-form-item {
  input {
    border-radius: 4px 0 0 4px !important;
  }
}

.imis-city-form-item {
  input {
    border-radius: 0 4px 4px 0 !important;
  }
}
</style>
