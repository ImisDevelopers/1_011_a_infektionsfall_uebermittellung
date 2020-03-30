// vue imports
import Vue from "vue";
import VueRouter from "vue-router";

// error tracking imports
import * as Sentry from '@sentry/browser';
import * as Integrations from '@sentry/integrations';

// ui imports
import Antd from "ant-design-vue";
import "ant-design-vue/dist/antd.css";

// application imports
import App from "./App.vue";
import { routes } from "./routes/routes";
import { authenticationStore } from "./util/auth";

Vue.use(Antd);
Vue.use(VueRouter);
Vue.config.productionTip = false;

if (process.env.SENTRY_FRONTEND_DSN != "") {
    Sentry.init({
        dsn: process.env.SENTRY_FRONTEND_DSN,
        integrations: [new Integrations.Vue({Vue, attachProps: true})],
    })
}

const router = new VueRouter({
    mode: "history",
    routes
});

router.beforeEach((to, from, next) => {
    // redirect to login page if not logged in and trying to access a restricted page
    const user = authenticationStore.user;

    const route = routes.find(route => route.path === to.path);

    if (route && route.roles) {
        if (!user.sub) {
            console.log("User not logged in. Forwarding to login.");
            return next("/prototype/login?forwardTo=" + encodeURI(to.path));
        }
        if (!authenticationStore.hasAnyRoleOf(route.roles)) {
            console.log("User does not have any of the required roles " + route.roles + ". Forwarding to login.");
            return next("/prototype/login?forwardTo=" + encodeURI(to.path));
        }
    }


    next();
});

authenticationStore.initAuthentication();

new Vue({
    router,
    render: h => h(App)
}).$mount("#app");
