/* tslint:disable */
/* eslint-disable */

/*
 * ---------------------------------------------------------------
 * ## THIS FILE WAS GENERATED VIA SWAGGER-TYPESCRIPT-API        ##
 * ##                                                           ##
 * ## AUTHOR: acacode                                           ##
 * ## SOURCE: https://github.com/acacode/swagger-typescript-api ##
 * ---------------------------------------------------------------
 */

export interface AdministrativeIncident {
  caseId?: string;
  comment?: string;
  dateOfIllness?: string;
  dateOfReporting?: string;
  eventDate: string;
  eventType:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
  id?: string;
  illness?: "CORONA";
  patient?: Patient;
  responsibleDoctor?: Doctor;
  symptoms?: string[];
  versionTimestamp?: string;
  versionUser?: User;
}

export type AggregationResultZip = object;

export interface ApiFunctionSpec {
  method?: string;
  path?: string;
}

export interface AuthRequestDTO {
  password?: string;
  username?: string;
}

export interface BulkInsertOptions {
  allowOverride?: boolean;
}

export interface ChangePasswordDTO {
  newPassword?: string;
  oldPassword?: string;
}

export interface CreateInstitutionDTO {
  city?: string;
  comment?: string;
  email?: string;
  houseNumber?: string;
  id?: string;
  institutionType?:
    | "LABORATORY"
    | "TEST_SITE"
    | "CLINIC"
    | "DOCTORS_OFFICE"
    | "GOVERNMENT_AGENCY"
    | "DEPARTMENT_OF_HEALTH";
  name?: string;
  phoneNumber?: string;
  street?: string;
  user?: RegisterUserRequest;
  zip?: string;
}

export interface CreatePatientDTO {
  city?: string;
  coronaContacts?: boolean;
  country?: string;
  dateOfBirth?: string;
  dateOfDeath?: string;
  dateOfHospitalization?: string;
  dateOfIllness?: string;
  dateOfReporting?: string;
  email?: string;
  employer?: string;
  firstName?: string;
  fluImmunization?: boolean;
  gender?: string;
  houseNumber?: string;
  insuranceCompany?: string;
  insuranceMembershipNumber?: string;
  lastName?: string;
  nationality?: string;
  occupation?: string;
  onIntensiveCareUnit?: boolean;
  patientStatus?:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
  phoneNumber?: string;
  preIllnesses?: string[];
  quarantineUntil?: string;
  riskAreas?: string[];
  riskOccupation?:
    | "NO_RISK_OCCUPATION"
    | "FIRE_FIGHTER_POLICE"
    | "TEACHER"
    | "PUBLIC_ADMINISTRATION"
    | "STUDENT"
    | "DOCTOR"
    | "CAREGIVER"
    | "NURSE";
  speedOfSymptomsOutbreak?: string;
  stayCity?: string;
  stayCountry?: string;
  stayHouseNumber?: string;
  stayStreet?: string;
  stayZip?: string;
  street?: string;
  symptoms?: string[];
  weakenedImmuneSystem?: boolean;
  zip?: string;
}

export interface Doctor {
  city?: string;
  comment?: string;
  email?: string;
  houseNumber?: string;
  id?: string;
  name?: string;
  phoneNumber?: string;
  street?: string;
  type?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY" | "DEPARTMENT_OF_HEALTH";
  users?: User[];
  zip?: string;
}

export interface ExposureContactContactView {
  firstName?: string;
  id?: string;
  inQuarantine?: boolean;
  infected?: boolean;
  lastName?: string;
}

export interface ExposureContactFromServer {
  comment?: string;
  contact?: ExposureContactContactView;
  context?: string;
  dateOfContact?: string;
  id?: number;
  source?: ExposureContactContactView;
}

export interface ExposureContactToServer {
  comment?: string;
  contact?: string;
  context?: string;
  dateOfContact?: string;
  id?: number;
  source?: string;
}

export interface GrantedAuthority {
  authority?: string;
}

export interface HealthInsuranceCompanies {
  preDefined?: string[];
  userDefined?: string[];
}

export interface HospitalizationIncident {
  caseId?: string;
  eventDate: string;
  eventType:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
  id?: string;
  intensiveCare?: boolean;
  patient?: Patient;
  releasedOn?: string;
  versionTimestamp?: string;
  versionUser?: User;
}

export interface Institution {
  city?: string;
  comment?: string;
  email?: string;
  houseNumber?: string;
  id?: string;
  name?: string;
  phoneNumber?: string;
  street?: string;
  type?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY" | "DEPARTMENT_OF_HEALTH";
  users?: User[];
  zip?: string;
}

