import { PatientStatus } from '@/models/index'

export interface EventTypeItem {
  id: PatientStatus
  label: string
  icon: string
}

export const eventTypes: EventTypeItem[] = [
  {
    id: 'REGISTERED',
    label: 'Registriert',
    icon: 'login',
  },
  {
    id: 'SUSPECTED',
    label: 'Verdachtsfall',
    icon: 'search',
  },
  {
    id: 'ORDER_TEST',
    label: 'Test angefordert',
    icon: 'experiment',
  },
  {
    id: 'SCHEDULED_FOR_TESTING',
    label: 'Wartet auf Test',
    icon: 'team',
  },
  {
    id: 'TEST_SUBMITTED',
    label: 'Test eingereicht',
    icon: 'clock-circle',
  },
  {
    id: 'TEST_FINISHED_POSITIVE',
    label: 'Test positiv',
    icon: 'check',
  },
  {
    id: 'TEST_FINISHED_NEGATIVE',
    label: 'Test negativ',
    icon: 'stop',
  },
  {
    id: 'PATIENT_DEAD',
    label: 'Verstorben',
    icon: 'cloud',
  },
  {
    id: 'DOCTORS_VISIT',
    label: 'Arztbesuch',
    icon: 'reconciliation',
  },
  {
    id: 'QUARANTINE_SELECTED',
    label: 'Quarantäne vorgemerkt',
    icon: 'safety',
  },
  {
    id: 'QUARANTINE_MANDATED',
    label: 'Quarantäne angeordnet',
    icon: 'safety',
  },
  {
    id: 'QUARANTINE_RELEASED',
    label: 'Quarantäne aufgehoben',
    icon: 'safety',
  },
  {
    id: 'QUARANTINE_PROFESSIONBAN_RELEASED',
    label: 'Arbeitsverbot aufgehoben',
    icon: 'safety',
  },
  {
    id: 'HOSPITALIZATION_MANDATED',
    label: 'Hospitalisiert',
    icon: 'safety',
  },
  {
    id: 'HOSPITALIZATION_RELEASED',
    label: 'Hospitalisierung aufgehoben',
    icon: 'safety',
  },
  {
    id: 'CASE_DATA_UPDATED',
    label: 'Symptome oder Erkrankungsdatum aktualisiert',
    icon: 'safety',
  },
]

export interface TestResultType {
  id:
    | 'TEST_SUBMITTED'
    | 'TEST_IN_PROGRESS'
    | 'TEST_POSITIVE'
    | 'TEST_NEGATIVE'
    | 'TEST_INVALID'
  label: string
  icon: string
}
export const testResults: TestResultType[] = [
  {
    id: 'TEST_SUBMITTED',
    label: 'Test eingereicht',
    icon: 'login',
  },
  {
    id: 'TEST_POSITIVE',
    label: 'Test positiv',
    icon: 'check',
  },
  {
    id: 'TEST_NEGATIVE',
    label: 'Test negativ',
    icon: 'stop',
  },
]
