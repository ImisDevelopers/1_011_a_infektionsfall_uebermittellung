import { InstitutionRole } from '@/models'
import AccountView from '@/views/Account.vue'
import AppRoot from '@/views/AppRoot.vue'
import LandingPage from '@/views/LandingPage.vue'
import Login from '@/views/Login.vue'
import PatientDetails from '@/views/PatientDetails.vue'
import PatientList from '@/views/PatientList.vue'
import PublicRegister from '@/views/PublicRegister.vue'
import PublicStatistics from '@/views/PublicStatistics.vue'
import RegisterInstitution from '@/views/RegisterInstitution.vue'
import RegisterPatient from '@/views/RegisterPatient.vue'
import RegisterTest from '@/views/RegisterTest.vue'
import SubmitTestResult from '@/views/SubmitTestResult.vue'
import TestList from '@/views/TestList.vue'
import Vue from 'vue'
import VueRouter, { Route, RouteConfig } from 'vue-router'

Vue.use(VueRouter)

// not working, maybe because of circular dependency, when router is not imported in auth.modele, it works
// const authGetters = authMapper.mapGetters({
//   isAuthenticated: 'isAuthenticated',
// })

function isAuthenticated() {
  // return authGetters.isAuthenticated()
  return window.localStorage.token
}

const checkNotAuthenticatedBeforeEnter = (to: Route, from: Route, next: Function) => {
  if (isAuthenticated()) {
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

export interface AppRoute extends RouteConfig {
  meta?: {
    navigationInfo?: {
      icon: string;
      title: string;
      authorities: InstitutionRole[];
    };
  };
}

const ALL_INSTITUTIONS: InstitutionRole[] = [
  'ROLE_TEST_SITE',
  'ROLE_LABORATORY',
  'ROLE_DOCTORS_OFFICE',
  'ROLE_CLINIC',
  'ROLE_GOVERNMENT_AGENCY',
  'ROLE_DEPARTMENT_OF_HEALTH',
]

const appRoutes: AppRoute[] = [
  {
    name: 'account',
    path: 'account',
    component: AccountView,
    meta: {
      navigationInfo: {
        icon: 'user',
        title: 'Account',
        authorities: ALL_INSTITUTIONS,
      },
    },
  },
  {
    name: 'register-patient',
    path: 'register-patient',
    component: RegisterPatient,
    meta: {
      navigationInfo: {
        icon: 'user-add',
        title: 'Patient Registrieren',
        authorities: ['ROLE_DEPARTMENT_OF_HEALTH', 'ROLE_CLINIC', 'ROLE_DOCTORS_OFFICE', 'ROLE_TEST_SITE'],
      },
    },
  },
  {
    name: 'register-test',
    path: 'register-test',
    component: RegisterTest,
    meta: {
      navigationInfo: {
        icon: 'deployment-unit',
        title: 'Probe Zuordnen',
        authorities: ['ROLE_DEPARTMENT_OF_HEALTH', 'ROLE_CLINIC', 'ROLE_DOCTORS_OFFICE', 'ROLE_TEST_SITE'],
      },
    },
  },
  {
    name: 'submit-test-result',
    path: 'submit-test-result',
    component: SubmitTestResult,
    meta: {
      navigationInfo: {
        icon: 'experiment',
        title: 'Testresultat Zuordnen',
        authorities: ['ROLE_DEPARTMENT_OF_HEALTH', 'ROLE_LABORATORY', 'ROLE_TEST_SITE'],
      },
    },
  },
  {
    name: 'test-list',
    path: 'test-list',
    component: TestList,
    meta: {
      navigationInfo: {
        icon: 'unordered-list',
        title: 'Alle Tests',
        authorities: ['ROLE_DEPARTMENT_OF_HEALTH', 'ROLE_LABORATORY', 'ROLE_TEST_SITE'],
      },
    },
  },
  {
    name: 'patient-list',
    path: 'patient-list',
    component: PatientList,
    meta: {
      navigationInfo: {
        icon: 'team',
        title: 'Alle Patienten',
        authorities: ['ROLE_DEPARTMENT_OF_HEALTH', 'ROLE_CLINIC', 'ROLE_DOCTORS_OFFICE', 'ROLE_TEST_SITE'],
      },
    },
  },
  {
    name: 'public-statistics',
    path: 'public-statistics',
    component: PublicStatistics,
    meta: {
      navigationInfo: {
        icon: 'stock',
        title: 'Statistiken',
        authorities: ALL_INSTITUTIONS,
      },
    },
  },
  {
    name: 'patient-detail',
    path: 'patient/:id',
    component: PatientDetails,
  },
  {
    path: '*',
    redirect: { name: 'app' },
  },
]

const routes = [
  {
    name: 'landing-page',
    path: '/',
    component: LandingPage,
  },
  {
    name: 'public-register',
    path: '/public-register',
    component: PublicRegister,
  },
  {
    name: 'login',
    path: '/login',
    component: Login,
    beforeEnter: checkNotAuthenticatedBeforeEnter,
    beforeRouteLeave: loginBeforeRouteLeave,
  },
  {
    name: 'register-institution',
    path: '/register-institution/:id',
    component: RegisterInstitution,
    beforeEnter: checkNotAuthenticatedBeforeEnter,
  },
  {
    name: 'app',
    path: '/app',
    component: AppRoot,
    children: appRoutes,
    redirect: { name: 'account' },
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
  .filter(r => !r.path.includes('*') && r.meta?.navigationInfo)

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isAuthenticated()) {
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
