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

export type AggregationResultZip = object;

export interface AuthRequestDTO {
  password?: string;
  username?: string;
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

export interface CreateLabTestDTO {
  comment?: string;
  laboratoryId?: string;
  patientId?: string;
  testId?: string;
  testType?: "PCR" | "ANTIBODY";
}

export interface CreatePatientDTO {
  city?: string;
  coronaContacts?: boolean;
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
  occupation?: string;
  onIntensiveCareUnit?: boolean;
  patientStatus?:
    | "REGISTERED"
    | "SUSPECTED"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED_IN_PROGRESS"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "TEST_FINISHED_INVALID"
    | "TEST_FINISHED_RECOVERED"
    | "TEST_FINISHED_NOT_RECOVERED"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT";
  phoneNumber?: string;
  preIllnesses?: string[];
  riskAreas?: string[];
  riskOccupation?: "NO_RISK_OCCUPATION" | "FIRE_FIGHTER" | "DOCTOR" | "CAREGIVER" | "NURSE";
  speedOfSymptomsOutbreak?: string;
  stayCity?: string;
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

export interface GrantedAuthority {
  authority?: string;
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

export interface LabTest {
  comment?: string;
  id?: string;
  report?: string;
  testId: string;
  testStatus?: "TEST_SUBMITTED" | "TEST_IN_PROGRESS" | "TEST_POSITIVE" | "TEST_NEGATIVE" | "TEST_INVALID";
  testType?: "PCR" | "ANTIBODY";
}

export interface Laboratory {
  assignedLabTest?: LabTest[];
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
  creationTimestamp?: string;
  dateOfBirth?: string;
  dateOfDeath?: string;
  dateOfHospitalization?: string;
  dateOfIllness?: string;
  email?: string;
  employer?: string;
  events?: PatientEvent[];
  firstName?: string;
  fluImmunization?: boolean;
  gender?: string;
  houseNumber?: string;
  id?: string;
  insuranceCompany?: string;
  insuranceMembershipNumber?: string;
  lastName?: string;
  occupation?: string;
  onIntensiveCareUnit?: boolean;
  patientStatus?:
    | "REGISTERED"
    | "SUSPECTED"
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED_IN_PROGRESS"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "TEST_FINISHED_INVALID"
    | "TEST_FINISHED_RECOVERED"
    | "TEST_FINISHED_NOT_RECOVERED"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT";
  phoneNumber?: string;
  preIllnesses?: string[];
  riskAreas?: string[];
  riskOccupation?: "NO_RISK_OCCUPATION" | "FIRE_FIGHTER" | "DOCTOR" | "CAREGIVER" | "NURSE";
  speedOfSymptomsOutbreak?: string;
  stayCity?: string;
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
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED_IN_PROGRESS"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "TEST_FINISHED_INVALID"
    | "TEST_FINISHED_RECOVERED"
    | "TEST_FINISHED_NOT_RECOVERED"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT";
  id?: string;
  illness?: "CORONA";
  labTest?: LabTest;
  patient?: Patient;
  responsibleDoctor?: Doctor;
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
    | "SCHEDULED_FOR_TESTING"
    | "TEST_SUBMITTED_IN_PROGRESS"
    | "TEST_FINISHED_POSITIVE"
    | "TEST_FINISHED_NEGATIVE"
    | "TEST_FINISHED_INVALID"
    | "TEST_FINISHED_RECOVERED"
    | "TEST_FINISHED_NOT_RECOVERED"
    | "PATIENT_DEAD"
    | "DOCTORS_VISIT";
  phoneNumber?: string;
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

export interface RegisterUserRequest {
  password?: string;
  userRole?: "USER_ROLE_ADMIN" | "USER_ROLE_REGULAR";
  username?: string;
}

export interface RequestLabTestDTO {
  doctorId?: string;
  laboratoryId?: string;
  patientId?: string;
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

export interface UpdateTestStatusDTO {
  comment?: string;
  file?: string;
  status?: string;
  testId?: string;
}

export interface User {
  accountNonExpired?: boolean;
  accountNonLocked?: boolean;
  authorities?: GrantedAuthority[];
  credentialsNonExpired?: boolean;
  enabled?: boolean;
  id?: number;
  institutionId?: string;
  institutionType?:
    | "LABORATORY"
    | "TEST_SITE"
    | "CLINIC"
    | "DOCTORS_OFFICE"
    | "GOVERNMENT_AGENCY"
    | "DEPARTMENT_OF_HEALTH";
  username?: string;
}

export interface View {
  contentType?: string;
}

export type Map_string_Link_ = Record<string, Link>;

export type RequestParams = Omit<RequestInit, "body" | "method"> & {
  secure?: boolean;
};

export type RequestQueryParamsType = Record<string, string | string[] | number | number[] | boolean | undefined>;

type ApiConfig<SecurityDataType> = {
  baseUrl?: string;
  baseApiParams?: RequestParams;
  securityWorker?: (securityData: SecurityDataType) => RequestParams;
};

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

