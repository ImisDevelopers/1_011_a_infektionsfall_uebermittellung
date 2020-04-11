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
  institutionType?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY";
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
  type?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY";
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
  type?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY";
  users?: User[];
  zip?: string;
}

export interface InstitutionDTO {
  city?: string;
  comment?: string;
  email?: string;
  houseNumber?: string;
  id?: string;
  institutionType?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY";
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
  type?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY";
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
  institutionType?: "LABORATORY" | "TEST_SITE" | "CLINIC" | "DOCTORS_OFFICE" | "GOVERNMENT_AGENCY";
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
  public baseUrl: string = "//localhost:8642/";
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
      encodeURIComponent(key) +
      "=" +
      encodeURIComponent(Array.isArray(query[key]) ? (query[key] as any).join(",") : query[key])
    );
  }

  protected addQueryParams(query?: RequestQueryParamsType): string {
    const fixedQuery = query || {};
    const keys = Object.keys(fixedQuery).filter((key) => "undefined" !== typeof fixedQuery[key]);
    return keys.length === 0 ? "" : `?${keys.map((key) => this.addQueryParam(fixedQuery, key)).join("&")}`;
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
}

/**
 * @title Api Documentation
 * @version 1.0
 * @baseUrl //localhost:8642/
 * Api Documentation
 */
