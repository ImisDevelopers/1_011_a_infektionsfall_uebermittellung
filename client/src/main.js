// vue imports
import Vue from 'vue'
import VueRouter from 'vue-router'

// ui imports
import Antd from "ant-design-vue";
import "ant-design-vue/dist/antd.css";

// application imports
import App from './App.vue'
import { routes } from './routes/routes'

Vue.use(Antd);
Vue.use(VueRouter);
Vue.config.productionTip = false

const router = new VueRouter({
  routes // short for `routes: routes`
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')