import Vue from 'vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'

// important to import router before the store and authModule!! otherwise it breaks
import router from './router'
import store from './store'
import { authModule } from '@/store/modules/auth.module'
import Root from './Root.vue'
// import './registerServiceWorker' remove for now

Vue.config.productionTip = false

Vue.use(Antd)

authModule.context(store).actions.init()

new Vue({
  router,
  store,
  render: h => h(Root),
}).$mount('#app')