export class Api<SecurityDataType = any> extends HttpClient<SecurityDataType> {
  /**
   * @tags redirect-controller
   * @name methodUsingGET
   * @summary method
   * @request GET:/
   * @secure
   */
  methodUsingGet = (params?: RequestParams) => this.request<any, any>(`/`, "GET", params, null, true);

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
  auth = {
    /**
     * @tags auth-controller
     * @name signInUserUsingPOST
     * @summary signInUser
     * @request POST:/auth
     * @secure
     */
    signInUserUsingPost: (authRequestDTO: AuthRequestDTO, params?: RequestParams) =>
      this.request<TokenDTO, any>(`/auth`, "POST", params, authRequestDTO, true),

    /**
     * @tags auth-controller
     * @name getInstitutionUsingGET
     * @summary getInstitution
     * @request GET:/auth/institution
     * @secure
     */
    getInstitutionUsingGet: (params?: RequestParams) =>
      this.request<Institution, any>(`/auth/institution`, "GET", params, null, true),

    /**
     * @tags auth-controller
     * @name registerInstitutionUsingPOST
     * @summary registerInstitution
     * @request POST:/auth/register/institution
     * @secure
     */
    registerInstitutionUsingPost: (institutionDTO: CreateInstitutionDTO, params?: RequestParams) =>
      this.request<Institution, any>(`/auth/register/institution`, "POST", params, institutionDTO, true),

    /**
     * @tags auth-controller
     * @name registerUserUsingPOST
     * @summary registerUser
     * @request POST:/auth/register/user
     * @secure
     */
    registerUserUsingPost: (registerUserRequest: RegisterUserRequest, params?: RequestParams) =>
      this.request<User, any>(`/auth/register/user`, "POST", params, registerUserRequest, true),

    /**
     * @tags auth-controller
     * @name currentUserUsingGET
     * @summary currentUser
     * @request GET:/auth/user
     * @secure
     */
    currentUserUsingGet: (params?: RequestParams) => this.request<User, any>(`/auth/user`, "GET", params, null, true),
  };
  doctor = {
    /**
     * @tags doctor-controller
     * @name addScheduledEventUsingPOST
     * @summary addScheduledEvent
     * @request POST:/doctor/create_appointment
     * @secure
     */
    addScheduledEventUsingPost: (dto: RequestLabTestDTO, params?: RequestParams) =>
      this.request<PatientEvent, any>(`/doctor/create_appointment`, "POST", params, dto, true),
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
      this.request<Record<string, object>, any>(`/error`, "GET", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingHEAD
     * @summary error
     * @request HEAD:/error
     * @secure
     */
    errorUsingHead: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "HEAD", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingPOST
     * @summary error
     * @request POST:/error
     * @secure
     */
    errorUsingPost: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "POST", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingPUT
     * @summary error
     * @request PUT:/error
     * @secure
     */
    errorUsingPut: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "PUT", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingDELETE
     * @summary error
     * @request DELETE:/error
     * @secure
     */
    errorUsingDelete: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "DELETE", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingOPTIONS
     * @summary error
     * @request OPTIONS:/error
     * @secure
     */
    errorUsingOptions: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "OPTIONS", params, null, true),

    /**
     * @tags basic-error-controller
     * @name errorUsingPATCH
     * @summary error
     * @request PATCH:/error
     * @secure
     */
    errorUsingPatch: (params?: RequestParams) =>
      this.request<Record<string, object>, any>(`/error`, "PATCH", params, null, true),
  };
  institutions = {
    /**
     * @tags institution-controller
     * @name createInstitutionUsingPOST
     * @summary createInstitution
     * @request POST:/institutions
     * @secure
     */
    createInstitutionUsingPost: (createInstitutionDTO: CreateInstitutionDTO, params?: RequestParams) =>
      this.request<InstitutionDTO, any>(`/institutions`, "POST", params, createInstitutionDTO, true),

    /**
     * @tags institution-controller
     * @name getAllDoctorsUsingGET
     * @summary getAllDoctors
     * @request GET:/institutions/doctors
     * @secure
     */
    getAllDoctorsUsingGet: (params?: RequestParams) =>
      this.request<Doctor[], any>(`/institutions/doctors`, "GET", params, null, true),

    /**
     * @tags institution-controller
     * @name getAllLaboratoriesUsingGET
     * @summary getAllLaboratories
     * @request GET:/institutions/laboratories
     * @secure
     */
    getAllLaboratoriesUsingGet: (params?: RequestParams) =>
      this.request<Laboratory[], any>(`/institutions/laboratories`, "GET", params, null, true),

    /**
     * @tags institution-controller
     * @name queryAllLaboratoriesUsingGET
     * @summary queryAllLaboratories
     * @request GET:/institutions/laboratories/query
     * @secure
     */
    queryAllLaboratoriesUsingGet: (query: { id: string }, params?: RequestParams) =>
      this.request<Laboratory[], any>(
        `/institutions/laboratories/query${this.addQueryParams(query)}`,
        "GET",
        params,
        null,
        true,
      ),
  };
  labtests = {
    /**
     * @tags lab-test-controller
     * @name createTestForPatientUsingPOST
     * @summary createTestForPatient
     * @request POST:/labtests
     * @secure
     */
    createTestForPatientUsingPost: (createLabTestRequest: CreateLabTestDTO, params?: RequestParams) =>
      this.request<LabTest, any>(`/labtests`, "POST", params, createLabTestRequest, true),

    /**
     * @tags lab-test-controller
     * @name getLabTestForPatientUsingGET
     * @summary getLabTestForPatient
     * @request GET:/labtests/patient/{id}
     * @secure
     */
    getLabTestForPatientUsingGet: (id: string, params?: RequestParams) =>
      this.request<LabTest[], any>(`/labtests/patient/${id}`, "GET", params, null, true),

    /**
     * @tags lab-test-controller
     * @name queryLabTestsByIdUsingGET
     * @summary queryLabTestsById
     * @request GET:/labtests/query
     * @secure
     */
    queryLabTestsByIdUsingGet: (query: { labTestIdQuery: string }, params?: RequestParams) =>
      this.request<LabTest[], any>(`/labtests/query${this.addQueryParams(query)}`, "GET", params, null, true),

    /**
     * @tags lab-test-controller
     * @name updateTestStatusUsingPUT
     * @summary updateTestStatus
     * @request PUT:/labtests/{laboratoryId}
     * @secure
     */
    updateTestStatusUsingPut: (laboratoryId: string, statusDTO: UpdateTestStatusDTO, params?: RequestParams) =>
      this.request<LabTest, any>(`/labtests/${laboratoryId}`, "PUT", params, statusDTO, true),
  };
  patients = {
    /**
     * @tags patient-controller
     * @name getAllPatientsUsingGET
     * @summary getAllPatients
     * @request GET:/patients
     * @secure
     */
    getAllPatientsUsingGet: (params?: RequestParams) =>
      this.request<Patient[], any>(`/patients`, "GET", params, null, true),

    /**
     * @tags patient-controller
     * @name addPatientUsingPOST
     * @summary addPatient
     * @request POST:/patients
     * @secure
     */
    addPatientUsingPost: (dto: CreatePatientDTO, params?: RequestParams) =>
      this.request<Patient, any>(`/patients`, "POST", params, dto, true),

    /**
     * @tags patient-controller
     * @name queryPatientsUsingPOST
     * @summary queryPatients
     * @request POST:/patients/query
     * @secure
     */
    queryPatientsUsingPost: (patientSearchParamsDTO: PatientSearchParamsDTO, params?: RequestParams) =>
      this.request<Patient[], any>(`/patients/query`, "POST", params, patientSearchParamsDTO, true),

    /**
     * @tags patient-controller
     * @name queryPatientsSimpleUsingPOST
     * @summary queryPatientsSimple
     * @request POST:/patients/query-simple
     * @secure
     */
    queryPatientsSimpleUsingPost: (query: PatientSimpleSearchParamsDTO, params?: RequestParams) =>
      this.request<Patient[], any>(`/patients/query-simple`, "POST", params, query, true),

    /**
     * @tags patient-controller
     * @name countQueryPatientsSimpleUsingGET
     * @summary countQueryPatientsSimple
     * @request GET:/patients/query-simple/count
     * @secure
     */
    countQueryPatientsSimpleUsingGet: (query: { query: string }, params?: RequestParams) =>
      this.request<number, any>(`/patients/query-simple/count${this.addQueryParams(query)}`, "GET", params, null, true),

    /**
     * @tags patient-controller
     * @name countQueryPatientsUsingPOST
     * @summary countQueryPatients
     * @request POST:/patients/query/count
     * @secure
     */
    countQueryPatientsUsingPost: (patientSearchParamsDTO: PatientSearchParamsDTO, params?: RequestParams) =>
      this.request<number, any>(`/patients/query/count`, "POST", params, patientSearchParamsDTO, true),

    /**
     * @tags patient-controller
     * @name getPatientForIdUsingGET
     * @summary getPatientForId
     * @request GET:/patients/{id}
     * @secure
     */
    getPatientForIdUsingGet: (id: string, params?: RequestParams) =>
      this.request<Patient, any>(`/patients/${id}`, "GET", params, null, true),
  };
  stats = {
    /**
     * @tags stats-controller
     * @name getForZipUsingGET
     * @summary getForZip
     * @request GET:/stats
     * @secure
     */
    getForZipUsingGet: (query: { lowerBoundsZip: string; upperBoundsZips: string }, params?: RequestParams) =>
      this.request<AggregationResultZip[], any>(`/stats${this.addQueryParams(query)}`, "GET", params, null, true),
  };
}
