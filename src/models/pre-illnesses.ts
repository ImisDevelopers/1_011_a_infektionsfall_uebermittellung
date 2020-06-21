import { Option } from '@/models/index'

export const ADDITIONAL_PRE_ILLNESSES: Option[] = [
  {
    label: 'Akutes schweres Atemsyndrom (ARDS)',
    value: 'ARDS',
  },
  {
    label: 'Beatmungspflichtige Atemwegserkrankung',
    value: 'RESPIRATORY_DISEASE',
  },
]

export const PRE_ILLNESSES: Option[] = [
  {
    label: 'Chronische Lungenerkrankung (z.B. COPD)',
    value: 'CHRONIC_LUNG_DISEASE',
  },
  {
    label: 'Diabetes',
    value: 'DIABETES',
  },
  {
    label: 'Fettleibigkeit',
    value: 'ADIPOSITAS',
  },
  {
    label: 'Herz-Kreislauf (inkl. Bluthochdruck)',
    value: 'CARDIOVASCULAR_DISEASE',
  },
  {
    label: 'Immundefizit (inkl. HIV)',
    value: 'IMMUNODEFICIENCY',
  },
  {
    label: 'Krebserkrankung',
    value: 'CANCER',
  },
  {
    label: 'Lebererkrankung',
    value: 'LIVER_DISEASE',
  },
  {
    label: 'Neurologische / neuromuskuläre Erkrankung',
    value: 'NEUROLOGICAL_DISEASE',
  },
  {
    label: 'Nierenerkrankung',
    value: 'KIDNEY_DISEASE',
  },
  {
    label: 'Raucher',
    value: 'SMOKING',
  },
  {
    label: 'Schwangerschaft',
    value: 'PREGNANCY',
  },
]
