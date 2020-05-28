import { CreateInstitutionDTO, RegisterUserRequest } from '@/api/SwaggerApi'

export type InstitutionType = Exclude<
  CreateInstitutionDTO['institutionType'],
  undefined
>
export type InstitutionRole =
  | 'ROLE_TEST_SITE'
  | 'ROLE_LABORATORY'
  | 'ROLE_DOCTORS_OFFICE'
  | 'ROLE_CLINIC'
  | 'ROLE_GOVERNMENT_AGENCY'
  | 'ROLE_DEPARTMENT_OF_HEALTH'
export type UserRole = Exclude<RegisterUserRequest['userRole'], undefined>
export type PatientStatus =
  | 'REGISTERED'
  | 'SUSPECTED'
  | 'ORDER_TEST'
  | 'SCHEDULED_FOR_TESTING'
  | 'TEST_SUBMITTED_IN_PROGRESS'
  | 'TEST_FINISHED_POSITIVE'
  | 'TEST_FINISHED_NEGATIVE'
  | 'TEST_FINISHED_INVALID'
  | 'TEST_FINISHED_RECOVERED'
  | 'TEST_FINISHED_NOT_RECOVERED'
  | 'PATIENT_DEAD'
  | 'DOCTORS_VISIT'
  | 'QUARANTINE_SELECTED'
  | 'QUARANTINE_MANDATED'
  | 'QUARANTINE_RELEASED'
  | 'QUARANTINE_PROFESSIONBAN_RELEASED'
  | 'HOSPITALIZATION_MANDATED'
export type RiskOccupation =
  | 'NO_RISK_OCCUPATION'
  | 'FIRE_FIGHTER_POLICE'
  | 'TEACHER'
  | 'PUBLIC_ADMINISTRATION'
  | 'STUDENT'
  | 'DOCTOR'
  | 'CAREGIVER'
  | 'NURSE'

export interface Option {
  label: string
  value: string
}
