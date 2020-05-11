import { RiskOccupation } from '@/models/index'

export interface RiskOccupationOption {
  label: string;
  value: RiskOccupation;
}

export const RISK_OCCUPATIONS: RiskOccupationOption[] = [
  { value: 'PUBLIC_ADMINISTRATION', label: 'Öffentliche Verwaltung' },
  { value: 'STUDENT', label: 'Schüler' },
  { value: 'TEACHER', label: 'Lehrer' },
  { value: 'FIRE_FIGHTER_POLICE', label: 'Gefahrenabwehr (Polizei, Feuerwehr usw.)' },
  { value: 'DOCTOR', label: 'Arzt/Ärztin' },
  { value: 'NURSE', label: 'Pflegepersonal' },
  { value: 'CAREGIVER', label: 'Altenpflege' },
  { value: 'NO_RISK_OCCUPATION', label: 'Keiner der genannten (bitte unten eingeben)' },
]
