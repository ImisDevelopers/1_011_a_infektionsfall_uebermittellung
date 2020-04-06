import { CreateInstitutionDTO, RegisterUserRequest } from '@/api/SwaggerApi'

export type InstitutionType = CreateInstitutionDTO['institutionType'];
export type UserRole = Exclude<RegisterUserRequest['userRole'], undefined>;
