import { CreateInstitutionDTO, RegisterUserRequest } from '@/api/SwaggerApi'

export type InstitutionType = CreateInstitutionDTO['institutionType'];
export type InstitutionRule = 'ROLE_TEST_SITE' | 'ROLE_LABORATORY' | 'ROLE_DOCTORS_OFFICE' | 'ROLE_CLINIC' | 'ROLE_GOVERNMENT_AGENCY'
export type UserRole = Exclude<RegisterUserRequest['userRole'], undefined>;
