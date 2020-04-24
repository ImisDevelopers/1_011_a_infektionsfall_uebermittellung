import { RiskOccupation } from '@/models/index'

export interface RiskOccupationOption {
  label: string;
  value: RiskOccupation;
}

export const RISK_OCCUPATIONS: RiskOccupationOption[] = [
  { value: 'FIRE_FIGHTER', label: 'Feuerwehrmann/frau' },
  { value: 'DOCTOR', label: 'Arzt/Ärztin' },
  { value: 'NURSE', label: 'Pflegepersonal' },
  { value: 'CAREGIVER', label: 'Altenpflege' },
  { value: 'NO_RISK_OCCUPATION', label: 'Keiner der genannten (bitte unten eingeben)' },
]
