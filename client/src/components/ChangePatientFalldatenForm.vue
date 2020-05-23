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
      <h4>Symptome</h4>
      <SymptomsForm :form="form" :patient="patient" />
      <a-divider />
      <h4>Vorerkrankungen und Risikofaktoren</h4>
      <PreIllnessesForm :form="form" :patient="patient" />
      <a-divider />
      <h4>Exposition</h4>
      <ExpositionForm :form="form" />
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import Vue from 'vue'
import Api from '@/api'
import { patientMapper } from '@/store/modules/patients.module'
import SymptomsForm from '@/components/SymptomsForm.vue'
import PreIllnessesForm from '@/components/PreIllnessesForm.vue'
import ExpositionForm from '@/components/ExpositionForm.vue'

export default Vue.extend({
  name: 'ChangePatientFalldatenForm',
  props: ['visible', 'patient'],
  components: {
    SymptomsForm,
    PreIllnessesForm,
    ExpositionForm,
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
        Api.updatePatientUsingPut(values)
          .then((updatedPatient) => {
            this.setPatient(updatedPatient)
            this.$notification.success({
              message: 'Falldaten erfolgreich aktualisiert',
              description: '',
            })
            this.form.resetFields()
            this.$emit('create')
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
