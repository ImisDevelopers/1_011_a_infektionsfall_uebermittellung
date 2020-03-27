// vue imports
import Vue from "vue";
import VueRouter from "vue-router";

// ui imports
import Antd from "ant-design-vue";
import "ant-design-vue/dist/antd.css";

// application imports
import App from "./App.vue";
import { routes } from "./routes/routes";

Vue.use(Antd);
Vue.use(VueRouter);
Vue.config.productionTip = false;

const router = new VueRouter({
  mode: "history",
  routes
});

router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ["/", "/login", "/prototype/public-statistics"];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem("user");

  if (authRequired && !loggedIn) {
    console.log("Forwarding to login.");
    return next("/login?forwardTo=" + encodeURI(to.path));
  }

  next();
});

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
