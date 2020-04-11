import { RiskOccupation } from '@/models/index'

export interface RiskOccupationOption {
  label: string;
  value: RiskOccupation;
}

export const RISK_OCCUPATIONS: RiskOccupationOption[] = [
  { value: 'NO_RISK_OCCUPATION', label: 'Kein systemrelevanter Beruf' },
  { value: 'FIRE_FIGHTER', label: 'Feuerwehrmann/frau' },
  { value: 'DOCTOR', label: 'Arzt/Ärztin' },
  { value: 'NURSE', label: 'Pflegepersonal' },
  { value: 'CAREGIVER', label: 'Altenpflege' },
]
