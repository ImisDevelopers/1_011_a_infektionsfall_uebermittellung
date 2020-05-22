<template>
  <a-card
    style="width: 500px; margin: 4rem auto; min-height: 300px;"
    align="center"
  >
    <a-form :form="form" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <div class="login-header">
        <img height="100" src="../assets/logo.png" />
        <h3 style="margin-top: 15px;">Login</h3>
      </div>
      <a-form-item label="Kennung">
        <a-input
          placeholder="z.B 1337-4237-9438"
          v-decorator="[
            'username',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie Ihre Kennung ein.',
                },
              ],
              initialValue: '',
            },
          ]"
        >
          <a-icon slot="prefix" type="user" />
        </a-input>
      </a-form-item>
      <a-form-item label="Passwort">
        <a-input
          placeholder="**********"
          type="password"
          v-decorator="[
            'password',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie Ihr Passwort ein.',
                },
              ],
              initialValue: '',
            },
          ]"
        >
          <a-icon slot="prefix" type="lock" />
        </a-input>
      </a-form-item>
      <a-divider />
      <a-form-item
        :wrapper-col="{ span: 24, offset: 0 }"
        style="margin-bottom: 20px;"
      >
        <a-button
          @click.prevent="handleLogin"
          html-type="submit"
          type="primary"
        >
          Einloggen
        </a-button>
      </a-form-item>
    </a-form>
    <div>
      <p>Demo-Zugänge:</p>
      <!--
      <p><strong>test_lab</strong> mit Passwort <strong>asdf</strong></p>
      <p><strong>test_doctor</strong> mit Passwort <strong>asdf</strong></p>
      -->
      <p>
        <strong>test_testing_site</strong> mit Passwort <strong>asdf</strong>
      </p>
      <p>
        <strong>test_department_of_health</strong> mit Passwort
        <strong>asdf</strong>
      </p>
      <!--<router-link :to="{ name: 'register-institution', params: { id: 'demo' } }">Als Institution registrieren
      </router-link>-->
    </div>
  </a-card>
</template>

<script lang="ts">
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'

export default Vue.extend({
  name: 'Login',
  data() {
    return {
      form: this.$form.createForm(this),
    }
  },
  methods: {
    ...authMapper.mapActions({
      login: 'login',
    }),
    handleLogin() {
      this.form.validateFields((err: any, values: any) => {
        if (err) {
          return
        }
        this.login({
          username: values.username,
          password: values.password,
        })
      })
    },
  },
})
</script>

<style>
.login-header {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
