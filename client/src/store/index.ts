import Vue from 'vue'
import Vuex from 'vuex'
import { authModule } from './modules/auth.module'
import { patientModule } from './modules/patients.module'

import { createStore, Module } from 'vuex-smart-module'

Vue.use(Vuex)

const root = new Module({
  modules: {
    authModule,
    patientModule,
  },
})

const store = createStore(root)
console.log(store)
export default store
