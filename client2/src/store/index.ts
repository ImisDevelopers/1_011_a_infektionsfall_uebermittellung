import Vue from 'vue'
import Vuex from 'vuex'
import { authModule, AuthState } from './modules/auth.module'
import { patientModule, PatientState } from '@/store/modules/patients.module'
import { sharedModule, SharedState } from '@/store/modules/shared.module'

Vue.use(Vuex)

export interface State {
  auth: AuthState;
  patient: PatientState;
  shared: SharedState;
}

const store = new Vuex.Store({
  // mutations: {
  //   ...authModule.mutations,
  // },
  // actions: {
  //   ...authModule.actions,
  // },
  getters: {
    isAuthenticated (state) {
      return !!state.auth.jwtToken
    },
  },
  modules: {
    auth: authModule,
    patient: patientModule,
    shared: sharedModule,
  },
})
console.log(store)
export default store