export interface InstitutionDTO {
  city?: string;
  comment?: string;
  email?: string;
  houseNumber?: string;
  id?: string;
  institutionType?:
    | "LABORATORY"
    | "TEST_SITE"
    | "CLINIC"
    | "DOCTORS_OFFICE"
    | "GOVERNMENT_AGENCY"
    | "DEPARTMENT_OF_HEALTH";
  name?: string;
  phoneNumber?: string;
  street?: string;
  zip?: string;
}

export interface InstitutionImpl {
  city?: string;
  comment?: string;
  email?: string;
  houseNumber?: string;
  id?: string;
  name?: string;
  phoneNumber?: string;
  street?: string;
  type?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY" | "DEPARTMENT_OF_HEALTH";
  users?: User[];
  zip?: string;
}

export interface Laboratory {
  assignedTestIncidents?: TestIncident[];
  city?: string;
  comment?: string;
  email?: string;
  houseNumber?: string;
  id?: string;
  name?: string;
  phoneNumber?: string;
  street?: string;
  type?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY" | "DEPARTMENT_OF_HEALTH";
  users?: User[];
  zip?: string;
}

export interface Link {
  href?: string;
  templated?: boolean;
}

export interface ModelAndView {
  empty?: boolean;
  model?: object;
  modelMap?: Record<string, object>;
  reference?: boolean;
  status?:
    | "100 CONTINUE"
    | "101 SWITCHING_PROTOCOLS"
    | "102 PROCESSING"
    | "103 CHECKPOINT"
    | "200 OK"
    | "201 CREATED"
    | "202 ACCEPTED"
    | "203 NON_AUTHORITATIVE_INFORMATION"
    | "204 NO_CONTENT"
    | "205 RESET_CONTENT"
    | "206 PARTIAL_CONTENT"
    | "207 MULTI_STATUS"
    | "208 ALREADY_REPORTED"
    | "226 IM_USED"
    | "300 MULTIPLE_CHOICES"
    | "301 MOVED_PERMANENTLY"
    | "302 FOUND"
    | "302 MOVED_TEMPORARILY"
    | "303 SEE_OTHER"
    | "304 NOT_MODIFIED"
    | "305 USE_PROXY"
    | "307 TEMPORARY_REDIRECT"
    | "308 PERMANENT_REDIRECT"
    | "400 BAD_REQUEST"
    | "401 UNAUTHORIZED"
    | "402 PAYMENT_REQUIRED"
    | "403 FORBIDDEN"
    | "404 NOT_FOUND"
    | "405 METHOD_NOT_ALLOWED"
    | "406 NOT_ACCEPTABLE"
    | "407 PROXY_AUTHENTICATION_REQUIRED"
    | "408 REQUEST_TIMEOUT"
    | "409 CONFLICT"
    | "410 GONE"
    | "411 LENGTH_REQUIRED"
    | "412 PRECONDITION_FAILED"
    | "413 PAYLOAD_TOO_LARGE"
    | "413 REQUEST_ENTITY_TOO_LARGE"
    | "414 URI_TOO_LONG"
    | "414 REQUEST_URI_TOO_LONG"
    | "415 UNSUPPORTED_MEDIA_TYPE"
    | "416 REQUESTED_RANGE_NOT_SATISFIABLE"
    | "417 EXPECTATION_FAILED"
    | "418 I_AM_A_TEAPOT"
    | "419 INSUFFICIENT_SPACE_ON_RESOURCE"
    | "420 METHOD_FAILURE"
    | "421 DESTINATION_LOCKED"
    | "422 UNPROCESSABLE_ENTITY"
    | "423 LOCKED"
    | "424 FAILED_DEPENDENCY"
    | "425 TOO_EARLY"
    | "426 UPGRADE_REQUIRED"
    | "428 PRECONDITION_REQUIRED"
    | "429 TOO_MANY_REQUESTS"
    | "431 REQUEST_HEADER_FIELDS_TOO_LARGE"
    | "451 UNAVAILABLE_FOR_LEGAL_REASONS"
    | "500 INTERNAL_SERVER_ERROR"
    | "501 NOT_IMPLEMENTED"
    | "502 BAD_GATEWAY"
    | "503 SERVICE_UNAVAILABLE"
    | "504 GATEWAY_TIMEOUT"
    | "505 HTTP_VERSION_NOT_SUPPORTED"
    | "506 VARIANT_ALSO_NEGOTIATES"
    | "507 INSUFFICIENT_STORAGE"
    | "508 LOOP_DETECTED"
    | "509 BANDWIDTH_LIMIT_EXCEEDED"
    | "510 NOT_EXTENDED"
    | "511 NETWORK_AUTHENTICATION_REQUIRED";
  view?: View;
  viewName?: string;
}

