import Vue from 'vue'
import Vuex from 'vuex'
import { authModule } from './modules/auth.module'

import { createStore, Module } from 'vuex-smart-module'

Vue.use(Vuex)

const root = new Module({
  modules: {
    authModule,
  },
})

const store = createStore(root)

console.log(store)
export default store
