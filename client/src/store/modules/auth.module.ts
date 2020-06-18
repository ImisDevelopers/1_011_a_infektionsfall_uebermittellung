import Api, { removeBearerToken, setBearerToken } from '@/api'
import {
  ChangePasswordDTO,
  Institution,
  InstitutionDTO,
  RegisterUserRequest,
  User,
  UserDTO,
} from '@/api/ImisSwaggerApi'
import { config } from '@/config'
import { InstitutionRole } from '@/models'
import router, { AppRoute, navigationRoutes } from '@/router'
import { parseJwt } from '@/util'
import {
  Actions,
  createMapper,
  Getters,
  Module,
  Mutations,
} from 'vuex-smart-module'

interface JwtData {
  roles: InstitutionRole[]
  exp: number
  [key: string]: any
}

class AuthState {
  jwtToken: string | undefined = undefined
  jwtData: JwtData | undefined = undefined
  user: User | undefined = undefined
  institution: Institution | undefined = undefined
  institutionUsers: UserDTO[] | undefined = undefined
}

class AuthGetters extends Getters<AuthState> {
  isAuthenticated(): boolean {
    return !!this.state.jwtToken // add is valid check expire date
  }

  institution(): Institution | undefined {
    return this.state.institution
  }

  roles() {
    return this.state.jwtData?.roles || []
  }

  routes(): AppRoute[] {
    return navigationRoutes.filter(
      (r) =>
        config.showAllViews ||
        this.getters
          .roles()
          .some((a) => r.meta?.navigationInfo?.authorities.includes(a))
    )
  }

  institutionUsers() {
    return this.state.institutionUsers || []
  }
}

class AuthMutations extends Mutations<AuthState> {
  loginSuccess(jwtToken: string) {
    this.state.jwtToken = jwtToken
    this.state.jwtData = parseJwt(jwtToken)
    setBearerToken(jwtToken)
  }

  logoutSuccess() {
    this.state.jwtToken = undefined
    this.state.jwtData = undefined
    removeBearerToken()
  }

  setAuthenticatedInstitution(institution: Institution) {
    this.state.institution = institution
  }

  setInstitutionUsers(users: UserDTO[]) {
    this.state.institutionUsers = users
  }

  setUser(user: User) {
    this.state.user = user
  }
}

class AuthActions extends Actions<
  AuthState,
  AuthGetters,
  AuthMutations,
  AuthActions
> {
  async login(payload: { username: string; password: string }) {
    // # TODO loading animation, encrypt jwt
    const token: string | undefined = (
      await Api.signInUserUsingPost({
        username: payload.username,
        password: payload.password,
      })
    ).jwtToken
    if (token) {
      this.commit('loginSuccess', token)
      this.dispatch('getAuthenticatedInstitution')
      this.dispatch('getAuthenticatedUser')
      window.localStorage.setItem('token', '' + token)
      router.push({ name: 'app' })
    }
  }

  async logout() {
    // # TODO logout request
    this.commit('logoutSuccess')
    window.localStorage.clear()
    // # TODO empty state
    router.push({ name: 'login' })
  }

  async init() {
    const jwtToken = window.localStorage.token
    if (jwtToken) {
      const decoded = parseJwt(jwtToken)
      const now = new Date()
      const tokenExpireDate = new Date(decoded.exp * 1000)
      if (tokenExpireDate > now) {
        this.commit('loginSuccess', jwtToken)
        this.dispatch('getAuthenticatedInstitution')
        this.dispatch('getAuthenticatedUser')
      } else {
        // this.commit('tokenExpired')
        window.localStorage.clear()
      }
    }
  }

  async getAuthenticatedInstitution() {
    const institution = await Api.getInstitutionUsingGet()
    this.commit('setAuthenticatedInstitution', institution)
  }

  async getInstitutionUsers() {
    const users = await Api.getInstitutionUsersUsingGet()
    this.commit('setInstitutionUsers', users)
  }

  async getAuthenticatedUser() {
    const user = await Api.currentUserUsingGet()
    this.commit('setUser', user)
  }

  async updateInstitution(institution: InstitutionDTO) {
    const updatedInstitution = await Api.updateInstitutionUsingPut(institution)
    this.commit('setAuthenticatedInstitution', updatedInstitution)
  }

  async registerUserForInstitution(user: RegisterUserRequest) {
    const res = await Api.registerUserUsingPost(user)
    this.dispatch('getInstitutionUsers')
  }

  async deleteUserForInstitution(userId: number) {
    const res = await Api.deleteInstitutionUserUsingDelete(userId)
    this.dispatch('getInstitutionUsers')
  }

  async updateUserForInstitution(user: UserDTO) {
    await Api.updateInstitutionUserUsingPut(user)
    this.dispatch('getInstitutionUsers')
  }

  changePassword(changePassword: ChangePasswordDTO): Promise<void> {
    return Api.changePasswordUsingPost(changePassword)
  }
}

export const authModule = new Module({
  state: AuthState,
  getters: AuthGetters,
  mutations: AuthMutations,
  actions: AuthActions,
})

export const authMapper = createMapper(authModule)