export interface Patient {
  city?: string;
  comment?: string;
  confirmed?: boolean;
  coronaContacts?: boolean;
  country?: string;
  creationTimestamp?: string;
  dateOfBirth?: string;
  dateOfDeath?: string;
  dateOfHospitalization?: string;
  dateOfIllness?: string;
  email?: string;
  employer?: string;
  firstName?: string;
  fluImmunization?: boolean;
  gender?: string;
  houseNumber?: string;
  id?: string;
  insuranceCompany?: string;
  insuranceMembershipNumber?: string;
  lastName?: string;
  nationality?: string;
  occupation?: string;
  onIntensiveCareUnit?: boolean;
  patientStatus:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
  phoneNumber?: string;
  preIllnesses?: string[];
  quarantineUntil?: string;
  riskAreas?: string[];
  riskOccupation?:
    | "NO_RISK_OCCUPATION"
    | "FIRE_FIGHTER_POLICE"
    | "TEACHER"
    | "PUBLIC_ADMINISTRATION"
    | "STUDENT"
    | "DOCTOR"
    | "CAREGIVER"
    | "NURSE";
  speedOfSymptomsOutbreak?: string;
  stayCity?: string;
  stayCountry?: string;
  stayHouseNumber?: string;
  stayStreet?: string;
  stayZip?: string;
  street?: string;
  symptoms?: string[];
  weakenedImmuneSystem?: boolean;
  zip?: string;
}

export interface PatientEvent {
  accomodation?: string;
  comment?: string;
  eventTimestamp?: Timestamp;
  eventType?:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
  id?: string;
  illness?: "CORONA";
  patient?: Patient;
  responsibleDoctor?: Doctor;
}

export interface PatientLogDto {
  administrativeIncidents: AdministrativeIncident[];
  hospitalizationIncidents: HospitalizationIncident[];
  quarantineIncidents: QuarantineIncident[];
  testIncidents: TestIncident[];
}

export interface PatientSearchParamsDTO {
  city?: string;
  doctorId?: string;
  email?: string;
  firstName?: string;
  gender?: string;
  houseNumber?: string;
  id?: string;
  includePatientEvents?: boolean;
  insuranceCompany?: string;
  insuranceMembershipNumber?: string;
  laboratoryId?: string;
  lastName?: string;
  offsetPage?: number;
  order?: string;
  orderBy?: string;
  pageSize?: number;
  patientStatus?:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
  phoneNumber?: string;
  quarantineStatus?: (
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED"
  )[];
  street?: string;
  zip?: string;
}

export interface PatientSimpleSearchParamsDTO {
  offsetPage?: number;
  order?: string;
  orderBy?: string;
  pageSize?: number;
  query?: string;
}

export interface QuarantineIncident {
  caseId?: string;
  comment?: string;
  eventDate: string;
  eventType:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
  id?: string;
  patient?: Patient;
  until?: string;
  versionTimestamp?: string;
  versionUser?: User;
}

export interface RegisterUserRequest {
  firstName?: string;
  lastName?: string;
  password?: string;
  userRole?: "USER_ROLE_ADMIN" | "USER_ROLE_REGULAR";
  username?: string;
}

export interface RequestLabTestDTO {
  doctorId?: string;
  laboratoryId?: string;
  patientId?: string;
}

export interface RequestQuarantineDTO {
  comment?: string;
  dateUntil?: string;
  eventDate?: string;
  status?:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
}

export interface SendToQuarantineDTO {
  eventDate?: string;
  patientIds?: string[];
}

export interface TestIncident {
  caseId?: string;
  comment?: string;
  eventDate: string;
  eventType:
    | "REGISTERED"
    | "SUSPECTED"
    | "ORDER_TEST"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT"
    | "QUARANTINE_SELECTED"
    | "QUARANTINE_MANDATED"
    | "QUARANTINE_RELEASED"
    | "QUARANTINE_PROFESSIONBAN_RELEASED"
    | "HOSPITALIZATION_MANDATED"
    | "HOSPITALIZATION_RELEASED"
    | "CASE_DATA_UPDATED";
  id?: string;
  laboratory?: Laboratory;
  material?: "RACHENABSTRICH" | "NASENABSTRICH" | "VOLLBLUT";
  patient?: Patient;
  report?: string;
  status?: "TEST_SUBMITTED" | "TEST_POSITIVE" | "TEST_NEGATIVE";
  testId?: string;
  type?: "PCR" | "ANTIBODY";
  versionTimestamp?: string;
  versionUser?: User;
}

export interface Timestamp {
  date?: number;
  day?: number;
  hours?: number;
  minutes?: number;
  month?: number;
  nanos?: number;
  seconds?: number;
  time?: number;
  timezoneOffset?: number;
  year?: number;
}

export interface TokenDTO {
  jwtToken?: string;
}

