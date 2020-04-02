import { CreatePatientDTO, Patient } from '@/store/SwaggerApi'
import { Module } from 'vuex'
import { State } from '@/store'
import { AuthState } from '@/store/modules/auth.module'
import Api from '../api'
import Notification from '@/util/notification'

export interface PatientState {
  patients: Patient[];
  patient?: Patient;
}

export const patientModule: Module<PatientState, State> = {
  namespaced: true,
  state: {
    patient: undefined,
    patients: [],
  },
  mutations: {
    addPatients (state, patients) {
      state.patients.concat(patients)
    },
    setPatients (state, patients) {
      state.patients = patients
    },
    setPatient (state, patient) {
      state.patient = patient
    },
  },
  actions: {
    async fetchPatients ({ commit }) {
      try {
        commit('shared/startedLoading', 'fetchPatients', { root: true })
        const patients = await Api.patients.getAllPatientsUsingGet()
        commit('setPatients', patients)
      } catch (err) {
        Notification.error({
          message: 'Fehler',
          description: 'Patienten kontent nicht geladen werden',
        })
      }
      commit('shared/finishedLoading', 'fetchPatients', { root: true })
    },
    async registerPatient ({ commit }, patient) {
      commit('shared/startedLoading', 'registerPatient', { root: true })
      try {
        const res = await Api.patients.addPatientUsingPost(patient)
        commit('setPatient', res)
        const notification = {
          message: 'Patient registriert.',
          description: 'Der Patient wurde erfolgreich registriert.',
        }
        Notification.success(notification)
      } catch (err) {
        const notification = {
          message: 'Fehler beim Registrieren des Patienten.',
          description: err.message,
        }
        Notification.error(notification)
      }
      commit('shared/finishedLoading', 'registerPatient', { root: true })
    },
  },
}
