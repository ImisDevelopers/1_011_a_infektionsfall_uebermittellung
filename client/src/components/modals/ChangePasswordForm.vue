<template>
  <a-modal
    :visible="visible"
    title="Passwort ändern"
    okText="Passwort ändern"
    @cancel="
      () => {
        $emit('cancel')
      }
    "
    @ok="handleChangePassword"
  >
    <a-form :form="changePasswordForm" :layout="'vertical'">
      <a-form-item label="Altes Passwort">
        <a-input
          v-decorator="['oldPassword', { rules: [{ required: true }] }]"
        />
      </a-form-item>
      <a-form-item label="Neues Passwort">
        <a-input
          @change="handlePasswordRetryChangeNew"
          type="password"
          v-decorator="['newPassword', { rules: [{ required: true }] }]"
        />
      </a-form-item>
      <a-form-item
        label="Neues Passwort wiederholen"
        :help="passwordRetry.errorMsg"
        :validate-status="passwordRetry.validateStatus"
      >
        <a-input
          @change="handlePasswordRetryChangeNewRetry"
          type="password"
          v-decorator="['newPasswordRetry', { rules: [{ required: true }] }]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'

export default Vue.extend({
  name: 'Account',
  props: ['visible'],
  data() {
    return {
      changePasswordForm: this.$form.createForm(this, {
        name: 'change_password',
      }),
      passwordRetry: {},
    }
  },
  methods: {
    ...authMapper.mapActions({
      changePassword: 'changePassword',
    }),
    handleChangePassword() {
      this.changePasswordForm.validateFields((err: any, values: any) => {
        if (err) {
          console.warn(err)
          return
        }
        this.changePassword({
          ...values,
        })
          .then(() => {
            this.$notification.success({
              message: 'Passwort erfolgreich geändert',
              description: '',
            })
            this.changePasswordForm.resetFields()
            this.$emit('create')
          })
          .catch((error: Error) => {
            this.$notification.error({
              message: 'Passwort konnte nicht geändert werden.',
              description: error.message,
            })
          })
      })
    },
    handlePasswordRetryChangeNew(input: any) {
      const newPasswordRetry = this.changePasswordForm.getFieldValue(
        'newPasswordRetry'
      )
      const newPassword = input.target.value
      this.handlePasswordRetryChange(newPassword, newPasswordRetry)
    },
    handlePasswordRetryChangeNewRetry(input: any) {
      const newPasswordRetry = input.target.value
      const newPassword = this.changePasswordForm.getFieldValue('newPassword')
      this.handlePasswordRetryChange(newPassword, newPasswordRetry)
    },
    handlePasswordRetryChange(newPassword: string, newPasswordRetry: string) {
      this.passwordRetry =
        newPassword === newPasswordRetry
          ? {
              validateStatus: 'success',
              errorMsg: null,
            }
          : {
              validateStatus: 'error',
              errorMsg: 'Passwörter stimmen nicht überein',
            }
    },
  },
})
</script>

<style scoped lang="scss"></style>
