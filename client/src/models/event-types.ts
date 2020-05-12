import { PatientStatus } from '@/models/index'

export interface EventTypeItem {
  id: PatientStatus;
  label: string;
  icon: string;
  color: string;
}

export const eventTypes: EventTypeItem[] = [
  {
    id: 'REGISTERED',
    label: 'Registriert',
    icon: 'login',
    color: 'blue',
  }, {
    id: 'SUSPECTED',
    label: 'Verdachtsfall',
    icon: 'search',
    color: 'purple',
  }, {
    id: 'ORDER_TEST',
    label: 'Test angefordert',
    icon: 'experiment',
    color: 'aqua',
  }, {
    id: 'SCHEDULED_FOR_TESTING',
    label: 'Wartet auf Test',
    icon: 'team',
    color: 'purple',
  }, {
    id: 'TEST_SUBMITTED_IN_PROGRESS',
    label: 'Test eingereicht',
    icon: 'clock-circle',
    color: 'purple',
  }, {
    id: 'TEST_FINISHED_POSITIVE',
    label: 'Test positiv',
    icon: 'check',
    color: 'red',
  }, {
    id: 'TEST_FINISHED_NEGATIVE',
    label: 'Test negativ',
    icon: 'stop',
    color: 'green',
  }, {
    id: 'TEST_FINISHED_INVALID',
    label: 'Test invalide',
    icon: 'warning',
    color: 'gold',
  }, {
    id: 'TEST_FINISHED_RECOVERED',
    label: 'Getestet und erholt',
    icon: 'rollback',
    color: 'green',
  }, {
    id: 'TEST_FINISHED_NOT_RECOVERED',
    label: 'Getestet und nicht erholt',
    icon: 'logout',
    color: 'red',
  }, {
    id: 'PATIENT_DEAD',
    label: 'Verstorben',
    icon: 'cloud',
    color: 'gray',
  }, {
    id: 'DOCTORS_VISIT',
    label: 'Arztbesuch',
    icon: 'reconciliation',
    color: 'blue',
  },
  {
    id: 'QUARANTINE_MANDATED',
    label: 'Quarantäne angeordnet',
    icon: 'safety',
    color: 'palevioletred',
  },
]

export interface TestResultType {
  id: 'TEST_SUBMITTED' | 'TEST_IN_PROGRESS' | 'TEST_POSITIVE' | 'TEST_NEGATIVE' | 'TEST_INVALID';
  label: string;
  icon: string;
}
export const testResults: TestResultType[] = [
  {
    id: 'TEST_SUBMITTED',
    label: 'Test eingereicht',
    icon: 'login',
  }, {
    id: 'TEST_IN_PROGRESS',
    label: 'Test läuft',
    icon: 'clock-circle',
  }, {
    id: 'TEST_POSITIVE',
    label: 'Test positiv',
    icon: 'check',
  }, {
    id: 'TEST_NEGATIVE',
    label: 'Test negativ',
    icon: 'stop',
  }, {
    id: 'TEST_INVALID',
    label: 'Test invalide',
    icon: 'warning',
  },
]
