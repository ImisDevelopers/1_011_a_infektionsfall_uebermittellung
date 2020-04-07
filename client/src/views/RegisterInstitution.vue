<template>
  <div class="wrapper">
    <div v-if="!createdInstitution">
      <a-card style="margin: 3rem auto; max-width: 860px">
        <div class="login-header">
          <img
            src="../assets/logo.png"
            height="100"
          />
          <h3>
            Registrieren Sie hier eine neue Instutition in IMIS.
          </h3>
        </div>

        <a-form
          :form="form"
          :layout="'horizontal'"
          :labelCol="{ span: 6 }"
          :wrapperCol="{ span: 18}"
          @submit.prevent="handleSubmit"
        >
          <a-row>
            <a-col :span="24">
              <a-form-item label="Benutzername">
                <a-input v-decorator="['username', { rules: [{ required: true }] }]">
                  <a-icon slot="prefix" type="user"/>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="Passwort">
                <a-input v-decorator="['password', { rules: [{ required: true }] }]" type="password">
                  <a-icon slot="prefix" type="lock"/>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="Passwort wiederholen" has-feedback
                           :validate-status="passwordRepeat.validateStatus"
                           :help="passwordRepeat.errorMsg"
              >
                <a-input v-decorator="['password-repeat', { rules: [{ required: true }] }]"
                         @change="handlePasswordRepeatChange" type="password">
                  <a-icon slot="prefix" type="lock"/>
                </a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-divider></a-divider>
          <a-row>
            <a-col :md="12">
              <a-form-item label="Typ">
                <a-select
                  v-decorator="['institutionType', { rules: [{
                                          required: true,
                                          message: 'Please select your institution type!'
                                        }]}]"
                  placeholder="Bitte wählen..."
                >
                  <a-select-option value="LABORATORY">Labor</a-select-option>
                  <a-select-option value="DOCTORS_OFFICE">Arztpraxis</a-select-option>
                  <a-select-option value="CLINIC">Klinik</a-select-option>
                  <a-select-option value="TEST_SITE">Teststelle</a-select-option>
                </a-select>
              </a-form-item>
              <a-form-item label="Name">
                <a-input
                  v-decorator="['name', { rules: [{ required: true }] }]"
                />
              </a-form-item>
              <a-form-item label="Straße">
                <a-input
                  v-decorator="['street', { rules: [{ required: true }] }]"
                />
              </a-form-item>
              <a-form-item label="Hausnr.">
                <a-input
                  v-decorator="['houseNumber', { rules: [{ required: true }] }]"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12">
              <a-form-item label="PLZ">
                <a-input
                  v-decorator="['zip', { rules: [{ required: true }] }]"
                />
              </a-form-item>
              <a-form-item label="Ort">
                <a-input
                  v-decorator="['city', { rules: [{ required: true }] }]"
                />
              </a-form-item>
              <a-form-item label="E-mail">
                <a-input
                  v-decorator="['email', { rules: [{ required: true }] }]"
                />
              </a-form-item>
              <a-form-item label="Telefon">
                <a-input
                  v-decorator="['phoneNumber', { rules: [{ required: true }] }]"
                />
              </a-form-item>
            </a-col>
            <a-col>
              <a-form-item
                label="Anmerkungen"
                :labelCol="{ span: 24}"
                :wrapperCol="{ span: 24}"
              >
                <a-input v-decorator="['comment']"/>
              </a-form-item>
            </a-col>
            <a-col :xs="6" :md="3"></a-col>
            <a-col>
              <!-- Datenschutzerklärung Bestätigung-->
              <a-form-item >
                <a-checkbox @change="onCheck">
                                  <span :class="dataProcessingClass">
                                    Ich erkläre mich mit der Übermittlung dieser Daten zur weiteren
                                    Verarbeitung einverstanden.
                                  </span>
                </a-checkbox>
              </a-form-item>
            </a-col>
            <a-col>
              <!-- Submit Button -->
              <a-form-item :wrapperCol="{ span: 24, offset: 0 }">
                <a-row :gutter="16" type="flex" justify="end">
                  <a-col>
                    <a-button type="primary" html-type="submit">
                      Registrieren
                    </a-button>
                  </a-col>
                </a-row>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-card>
    </div>
    <div v-else>
      <div>Die Institution wurde erfolgreich registriert.</div>
      <br/>
      <div>Die Instituions ID lautet: {{ createdInstitution.id }}</div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import Api from '@/api'
import { anonymizeProperties } from '@/util'

function validatePasswordRepeat(password: string, passwordRepeat: string) {
  if (password === passwordRepeat) {
    return {
      validateStatus: 'success',
      errorMsg: null,
    }
  }
  return {
    validateStatus: 'error',
    errorMsg: 'Passwörter stimmen nicht überein',
  }
}

export default Vue.extend({
  name: 'RegisterInstitutionPage',
  data() {
    return {
      form: this.$form.createForm(this, { name: 'coordinated' }),
      createdInstitution: null,
      dataProcessingClass: '',
      checked: false,
      passwordRepeat: {},
    }
  },
  methods: {
    onCheck(e: any) {
      this.checked = e.target.checked
    },
    // For Explanation of dynamic form validation
    // see https://www.antdv.com/components/form/#components-form-demo-handle-form-data-manually
    handlePasswordRepeatChange(event: any) {
      this.passwordRepeat = validatePasswordRepeat(this.form.getFieldValue('password'), event.target.value)
    },
    handleSubmit() {
      this.form.validateFields((err: any, values: any) => {
        if (!this.checked) {
          this.dataProcessingClass = 'data-processing-not-selected'
          return
        } else if (err) {
          console.error(err)
          return
        }

        // TODO: Remove this when we go to production
        anonymizeProperties(
          [
            'email',
            'phoneNumber',
            'street',
            'houseNumber',
            { key: 'zip', type: 'number' },
            'city',
          ],
          values,
        )
        values = {
          ...values,
          user: {
            username: values.username,
            password: values.password,
            userRole: 'USER_ROLE_ADMIN',
          },
        }
        Api.auth.registerInstitutionUsingPost(values).then(() => {
          this.form.resetFields()
          const notification = {
            message: 'Institution registriert.',
            description: 'Die Institution wurde erfolgreich registriert.',
          }
          this.$notification.success(notification)
          this.$store.dispatch('authModule/login', {
            username: values.username,
            password: values.password,
          })
        }).catch(err => {
          const notification = {
            message: 'Fehler beim Registrieren der Institution.',
            description: err.message,
          }
          this.$notification.error(notification)
        })
      })
    },
  },
})
</script>

<style scoped>
  .wrapper {
    text-align: left;
    padding: 2%;
    width: 100%;
  }

  .data-processing-not-selected {
    color: red;
  }
  .login-header {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
</style>
