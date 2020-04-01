import Vue from 'vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'

import Root from './Root.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'

Vue.config.productionTip = false

Vue.use(Antd)

new Vue({
  router,
  store,
  render: h => h(Root),
}).$mount('#app')
