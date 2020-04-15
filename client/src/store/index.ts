import Vue from 'vue'
import Vuex from 'vuex'

import { createStore, Module } from 'vuex-smart-module'
import { authModule } from './modules/auth.module'
import { patientModule } from './modules/patients.module'

Vue.use(Vuex)

const root = new Module({
  modules: {
    authModule,
    patientModule,
  },
})

const store = createStore(root)
export default store
