<template>
  <div class="edit-exposure-contact">
    <a-form-item style="display: none;"
      :selfUpdate="true">
      <a-input type="hidden"
        v-decorator="[ formInputKey('id') ]"/>
    </a-form-item>
    <a-row class="patients-flex">
      <!-- Originating and Contact Names -->
      <a-form-item
        :style="`display: ${$props.showOriginatorPatient ? 'unset' : 'none'}`"
        :selfUpdate="true"
        label="Ursprungspatient"
        class="patient-input">
        <patient-input
          v-bind="inputProps.source"
          :disabled="$props.disableOriginatorPatient"
          :filterOption="filterSources"
          v-decorator="[ formInputKey('source'), {
              rules: [
                { required: true, message: 'Bitte Ursprungspatienten angeben' },
              ],
          }]"/>
      </a-form-item>
      <a-icon
        v-if="$props.showOriginatorPatient && $props.showContactPatient"
        type="swap"/>
      <a-form-item
        :style="`display: ${$props.showContactPatient ? 'unset' : 'none'}`"
        :selfUpdate="true"
        label="Kontaktperson"
        class="patient-input">
        <patient-input
          v-bind="inputProps.contact"
          :disabled="$props.disableContactPatient"
          :filterOption="filterContacts"
          v-decorator="[ formInputKey('contact'), {
              rules: [
                { required: true, message: 'Bitte Kontaktperson angeben' },
              ],
          }]"/>
      </a-form-item>
    </a-row>
    <a-row>
      <!-- When and How -->
      <a-col :md="11" :sm="24">
        <a-form-item label="Datum des Kontakts"
          :selfUpdate="true">
          <date-input
            v-bind="inputProps.dateOfContact"
            :disabledDate="date => date.isAfter(moment())"
            v-decorator="[ formInputKey('dateOfContact'), {
              rules: [
                { required: true, message: 'Bitte ein gültiges Datum angeben' },
              ],
            }]"/>
        </a-form-item>
      </a-col>
      <a-col :md="2" :sm="0"></a-col>
      <a-col :md="11" :sm="24">
        <a-form-item label="Umgebung / Kontext"
          :selfUpdate="true">
          <a-auto-complete
            v-bind="inputProps.context"
            :dataSource="contexts"
            :filterOption="false"
            v-decorator="[ formInputKey('context'), {
              rules: [
                { required: true, message: 'Bitte den Umstand des Kontakts angeben' }
              ]
            }]"/>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <!-- Comment Space -->
      <a-form-item label="Sonstiges / Kommentar"
        :selfUpdate="true">
        <a-textarea
          v-bind="inputProps.comment"
          v-decorator="[ formInputKey('comment'), {
            rules: [],
          }]"/>
      </a-form-item>
    </a-row>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import mixins from 'vue-typed-mixins'
import Api from '@/api'
import moment from 'moment'

import DateInput from '@/components/DateInput.vue'
import PatientInput from '@/components/PatientInput.vue'
import { FormGroupMixin } from '@/util/forms.ts'

const exposureContexts = [
  'Haushaltskontakt',
  'Berufskontakt',
  'Kontakt in Gemeinschaftseinrichtung',
]

export default mixins(FormGroupMixin).extend({
  name: 'EditExposureFormGroup',
  components: {
    DateInput,
    PatientInput,
  },
  inputKeys: ['id', 'source', 'contact', 'dateOfContact', 'context', 'comment'],
  props: {
    showOriginatorPatient: { default: true },
    showContactPatient: { default: true },
    disableOriginatorPatient: { default: false },
    disableContactPatient: { default: false },
  },
  data() {
    return {
      contexts: exposureContexts,
    }
  },
  methods: {
    moment,
    filterContacts(inputVal: string, option: any): boolean {
      return option.key !== (this as any).getSingleValue('source')
    },
    filterSources(inputVal: string, option: any): boolean {
      return option.key !== (this as any).getSingleValue('contact')
    },
  },
} as any)
</script>

<style lang="scss">
  .edit-exposure-contact {
    .patients-flex {
      display: flex;
      flex-flow: row wrap;
      align-items: center;
      align-content: center;
      justify-content: center;
    }

    .patients-flex > * {
      flex: 1 1;
    }

    .patients-flex > .patient-input {
      min-width: 47%;
    }
  }
</style>
