<template>
  <a-modal
    :visible="visible"
    title="Falldaten ändern"
    okText="Speichern"
    cancelText="Abbrechen"
    @cancel="
      () => {
        $emit('cancel')
      }
    "
    @ok="save"
    width="1000px"
  >
    <a-form :form="form" :layout="'vertical'" :wrapperCol="{ span: 24 }">
      <IllnessStatusForm :form="form" :patient="patient" />
      <a-divider />
      <h4>Symptome</h4>
      <SymptomsForm :form="form" :patient="patient" />
      <a-divider />
      <h4>Vorerkrankungen und Risikofaktoren</h4>
      <PreIllnessesForm :form="form" :patient="patient" />
      <a-divider />
      <h4>Exposition</h4>
      <ExposureForm :form="form" :patient="patient" />
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import Vue from 'vue'
import Api from '@/api'
import { patientMapper } from '@/store/modules/patients.module'
import SymptomsForm from '@/components/form-groups/SymptomsForm.vue'
import PreIllnessesForm from '@/components/form-groups/PreIllnessesForm.vue'
import ExposureForm from '@/components/form-groups/ExposureForm.vue'
import IllnessStatusForm from '@/components/form-groups/IllnessStatusForm.vue'
import moment from 'moment'

export default Vue.extend({
  name: 'ChangePatientFalldatenForm',
  props: ['visible', 'patient'],
  components: {
    SymptomsForm,
    PreIllnessesForm,
    ExposureForm,
    IllnessStatusForm,
  },
  data() {
    return {
      form: this.$form.createForm(this, { name: 'patient' }),
    }
  },
  methods: {
    ...patientMapper.mapMutations({
      setPatient: 'setPatient',
    }),
    save() {
      this.form.validateFields((err: any, values: any) => {
        if (err) {
          console.warn(err)
          return
        }
        values = { ...this.patient, ...values }

        // Illness and Status
        values.dateOfIllness = (values.dateOfIllness || moment()).format(
          'YYYY-MM-DD'
        )
        values.dateOfReporting = (values.dateOfReporting || moment()).format(
          /*
            ToDo: dateOfReporting is part of the createPatientDTO but not of Patient. Hence it works when creating a patient, but not on update.
          */
          'YYYY-MM-DD'
        )
        console.log(values.patientHospitalized) // ToDo When patientHospitalized is initially true, this is undefined and evaluated to false if the user does not actively un-tick and tick again.
        if (values.patientHospitalized) {
          values.dateOfHospitalization = values.dateOfHospitalization.format(
            'YYYY-MM-DD'
          )
        } else {
          values.dateOfHospitalization = null
          values.onIntensiveCareUnit = null
        }

        // Symptoms
        if (!values.symptoms) {
          values.symptoms = []
        }
        if (values.showOtherSymptoms && values.symptomsOther) {
          values.symptoms.push(values.symptomsOther)
        }

        // Illnesses
        if (!values.preIllnesses) {
          values.preIllnesses = []
        }
        if (values.showOtherPreIllnesses && values.preIllnessesOther) {
          values.preIllnesses.push(values.preIllnessesOther)
        }

        // Exposures
        if (values.exposures) {
          values.riskAreas = values.exposures
        } else {
          values.riskAreas = []
        }
        if (values.exposureLocation) {
          values.riskAreas = values.riskAreas.concat(
            values.exposureLocation.map(
              (location: string) => 'CONTACT_WITH_CORONA_' + location
            )
          )
        }

        Api.updatePatientUsingPut(values)
          .then((updatedPatient) => {
            this.setPatient(updatedPatient)
            this.$notification.success({
              message: 'Falldaten erfolgreich aktualisiert',
              description: '',
            })
            this.form.resetFields()
            this.$emit('create')
            this.$forceUpdate()
          })
          .catch((error: Error) => {
            this.$notification.error({
              message: 'Falldaten konnten nicht aktualisiert werden.',
              description: error.message,
            })
          })
      })
    },
  },
})
</script>

<style scoped lang="scss"></style>
