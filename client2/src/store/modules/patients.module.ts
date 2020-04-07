import router from '@/router'
import { Patient } from '@/api/SwaggerApi'
import { Vue } from 'vue/types/vue'
import { Actions, createMapper, Getters, Module, Mutations } from 'vuex-smart-module'
import Api from '@/api'

class PatientState {
  patient: Patient | undefined
  patients: Patient[] = []
}

class PatientGetters extends Getters<PatientState> {
  patientById(id: string): Patient | undefined {
    if (this.state.patient && this.state.patient.id === id) {
      return this.state.patient
    }
    return this.state.patients.find(patient => patient.id === id)
  }
}

class PatientMutations extends Mutations<PatientState> {
  addPatients(patients: Patient[]) {
    this.state.patients.concat(patients)
  }

  setPatients(patients: Patient[]) {
    this.state.patients = patients
  }

  setPatient(patient: Patient) {
    this.state.patient = patient
  }
}

class PatientActions extends Actions<PatientState, PatientGetters, PatientMutations, PatientActions> {
  async fetchPatients(instance: Vue) {
    try {
      // this.commit('shared/startedLoading', 'fetchPatients', { root: true })
      const patients = await Api.patients.getAllPatientsUsingGet()
      this.commit('setPatients', patients)
    } catch (err) {
      instance.$notification.error({
        message: 'Fehler',
        description: 'Patienten kontent nicht geladen werden',
      })
    }
    // commit('shared/finishedLoading', 'fetchPatients', { root: true })
  }

  async registerPatient(patient: Patient, instance: Vue) {
    // commit('shared/startedLoading', 'registerPatient', { root: true })
    try {
      const patientResponse = await Api.patients.addPatientUsingPost(patient)
      this.commit('setPatient', patientResponse)
      const notification = {
        message: 'Patient registriert.',
        description: 'Der Patient wurde erfolgreich registriert.',
      }
      instance.$notification.success(notification)
      if (patientResponse.id) {
        router.push({ name: 'patient-detail', params: { id: patientResponse.id } })
      }
    } catch (err) {
      console.log(err)
      const notification = {
        message: 'Fehler beim Registrieren des Patienten.',
        description: err.message,
      }
      instance.$notification.error(notification)
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