export interface User {
  accountNonExpired?: boolean;
  accountNonLocked?: boolean;
  authorities?: GrantedAuthority[];
  credentialsNonExpired?: boolean;
  enabled?: boolean;
  firstName?: string;
  id?: number;
  institution?: InstitutionImpl;
  institutionId?: string;
  institutionType?:
    | "LABORATORY"
    | "TEST_SITE"
    | "CLINIC"
    | "DOCTORS_OFFICE"
    | "GOVERNMENT_AGENCY"
    | "DEPARTMENT_OF_HEALTH";
  lastName?: string;
  userRole?: "USER_ROLE_ADMIN" | "USER_ROLE_REGULAR";
  username?: string;
}

export interface UserDTO {
  authorities?: GrantedAuthority[];
  firstName?: string;
  id?: number;
  lastName?: string;
  userRole?: "USER_ROLE_ADMIN" | "USER_ROLE_REGULAR";
  username?: string;
}

export interface View {
  contentType?: string;
}

export interface BulkRequest_BulkInsertOptions_ExposureContactToServer_ {
  items?: ExposureContactToServer[];
  options?: BulkInsertOptions;
}

export interface ItemStatus_ExposureContactFromServer_string_string_ {
  details?: "CREATE" | "OVERRIDE";
  error?: string;
  result?: ExposureContactFromServer;
  success?: boolean;
}

export type Map_string_Link_ = Record<string, Link>;

export type RequestParams = Omit<RequestInit, "body" | "method"> & {
  secure?: boolean;
};

export type RequestQueryParamsType = Record<string | number, any>;

type ApiConfig<SecurityDataType> = {
  baseUrl?: string;
  baseApiParams?: RequestParams;
  securityWorker?: (securityData: SecurityDataType) => RequestParams;
};

const enum BodyType {
  Json,
}

class HttpClient<SecurityDataType> {
  public baseUrl: string = "//localhost/";
  private securityData: SecurityDataType = null as any;
  private securityWorker: ApiConfig<SecurityDataType>["securityWorker"] = (() => {}) as any;

  private baseApiParams: RequestParams = {
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
    redirect: "follow",
    referrerPolicy: "no-referrer",
  };

  constructor({ baseUrl, baseApiParams, securityWorker }: ApiConfig<SecurityDataType> = {}) {
    this.baseUrl = baseUrl || this.baseUrl;
    this.baseApiParams = baseApiParams || this.baseApiParams;
    this.securityWorker = securityWorker || this.securityWorker;
  }

  public setSecurityData = (data: SecurityDataType) => {
    this.securityData = data;
  };

  private addQueryParam(query: RequestQueryParamsType, key: string) {
    return (
      encodeURIComponent(key) + "=" + encodeURIComponent(Array.isArray(query[key]) ? query[key].join(",") : query[key])
    );
  }

  protected addQueryParams(rawQuery?: RequestQueryParamsType): string {
    const query = rawQuery || {};
    const keys = Object.keys(query).filter((key) => "undefined" !== typeof query[key]);
    return keys.length
      ? `?${keys
          .map((key) =>
            typeof query[key] === "object" && !Array.isArray(query[key])
              ? this.addQueryParams(query[key] as object).substring(1)
              : this.addQueryParam(query, key),
          )
          .join("&")}`
      : "";
  }

  private bodyFormatters: Record<BodyType, (input: any) => any> = {
    [BodyType.Json]: JSON.stringify,
  };

  private mergeRequestOptions(params: RequestParams, securityParams?: RequestParams): RequestParams {
    return {
      ...this.baseApiParams,
      ...params,
      ...(securityParams || {}),
      headers: {
        ...(this.baseApiParams.headers || {}),
        ...(params.headers || {}),
        ...((securityParams && securityParams.headers) || {}),
      },
    };
  }

  private safeParseResponse = <T = any, E = any>(response: Response): Promise<T> =>
    response
      .json()
      .then((data) => data)
      .catch((e) => response.text);

  public request = <T = any, E = any>(
    path: string,
    method: string,
    { secure, ...params }: RequestParams = {},
    body?: any,
    bodyType?: BodyType,
    secureByDefault?: boolean,
  ): Promise<T> =>
    fetch(`${this.baseUrl}${path}`, {
      // @ts-ignore
      ...this.mergeRequestOptions(params, (secureByDefault || secure) && this.securityWorker(this.securityData)),
      method,
      body: body ? this.bodyFormatters[bodyType || BodyType.Json](body) : null,
    }).then(async (response) => {
      const data = await this.safeParseResponse<T, E>(response);
      if (!response.ok) throw data;
      return data;
    });
}

/**
 * @title Api Documentation
 * @version 1.0
 * @baseUrl //localhost/
 * Api Documentation
 */
