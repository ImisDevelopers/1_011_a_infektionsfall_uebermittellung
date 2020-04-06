<template>
  <a-card class="table-container">
    <div>Account {{ JSON.stringify(institution()) }}</div>
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
import { UserRole } from '@/models'
import Vue from 'vue'
import { authMapper } from '@/store/modules/auth.module'
import Component from 'vue-class-component'

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
    scopedSlots: { customRender: 'role' },
  },
]

const roleMapping: { [key in UserRole]: string } = {
  USER_ROLE_ADMIN: 'Admin',
  USER_ROLE_REGULAR: 'Normal',
}

const Base = Vue.extend({
  data() {
    return {
      ...authMapper.mapState({
        user: 'user',
        institution: 'institution',
      }),
      tableColumns,
      roleMapping,
    }
  },
  computed: {
    ...authMapper.mapGetters({
      users: 'institutionUsers',
    }),
  },
})
@Component
export default class AccountView extends Base {
}
</script>

<style>
  .table-container {
    width: 100%;
  }
</style>