  public request = <T = any, E = any>(
    path: string,
    method: string,
    { secure, ...params }: RequestParams = {},
    body?: any,
    secureByDefault?: boolean,
  ): Promise<T> =>
    fetch(`${this.baseUrl}${path}`, {
      // @ts-ignore
      ...this.mergeRequestOptions(params, (secureByDefault || secure) && this.securityWorker(this.securityData)),
      method,
      body: body ? JSON.stringify(body) : null,
    }).then(async (response) => {
      const data = await this.safeParseResponse<T, E>(response);
      if (!response.ok) throw data;
      return data;
    });

  protected addQueryParams(query?: RequestQueryParamsType): string {
    const fixedQuery = query || {};
    const keys = Object.keys(fixedQuery).filter((key) => "undefined" !== typeof fixedQuery[key]);
    return keys.length === 0 ? "" : `?${keys.map((key) => this.addQueryParam(fixedQuery, key)).join("&")}`;
  }

  private addQueryParam(query: RequestQueryParamsType, key: string) {
    return (
      encodeURIComponent(key) +
      "=" +
      encodeURIComponent(Array.isArray(query[key]) ? (query[key] as any).join(",") : query[key])
    );
  }

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
      this.request<Record<string, Record<string, Link>>, any>(`/actuator`, "GET", params, null, true),

    /**
     * @tags operation-handler
     * @name handleUsingGET_1
     * @summary handle
     * @request GET:/actuator/health
     * @secure
     */
    handleUsingGet1: (body: Record<string, string>, params?: RequestParams) =>
      this.request<AggregationResultZip, any>(`/actuator/health`, "GET", params, body, true),

    /**
     * @tags operation-handler
     * @name handleUsingGET
     * @summary handle
     * @request GET:/actuator/health/**
     * @secure
     */
    handleUsingGet: (body: Record<string, string>, params?: RequestParams) =>
      this.request<AggregationResultZip, any>(`/actuator/health/**`, "GET", params, body, true),

    /**
     * @tags operation-handler
     * @name handleUsingGET_2
     * @summary handle
     * @request GET:/actuator/info
     * @secure
     */
    handleUsingGet2: (body: Record<string, string>, params?: RequestParams) =>
      this.request<AggregationResultZip, any>(`/actuator/info`, "GET", params, body, true),
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
      this.request<TokenDTO, any>(`/api/auth`, "POST", params, authRequestDTO, true),

    /**
     * @tags auth-controller
     * @name getInstitutionUsingGET
     * @summary getInstitution
     * @request GET:/api/auth/institution
     * @secure
     */
    getInstitutionUsingGet: (params?: RequestParams) =>
      this.request<Institution, any>(`/api/auth/institution`, "GET", params, null, true),

    /**
     * @tags auth-controller
     * @name registerInstitutionUsingPOST
     * @summary registerInstitution
     * @request POST:/api/auth/register/institution
     * @secure
     */
    registerInstitutionUsingPost: (institutionDTO: CreateInstitutionDTO, params?: RequestParams) =>
      this.request<Institution, any>(`/api/auth/register/institution`, "POST", params, institutionDTO, true),

