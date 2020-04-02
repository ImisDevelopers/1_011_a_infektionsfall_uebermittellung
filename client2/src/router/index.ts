import Vue from 'vue'
import VueRouter, { Route, RouteConfig } from 'vue-router'
import Login from '../views/Login.vue'
import LandingPage from '../views/LandingPage.vue'
import AppRoot from '../views/AppRoot.vue'
import RegisterPatient from '../views/RegisterPatient.vue'
import RegisterTest from '../views/RegisterTest.vue'
import SubmitTestResult from '../views/SubmitTestResult.vue'
import TestList from '../views/TestList.vue'
import PatientList from '../views/PatientList.vue'
import store from '@/store'
import { InstitutionType } from '@/store/modules/auth.module'

Vue.use(VueRouter)

const loginBeforeEnter = (to: Route, from: Route, next: Function) => {
  if (store.getters.isAuthenticated) {
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

interface AppRoute extends RouteConfig {
  meta?: {
    icon: string;
    title: string;
    authorities: InstitutionType[];
  };
}

const appRoutes: AppRoute[] = [
  {
    name: 'register-patient',
    path: 'register-patient',
    component: RegisterPatient,
    meta: {
      icon: 'user-add',
      title: 'Patient Registrieren',
      authorities: ['CLINIC', 'DOCTORS_OFFICE', 'TEST_SITE'],
    },
  },
  {
    name: 'register-test',
    path: 'register-test',
    component: RegisterTest,
    meta: {
      icon: 'deployment-unit',
      title: 'Probe Zuordnen',
      authorities: ['CLINIC', 'DOCTORS_OFFICE', 'TEST_SITE'],
    },
  },
  {
    name: 'submit-test-result',
    path: 'submit-test-result',
    component: SubmitTestResult,
    meta: {
      icon: 'experiment',
      title: 'Testresultat Zuordnen',
      authorities: ['LABORATORY', 'TEST_SITE'],
    },
  },
  {
    name: 'test-list',
    path: 'test-list',
    component: TestList,
    meta: {
      icon: 'unorderd-list',
      title: 'Alle Tests',
      authorities: ['LABORATORY', 'TEST_SITE'],
    },
  },
  {
    name: 'patient-list',
    path: 'patient-list',
    component: PatientList,
    meta: {
      icon: 'team',
      title: 'Alle Patienten',
      authorities: ['CLINIC', 'DOCTORS_OFFICE', 'TEST_SITE'],
    },
  },
  {
    path: '*',
    redirect: { name: 'app' },
  },
]

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
    children: appRoutes,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '*',
    redirect: '/',
  },
]

export const navigationRoutes = appRoutes
  .filter(r => !r.path.includes('*'))

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.getters.isAuthenticated) {
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
