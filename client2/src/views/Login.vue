<template>
  <a-card style="width: 500px; margin: 4rem auto; min-height: 300px;">
    <a-form :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }" :form="form">
      <div class="login-header">
        <img
          src="../assets/logo.png"
          height="100"
        />
        <p>Login</p>
      </div>
      <a-form-item label="Kennung">
        <a-input
          v-decorator="['username', {
                            rules: [{
                                required: true,
                                message: 'Bitte geben Sie Ihre Kennung ein.',
                            }],
                            initialValue: '',
                        }]"
          placeholder="z.B 1337-4237-9438"
        >
          <a-icon slot="prefix" type="user" />
        </a-input>
      </a-form-item>
      <a-form-item label="Passwort">
        <a-input
          v-decorator="['password', { rules: [{
              required: true,
              message: 'Bitte geben Sie Ihr Passwort ein.'
            }],
            initialValue: '',
          }]"
          type="password"
          placeholder="**********"
        >
          <a-icon slot="prefix" type="lock" />
        </a-input>
      </a-form-item>
      <a-divider />
      <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
        <a-button type="primary" html-type="submit" @click.prevent="handleLogin">
          Einloggen
        </a-button>
      </a-form-item>
    </a-form>
    <div>
      <p>Demo-Zugang <strong>test_lab</strong> mit Passwort <strong>asdf</strong></p>
      <router-link :to="{ name: 'register-institution', params: { id: 'demo' } }">Als Institution registrieren
      </router-link>
    </div>
  </a-card>
</template>

<script lang="ts">

import { authMapper } from '@/store/modules/auth.module'
import Vue from 'vue'

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
        }).catch(error => {
          console.error(error)
          this.$notification.error({
            message: 'Login fehlgeschlagen',
            description: 'Logindaten fehlerhaft',
          })
        })
      })
    },
  },
})

</script>
