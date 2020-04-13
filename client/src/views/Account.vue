<template>
  <a-card class="table-container">
    <h3>Eingeloggt: {{ username }}</h3>
    <a-form
      :form="registerUserForm"
      :labelCol="{ span: 8 }"
      :layout="'horizontal'"
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
      <a-button html-type="submit" type="primary">
        Nutzer hinzufügen
      </a-button>
    </a-form>
    <a-table
      :columns="tableColumns"
      :dataSource="users()"
      :scroll="{x: 1, y: 0}"
      rowKey="email"
    >
      <div slot="role" slot-scope="role">
        {{ roleMapping[role]}}
      </div>
    </a-table>

  </a-card>
</template>

<script lang="ts">
import Vue from 'vue'
import {UserRole} from '@/models'
import {authMapper} from '@/store/modules/auth.module'

const tableColumns = [
  // {
  //   title: 'Vorname',
  //   key: 'firstName',
  //   dataIndex: 'firstName',
  // },
  // {
  //   title: 'Nachname',
  //   key: 'lastName',
  //   dataIndex: 'lastName',
  // },
  {
    title: 'Username',
    key: 'username',
    dataIndex: 'username',
  },
  {
    title: 'Role',
    key: 'userrole',
    dataIndex: 'role',
    scopedSlots: {customRender: 'role'},
  },
]

const roleMapping: { [key in UserRole]: string } = {
  USER_ROLE_ADMIN: 'Admin',
  USER_ROLE_REGULAR: 'Normal',
}

export default Vue.extend({
  name: 'Account',
  data() {
    return {
      tableColumns,
      roleMapping,
      registerUserForm: this.$form.createForm(this),
    }
  },
  computed: {
    ...authMapper.mapState({
      user: 'user',
      institution: 'institution',
    }),
    ...authMapper.mapGetters({
      users: 'institutionUsers',
    }),
    username(): string {
      return this.user?.username || ''
    },
  },
  methods: {
    ...authMapper.mapActions({
      registerUser: 'registerUserForInstitution',
    }),
    handleRegisterUser() {
      this.registerUserForm.validateFields(async (err: Error, values: any) => {
        if (err) {
          return
        }
        this.registerUser({user: values, instance: this})
      })
    },
  },
})
</script>

<style>
  .table-container {
    width: 100%;
  }
</style>
