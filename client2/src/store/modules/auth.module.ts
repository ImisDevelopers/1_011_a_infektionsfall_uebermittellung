import { Module } from 'vuex'

import { Api } from '../SwaggerApi'
import router from '@/router'

const api = new Api()

export interface AuthState {
  initialized: boolean;
  jwtToken?: string;
  jwtData?: string;
}

export const authModule: Module<AuthState, any> = {
  state: {
    initialized: false,
    jwtToken: undefined,
    jwtData: undefined,
  },
  mutations: {
    loginSuccess (state, jwtToken) {
      state.jwtToken = jwtToken
    },
    logoutSuccess (state) {
      state.jwtToken = undefined
      state.jwtData = undefined
    },
  },
  actions: {
    async login ({ commit }, { username, password }) {
      // # TODO loading animation, encrypt jwt
      const req = await api.auth.signInUserUsingPost({
        userName: username,
        password,
      })
      commit('loginSuccess', req)
      router.push({ name: 'app' })
    },
    async logout ({ commit }) {
      // # TODO logut request
      commit('logoutSuccess')
      router.push({ name: 'login' })
    },
  },
  getters: {
    isAuthenticated: (state: AuthState) => !!state.jwtToken, // add is valid check expire date
  },
}