export class Api<SecurityDataType = any> extends HttpClient<SecurityDataType> {
  actuator = {
    /**
     * @tags web-mvc-links-handler
     * @name linksUsingGET
     * @summary links
     * @request GET:/actuator
     * @secure
     */
    linksUsingGet: (params?: RequestParams) =>
      this.request<Record<string, Record<string, Link>>, any>(`/actuator`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags operation-handler
     * @name handleUsingGET_1
     * @summary handle
     * @request GET:/actuator/health
     * @secure
     */
    handleUsingGet1: (body: Record<string, string>, params?: RequestParams) =>
      this.request<AggregationResultZip, any>(`/actuator/health`, "GET", params, body, BodyType.Json, true),

    /**
     * @tags operation-handler
     * @name handleUsingGET
     * @summary handle
     * @request GET:/actuator/health/**
     * @secure
     */
    handleUsingGet: (body: Record<string, string>, params?: RequestParams) =>
      this.request<AggregationResultZip, any>(`/actuator/health/**`, "GET", params, body, BodyType.Json, true),

    /**
     * @tags operation-handler
     * @name handleUsingGET_2
     * @summary handle
     * @request GET:/actuator/info
     * @secure
     */
    handleUsingGet2: (body: Record<string, string>, params?: RequestParams) =>
      this.request<AggregationResultZip, any>(`/actuator/info`, "GET", params, body, BodyType.Json, true),
  };
  api = {
    /**
     * @tags auth-controller
     * @name signInUserUsingPOST
     * @summary signInUser
     * @request POST:/api/auth
     * @secure
     */
    signInUserUsingPost: (authRequestDTO: AuthRequestDTO, params?: RequestParams) =>
      this.request<TokenDTO, any>(`/api/auth`, "POST", params, authRequestDTO, BodyType.Json, true),

    /**
     * @tags auth-controller
     * @name getInstitutionUsingGET
     * @summary getInstitution
     * @request GET:/api/auth/institution
     * @secure
     */
    getInstitutionUsingGet: (params?: RequestParams) =>
      this.request<InstitutionDTO, any>(`/api/auth/institution`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags auth-controller
     * @name registerInstitutionUsingPOST
     * @summary registerInstitution
     * @request POST:/api/auth/register/institution
     * @secure
     */
    registerInstitutionUsingPost: (institutionDTO: CreateInstitutionDTO, params?: RequestParams) =>
      this.request<Institution, any>(
        `/api/auth/register/institution`,
        "POST",
        params,
        institutionDTO,
        BodyType.Json,
        true,
      ),

    /**
     * @tags auth-controller
     * @name registerUserUsingPOST
     * @summary registerUser
     * @request POST:/api/auth/register/user
     * @secure
     */
    registerUserUsingPost: (registerUserRequest: RegisterUserRequest, params?: RequestParams) =>
      this.request<User, any>(`/api/auth/register/user`, "POST", params, registerUserRequest, BodyType.Json, true),

    /**
     * @tags auth-controller
     * @name currentUserUsingGET
     * @summary currentUser
     * @request GET:/api/auth/user
     * @secure
     */
    currentUserUsingGet: (params?: RequestParams) =>
      this.request<User, any>(`/api/auth/user`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags auth-controller
     * @name changePasswordUsingPOST
     * @summary changePassword
     * @request POST:/api/auth/user/change-password
     * @secure
     */
    changePasswordUsingPost: (changePasswordDTO: ChangePasswordDTO, params?: RequestParams) =>
      this.request<any, any>(`/api/auth/user/change-password`, "POST", params, changePasswordDTO, BodyType.Json, true),

    /**
     * @tags doctor-controller
     * @name addScheduledEventUsingPOST
     * @summary addScheduledEvent
     * @request POST:/api/doctor/create_appointment
     * @secure
     */
    addScheduledEventUsingPost: (dto: RequestLabTestDTO, params?: RequestParams) =>
      this.request<PatientEvent, any>(`/api/doctor/create_appointment`, "POST", params, dto, BodyType.Json, true),

    /**
     * @tags enum-data-controller
     * @name getHealthInsuranceCompaniesUsingGET
     * @summary getHealthInsuranceCompanies
     * @request GET:/api/enum-data/health-insurance-companies
     * @secure
     */
    getHealthInsuranceCompaniesUsingGet: (params?: RequestParams) =>
      this.request<HealthInsuranceCompanies, any>(
        `/api/enum-data/health-insurance-companies`,
        "GET",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags exposure-contact-controller
     * @name createExposureContactUsingPOST
     * @summary createExposureContact
     * @request POST:/api/exposure-contacts
     * @secure
     */
    createExposureContactUsingPost: (dto: ExposureContactToServer, params?: RequestParams) =>
      this.request<ExposureContactFromServer, any>(`/api/exposure-contacts`, "POST", params, dto, BodyType.Json, true),

    /**
     * @tags exposure-contact-controller
     * @name updateExposureContactUsingPUT
     * @summary updateExposureContact
     * @request PUT:/api/exposure-contacts
     * @secure
     */
    updateExposureContactUsingPut: (contact: ExposureContactToServer, params?: RequestParams) =>
      this.request<ExposureContactFromServer, any>(
        `/api/exposure-contacts`,
        "PUT",
        params,
        contact,
        BodyType.Json,
        true,
      ),

    /**
     * @tags exposure-contact-controller
     * @name bulkInsertUsingPOST
     * @summary bulkInsert
     * @request POST:/api/exposure-contacts/bulk
     * @secure
     */
    bulkInsertUsingPost: (req: BulkRequest_BulkInsertOptions_ExposureContactToServer_, params?: RequestParams) =>
      this.request<ItemStatus_ExposureContactFromServer_string_string_[], any>(
        `/api/exposure-contacts/bulk`,
        "POST",
        params,
        req,
        BodyType.Json,
        true,
      ),

    /**
     * @tags exposure-contact-controller
     * @name getExposureSourceContactsForPatientsUsingPOST
     * @summary getExposureSourceContactsForPatients
     * @request POST:/api/exposure-contacts/by-contact/
     * @secure
     */
    getExposureSourceContactsForPatientsUsingPost: (patientIds: string[], params?: RequestParams) =>
      this.request<Record<string, ExposureContactFromServer[]>, any>(
        `/api/exposure-contacts/by-contact/`,
        "POST",
        params,
        patientIds,
        BodyType.Json,
        true,
      ),

    /**
     * @tags exposure-contact-controller
     * @name getExposureSourceContactsForPatientUsingGET
     * @summary getExposureSourceContactsForPatient
     * @request GET:/api/exposure-contacts/by-contact/{id}
     * @secure
     */
    getExposureSourceContactsForPatientUsingGet: (id: string, params?: RequestParams) =>
      this.request<ExposureContactFromServer[], any>(
        `/api/exposure-contacts/by-contact/${id}`,
        "GET",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags exposure-contact-controller
     * @name getExposureContactsForPatientUsingGET
     * @summary getExposureContactsForPatient
     * @request GET:/api/exposure-contacts/by-source/{id}
     * @secure
     */
    getExposureContactsForPatientUsingGet: (id: string, params?: RequestParams) =>
      this.request<ExposureContactFromServer[], any>(
        `/api/exposure-contacts/by-source/${id}`,
        "GET",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags exposure-contact-controller
     * @name getExposureContactUsingGET
     * @summary getExposureContact
     * @request GET:/api/exposure-contacts/{id}
     * @secure
     */
    getExposureContactUsingGet: (id: number, params?: RequestParams) =>
      this.request<ExposureContactFromServer, any>(
        `/api/exposure-contacts/${id}`,
        "GET",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags exposure-contact-controller
     * @name removeExposureContactUsingDELETE
     * @summary removeExposureContact
     * @request DELETE:/api/exposure-contacts/{id}
     * @secure
     */
    removeExposureContactUsingDelete: (id: number, params?: RequestParams) =>
      this.request<any, any>(`/api/exposure-contacts/${id}`, "DELETE", params, null, BodyType.Json, true),

    /**
     * @tags incident-controller
     * @name getPatientLogUsingGET
     * @summary getPatientLog
     * @request GET:/api/incidents/patient/{id}/log
     * @secure
     */
    getPatientLogUsingGet: (id: string, params?: RequestParams) =>
      this.request<PatientLogDto, any>(`/api/incidents/patient/${id}/log`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags quarantine-incident-controller
     * @name getSelectedForQuarantineUsingGET
     * @summary getSelectedForQuarantine
     * @request GET:/api/incidents/selected-for-quarantine
     * @secure
     */
    getSelectedForQuarantineUsingGet: (params?: RequestParams) =>
      this.request<QuarantineIncident[], any>(
        `/api/incidents/selected-for-quarantine`,
        "GET",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags test-incident-controller
     * @name setTestUsingPOST
     * @summary setTest
     * @request POST:/api/incidents/test
     * @secure
     */
    setTestUsingPost: (test: TestIncident, params?: RequestParams) =>
      this.request<TestIncident, any>(`/api/incidents/test`, "POST", params, test, BodyType.Json, true),

    /**
     * @tags test-incident-controller
     * @name getPatientsCurrentByTypeUsingPOST
     * @summary getPatientsCurrentByType
     * @request POST:/api/incidents/test/patient
     * @secure
     */
    getPatientsCurrentByTypeUsingPost: (patientIds: string[], params?: RequestParams) =>
      this.request<Record<string, TestIncident[]>, any>(
        `/api/incidents/test/patient`,
        "POST",
        params,
        patientIds,
        BodyType.Json,
        true,
      ),

    /**
     * @tags test-incident-controller
     * @name setTestByTestAndLabIdUsingPOST
     * @summary setTestByTestAndLabId
     * @request POST:/api/incidents/test/test-id
     * @secure
     */
    setTestByTestAndLabIdUsingPost: (test: TestIncident, params?: RequestParams) =>
      this.request<TestIncident, any>(`/api/incidents/test/test-id`, "POST", params, test, BodyType.Json, true),

    /**
     * @tags institution-controller
     * @name createInstitutionUsingPOST
     * @summary createInstitution
     * @request POST:/api/institutions
     * @secure
     */
    createInstitutionUsingPost: (createInstitutionDTO: CreateInstitutionDTO, params?: RequestParams) =>
      this.request<InstitutionDTO, any>(`/api/institutions`, "POST", params, createInstitutionDTO, BodyType.Json, true),

    /**
     * @tags institution-controller
     * @name updateInstitutionUsingPUT
     * @summary updateInstitution
     * @request PUT:/api/institutions
     * @secure
     */
    updateInstitutionUsingPut: (institutionDTO: InstitutionDTO, params?: RequestParams) =>
      this.request<InstitutionDTO, any>(`/api/institutions`, "PUT", params, institutionDTO, BodyType.Json, true),

    /**
     * @tags institution-controller
     * @name getAllDoctorsUsingGET
     * @summary getAllDoctors
     * @request GET:/api/institutions/doctors
     * @secure
     */
    getAllDoctorsUsingGet: (params?: RequestParams) =>
      this.request<Doctor[], any>(`/api/institutions/doctors`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags institution-controller
     * @name getAllLaboratoriesUsingGET
     * @summary getAllLaboratories
     * @request GET:/api/institutions/laboratories
     * @secure
     */
    getAllLaboratoriesUsingGet: (params?: RequestParams) =>
      this.request<Laboratory[], any>(`/api/institutions/laboratories`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags institution-controller
     * @name queryAllLaboratoriesUsingGET
     * @summary queryAllLaboratories
     * @request GET:/api/institutions/laboratories/query
     * @secure
     */
    queryAllLaboratoriesUsingGet: (query: { id: string }, params?: RequestParams) =>
      this.request<Laboratory[], any>(
        `/api/institutions/laboratories/query${this.addQueryParams(query)}`,
        "GET",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags patient-controller
     * @name getAllPatientsUsingGET
     * @summary getAllPatients
     * @request GET:/api/patients
     * @secure
     */
    getAllPatientsUsingGet: (params?: RequestParams) =>
      this.request<Patient[], any>(`/api/patients`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags patient-controller
     * @name addPatientUsingPOST
     * @summary addPatient
     * @request POST:/api/patients
     * @secure
     */
    addPatientUsingPost: (dto: CreatePatientDTO, params?: RequestParams) =>
      this.request<Patient, any>(`/api/patients`, "POST", params, dto, BodyType.Json, true),

    /**
     * @tags patient-controller
     * @name updatePatientUsingPUT
     * @summary updatePatient
     * @request PUT:/api/patients
     * @secure
     */
    updatePatientUsingPut: (patient: Patient, params?: RequestParams) =>
      this.request<Patient, any>(`/api/patients`, "PUT", params, patient, BodyType.Json, true),

    /**
     * @tags patient-controller
     * @name createOrderTestEventUsingPOST
     * @summary createOrderTestEvent
     * @request POST:/api/patients/event/order-test
     * @secure
     */
    createOrderTestEventUsingPost: (query?: { patientId?: string }, params?: RequestParams) =>
      this.request<PatientEvent, any>(
        `/api/patients/event/order-test${this.addQueryParams(query)}`,
        "POST",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags patient-controller
     * @name sendToQuarantineUsingPOST
     * @summary sendToQuarantine
     * @request POST:/api/patients/quarantine
     * @secure
     */
    sendToQuarantineUsingPost: (dto: SendToQuarantineDTO, params?: RequestParams) =>
      this.request<any, any>(`/api/patients/quarantine`, "POST", params, dto, BodyType.Json, true),

    /**
     * @tags patient-controller
     * @name requestQuarantineUsingPOST
     * @summary requestQuarantine
     * @request POST:/api/patients/quarantine/{id}
     * @secure
     */
    requestQuarantineUsingPost: (id: string, statusDTO: RequestQuarantineDTO, params?: RequestParams) =>
      this.request<Patient, any>(`/api/patients/quarantine/${id}`, "POST", params, statusDTO, BodyType.Json, true),

    /**
     * @tags patient-controller
     * @name queryPatientsUsingPOST
     * @summary queryPatients
     * @request POST:/api/patients/query
     * @secure
     */
    queryPatientsUsingPost: (patientSearchParamsDTO: PatientSearchParamsDTO, params?: RequestParams) =>
      this.request<Patient[], any>(`/api/patients/query`, "POST", params, patientSearchParamsDTO, BodyType.Json, true),

    /**
     * @tags patient-controller
     * @name queryPatientsSimpleUsingPOST
     * @summary queryPatientsSimple
     * @request POST:/api/patients/query-simple
     * @secure
     */
    queryPatientsSimpleUsingPost: (query: PatientSimpleSearchParamsDTO, params?: RequestParams) =>
      this.request<Patient[], any>(`/api/patients/query-simple`, "POST", params, query, BodyType.Json, true),

    /**
     * @tags patient-controller
     * @name countQueryPatientsSimpleUsingGET
     * @summary countQueryPatientsSimple
     * @request GET:/api/patients/query-simple/count
     * @secure
     */
    countQueryPatientsSimpleUsingGet: (query: { query: string }, params?: RequestParams) =>
      this.request<number, any>(
        `/api/patients/query-simple/count${this.addQueryParams(query)}`,
        "GET",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags patient-controller
     * @name countQueryPatientsUsingPOST
     * @summary countQueryPatients
     * @request POST:/api/patients/query/count
     * @secure
     */
    countQueryPatientsUsingPost: (patientSearchParamsDTO: PatientSearchParamsDTO, params?: RequestParams) =>
      this.request<number, any>(
        `/api/patients/query/count`,
        "POST",
        params,
        patientSearchParamsDTO,
        BodyType.Json,
        true,
      ),

    /**
     * @tags patient-controller
     * @name getPatientForIdUsingGET
     * @summary getPatientForId
     * @request GET:/api/patients/{id}
     * @secure
     */
    getPatientForIdUsingGet: (id: string, params?: RequestParams) =>
      this.request<Patient, any>(`/api/patients/${id}`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags permission-query-controller
     * @name queryPermissionsUsingPOST
     * @summary queryPermissions
     * @request POST:/api/permissions
     * @secure
     */
    queryPermissionsUsingPost: (items: Record<string, ApiFunctionSpec>, params?: RequestParams) =>
      this.request<Record<string, boolean>, any>(`/api/permissions`, "POST", params, items, BodyType.Json, true),

    /**
     * @tags stats-controller
     * @name getForZipUsingGET
     * @summary getForZip
     * @request GET:/api/stats
     * @secure
     */
    getForZipUsingGet: (query: { lowerBoundsZip: string; upperBoundsZips: string }, params?: RequestParams) =>
      this.request<AggregationResultZip[], any>(
        `/api/stats${this.addQueryParams(query)}`,
        "GET",
        params,
        null,
        BodyType.Json,
        true,
      ),

    /**
     * @tags user-controller
     * @name getInstitutionUsersUsingGET
     * @summary getInstitutionUsers
     * @request GET:/api/users
     * @secure
     */
    getInstitutionUsersUsingGet: (params?: RequestParams) =>
      this.request<UserDTO[], any>(`/api/users`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags user-controller
     * @name updateInstitutionUserUsingPUT
     * @summary updateInstitutionUser
     * @request PUT:/api/users
     * @secure
     */
    updateInstitutionUserUsingPut: (
      query?: {
        "authorities[0].authority"?: string;
        firstName?: string;
        id?: number;
        lastName?: string;
        userRole?: "USER_ROLE_ADMIN" | "USER_ROLE_REGULAR";
        username?: string;
      },
      params?: RequestParams,
    ) =>
      this.request<UserDTO, any>(`/api/users${this.addQueryParams(query)}`, "PUT", params, null, BodyType.Json, true),

    /**
     * @tags user-controller
     * @name deleteInstitutionUserUsingDELETE
     * @summary deleteInstitutionUser
     * @request DELETE:/api/users/{id}
     * @secure
     */
    deleteInstitutionUserUsingDelete: (id: number, params?: RequestParams) =>
      this.request<any, any>(`/api/users/${id}`, "DELETE", params, null, BodyType.Json, true),
  };
  error = {
    /**
     * @tags basic-error-controller
     * @name errorUsingGET
     * @summary error
     * @request GET:/error
     * @secure
     */
    errorUsingGet: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "GET", params, null, BodyType.Json, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingHEAD
     * @summary error
     * @request HEAD:/error
     * @secure
     */
    errorUsingHead: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "HEAD", params, null, BodyType.Json, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingPOST
     * @summary error
     * @request POST:/error
     * @secure
     */
    errorUsingPost: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "POST", params, null, BodyType.Json, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingPUT
     * @summary error
     * @request PUT:/error
     * @secure
     */
    errorUsingPut: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "PUT", params, null, BodyType.Json, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingDELETE
     * @summary error
     * @request DELETE:/error
     * @secure
     */
    errorUsingDelete: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "DELETE", params, null, BodyType.Json, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingOPTIONS
     * @summary error
     * @request OPTIONS:/error
     * @secure
     */
    errorUsingOptions: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "OPTIONS", params, null, BodyType.Json, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingPATCH
     * @summary error
     * @request PATCH:/error
     * @secure
     */
    errorUsingPatch: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "PATCH", params, null, BodyType.Json, true),
  };
}
