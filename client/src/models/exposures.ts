import { Option } from '@/models/index'

// All Exposure items:

const MEDICAL_HEALTH_PROFESSION = {
  label: 'Medizinischer Heilberuf',
  value: 'MEDICAL_HEALTH_PROFESSION',
}
const MEDICAL_LABORATORY = {
  label: 'Arbeit in medizinischem Labor',
  value: 'MEDICAL_LABORATORY',
}

const STAY_IN_MEDICAL_FACILITY = {
  label: 'Aufenthalt in medizinischer Einrichtung in den letzten 14 Tagen vor der Erkrankung',
  value: 'STAY_IN_MEDICAL_FACILITY',
}

const CONTACT_WITH_CORONA_CASE = {
  label: 'Enger Kontakt mit wahrscheinlichem oder bestätigtem Fall in den letzten 14 Tagen vor der Erkrankung',
  value: 'CONTACT_WITH_CORONA_CASE',
}

const COMMUNITY_FACILITY = {
  label: 'Arbeit in Gemeinschaftseinrichtung (z.B. Schule, Kinderkrippe, Heim, sonst. Massenunterkünfte (§§34 und 36 Abs. 1 IfSG))',
  value: 'COMMUNITY_FACILITY',
}

const COMMUNITY_FACILITY_MINORS = {
  label: 'Betreuung in Gemeinschaftseinrichtung für Kinder oder Jugendliche, z.B.Schule, Kinderkrippe (§33 IfSG)',
  value: 'COMMUNITY_FACILITY_MINORS',
}

// We want to display different items depending on self-registration or registration by doctor

// Registration By Doctor
export const EXPOSURES_INTERNAL: Option[] = [
  MEDICAL_HEALTH_PROFESSION, MEDICAL_LABORATORY, STAY_IN_MEDICAL_FACILITY,
  COMMUNITY_FACILITY, COMMUNITY_FACILITY_MINORS,
  CONTACT_WITH_CORONA_CASE,
]

// Self-Registration
export const EXPOSURES_PUBLIC: Option[] = [
  MEDICAL_HEALTH_PROFESSION, MEDICAL_LABORATORY, STAY_IN_MEDICAL_FACILITY, CONTACT_WITH_CORONA_CASE,
]

export const EXPOSURE_LOCATIONS: Option[] = [
  {
    label: 'in einer medizinischen Einrichtung',
    value: 'MEDICAL_FACILITY',
  }, {
    label: 'im privaten Haushalt',
    value: 'PRIVATE',
  }, {
    label: 'am Arbeitsplatz',
    value: 'WORK',
  }, {
    label: 'andere / sonstige',
    value: 'OTHER',
  },
]
