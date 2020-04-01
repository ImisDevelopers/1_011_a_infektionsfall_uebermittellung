import { Api } from '@/store/SwaggerApi'

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

export default api
