<template>
  <a-modal
    :visible="visible"
    title="Patientenstammdaten ändern"
    okText="Speichern"
    cancelText="Abbrechen"
    @cancel="
      () => {
        $emit('cancel')
      }
    "
    @ok="save"
    width="650px"
  >
    <a-form :form="form" :layout="'vertical'" :wrapperCol="{ span: 23 }">
      <PatientStammdaten
        :patient="patient"
        :form="form"
        show-death="true"
        show-stay="true"
      />
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import Vue from 'vue'
import PatientStammdaten from '../form-groups/PatientStammdaten.vue'
import Api from '@/api'
import { patientMapper } from '@/store/modules/patients.module'

export default Vue.extend({
  name: 'ChangePatientStammdatenForm',
  props: ['visible', 'patient'],
  components: {
    PatientStammdaten,
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
        values.dateOfBirth = values.dateOfBirth.format('YYYY-MM-DD')
        if (values.dateOfDeath) {
          values.dateOfDeath = values.dateOfDeath.format('YYYY-MM-DD')
        }
        values = { ...this.patient, ...values }
        Api.updatePatientUsingPut(values)
          .then((updatedPatient) => {
            this.setPatient(updatedPatient)
            this.$notification.success({
              message: 'Patient erfolgreich aktualisiert',
              description: '',
            })
            this.form.resetFields()
            this.$emit('create')
          })
          .catch((error: Error) => {
            this.$notification.error({
              message: 'Patient konnte nicht aktualisiert werden.',
              description: error.message,
            })
          })
      })
    },
  },
})
</script>

<style scoped lang="scss"></style>
