<template>
  <div class="edit-exposure-contact">
    <a-form-item style="display: none;"
      :selfUpdate="true">
      <a-input type="hidden"
        v-decorator="[ formInputKey('id') ]"/>
    </a-form-item>
    <a-row :style="`display: ${showOriginatorPatient ? 'unset' : 'none'}`">
      <!-- Originating and Contact Names -->
      <a-form-item
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
              ], initialValue: undefined
          }]"/>
      </a-form-item>

      <a-divider/>

    </a-row>

    <h4>Kontaktperson</h4>

    <a-form-item style="display: none;"
      :selfUpdate="true">
      <a-input type="hidden"
        v-decorator="[ formInputKey('contact') ]"/>
    </a-form-item>

    <transition name="fading" mode="out-in">
      <div key="manual-contact" v-if="!contact">
        <a-row :gutter="8">

          <a-col :md="12">
            <a-form-item label="Nachname"
              :selfUpdate="true">
              <a-input
                v-bind="inputProps.contactLastName"
                @keyup='fetchPropositions'
                v-decorator="[ formInputKey('contactLastName'), {
                  rules: [],
                }]"/>
            </a-form-item>
          </a-col>
          <a-col :md="12">
            <a-form-item label="Vorname"
              :selfUpdate="true">
              <a-input
                v-bind="inputProps.contactFirstName"
                @keyup='fetchPropositions'
                v-decorator="[ formInputKey('contactFirstName'), {
                  rules: [],
                }]"/>
            </a-form-item>
          </a-col>
          <a-col :md="12">
            <a-form-item label="Geburtsdatum"
              :selfUpdate="true">
              <date-input
                v-bind="inputProps.dateOfContact"
                @change='fetchPropositions'
                :disabledDate="date => date.isAfter(moment())"
                v-decorator="[ formInputKey('contactDateOfBirth'), {
                  rules: [],
                }]"/>
            </a-form-item>
          </a-col>

        </a-row>

        <transition name="fading">
          <div v-show="patientPropositions.length > 0">

            <h4>Vorschläge</h4>

            <a-list :grid="{ gutter: 16, column: 1 }" :data-source="patientPropositions" itemLayout="horizontal">
              <a-list-item slot="renderItem" slot-scope="proposedPatient" class="proposition-card">

                <a-card :title="`${proposedPatient.lastName}, ${proposedPatient.firstName}`" size="small" :key="proposedPatient.id">
                  <a-button slot="extra" ghost size="small" type="primary" @click="useProposition(proposedPatient)" block>verwenden</a-button>
                  <a-descriptions  layout="horizontal" :column="1" size="small">
                    <a-descriptions-item label="Geburtsdatum">
                      {{moment(proposedPatient.dateOfBirth).format('DD.MM.YYYY')}}
                    </a-descriptions-item>
                    <a-descriptions-item label="Adresse">
                      {{proposedPatient.street}} {{proposedPatient.houseNumber}}, {{proposedPatient.zip}} {{proposedPatient.city}}
                    </a-descriptions-item>
                  </a-descriptions>
                </a-card>

              </a-list-item>
            </a-list>

          </div>
        </transition>

        <a-divider/>

      </div>

      <div key="selected-contact" v-else style="margin-bottom: 20px;">
        <a-card :title="`${contact.lastName}, ${contact.firstName}`" size="small">
          <a-button slot="extra" ghost size="small" type="primary" @click="removeProposition()">manuell eingeben</a-button>
          <a-descriptions  layout="horizontal" :column="1" size="small">
            <a-descriptions-item label="Geburtsdatum">
              {{moment(contact.dateOfBirth).format('DD.MM.YYYY')}}
            </a-descriptions-item>
            <a-descriptions-item label="Adresse">
              {{contact.street}} {{contact.houseNumber}}, {{contact.zip}} {{contact.city}}
            </a-descriptions-item>
          </a-descriptions>
        </a-card>
      </div>
    </transition>

    <h4>Kontaktdetails</h4>

    <a-row :gutter="8">
      <!-- When and How -->
      <a-col :md="12">
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
      <a-col :md="12">
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
import mixins from 'vue-typed-mixins'
import moment from 'moment'

import Api from '@/api'
import DateInput from '@/components/DateInput.vue'
import PatientInput from '@/components/PatientInput.vue'
import { FormGroupMixin } from '@/util/forms.ts'
import { Patient } from '@/api/SwaggerApi'

const exposureContexts = [
  'Haushaltskontakt',
  'Berufskontakt',
  'Kontakt in Gemeinschaftseinrichtung',
]

interface State {
  contexts: string[];
  patientPropositions: Patient[];
  contact?: Patient;
}

export default mixins(FormGroupMixin).extend({
  name: 'EditExposureFormGroup',
  components: {
    DateInput,
    PatientInput,
  },
  inputKeys: ['id', 'source', 'contact', 'contactFirstName', 'contactLastName', 'contactDateOfBirth', 'dateOfContact', 'context', 'comment'],
  props: {
    showOriginatorPatient: { default: true },
    disableOriginatorPatient: { default: false },
  },
  data(): State {
    return {
      contexts: exposureContexts,
      patientPropositions: [],
      contact: undefined,
    }
  },
  watch: {
    contact(c: Patient) {
      if (c) {
        (this as any).setData({ contact: c.id })
      }
    },
  },
  methods: {
    moment,
    filterContacts(inputVal: string, option: any): boolean {
      return option.key !== (this as any).getSingleValue('source')
    },
    filterSources(inputVal: string, option: any): boolean {
      return option.key !== (this as any).getSingleValue('contact')
    },
    async fetchPropositions() {
      const query = [(this as any).getSingleValue('contactFirstName'), (this as any).getSingleValue('contactLastName')].filter(u => u).join(' ').trim()
      if (!query) {
        return
      }
      const propositions = await Api.queryPatientsSimpleUsingPost({
        query,
        order: 'asc',
        orderBy: 'lastName',
      })
      this.patientPropositions = propositions.slice(0, 3)
    },
    useProposition(contact: Patient) {
      this.contact = contact
    },
    removeProposition() {
      this.contact = undefined
      this.patientPropositions = []
    },
  },
})
</script>

<style lang="scss" scoped>
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
