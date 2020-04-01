import { Module } from 'vuex'

export interface AuthState {
  initialized: boolean;
  jwtToken?: string;
  jwtData?: string;
}

export const authModule: Module<AuthState, any> = {
  state: {
    initialized: false,
    jwtToken: 'asdf',
    jwtData: undefined,
  },
  mutations: {

  },
  actions: {

  },
  getters: {
    isAuthenticated: (state: AuthState) => !!state.jwtToken, // add is valid check expire date
  },
}
