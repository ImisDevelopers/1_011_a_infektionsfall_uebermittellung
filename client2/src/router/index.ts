import Vue from 'vue'
import VueRouter, { Route } from 'vue-router'
import Login from '../views/Login.vue'
import LandingPage from '../views/LandingPage.vue'
import AppRoot from '../views/AppRoot.vue'
import Store from '@/store'

Vue.use(VueRouter)

const state = Store.state

const loginBeforeEnter = (to: Route, from: Route, next: Function) => {
  if (state.jwtToken) {
    next({ name: 'app' })
  } else {
    next()
  }
}

const loginBeforeRouteLeave = (to: Route, from: Route, next: Function) => {
  if (from.query.redirect) {
    next({ path: from.query.redirect })
  } else {
    next()
  }
}

const routes = [
  {
    path: '/',
    name: 'landing-page',
    component: LandingPage,
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    beforeEnter: loginBeforeEnter,
    beforeRouteLeave: loginBeforeRouteLeave,
  },
  {
    path: '/app',
    name: 'app',
    component: AppRoot,
    children: [
      {
        path: '*',
        redirect: { name: 'app' },
      },
    ],
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '*',
    redirect: '/',
  },
]

export const navigationRoutes = routes
  .filter(r => r.name === 'app')[0]?.children
  ?.filter(r => !r.path.includes('*'))

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!state.jwtToken) {
      next({
        path: '/login',
        query: { redirect: to.fullPath },
      })
    } else {
      next()
    }
  } else {
    next() // make sure to always call next()!
  }
})

export default router
