import Vue from 'vue'
import Vuex, { Store } from 'vuex'
import { authModule, AuthState } from './modules/auth.module'

Vue.use(Vuex)

export interface State {
  auth: AuthState;
}

const store = new Vuex.Store({
  // state: authModule.state,
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
  },
})
console.log(store)

export default store
