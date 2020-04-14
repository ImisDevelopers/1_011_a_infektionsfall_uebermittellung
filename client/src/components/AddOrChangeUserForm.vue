<template>
  <a-modal
    :visible="visible"
    :title="'Nutzer ' + (isNewUser ? 'hinzufügen' : 'bearbeiten')"
    :okText="isNewUser ? 'Anlegen': 'Speichern'"
    cancelText="Abbrechen"
    @cancel="() => { $emit('cancel') }"
    @ok="save"
  >

    <a-form
      :form="form"
      :layout="'vertical'"
    >
      <a-form-item label="Benutzername">
        <a-input
          v-decorator="['username', { rules: [{
            required: true,
            message: 'Bitte Benutzernamen eingeben',
          }], initialValue: user.username }]"
        />
      </a-form-item>
      <a-form-item label="Vorname">
        <a-input
          v-decorator="['firstName', { rules: [{
            required: true,
            message: 'Bitte Vornamen eingeben',
          }], initialValue: user.firstName }]"
        />
      </a-form-item>
      <a-form-item label="Nachname">
        <a-input
          v-decorator="['lastName', { rules: [{
            required: true,
            message: 'Bitte Nachnamen eingeben',
          }], initialValue: user.lastName }]"
        />
      </a-form-item>
      <a-form-item label="Passwort" v-if="isNewUser">
        <a-input
          type="password"
          v-decorator="['password', { rules: [{
            required: true,
            message: 'Bitte Passwort eingeben',
          }], initialValue: '' }]"
        />
      </a-form-item>
      <a-form-item label="Rolle">
        <a-select
          v-decorator="['userRole', { rules: [{
            required: true,
            message: 'Bitte Benutzerrolle eingeben',
          }], initialValue: user.userRole }]"
        >
          <a-select-option value="USER_ROLE_ADMIN">Admin</a-select-option>
          <a-select-option value="USER_ROLE_REGULAR">Regular</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'

export default Vue.extend({
  name: 'Account',
  props: ['visible', 'user'],
  components: {},
  data() {
    return {
      form: this.$form.createForm(this, { name: 'user' }),
      isNewUser: false,
    }
  },
  watch: {
    user() {
      this.isNewUser = !this.user.id
    },
  },
  methods: {
    ...authMapper.mapActions({
      registerUserForInstitution: 'registerUserForInstitution',
      updateUserForInstitution: 'updateUserForInstitution',
    }),
    save() {
      this.form.validateFields((err: any, values: any) => {
        if (err) {
          console.warn(err)
          return
        }
        if (this.isNewUser) {
          this.registerUserForInstitution({
            ...values,
          }).then(() => {
            this.$notification.success({
              message: 'Benutzer erfolgreich angelegt',
              description: '',
            })
            this.form.resetFields()
            this.$emit('create')
          }).catch((error: Error) => {
            this.$notification.error({
              message: 'Benutzer konnte nicht angelegt werden.',
              description: error.message,
            })
          })
        } else {
          this.updateUserForInstitution({
            id: this.user.id,
            ...values,
          }).then(() => {
            this.$notification.success({
              message: 'Benutzer erfolgreich aktualisiert',
              description: '',
            })
            this.form.resetFields()
            this.$emit('create')
          }).catch((error: Error) => {
            this.$notification.error({
              message: 'Benutzer konnte nicht aktualisiert werden.',
              description: error.message,
            })
          })
        }
      })
    },
  },
})
</script>

<style scoped lang="scss">
</style>
