import { Module } from 'vuex'

import router from '@/router'
import { State } from '@/store'
import { parseJwt } from '@/util'
import Api from '../api'
import Notification from '@/util/notification'
import { CreateInstitutionDTO } from '@/store/SwaggerApi'

export type InstitutionType = CreateInstitutionDTO['institutionType'];

export interface AuthState {
  jwtToken?: string;
  jwtData?: {
    roles: InstitutionType[];
    exp: number;
    [key: string]: any;
  };
}

interface AuthGetters {
  isAuthenticated: (state: AuthState) => void;
}

export const authModule: Module<AuthState, State> = {
  namespaced: true,
  state: {
    jwtToken: undefined,
    jwtData: undefined,
  },
  mutations: {
    loginSuccess (state, jwtToken) {
      state.jwtToken = jwtToken
      state.jwtData = parseJwt(jwtToken)
      Api.setSecurityData({
        headers: {
          Bearer: jwtToken,
        },
      })
    },
    logoutSuccess (state) {
      state.jwtToken = undefined
      state.jwtData = undefined
    },
  },
  actions: {
    async login ({ commit }, { username, password }) {
      // # TODO loading animation, encrypt jwt
      const token = await Api.auth.signInUserUsingPost({
        userName: username,
        password,
      })
      commit('loginSuccess', token.jwtToken)
      window.localStorage.setItem('token', '' + token.jwtToken)
      router.push({ name: 'app' })
    },
    async logout ({ commit }) {
      // # TODO logout request
      commit('logoutSuccess')
      window.localStorage.clear()
      router.push({ name: 'login' })
    },
    async tokenValidity () {
      // TODO
    },
    async init ({ commit }) {
      const jwtToken = window.localStorage.token
      if (jwtToken) {
        const decoded = parseJwt(jwtToken)
        const now = new Date()
        const tokenExpireDate = new Date(decoded.exp * 1000)
        if (tokenExpireDate > now) {
          commit('loginSuccess', jwtToken)
        } else {
          commit('tokenExpired')
          const notification = {
            message: 'Session Expired',
            description: 'Ihre Sitzung ist abgelaufen',
          }
          Notification.info(notification)
          window.localStorage.clear()
        }
      }
    },
  },
  getters: {
    isAuthenticated: (state: AuthState) => !!state.jwtToken, // add is valid check expire date
    roles: (state: AuthState) => state.jwtData?.roles,
  },
}
