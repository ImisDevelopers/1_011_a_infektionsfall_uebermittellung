import { Module } from 'vuex'

import { Api } from '../SwaggerApi'
import router from '@/router'
import { State } from '@/store'

let baseUrl
if (
  location.host.includes('localhost') ||
  location.host.includes('127.0.0.1')
) {
  baseUrl = 'http://localhost:80'
  // Alternative config to run the app locally without root; see proxy conf
  // this.BASE_URL = "http://localhost:8080/api";
} else if (location.host.startsWith('staging')) {
  baseUrl = 'https://api.staging.imis-prototyp.de'
} else {
  baseUrl = 'https://api.imis-prototyp.de'
}

const api = new Api({
  baseUrl: baseUrl,
})

export interface AuthState {
  jwtToken?: string;
  jwtData?: string;
}

interface AuthGetters {
  isAuthenticated: (state: AuthState) => void;
}

export const authModule: Module<AuthState, State> = {
  namespaced: true,
  state: {
    jwtToken: window.localStorage.token,
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
      const token = await api.auth.signInUserUsingPost({
        userName: username,
        password,
      })
      commit('loginSuccess', token.jwtToken)
      window.localStorage.setItem('token', '' + token.jwtToken)
      router.push({ name: 'app' })
    },
    async logout ({ commit }) {
      // # TODO logut request
      commit('logoutSuccess')
      window.localStorage.clear()
      router.push({ name: 'login' })
    },
    async tokenValidity () {
      // TODO
    },
  },
  getters: {
    isAuthenticated: (state: AuthState) => !!state.jwtToken, // add is valid check expire date
  },
}
