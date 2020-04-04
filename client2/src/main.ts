import Vue from 'vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import * as Sentry from '@sentry/browser'
import * as Integrations from '@sentry/integrations'

import store from './store'
import Root from './Root.vue'
import './registerServiceWorker'
import router from './router'
import { authModule } from '@/store/modules/auth.module'

Vue.config.productionTip = false

if (process.env.SENTRY_FRONTEND_DSN) {
  Sentry.init({
    dsn: process.env.SENTRY_FRONTEND_DSN,
    integrations: [new Integrations.Vue({ Vue, attachProps: true })],
  })
}

Vue.use(Antd)

authModule.context(store).actions.init()

new Vue({
  router,
  store,
  render: h => h(Root),
}).$mount('#app')