    /**
     * @tags auth-controller
     * @name registerUserUsingPOST
     * @summary registerUser
     * @request POST:/api/auth/register/user
     * @secure
     */
    registerUserUsingPost: (registerUserRequest: RegisterUserRequest, params?: RequestParams) =>
      this.request<User, any>(`/api/auth/register/user`, "POST", params, registerUserRequest, true),

    /**
     * @tags auth-controller
     * @name currentUserUsingGET
     * @summary currentUser
     * @request GET:/api/auth/user
     * @secure
     */
    currentUserUsingGet: (params?: RequestParams) =>
      this.request<User, any>(`/api/auth/user`, "GET", params, null, true),

    /**
     * @tags doctor-controller
     * @name addScheduledEventUsingPOST
     * @summary addScheduledEvent
     * @request POST:/api/doctor/create_appointment
     * @secure
     */
    addScheduledEventUsingPost: (dto: RequestLabTestDTO, params?: RequestParams) =>
      this.request<PatientEvent, any>(`/api/doctor/create_appointment`, "POST", params, dto, true),

    /**
     * @tags institution-controller
     * @name createInstitutionUsingPOST
     * @summary createInstitution
     * @request POST:/api/institutions
     * @secure
     */
    createInstitutionUsingPost: (createInstitutionDTO: CreateInstitutionDTO, params?: RequestParams) =>
      this.request<InstitutionDTO, any>(`/api/institutions`, "POST", params, createInstitutionDTO, true),

    /**
     * @tags institution-controller
     * @name getAllDoctorsUsingGET
     * @summary getAllDoctors
     * @request GET:/api/institutions/doctors
     * @secure
     */
    getAllDoctorsUsingGet: (params?: RequestParams) =>
      this.request<Doctor[], any>(`/api/institutions/doctors`, "GET", params, null, true),

    /**
     * @tags institution-controller
     * @name getAllLaboratoriesUsingGET
     * @summary getAllLaboratories
     * @request GET:/api/institutions/laboratories
     * @secure
     */
    getAllLaboratoriesUsingGet: (params?: RequestParams) =>
      this.request<Laboratory[], any>(`/api/institutions/laboratories`, "GET", params, null, true),

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
        true,
      ),

    /**
     * @tags lab-test-controller
     * @name createTestForPatientUsingPOST
     * @summary createTestForPatient
     * @request POST:/api/labtests
     * @secure
     */
    createTestForPatientUsingPost: (createLabTestRequest: CreateLabTestDTO, params?: RequestParams) =>
      this.request<LabTest, any>(`/api/labtests`, "POST", params, createLabTestRequest, true),

    /**
     * @tags lab-test-controller
     * @name getLabTestForPatientUsingGET
     * @summary getLabTestForPatient
     * @request GET:/api/labtests/patient/{id}
     * @secure
     */
    getLabTestForPatientUsingGet: (id: string, params?: RequestParams) =>
      this.request<LabTest[], any>(`/api/labtests/patient/${id}`, "GET", params, null, true),

    /**
     * @tags lab-test-controller
     * @name queryLabTestsByIdUsingGET
     * @summary queryLabTestsById
     * @request GET:/api/labtests/query
     * @secure
     */
    queryLabTestsByIdUsingGet: (query: { labTestIdQuery: string }, params?: RequestParams) =>
      this.request<LabTest[], any>(`/api/labtests/query${this.addQueryParams(query)}`, "GET", params, null, true),

    /**
     * @tags lab-test-controller
     * @name updateTestStatusUsingPUT
     * @summary updateTestStatus
     * @request PUT:/api/labtests/{laboratoryId}
     * @secure
     */
    updateTestStatusUsingPut: (laboratoryId: string, statusDTO: UpdateTestStatusDTO, params?: RequestParams) =>
      this.request<LabTest, any>(`/api/labtests/${laboratoryId}`, "PUT", params, statusDTO, true),

