import { CreatePatientDTO, Patient } from '@/store/SwaggerApi'
import Api from '../api'
import Notification from '@/util/notification'
import { Actions, createMapper, Getters, Module, Mutations } from 'vuex-smart-module'

// export interface PatientState {
//   patients: Patient[];
//   patient?: Patient;
// }

class PatientState {
  patient: Patient | undefined;
  patients: Patient[] = []
}

class PatientGetters extends Getters<PatientState> {
}

class PatientMutations extends Mutations<PatientState> {
  addPatients (patients: Patient[]) {
    this.state.patients.concat(patients)
  }

  setPatients (patients: Patient[]) {
    this.state.patients = patients
  }

  setPatient (patient: Patient) {
    this.state.patient = patient
  }
}

class PatientActions extends Actions<PatientState, PatientGetters, PatientMutations, PatientActions> {
  async fetchPatients () {
    try {
      // this.commit('shared/startedLoading', 'fetchPatients', { root: true })
      const patients = await Api.patients.getAllPatientsUsingGet()
      this.commit('setPatients', patients)
    } catch (err) {
      Notification.error({
        message: 'Fehler',
        description: 'Patienten kontent nicht geladen werden',
      })
    }
    // commit('shared/finishedLoading', 'fetchPatients', { root: true })
  }

  async registerPatient (patient: Patient) {
    // commit('shared/startedLoading', 'registerPatient', { root: true })
    try {
      const res = await Api.patients.addPatientUsingPost(patient)
      this.commit('setPatient', res)
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
    // commit('shared/finishedLoading', 'registerPatient', { root: true })
  }
}

export const patientModule = new Module({
  state: PatientState,
  getters: PatientGetters,
  mutations: PatientMutations,
  actions: PatientActions,
})

export const patientMapper = createMapper(patientModule)
