import Vue from 'vue'
import Vuex, { Store } from 'vuex'
import { authModule, AuthState } from './modules/auth.module'

Vue.use(Vuex)

const store: Store<AuthState> = new Vuex.Store({
  state: authModule.state,
  mutations: {
    ...authModule.mutations,
  },
  actions: {
    ...authModule.actions,
  },
  getters: {
    ...authModule.getters,
  },
  // modules: {
  // },
})

export default store
