import Vue from 'vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import * as Sentry from '@sentry/browser'
import * as Integrations from '@sentry/integrations'

import Root from './Root.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'

Vue.config.productionTip = false

if (process.env.SENTRY_FRONTEND_DSN) {
  Sentry.init({
    dsn: process.env.SENTRY_FRONTEND_DSN,
    integrations: [new Integrations.Vue({ Vue, attachProps: true })],
  })
}

Vue.use(Antd)

store.dispatch('auth/init')

new Vue({
  router,
  store,
  render: h => h(Root),
}).$mount('#app')
