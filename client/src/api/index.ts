import { Api, RequestParams } from '@/api/SwaggerApi'

let baseUrl: string
if (
  location.host.includes('localhost') ||
  location.host.includes('127.0.0.1')
) {
  // baseUrl = 'http://localhost:80'
  // Alternative config to run the app locally without root; see proxy conf
  baseUrl = 'http://localhost:8080/api'
} else if (location.host.startsWith('staging')) {
  baseUrl = 'https://api.staging.imis-prototyp.de'
} else {
  baseUrl = 'https://api.imis-prototyp.de'
}

/**
 * The npm package that creates the swagger client does not have a option
 * to change headers, but after sign in we have to set the jwt token
 * To to this we have to reinitialize the Api.
 *
 * To ensure all components always use the current api we use a proxy that
 * returns the correct Api object function
 *
 */

const baseApiParams: RequestParams = {
  credentials: 'same-origin',
  headers: {
    'Content-Type': 'application/json',
  },
  redirect: 'follow',
  referrerPolicy: 'no-referrer',
}

const apiWrapper = {
  api: new Api({
    baseUrl: baseUrl,
    baseApiParams: baseApiParams,
  }),
}

function createApiProxy(foo: Api): Api { // Proxy<Foo> is compatible with Foo
  const handler = {
    get: (target: Api, prop: keyof Api, receiver: any) => {
      if (Api.prototype[prop] !== null) {
        return apiWrapper.api[prop]
      }

      return Reflect.get(target, prop, receiver)
    },
  }
  return new Proxy(foo, handler)
}

export function setBearerToken(token: string) {
  apiWrapper.api = new Api({
    baseUrl: baseUrl,
    baseApiParams: {
      ...baseApiParams,
      headers: {
        ...baseApiParams.headers,
        Authorization: 'Bearer ' + token,
      },
    },
  })
}

export function removeBearerToken() {
  apiWrapper.api = new Api({
    baseUrl: baseUrl,
  })
}

export default createApiProxy(apiWrapper.api)
