import router from '@/router'
import { parseJwt } from '@/util'
import Api, { removeBearerToken, setBearerToken } from '../api'
import Notification from '@/util/notification'
import { Actions, createMapper, Getters, Module, Mutations } from 'vuex-smart-module'
import { InstitutionType } from '@/models'

interface JwtData {
  roles: InstitutionType[];
  exp: number;
  [key: string]: any;
}

class AuthState {
  jwtToken: string | undefined = undefined
  jwtData: JwtData | undefined = undefined
}

class AuthGetters extends Getters<AuthState> {
  isAuthenticated (): boolean {
    return !!this.state.jwtToken // add is valid check expire date
  }

  roles () {
    return this.state.jwtData?.roles
  }
}

class AuthMutations extends Mutations<AuthState> {
  loginSuccess (jwtToken: string) {
    this.state.jwtToken = jwtToken
    this.state.jwtData = parseJwt(jwtToken)
    setBearerToken(jwtToken)
  }

  logoutSuccess () {
    this.state.jwtToken = undefined
    this.state.jwtData = undefined
    removeBearerToken()
  }
}

class AuthActions extends Actions<AuthState, AuthGetters, AuthMutations, AuthActions> {
  async login (payload: { username: string; password: string }) {
    // # TODO loading animation, encrypt jwt
    const token: string | undefined = (await Api.auth.signInUserUsingPost({
      username: payload.username,
      password: payload.password,
    })).jwtToken
    if (token) {
      this.commit('loginSuccess', token)
      window.localStorage.setItem('token', '' + token)
      router.push({ name: 'app' })
    }
  }

  async logout () {
    // # TODO logout request
    this.commit('logoutSuccess')
    window.localStorage.clear()
    router.push({ name: 'login' })
  }

  async init () {
    const jwtToken = window.localStorage.token
    if (jwtToken) {
      const decoded = parseJwt(jwtToken)
      const now = new Date()
      const tokenExpireDate = new Date(decoded.exp * 1000)
      if (tokenExpireDate > now) {
        this.commit('loginSuccess', jwtToken)
      } else {
        // this.commit('tokenExpired')
        const notification = {
          message: 'Session Expired',
          description: 'Ihre Sitzung ist abgelaufen',
        }
        Notification.info(notification)
        window.localStorage.clear()
      }
    }
  }
}

export const authModule = new Module({
  state: AuthState,
  getters: AuthGetters,
  mutations: AuthMutations,
  actions: AuthActions,
})

export const authMapper = createMapper(authModule)
