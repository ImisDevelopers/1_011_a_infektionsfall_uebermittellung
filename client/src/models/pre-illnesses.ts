import { Option } from '@/models/index'

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
    label: 'Postpartum (weniger als 6 Wochen)',
    value: 'POSTPARTUM',
  },
  {
    label: 'Schwangerschaft',
    value: 'PREGNANCY',
  },
]
