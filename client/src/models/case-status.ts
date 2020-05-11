import { PatientStatus2 } from '@/models/index'

export interface CaseStatusItem {
  id: PatientStatus2;
  label: string;
  icon: string;
}

export const caseStatus: CaseStatusItem[] = [
  {
    id: 'REGISTERED',
    label: 'Registriert',
    icon: 'login',
  }, {
    id: 'CONTACT',
    label: 'Kontaktfall',
    icon: 'search',
  }, {
    id: 'SUSPECTED',
    label: 'Verdachtsfall',
    icon: 'search',
  }, {
    id: 'TEST_FINISHED_POSITIVE',
    label: 'Bestätigter Fall',
    icon: 'check',
  }, {
    id: 'TEST_FINISHED_NEGATIVE',
    label: 'Ausgeräumter Fall',
    icon: 'stop',
  }, {
    id: 'RECOVERED_WITH_EMISSION',
    label: 'Genesener Fall mit Ausscheidung',
    icon: 'logout',
  }, {
    id: 'RECOVERED_NO_EMISSION',
    label: 'Genesener Fall ohne Ausscheidung',
    icon: 'logout',
  }, {
    id: 'RECOVERED_NO_TEST',
    label: 'Genesener Fall ohne Nachweis',
    icon: 'logout',
  }, {
    id: 'PATIENT_DEAD',
    label: 'Verstorben',
    icon: 'cloud',
  },
  {
    id: 'QUARANTINE_MANDATED',
    label: 'Quarantäne angeordnet',
    icon: 'safety',
  },
]
