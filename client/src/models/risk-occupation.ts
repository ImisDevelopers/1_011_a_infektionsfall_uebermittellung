import { RiskOccupation } from '@/models/index'

export interface RiskOccupationOption {
  label: string
  value: RiskOccupation
}

export const RISK_OCCUPATIONS: RiskOccupationOption[] = [
  { value: 'DOCTOR', label: 'Arzt/Ärztin' },
  { value: 'CAREGIVER', label: 'Altenpfleger-in' },
  {
    value: 'FIRE_FIGHTER_POLICE',
    label: 'Gefahrenabwehr (Polizei, Feuerwehr usw.)',
  },
  { value: 'NURSE', label: 'Krankenpfleger-in' },
  { value: 'TEACHER', label: 'Lehrer-in/Kindergärtner-in' },
  { value: 'PUBLIC_ADMINISTRATION', label: 'Öffentliche Verwaltung' },
  { value: 'STUDENT', label: 'Schüler-in' },
  {
    value: 'NO_RISK_OCCUPATION',
    label: 'Anderer',
  },
]
