<template>
  <div style="max-width: 1020px; margin: 0 auto; padding: 1rem">
    <ChangePasswordForm
      @cancel="() => { showChangePasswordForm = false }"
      @create="() => { showChangePasswordForm = false }"
      :visible="showChangePasswordForm"
    />
    <ChangeInstitutionForm
      v-if="institution"
      @cancel="() => { showChangeInstitutionForm = false }"
      @create="() => { showChangeInstitutionForm = false }"
      :visible="showChangeInstitutionForm"
      :institution="institution"
    />
    <!-- Allgemein & Adresse -->
    <a-row :gutter="8">
      <a-col
        :span="24"
        :md="12"
      >
        <a-card v-if="user" title="Benutzer" :extra="user.id" align="left">
          <div>
            <table>
              <tr>
                <td>Name:</td>
                <td>{{user.username}}</td>
              </tr>
              <tr>
                <td>Rollen:</td>
                <td>
                  <div v-for="authority in user.authorities" :key="authority.authority">
                    {{roleMapping[authority.authority]}}
                  </div>
                </td>
              </tr>
            </table>
            <a-button type="primary" @click="showChangePasswordForm=true" icon="edit">Passwort wechseln</a-button>
          </div>
        </a-card>
      </a-col>
      <a-col :span="24" :md="12">
        <a-card v-if="institution" title="Institution" :extra="institution.id" align="left">
          <table>
            <tr>
              <td>Name:</td>
              <td>{{institution.name}}</td>
            </tr>
            <tr>
              <td>Typ:</td>
              <td>{{typeMapping[institution.institutionType]}}</td>
            </tr>
            <tr>
              <td>Adresse:</td>
              <td>{{institution.street}} {{institution.houseNumber}}<br>{{institution.zip}} {{institution.city}}</td>
            </tr>
            <tr>
              <td>E-Mail:</td>
              <td>{{institution.email}}</td>
            </tr>
            <tr>
              <td>Telefonnummer:</td>
              <td>{{institution.phoneNumber}}</td>
            </tr>
            <tr>
              <td>Kommentar:</td>
              <td>{{institution.comment}}</td>
            </tr>
          </table>
          <a-button v-if="isAdmin" type="primary" @click="showChangeInstitutionForm=true" icon="edit">Bearbeiten
          </a-button>
        </a-card>
      </a-col>
    </a-row>

    <a-card title="Benutzer der Institution" align="left" style="margin-top: 8px">
      <a-table
        :columns="tableColumns"
        :dataSource="institutionUsers"
        rowKey="email"
      >
        <div slot="authorities" slot-scope="authorities">
          {{ roleMapping[authorities[1].authority]}}
        </div>
      </a-table>
    </a-card>

    <a-card>
      <a-form
        :form="registerUserForm"
        :layout="'horizontal'"
        :labelCol="{ span: 8 }"
        :wrapperCol="{ span: 16}"
        @submit.prevent="handleRegisterUser"
      >
        <p>Füge einen Nutzer hinzu</p>
        <a-form-item label="username">
          <a-input
            v-decorator="['username', { rules: [{ required: true }] }]"
          />
        </a-form-item>
        <a-form-item label="password">
          <a-input
            type="password"
            v-decorator="['password', { rules: [{ required: true }] }]"
          />
        </a-form-item>
        <a-form-item label="Rolle">
          <a-select
            v-decorator="['userRole', { rules: [{ required: true }] }]"
          >
            <a-select-option value="USER_ROLE_ADMIN">Admin</a-select-option>
            <a-select-option value="USER_ROLE_REGULAR">Regular</a-select-option>
          </a-select>
        </a-form-item>
        <a-button type="primary" html-type="submit">
          Nutzer hinzufügen
        </a-button>
      </a-form>
    </a-card>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import { InstitutionType, UserRole } from '@/models'
import { authMapper } from '@/store/modules/auth.module'
import ChangePasswordForm from '@/components/ChangePasswordForm.vue'
import ChangeInstitutionForm from '@/components/ChangeInstitutionForm.vue'

const tableColumns = [
  {
    title: 'Vorname',
    key: 'firstName',
    dataIndex: 'firstName',
  },
  {
    title: 'Nachname',
    key: 'lastName',
    dataIndex: 'lastName',
  },
  {
    title: 'Benutzername',
    key: 'username',
    dataIndex: 'username',
  },
  {
    title: 'Rolle',
    key: 'authorities',
    dataIndex: 'authorities',
    scopedSlots: { customRender: 'authorities' },
  },
]

const roleMapping: { [key: string]: string } = {
  USER_ROLE_ADMIN: 'Admin',
  USER_ROLE_REGULAR: 'Normal',
  ROLE_LABORATORY: 'Labor',
  ROLE_TEST_SITE: 'Teststation',
  ROLE_CLINIC: 'Klinik',
  ROLE_DOCTORS_OFFICE: 'Arzt',
  ROLE_GOVERNMENT_AGENCY: 'Gesundheitsbehörde',
  ROLE_DEPARTMENT_OF_HEALTH: 'Gesundheitsamt',
}

const typeMapping: { [key in InstitutionType]: string } = {
  LABORATORY: 'Labor',
  TEST_SITE: 'Teststation',
  CLINIC: 'Klinik',
  DOCTORS_OFFICE: 'Arzt',
  GOVERNMENT_AGENCY: 'Gesundheitsbehörde',
  DEPARTMENT_OF_HEALTH: 'Gesundheitsamt',
}

export default Vue.extend({
  name: 'Account',
  created() {
    this.setIsAdmin()
    this.getInstitutionUsers()
  },
  data() {
    return {
      tableColumns,
      roleMapping,
      typeMapping,
      registerUserForm: this.$form.createForm(this),
      showChangePasswordForm: false,
      showChangeInstitutionForm: false,
      isAdmin: false,
    }
  },
  components: {
    ChangePasswordForm,
    ChangeInstitutionForm,
  },
  computed: {
    ...authMapper.mapState({
      user: 'user',
      institution: 'institution',
      institutionUsers: 'institutionUsers',
    }),
    username(): string {
      return this.user?.username || ''
    },
  },
  watch: {
    user() {
      this.setIsAdmin()
    },
  },
  methods: {
    ...authMapper.mapActions({
      registerUser: 'registerUserForInstitution',
      getInstitutionUsers: 'getInstitutionUsers',
    }),
    setIsAdmin() {
      if (this.user && this.user.authorities) {
        this.isAdmin = this.user.authorities.some(authority => authority.authority === 'USER_ROLE_ADMIN')
      }
    },
    handleRegisterUser() {
      this.registerUserForm.validateFields(async(err: Error, values: any) => {
        if (err) {
          return
        }
        this.registerUser({ user: values, instance: this })
      })
    },
  },
})
</script>

<style scoped lang="scss">
  table {
    border-collapse: separate;
    border-spacing: 15px;
  }
</style>