    /**
     * @tags patient-controller
     * @name getAllPatientsUsingGET
     * @summary getAllPatients
     * @request GET:/api/patients
     * @secure
     */
    getAllPatientsUsingGet: (params?: RequestParams) =>
      this.request<Patient[], any>(`/api/patients`, "GET", params, null, true),

    /**
     * @tags patient-controller
     * @name addPatientUsingPOST
     * @summary addPatient
     * @request POST:/api/patients
     * @secure
     */
    addPatientUsingPost: (dto: CreatePatientDTO, params?: RequestParams) =>
      this.request<Patient, any>(`/api/patients`, "POST", params, dto, true),

    /**
     * @tags patient-controller
     * @name queryPatientsUsingPOST
     * @summary queryPatients
     * @request POST:/api/patients/query
     * @secure
     */
    queryPatientsUsingPost: (patientSearchParamsDTO: PatientSearchParamsDTO, params?: RequestParams) =>
      this.request<Patient[], any>(`/api/patients/query`, "POST", params, patientSearchParamsDTO, true),

    /**
     * @tags patient-controller
     * @name queryPatientsSimpleUsingPOST
     * @summary queryPatientsSimple
     * @request POST:/api/patients/query-simple
     * @secure
     */
    queryPatientsSimpleUsingPost: (query: PatientSimpleSearchParamsDTO, params?: RequestParams) =>
      this.request<Patient[], any>(`/api/patients/query-simple`, "POST", params, query, true),

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
      this.request<number, any>(`/api/patients/query/count`, "POST", params, patientSearchParamsDTO, true),

    /**
     * @tags patient-controller
     * @name getPatientForIdUsingGET
     * @summary getPatientForId
     * @request GET:/api/patients/{id}
     * @secure
     */
    getPatientForIdUsingGet: (id: string, params?: RequestParams) =>
      this.request<Patient, any>(`/api/patients/${id}`, "GET", params, null, true),

    /**
     * @tags stats-controller
     * @name getForZipUsingGET
     * @summary getForZip
     * @request GET:/api/stats
     * @secure
     */
    getForZipUsingGet: (query: { lowerBoundsZip: string; upperBoundsZips: string }, params?: RequestParams) =>
      this.request<AggregationResultZip[], any>(`/api/stats${this.addQueryParams(query)}`, "GET", params, null, true),
  };
  error = {
    /**
     * @tags basic-error-controller
     * @name errorHtmlUsingGET
     * @summary errorHtml
     * @request GET:/error
     * @secure
     */
    errorHtmlUsingGet: (params?: RequestParams) => this.request<ModelAndView, any>(`/error`, "GET", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorHtmlUsingHEAD
     * @summary errorHtml
     * @request HEAD:/error
     * @secure
     */
    errorHtmlUsingHead: (params?: RequestParams) =>
      this.request<ModelAndView, any>(`/error`, "HEAD", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorHtmlUsingPOST
     * @summary errorHtml
     * @request POST:/error
     * @secure
     */
    errorHtmlUsingPost: (params?: RequestParams) =>
      this.request<ModelAndView, any>(`/error`, "POST", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorHtmlUsingPUT
     * @summary errorHtml
     * @request PUT:/error
     * @secure
     */
    errorHtmlUsingPut: (params?: RequestParams) => this.request<ModelAndView, any>(`/error`, "PUT", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorHtmlUsingDELETE
     * @summary errorHtml
     * @request DELETE:/error
     * @secure
     */
    errorHtmlUsingDelete: (params?: RequestParams) =>
      this.request<ModelAndView, any>(`/error`, "DELETE", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorHtmlUsingOPTIONS
     * @summary errorHtml
     * @request OPTIONS:/error
     * @secure
     */
    errorHtmlUsingOptions: (params?: RequestParams) =>
      this.request<ModelAndView, any>(`/error`, "OPTIONS", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorHtmlUsingPATCH
     * @summary errorHtml
     * @request PATCH:/error
     * @secure
     */
    errorHtmlUsingPatch: (params?: RequestParams) =>
      this.request<ModelAndView, any>(`/error`, "PATCH", params, null, true),
  };
}
