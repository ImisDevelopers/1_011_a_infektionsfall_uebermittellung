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

export interface AdditionalTestDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    sample?: SampleReferenceDto;
    testDateTime?: string;
    haemoglobinuria?: "POSITIVE" | "NEGATIVE" | "INDETERMINATE";
    proteinuria?: "POSITIVE" | "NEGATIVE" | "INDETERMINATE";
    hematuria?: "POSITIVE" | "NEGATIVE" | "INDETERMINATE";
    arterialVenousGasPH?: number;
    arterialVenousGasPco2?: number;
    arterialVenousGasPao2?: number;
    arterialVenousGasHco3?: number;
    gasOxygenTherapy?: number;
    altSgpt?: number;
    astSgot?: number;
    creatinine?: number;
    potassium?: number;
    urea?: number;
    haemoglobin?: number;
    totalBilirubin?: number;
    conjBilirubin?: number;
    wbcCount?: number;
    platelets?: number;
    prothrombinTime?: number;
    otherTestResults?: string;
}

export interface SampleReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface AggregateReportDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    reportingUser?: UserReferenceDto;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    year?: number;
    epiWeek?: number;
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    healthFacility?: FacilityReferenceDto;
    pointOfEntry?: PointOfEntryReferenceDto;
    newCases?: number;
    labConfirmations?: number;
    deaths?: number;
}

export interface DistrictReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface FacilityReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface PointOfEntryReferenceDto {
    uuid?: string;
    caption?: string;
    otherPointOfEntry?: boolean;
}

export interface RegionReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface UserReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface CaseDataDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    pseudonymized?: boolean;
    disease:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    diseaseDetails?: string;
    plagueType?: "BUBONIC" | "PNEUMONIC" | "SEPTICAEMIC";
    dengueFeverType?: "DENGUE_FEVER" | "DENGUE_HEMORRHAGIC_FEVER" | "DENUGE_SHOCK_SYNDROME";
    rabiesType?: "FURIOUS_RABIES" | "PARALYTIC_RABIES";
    person: PersonReferenceDto;
    epidNumber?: string;
    reportDate: string;
    reportingUser: UserReferenceDto;
    regionLevelDate?: string;
    nationalLevelDate?: string;
    districtLevelDate?: string;
    caseClassification: "NOT_CLASSIFIED" | "SUSPECT" | "PROBABLE" | "CONFIRMED" | "NO_CASE";
    classificationUser?: UserReferenceDto;
    classificationDate?: string;
    classificationComment?: string;
    investigationStatus: "PENDING" | "DONE" | "DISCARDED";
    investigatedDate?: string;
    outcome?: "NO_OUTCOME" | "DECEASED" | "RECOVERED" | "UNKNOWN";
    outcomeDate?: string;
    sequelae?: "YES" | "NO" | "UNKNOWN";
    sequelaeDetails?: string;
    region: RegionReferenceDto;
    district: DistrictReferenceDto;
    community?: CommunityReferenceDto;
    healthFacility: FacilityReferenceDto;
    healthFacilityDetails?: string;
    pregnant?: "YES" | "NO" | "UNKNOWN";
    vaccination?: "VACCINATED" | "UNVACCINATED" | "UNKNOWN";
    vaccinationDoses?: string;
    vaccinationDate?: string;
    vaccinationInfoSource?: "VACCINATION_CARD" | "ORAL_COMMUNICATION";
    vaccine?: string;
    smallpoxVaccinationScar?: "YES" | "NO" | "UNKNOWN";
    smallpoxVaccinationReceived?: "YES" | "NO" | "UNKNOWN";
    surveillanceOfficer?: UserReferenceDto;
    clinicianName?: string;
    clinicianPhone?: string;
    clinicianEmail?: string;
    notifyingClinic?: "PEDIATRIC_INPATIENT" | "NURSERY" | "EPU" | "CHER" | "OPD" | "EYE" | "ENT" | "CARDIOLOGY" | "OTHER";
    notifyingClinicDetails?: string;
    caseOfficer?: UserReferenceDto;
    reportLat?: number;
    reportLon?: number;
    reportLatLonAccuracy?: number;
    hospitalization?: HospitalizationDto;
    symptoms?: SymptomsDto;
    epiData?: EpiDataDto;
    therapy?: TherapyDto;
    clinicalCourse?: ClinicalCourseDto;
    maternalHistory?: MaternalHistoryDto;
    creationVersion?: string;
    portHealthInfo?: PortHealthInfoDto;
    caseOrigin?: "IN_COUNTRY" | "POINT_OF_ENTRY";
    pointOfEntry?: PointOfEntryReferenceDto;
    pointOfEntryDetails?: string;
    additionalDetails?: string;
    externalID?: string;
    sharedToCountry?: boolean;
    quarantine?: "INSTITUTIONELL" | "HOME" | "NONE" | "UNKNOWN";
    quarantineFrom?: string;
    quarantineTo?: string;
    quarantineHelpNeeded?: string;
    quarantineOrderedVerbally?: boolean;
    quarantineOrderedOfficialDocument?: boolean;
    quarantineOrderedVerballyDate?: string;
    quarantineOrderedOfficialDocumentDate?: string;
    quarantineHomePossible?: "YES" | "NO" | "UNKNOWN";
    quarantineHomePossibleComment?: string;
    quarantineHomeSupplyEnsured?: "YES" | "NO" | "UNKNOWN";
    quarantineHomeSupplyEnsuredComment?: string;
    reportingType?:
        | "DOCTOR"
        | "FORWARDING"
        | "HOSPITAL_OR_STATIONARY_CARE"
        | "COMMUNITY_FACILITY"
        | "LABORATORY"
        | "OWN_DETERMINATION"
        | "NOT_DETERMINABLE"
        | "NOT_RAISED"
        | "OTHER";
    postpartum?: "YES" | "NO" | "UNKNOWN";
    trimester?: "FIRST" | "SECOND" | "THIRD" | "UNKNOWN";
    unreferredPortHealthCase?: boolean;
}

export interface ClinicalCourseDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    healthConditions?: HealthConditionsDto;
}

export interface CommunityReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface EpiDataBurialDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    burialPersonName?: string;
    burialRelation?: string;
    burialDateFrom?: string;
    burialDateTo?: string;
    burialAddress?: LocationDto;
    burialIll?: "YES" | "NO" | "UNKNOWN";
    burialTouching?: "YES" | "NO" | "UNKNOWN";
}

export interface EpiDataDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    burialAttended?: "YES" | "NO" | "UNKNOWN";
    gatheringAttended?: "YES" | "NO" | "UNKNOWN";
    traveled?: "YES" | "NO" | "UNKNOWN";
    burials?: EpiDataBurialDto[];
    gatherings?: EpiDataGatheringDto[];
    travels?: EpiDataTravelDto[];
    directContactConfirmedCase?: "YES" | "NO" | "UNKNOWN";
    directContactProbableCase?: "YES" | "NO" | "UNKNOWN";
    closeContactProbableCase?: "YES" | "NO" | "UNKNOWN";
    areaConfirmedCases?: "YES" | "NO" | "UNKNOWN";
    processingConfirmedCaseFluidUnsafe?: "YES" | "NO" | "UNKNOWN";
    percutaneousCaseBlood?: "YES" | "NO" | "UNKNOWN";
    directContactDeadUnsafe?: "YES" | "NO" | "UNKNOWN";
    processingSuspectedCaseSampleUnsafe?: "YES" | "NO" | "UNKNOWN";
    areaInfectedAnimals?: "YES" | "NO" | "UNKNOWN";
    sickDeadAnimals?: "YES" | "NO" | "UNKNOWN";
    sickDeadAnimalsDetails?: string;
    sickDeadAnimalsDate?: string;
    sickDeadAnimalsLocation?: string;
    eatingRawAnimalsInInfectedArea?: "YES" | "NO" | "UNKNOWN";
    eatingRawAnimals?: "YES" | "NO" | "UNKNOWN";
    eatingRawAnimalsDetails?: string;
    rodents?: "YES" | "NO" | "UNKNOWN";
    bats?: "YES" | "NO" | "UNKNOWN";
    primates?: "YES" | "NO" | "UNKNOWN";
    swine?: "YES" | "NO" | "UNKNOWN";
    birds?: "YES" | "NO" | "UNKNOWN";
    rabbits?: "YES" | "NO" | "UNKNOWN";
    cattle?: "YES" | "NO" | "UNKNOWN";
    dogs?: "YES" | "NO" | "UNKNOWN";
    cats?: "YES" | "NO" | "UNKNOWN";
    canidae?: "YES" | "NO" | "UNKNOWN";
    otherAnimals?: "YES" | "NO" | "UNKNOWN";
    otherAnimalsDetails?: string;
    waterSource?: "PIPE_NETWORK" | "COMMUNITY_BOREHOLE_WELL" | "PRIVATE_BOREHOLE_WELL" | "STREAM" | "OTHER";
    waterSourceOther?: string;
    waterBody?: "YES" | "NO" | "UNKNOWN";
    waterBodyDetails?: string;
    tickBite?: "YES" | "NO" | "UNKNOWN";
    fleaBite?: "YES" | "NO" | "UNKNOWN";
    kindOfExposureBite?: "YES" | "NO" | "UNKNOWN";
    kindOfExposureTouch?: "YES" | "NO" | "UNKNOWN";
    kindOfExposureScratch?: "YES" | "NO" | "UNKNOWN";
    kindOfExposureLick?: "YES" | "NO" | "UNKNOWN";
    kindOfExposureOther?: "YES" | "NO" | "UNKNOWN";
    kindOfExposureDetails?: string;
    dateOfLastExposure?: string;
    placeOfLastExposure?: string;
    animalCondition?: "ALIVE" | "DEAD" | "PROCESSED" | "UNKNOWN";
    animalVaccinationStatus?: "VACCINATED" | "UNVACCINATED" | "UNKNOWN";
    prophylaxisStatus?: "YES" | "NO" | "UNKNOWN";
    dateOfProphylaxis?: string;
    visitedHealthFacility?: "YES" | "NO" | "UNKNOWN";
    contactWithSourceRespiratoryCase?: "YES" | "NO" | "UNKNOWN";
    visitedAnimalMarket?: "YES" | "NO" | "UNKNOWN";
    camels?: "YES" | "NO" | "UNKNOWN";
    snakes?: "YES" | "NO" | "UNKNOWN";
}

export interface EpiDataGatheringDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    description?: string;
    gatheringDate?: string;
    gatheringAddress?: LocationDto;
}

export interface EpiDataTravelDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    travelType?: "ABROAD" | "WITHIN_COUNTRY";
    travelDestination?: string;
    travelDateFrom?: string;
    travelDateTo?: string;
}

export interface HealthConditionsDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    tuberculosis?: "YES" | "NO" | "UNKNOWN";
    asplenia?: "YES" | "NO" | "UNKNOWN";
    hepatitis?: "YES" | "NO" | "UNKNOWN";
    diabetes?: "YES" | "NO" | "UNKNOWN";
    hiv?: "YES" | "NO" | "UNKNOWN";
    hivArt?: "YES" | "NO" | "UNKNOWN";
    chronicLiverDisease?: "YES" | "NO" | "UNKNOWN";
    malignancyChemotherapy?: "YES" | "NO" | "UNKNOWN";
    chronicHeartFailure?: "YES" | "NO" | "UNKNOWN";
    chronicPulmonaryDisease?: "YES" | "NO" | "UNKNOWN";
    chronicKidneyDisease?: "YES" | "NO" | "UNKNOWN";
    chronicNeurologicCondition?: "YES" | "NO" | "UNKNOWN";
    downSyndrome?: "YES" | "NO" | "UNKNOWN";
    congenitalSyphilis?: "YES" | "NO" | "UNKNOWN";
    immunodeficiencyOtherThanHiv?: "YES" | "NO" | "UNKNOWN";
    cardiovascularDiseaseIncludingHypertension?: "YES" | "NO" | "UNKNOWN";
    obesity?: "YES" | "NO" | "UNKNOWN";
    currentSmoker?: "YES" | "NO" | "UNKNOWN";
    formerSmoker?: "YES" | "NO" | "UNKNOWN";
    asthma?: "YES" | "NO" | "UNKNOWN";
    sickleCellDisease?: "YES" | "NO" | "UNKNOWN";
    immunodeficiencyIncludingHiv?: "YES" | "NO" | "UNKNOWN";
    otherConditions?: string;
}

export interface HospitalizationDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    admittedToHealthFacility?: "YES" | "NO" | "UNKNOWN";
    admissionDate?: string;
    dischargeDate?: string;
    isolated?: "YES" | "NO" | "UNKNOWN";
    isolationDate?: string;
    leftAgainstAdvice?: "YES" | "NO" | "UNKNOWN";
    hospitalizedPreviously?: "YES" | "NO" | "UNKNOWN";
    previousHospitalizations?: PreviousHospitalizationDto[];
    intensiveCareUnit?: "YES" | "NO" | "UNKNOWN";
    intensiveCareUnitStart?: string;
    intensiveCareUnitEnd?: string;
}

export interface LocationDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    pseudonymized?: boolean;
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    community?: CommunityReferenceDto;
    address?: string;
    details?: string;
    city?: string;
    areaType?: "URBAN" | "RURAL" | "UNKNOWN";
    latitude?: number;
    longitude?: number;
    latLonAccuracy?: number;
    postalCode?: string;
    emptyLocation?: boolean;
}

export interface MaternalHistoryDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    childrenNumber?: number;
    ageAtBirth?: number;
    conjunctivitis?: "YES" | "NO" | "UNKNOWN";
    conjunctivitisOnset?: string;
    conjunctivitisMonth?: number;
    maculopapularRash?: "YES" | "NO" | "UNKNOWN";
    maculopapularRashOnset?: string;
    maculopapularRashMonth?: number;
    swollenLymphs?: "YES" | "NO" | "UNKNOWN";
    swollenLymphsOnset?: string;
    swollenLymphsMonth?: number;
    arthralgiaArthritis?: "YES" | "NO" | "UNKNOWN";
    arthralgiaArthritisOnset?: string;
    arthralgiaArthritisMonth?: number;
    otherComplications?: "YES" | "NO" | "UNKNOWN";
    otherComplicationsOnset?: string;
    otherComplicationsMonth?: number;
    otherComplicationsDetails?: string;
    rubella?: "YES" | "NO" | "UNKNOWN";
    rubellaOnset?: string;
    rashExposure?: "YES" | "NO" | "UNKNOWN";
    rashExposureDate?: string;
    rashExposureMonth?: number;
    rashExposureRegion?: RegionReferenceDto;
    rashExposureDistrict?: DistrictReferenceDto;
    rashExposureCommunity?: CommunityReferenceDto;
}

export  interface PersonReferenceDto {
    uuid?: string;
    caption?: string;
    firstName?: string;
    lastName?: string;
}

export interface PortHealthInfoDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    airlineName?: string;
    flightNumber?: string;
    departureDateTime?: string;
    arrivalDateTime?: string;
    freeSeating?: "YES" | "NO" | "UNKNOWN";
    seatNumber?: string;
    departureAirport?: string;
    numberOfTransitStops?: number;
    transitStopDetails1?: string;
    transitStopDetails2?: string;
    transitStopDetails3?: string;
    transitStopDetails4?: string;
    transitStopDetails5?: string;
    vesselName?: string;
    vesselDetails?: string;
    portOfDeparture?: string;
    lastPortOfCall?: string;
    conveyanceType?: "CAR" | "BUS" | "MOTORBIKE" | "OTHER";
    conveyanceTypeDetails?: string;
    departureLocation?: string;
    finalDestination?: string;
    details?: string;
}

export interface PreviousHospitalizationDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    admissionDate?: string;
    dischargeDate?: string;
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    community?: CommunityReferenceDto;
    healthFacility?: FacilityReferenceDto;
    healthFacilityDetails?: string;
    isolated?: "YES" | "NO" | "UNKNOWN";
    description?: string;
}

export interface SymptomsDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    abdominalPain?: "YES" | "NO" | "UNKNOWN";
    anorexiaAppetiteLoss?: "YES" | "NO" | "UNKNOWN";
    backache?: "YES" | "NO" | "UNKNOWN";
    bedridden?: "YES" | "NO" | "UNKNOWN";
    blackeningDeathOfTissue?: "YES" | "NO" | "UNKNOWN";
    bleedingVagina?: "YES" | "NO" | "UNKNOWN";
    bloodInStool?: "YES" | "NO" | "UNKNOWN";
    bloodPressureDiastolic?: number;
    bloodPressureSystolic?: number;
    bloodUrine?: "YES" | "NO" | "UNKNOWN";
    bloodyBlackStool?: "YES" | "NO" | "UNKNOWN";
    buboesGroinArmpitNeck?: "YES" | "NO" | "UNKNOWN";
    bulgingFontanelle?: "YES" | "NO" | "UNKNOWN";
    chestPain?: "YES" | "NO" | "UNKNOWN";
    chillsSweats?: "YES" | "NO" | "UNKNOWN";
    conjunctivitis?: "YES" | "NO" | "UNKNOWN";
    cough?: "YES" | "NO" | "UNKNOWN";
    coughWithSputum?: "YES" | "NO" | "UNKNOWN";
    coughWithHeamoptysis?: "YES" | "NO" | "UNKNOWN";
    coughingBlood?: "YES" | "NO" | "UNKNOWN";
    darkUrine?: "YES" | "NO" | "UNKNOWN";
    dehydration?: "YES" | "NO" | "UNKNOWN";
    diarrhea?: "YES" | "NO" | "UNKNOWN";
    difficultyBreathing?: "YES" | "NO" | "UNKNOWN";
    digestedBloodVomit?: "YES" | "NO" | "UNKNOWN";
    eyePainLightSensitive?: "YES" | "NO" | "UNKNOWN";
    eyesBleeding?: "YES" | "NO" | "UNKNOWN";
    fatigueWeakness?: "YES" | "NO" | "UNKNOWN";
    fever?: "YES" | "NO" | "UNKNOWN";
    fluidInLungCavity?: "YES" | "NO" | "UNKNOWN";
    glasgowComaScale?: number;
    gumsBleeding?: "YES" | "NO" | "UNKNOWN";
    headache?: "YES" | "NO" | "UNKNOWN";
    hearingloss?: "YES" | "NO" | "UNKNOWN";
    heartRate?: number;
    height?: number;
    hiccups?: "YES" | "NO" | "UNKNOWN";
    injectionSiteBleeding?: "YES" | "NO" | "UNKNOWN";
    jaundice?: "YES" | "NO" | "UNKNOWN";
    jaundiceWithin24HoursOfBirth?: "YES" | "NO" | "UNKNOWN";
    jointPain?: "YES" | "NO" | "UNKNOWN";
    kopliksSpots?: "YES" | "NO" | "UNKNOWN";
    lesions?: "YES" | "NO" | "UNKNOWN";
    lesionsAllOverBody?: boolean;
    lesionsArms?: boolean;
    lesionsDeepProfound?: "YES" | "NO" | "UNKNOWN";
    lesionsFace?: boolean;
    lesionsGenitals?: boolean;
    lesionsLegs?: boolean;
    lesionsOnsetDate?: string;
    lesionsPalmsHands?: boolean;
    lesionsResembleImg1?: "YES" | "NO" | "UNKNOWN";
    lesionsResembleImg2?: "YES" | "NO" | "UNKNOWN";
    lesionsResembleImg3?: "YES" | "NO" | "UNKNOWN";
    lesionsResembleImg4?: "YES" | "NO" | "UNKNOWN";
    lesionsSameSize?: "YES" | "NO" | "UNKNOWN";
    lesionsSameState?: "YES" | "NO" | "UNKNOWN";
    lesionsSolesFeet?: boolean;
    lesionsThatItch?: "YES" | "NO" | "UNKNOWN";
    lesionsThorax?: boolean;
    lossSkinTurgor?: "YES" | "NO" | "UNKNOWN";
    lymphadenopathy?: "YES" | "NO" | "UNKNOWN";
    lymphadenopathyAxillary?: "YES" | "NO" | "UNKNOWN";
    lymphadenopathyCervical?: "YES" | "NO" | "UNKNOWN";
    lymphadenopathyInguinal?: "YES" | "NO" | "UNKNOWN";
    malaise?: "YES" | "NO" | "UNKNOWN";
    midUpperArmCircumference?: number;
    musclePain?: "YES" | "NO" | "UNKNOWN";
    nausea?: "YES" | "NO" | "UNKNOWN";
    neckStiffness?: "YES" | "NO" | "UNKNOWN";
    noseBleeding?: "YES" | "NO" | "UNKNOWN";
    oedemaFaceNeck?: "YES" | "NO" | "UNKNOWN";
    oedemaLowerExtremity?: "YES" | "NO" | "UNKNOWN";
    onsetDate?: string;
    onsetSymptom?: string;
    oralUlcers?: "YES" | "NO" | "UNKNOWN";
    otherHemorrhagicSymptoms?: "YES" | "NO" | "UNKNOWN";
    otherHemorrhagicSymptomsText?: string;
    otherNonHemorrhagicSymptoms?: "YES" | "NO" | "UNKNOWN";
    otherNonHemorrhagicSymptomsText?: string;
    otitisMedia?: "YES" | "NO" | "UNKNOWN";
    painfulLymphadenitis?: "YES" | "NO" | "UNKNOWN";
    palpableLiver?: "YES" | "NO" | "UNKNOWN";
    palpableSpleen?: "YES" | "NO" | "UNKNOWN";
    patientIllLocation?: string;
    pharyngealErythema?: "YES" | "NO" | "UNKNOWN";
    pharyngealExudate?: "YES" | "NO" | "UNKNOWN";
    rapidBreathing?: "YES" | "NO" | "UNKNOWN";
    redBloodVomit?: "YES" | "NO" | "UNKNOWN";
    refusalFeedorDrink?: "YES" | "NO" | "UNKNOWN";
    respiratoryRate?: number;
    runnyNose?: "YES" | "NO" | "UNKNOWN";
    sidePain?: "YES" | "NO" | "UNKNOWN";
    skinBruising?: "YES" | "NO" | "UNKNOWN";
    skinRash?: "YES" | "NO" | "UNKNOWN";
    soreThroat?: "YES" | "NO" | "UNKNOWN";
    stomachBleeding?: "YES" | "NO" | "UNKNOWN";
    sunkenEyesFontanelle?: "YES" | "NO" | "UNKNOWN";
    swollenGlands?: "YES" | "NO" | "UNKNOWN";
    symptomatic?: boolean;
    symptomsComments?: string;
    temperature?: number;
    temperatureSource?: "AXILLARY" | "ORAL" | "RECTAL" | "NON_CONTACT";
    throbocytopenia?: "YES" | "NO" | "UNKNOWN";
    tremor?: "YES" | "NO" | "UNKNOWN";
    bilateralCataracts?: "YES" | "NO" | "UNKNOWN";
    unilateralCataracts?: "YES" | "NO" | "UNKNOWN";
    congenitalGlaucoma?: "YES" | "NO" | "UNKNOWN";
    pigmentaryRetinopathy?: "YES" | "NO" | "UNKNOWN";
    purpuricRash?: "YES" | "NO" | "UNKNOWN";
    microcephaly?: "YES" | "NO" | "UNKNOWN";
    developmentalDelay?: "YES" | "NO" | "UNKNOWN";
    splenomegaly?: "YES" | "NO" | "UNKNOWN";
    meningoencephalitis?: "YES" | "NO" | "UNKNOWN";
    radiolucentBoneDisease?: "YES" | "NO" | "UNKNOWN";
    congenitalHeartDisease?: "YES" | "NO" | "UNKNOWN";
    congenitalHeartDiseaseType?: "PDA" | "PPS" | "VSD" | "OTHER";
    congenitalHeartDiseaseDetails?: string;
    unexplainedBleeding?: "YES" | "NO" | "UNKNOWN";
    vomiting?: "YES" | "NO" | "UNKNOWN";
    hydrophobia?: "YES" | "NO" | "UNKNOWN";
    opisthotonus?: "YES" | "NO" | "UNKNOWN";
    anxietyStates?: "YES" | "NO" | "UNKNOWN";
    delirium?: "YES" | "NO" | "UNKNOWN";
    uproariousness?: "YES" | "NO" | "UNKNOWN";
    paresthesiaAroundWound?: "YES" | "NO" | "UNKNOWN";
    excessSalivation?: "YES" | "NO" | "UNKNOWN";
    insomnia?: "YES" | "NO" | "UNKNOWN";
    paralysis?: "YES" | "NO" | "UNKNOWN";
    excitation?: "YES" | "NO" | "UNKNOWN";
    dysphagia?: "YES" | "NO" | "UNKNOWN";
    aerophobia?: "YES" | "NO" | "UNKNOWN";
    hyperactivity?: "YES" | "NO" | "UNKNOWN";
    paresis?: "YES" | "NO" | "UNKNOWN";
    agitation?: "YES" | "NO" | "UNKNOWN";
    ascendingFlaccidParalysis?: "YES" | "NO" | "UNKNOWN";
    erraticBehaviour?: "YES" | "NO" | "UNKNOWN";
    coma?: "YES" | "NO" | "UNKNOWN";
    convulsion?: "YES" | "NO" | "UNKNOWN";
    fluidInLungCavityAuscultation?: "YES" | "NO" | "UNKNOWN";
    fluidInLungCavityXray?: "YES" | "NO" | "UNKNOWN";
    abnormalLungXrayFindings?: "YES" | "NO" | "UNKNOWN";
    conjunctivalInjection?: "YES" | "NO" | "UNKNOWN";
    acuteRespiratoryDistressSyndrome?: "YES" | "NO" | "UNKNOWN";
    pneumoniaClinicalOrRadiologic?: "YES" | "NO" | "UNKNOWN";
    lossOfTaste?: "YES" | "NO" | "UNKNOWN";
    lossOfSmell?: "YES" | "NO" | "UNKNOWN";
    wheezing?: "YES" | "NO" | "UNKNOWN";
    skinUlcers?: "YES" | "NO" | "UNKNOWN";
    inabilityToWalk?: "YES" | "NO" | "UNKNOWN";
    inDrawingOfChestWall?: "YES" | "NO" | "UNKNOWN";
    respiratoryDiseaseVentilation?: "YES" | "NO" | "UNKNOWN";
    generalSignsOfDisease?: "YES" | "NO" | "UNKNOWN";
    fastHeartRate?: "YES" | "NO" | "UNKNOWN";
    oxygenSaturationLower94?: "YES" | "NO" | "UNKNOWN";
    weight?: number;
    alteredConsciousness?: "YES" | "NO" | "UNKNOWN";
    confusedDisoriented?: "YES" | "NO" | "UNKNOWN";
    hemorrhagicSyndrome?: "YES" | "NO" | "UNKNOWN";
    hyperglycemia?: "YES" | "NO" | "UNKNOWN";
    hypoglycemia?: "YES" | "NO" | "UNKNOWN";
    meningealSigns?: "YES" | "NO" | "UNKNOWN";
    otherComplications?: "YES" | "NO" | "UNKNOWN";
    otherComplicationsText?: string;
    seizures?: "YES" | "NO" | "UNKNOWN";
    sepsis?: "YES" | "NO" | "UNKNOWN";
    shock?: "YES" | "NO" | "UNKNOWN";
}

export interface TherapyDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
}

export type ClassificationAllOfCriteriaDto = ClassificationCriteriaDto & {
    drawSubCriteriaTogether?: boolean;
    subCriteria?: ClassificationCriteriaDto[];
    criteriaName?: string;
};

export type ClassificationCaseCriteriaDto = ClassificationCriteriaDto & {
    propertyId?: string;
    propertyValues?: object[];
};

export interface ClassificationCriteriaDto {
    type?: string;
}

export type ClassificationEpiDataCriteriaDto = ClassificationCaseCriteriaDto;

export type ClassificationNoneOfCriteriaDto = ClassificationCriteriaDto & { criteriaName?: string };

export type ClassificationNotInStartDateRangeCriteriaDto = ClassificationCaseCriteriaDto & {
    daysBeforeStartDate?: number;
};

export type ClassificationPathogenTestCriteriaDto = ClassificationCaseCriteriaDto & {
    testTypes?: (
        | "ANTIBODY_DETECTION"
        | "ANTIGEN_DETECTION"
        | "RAPID_TEST"
        | "CULTURE"
        | "HISTOPATHOLOGY"
        | "ISOLATION"
        | "IGM_SERUM_ANTIBODY"
        | "IGG_SERUM_ANTIBODY"
        | "INCUBATION_TIME"
        | "INDIRECT_FLUORESCENT_ANTIBODY"
        | "DIRECT_FLUORESCENT_ANTIBODY"
        | "MICROSCOPY"
        | "NEUTRALIZING_ANTIBODIES"
        | "PCR_RT_PCR"
        | "GRAM_STAIN"
        | "LATEX_AGGLUTINATION"
        | "CQ_VALUE_DETECTION"
        | "SEQUENCING"
        | "OTHER"
        )[];
};

export type ClassificationPathogenTestNegativeResultCriteriaDto = ClassificationCriteriaDto;

export type ClassificationPathogenTestOtherPositiveResultCriteriaDto = ClassificationCriteriaDto;

export type ClassificationPathogenTestPositiveResultCriteriaDto = ClassificationCriteriaDto & {
    pathogenTestTypes?: (
        | "ANTIBODY_DETECTION"
        | "ANTIGEN_DETECTION"
        | "RAPID_TEST"
        | "CULTURE"
        | "HISTOPATHOLOGY"
        | "ISOLATION"
        | "IGM_SERUM_ANTIBODY"
        | "IGG_SERUM_ANTIBODY"
        | "INCUBATION_TIME"
        | "INDIRECT_FLUORESCENT_ANTIBODY"
        | "DIRECT_FLUORESCENT_ANTIBODY"
        | "MICROSCOPY"
        | "NEUTRALIZING_ANTIBODIES"
        | "PCR_RT_PCR"
        | "GRAM_STAIN"
        | "LATEX_AGGLUTINATION"
        | "CQ_VALUE_DETECTION"
        | "SEQUENCING"
        | "OTHER"
        )[];
    sampleTestTypes?: (
        | "ANTIBODY_DETECTION"
        | "ANTIGEN_DETECTION"
        | "RAPID_TEST"
        | "CULTURE"
        | "HISTOPATHOLOGY"
        | "ISOLATION"
        | "IGM_SERUM_ANTIBODY"
        | "IGG_SERUM_ANTIBODY"
        | "INCUBATION_TIME"
        | "INDIRECT_FLUORESCENT_ANTIBODY"
        | "DIRECT_FLUORESCENT_ANTIBODY"
        | "MICROSCOPY"
        | "NEUTRALIZING_ANTIBODIES"
        | "PCR_RT_PCR"
        | "GRAM_STAIN"
        | "LATEX_AGGLUTINATION"
        | "CQ_VALUE_DETECTION"
        | "SEQUENCING"
        | "OTHER"
        )[];
};

export type ClassificationPersonAgeBetweenYearsCriteriaDto = ClassificationCriteriaDto & {
    lowerThreshold?: number;
    upperThreshold?: number;
};

export type ClassificationSymptomsCriteriaDto = ClassificationCaseCriteriaDto;

export type ClassificationXOfCriteriaDto = ClassificationCriteriaDto & {
    requiredAmount?: number;
    criteriaName?: string;
};

export interface DiseaseClassificationCriteriaDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    suspectCriteria?: ClassificationCriteriaDto;
    probableCriteria?: ClassificationCriteriaDto;
    confirmedCriteria?: ClassificationCriteriaDto;
    notACaseCriteria?: ClassificationCriteriaDto;
}

export interface ClinicalCourseReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface ClinicalVisitDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    clinicalCourse?: ClinicalCourseReferenceDto;
    symptoms?: SymptomsDto;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    visitDateTime?: string;
    visitRemarks?: string;
    visitingPerson?: string;
}

export interface CommunityDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    name?: string;
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    archived?: boolean;
    externalID?: string;
}

export interface CaseReferenceDto {
    uuid?: string;
    caption?: string;
    firstName?: string;
    lastName?: string;
}

export interface ContactDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    caze?: CaseReferenceDto;
    caseIdExternalSystem?: string;
    caseOrEventInformation?: string;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    diseaseDetails?: string;
    reportDateTime: string;
    reportingUser: UserReferenceDto;
    reportLat?: number;
    reportLon?: number;
    reportLatLonAccuracy?: number;
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    lastContactDate?: string;
    contactProximity?:
        | "TOUCHED_FLUID"
        | "PHYSICAL_CONTACT"
        | "CLOTHES_OR_OTHER"
        | "CLOSE_CONTACT"
        | "FACE_TO_FACE_LONG"
        | "MEDICAL_UNSAFE"
        | "SAME_ROOM"
        | "AIRPLANE"
        | "FACE_TO_FACE_SHORT"
        | "MEDICAL_SAFE"
        | "MEDICAL_SAME_ROOM"
        | "AEROSOL"
        | "MEDICAL_DISTANT"
        | "MEDICAL_LIMITED";
    contactProximityDetails?: string;
    contactCategory?: "HIGH_RISK" | "HIGH_RISK_MED" | "MEDIUM_RISK_MED" | "LOW_RISK" | "NO_RISK";
    contactClassification?: "UNCONFIRMED" | "CONFIRMED" | "NO_CONTACT";
    contactStatus?: "ACTIVE" | "CONVERTED" | "DROPPED";
    followUpStatus?: "FOLLOW_UP" | "COMPLETED" | "CANCELED" | "LOST" | "NO_FOLLOW_UP";
    followUpComment?: string;
    followUpUntil?: string;
    overwriteFollowUpUntil?: boolean;
    description?: string;
    relationToCase?: "SAME_HOUSEHOLD" | "FAMILY_MEMBER_OR_FRIEND" | "SAME_ENVIRONMENT" | "MEDICAL_CARE" | "OTHER";
    relationDescription?: string;
    externalID?: string;
    highPriority?: boolean;
    immunosuppressiveTherapyBasicDisease?: "YES" | "NO" | "UNKNOWN";
    immunosuppressiveTherapyBasicDiseaseDetails?: string;
    careForPeopleOver60?: "YES" | "NO" | "UNKNOWN";
    quarantine?: "INSTITUTIONELL" | "HOME" | "NONE" | "UNKNOWN";
    quarantineFrom?: string;
    quarantineTo?: string;
    person: PersonReferenceDto;
    contactOfficer?: UserReferenceDto;
    resultingCase?: CaseReferenceDto;
    resultingCaseUser?: UserReferenceDto;
    quarantineHelpNeeded?: string;
    quarantineOrderedVerbally?: boolean;
    quarantineOrderedOfficialDocument?: boolean;
    quarantineOrderedVerballyDate?: string;
    quarantineOrderedOfficialDocumentDate?: string;
    quarantineHomePossible?: "YES" | "NO" | "UNKNOWN";
    quarantineHomePossibleComment?: string;
    quarantineHomeSupplyEnsured?: "YES" | "NO" | "UNKNOWN";
    quarantineHomeSupplyEnsuredComment?: string;
    additionalDetails?: string;
}

export interface DiseaseConfigurationDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    active?: boolean;
    primaryDisease?: boolean;
    caseBased?: boolean;
    followUpEnabled?: boolean;
    followUpDuration?: number;
}

export interface DistrictDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    name?: string;
    epidCode?: string;
    growthRate?: number;
    region?: RegionReferenceDto;
    archived?: boolean;
    externalID?: string;
}

export interface EventParticipantDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    event: EventReferenceDto;
    person: PersonDto;
    involvementDescription?: string;
    resultingCase?: CaseReferenceDto;
}

export interface EventReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface PersonDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    pseudonymized?: boolean;
    firstName: string;
    lastName?: string;
    nickname?: string;
    mothersName?: string;
    mothersMaidenName?: string;
    fathersName?: string;
    sex?: "MALE" | "FEMALE";
    birthdateDD?: number;
    birthdateMM?: number;
    birthdateYYYY?: number;
    approximateAge?: number;
    approximateAgeType?: "YEARS" | "MONTHS" | "DAYS";
    approximateAgeReferenceDate?: string;
    placeOfBirthRegion?: RegionReferenceDto;
    placeOfBirthDistrict?: DistrictReferenceDto;
    placeOfBirthCommunity?: CommunityReferenceDto;
    placeOfBirthFacility?: FacilityReferenceDto;
    placeOfBirthFacilityDetails?: string;
    gestationAgeAtBirth?: number;
    birthWeight?: number;
    presentCondition?: "ALIVE" | "DEAD" | "BURIED";
    deathDate?: string;
    causeOfDeath?: "EPIDEMIC_DISEASE" | "OTHER_CAUSE";
    causeOfDeathDisease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    causeOfDeathDetails?: string;
    deathPlaceType?: "COMMUNITY" | "HOSPITAL" | "OTHER";
    deathPlaceDescription?: string;
    burialDate?: string;
    burialPlaceDescription?: string;
    burialConductor?: "FAMILY_COMMUNITY" | "OUTBREAK_TEAM";
    phone?: string;
    phoneOwner?: string;
    address?: LocationDto;
    emailAddress?: string;
    educationType?: "NONE" | "NURSERY" | "PRIMARY" | "SECONDARY" | "TERTIARY" | "OTHER";
    educationDetails?: string;
    occupationType?:
        | "FARMER"
        | "BUTCHER"
        | "HUNTER_MEAT_TRADER"
        | "MINER"
        | "RELIGIOUS_LEADER"
        | "HOUSEWIFE"
        | "PUPIL_STUDENT"
        | "CHILD"
        | "BUSINESSMAN_WOMAN"
        | "TRANSPORTER"
        | "HEALTHCARE_WORKER"
        | "TRADITIONAL_SPIRITUAL_HEALER"
        | "WORKING_WITH_ANIMALS"
        | "LABORATORY_STAFF"
        | "OTHER";
    occupationDetails?: string;
    occupationRegion?: RegionReferenceDto;
    occupationDistrict?: DistrictReferenceDto;
    occupationCommunity?: CommunityReferenceDto;
    occupationFacility?: FacilityReferenceDto;
    occupationFacilityDetails?: string;
    generalPractitionerDetails?: string;
    passportNumber?: string;
    nationalHealthId?: string;
}

export interface EventDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    eventStatus: "POSSIBLE" | "CONFIRMED" | "NO_EVENT";
    eventDesc: string;
    eventDate?: string;
    reportDateTime: string;
    reportingUser: UserReferenceDto;
    eventLocation?: LocationDto;
    typeOfPlace?: "FESTIVITIES" | "HOME" | "HOSPITAL" | "MEANS_OF_TRANSPORT" | "PUBLIC_PLACE" | "UNKNOWN" | "OTHER";
    srcFirstName?: string;
    srcLastName?: string;
    srcTelNo?: string;
    srcEmail?: string;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    diseaseDetails?: string;
    surveillanceOfficer?: UserReferenceDto;
    typeOfPlaceText?: string;
    reportLat?: number;
    reportLon?: number;
    reportLatLonAccuracy?: number;
}

export interface ExternalVisitDto {
    personUuid: string;
    disease:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    visitDateTime: string;
    visitStatus: "UNAVAILABLE" | "UNCOOPERATIVE" | "COOPERATIVE";
    visitRemarks?: string;
    symptoms?: SymptomsDto;
    reportLat?: number;
    reportLon?: number;
    reportLatLonAccuracy?: number;
}

export interface FacilityDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    name?: string;
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    community?: CommunityReferenceDto;
    city?: string;
    latitude?: number;
    longitude?: number;
    type?: "PRIMARY" | "SECONDARY" | "TERTIARY" | "LABORATORY";
    publicOwnership?: boolean;
    archived?: boolean;
    externalID?: string;
}

export interface FeatureConfigurationDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    featureType?:
        | "LINE_LISTING"
        | "AGGREGATE_REPORTING"
        | "EVENT_SURVEILLANCE"
        | "WEEKLY_REPORTING"
        | "CLINICAL_MANAGEMENT"
        | "NATIONAL_CASE_SHARING"
        | "TASK_GENERATION_CASE_SURVEILLANCE"
        | "TASK_GENERATION_CONTACT_TRACING"
        | "TASK_GENERATION_EVENT_SURVEILLANCE"
        | "TASK_GENERATION_GENERAL"
        | "CAMPAIGNS";
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    endDate?: string;
    enabled?: boolean;
}

export interface InfrastructureSyncDto {
    initialSyncRequired?: boolean;
    regions?: RegionDto[];
    districts?: DistrictDto[];
    communities?: CommunityDto[];
    facilities?: FacilityDto[];
    pointsOfEntry?: PointOfEntryDto[];
    users?: UserDto[];
    diseaseClassifications?: DiseaseClassificationCriteriaDto[];
    diseaseConfigurations?: DiseaseConfigurationDto[];
    userRoleConfigurations?: UserRoleConfigDto[];
    deletedUserRoleConfigurationUuids?: string[];
    featureConfigurations?: FeatureConfigurationDto[];
    deletedFeatureConfigurationUuids?: string[];
}

export interface PointOfEntryDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    pointOfEntryType?: "AIRPORT" | "SEAPORT" | "GROUND_CROSSING" | "OTHER";
    name?: string;
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    latitude?: number;
    longitude?: number;
    active?: boolean;
    archived?: boolean;
    externalID?: string;
    nameOtherPointOfEntry?: boolean;
    otherPointOfEntry?: boolean;
}

export interface RegionDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    name?: string;
    epidCode?: string;
    growthRate?: number;
    archived?: boolean;
    externalID?: string;
}

export interface UserDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    active?: boolean;
    userName?: string;
    firstName?: string;
    lastName?: string;
    userEmail?: string;
    phone?: string;
    address?: LocationDto;
    userRoles?: (
        | "ADMIN"
        | "NATIONAL_USER"
        | "SURVEILLANCE_SUPERVISOR"
        | "SURVEILLANCE_OFFICER"
        | "HOSPITAL_INFORMANT"
        | "COMMUNITY_INFORMANT"
        | "CASE_SUPERVISOR"
        | "CASE_OFFICER"
        | "CONTACT_SUPERVISOR"
        | "CONTACT_OFFICER"
        | "EVENT_OFFICER"
        | "LAB_USER"
        | "EXTERNAL_LAB_USER"
        | "NATIONAL_OBSERVER"
        | "STATE_OBSERVER"
        | "DISTRICT_OBSERVER"
        | "NATIONAL_CLINICIAN"
        | "POE_INFORMANT"
        | "POE_SUPERVISOR"
        | "POE_NATIONAL_USER"
        | "IMPORT_USER"
        | "REST_EXTERNAL_VISITS_USER"
        | "REST_USER"
        )[];
    region?: RegionReferenceDto;
    district?: DistrictReferenceDto;
    community?: CommunityReferenceDto;
    healthFacility?: FacilityReferenceDto;
    laboratory?: FacilityReferenceDto;
    pointOfEntry?: PointOfEntryReferenceDto;
    associatedOfficer?: UserReferenceDto;
    limitedDisease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    language?: "EN" | "EN_NG" | "EN_GH" | "FR" | "DE" | "ES_EC" | "ES_CU" | "FI";
    name?: string;
}

export interface UserRoleConfigDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    userRole?:
        | "ADMIN"
        | "NATIONAL_USER"
        | "SURVEILLANCE_SUPERVISOR"
        | "SURVEILLANCE_OFFICER"
        | "HOSPITAL_INFORMANT"
        | "COMMUNITY_INFORMANT"
        | "CASE_SUPERVISOR"
        | "CASE_OFFICER"
        | "CONTACT_SUPERVISOR"
        | "CONTACT_OFFICER"
        | "EVENT_OFFICER"
        | "LAB_USER"
        | "EXTERNAL_LAB_USER"
        | "NATIONAL_OBSERVER"
        | "STATE_OBSERVER"
        | "DISTRICT_OBSERVER"
        | "NATIONAL_CLINICIAN"
        | "POE_INFORMANT"
        | "POE_SUPERVISOR"
        | "POE_NATIONAL_USER"
        | "IMPORT_USER"
        | "REST_EXTERNAL_VISITS_USER"
        | "REST_USER";
    userRights?: (
        | "CASE_CREATE"
        | "CASE_VIEW"
        | "CASE_EDIT"
        | "CASE_TRANSFER"
        | "CASE_REFER_FROM_POE"
        | "CASE_INVESTIGATE"
        | "CASE_CLASSIFY"
        | "CASE_CHANGE_DISEASE"
        | "CASE_CHANGE_EPID_NUMBER"
        | "CASE_DELETE"
        | "CASE_IMPORT"
        | "CASE_EXPORT"
        | "CASE_SHARE"
        | "CASE_ARCHIVE"
        | "CASE_VIEW_ARCHIVED"
        | "CASE_MERGE"
        | "SAMPLE_CREATE"
        | "SAMPLE_VIEW"
        | "SAMPLE_EDIT"
        | "SAMPLE_EDIT_NOT_OWNED"
        | "SAMPLE_DELETE"
        | "SAMPLE_TRANSFER"
        | "SAMPLE_EXPORT"
        | "SAMPLE_VIEW_ARCHIVED"
        | "PATHOGEN_TEST_CREATE"
        | "PATHOGEN_TEST_EDIT"
        | "PATHOGEN_TEST_DELETE"
        | "ADDITIONAL_TEST_VIEW"
        | "ADDITIONAL_TEST_CREATE"
        | "ADDITIONAL_TEST_EDIT"
        | "ADDITIONAL_TEST_DELETE"
        | "CONTACT_CREATE"
        | "CONTACT_IMPORT"
        | "CONTACT_VIEW"
        | "CONTACT_ASSIGN"
        | "CONTACT_EDIT"
        | "CONTACT_DELETE"
        | "CONTACT_CLASSIFY"
        | "CONTACT_CONVERT"
        | "CONTACT_EXPORT"
        | "CONTACT_VIEW_ARCHIVED"
        | "CONTACT_REASSIGN_CASE"
        | "VISIT_CREATE"
        | "VISIT_EDIT"
        | "VISIT_DELETE"
        | "VISIT_EXPORT"
        | "TASK_CREATE"
        | "TASK_VIEW"
        | "TASK_EDIT"
        | "TASK_ASSIGN"
        | "TASK_VIEW_ARCHIVED"
        | "TASK_DELETE"
        | "EVENT_CREATE"
        | "EVENT_VIEW"
        | "EVENT_EDIT"
        | "EVENT_EXPORT"
        | "EVENT_ARCHIVE"
        | "EVENT_DELETE"
        | "EVENT_VIEW_ARCHIVED"
        | "EVENTPARTICIPANT_CREATE"
        | "EVENTPARTICIPANT_EDIT"
        | "WEEKLYREPORT_CREATE"
        | "WEEKLYREPORT_VIEW"
        | "USER_CREATE"
        | "USER_EDIT"
        | "USER_VIEW"
        | "CONFIGURATION_ACCESS"
        | "OUTBREAK_CONFIGURE_ALL"
        | "OUTBREAK_CONFIGURE_RESTRICTED"
        | "STATISTICS_ACCESS"
        | "STATISTICS_EXPORT"
        | "DATABASE_EXPORT_ACCESS"
        | "PERFORM_BULK_OPERATIONS"
        | "INFRASTRUCTURE_CREATE"
        | "INFRASTRUCTURE_EDIT"
        | "INFRASTRUCTURE_VIEW"
        | "INFRASTRUCTURE_VIEW_ARCHIVED"
        | "INFRASTRUCTURE_EXPORT"
        | "INFRASTRUCTURE_IMPORT"
        | "INFRASTRUCTURE_ARCHIVE"
        | "USER_RIGHTS_MANAGE"
        | "DASHBOARD_VIEW"
        | "DASHBOARD_SURVEILLANCE_ACCESS"
        | "DASHBOARD_CONTACT_ACCESS"
        | "DASHBOARD_CONTACT_VIEW_TRANSMISSION_CHAINS"
        | "CASE_MANAGEMENT_ACCESS"
        | "THERAPY_VIEW"
        | "PRESCRIPTION_CREATE"
        | "PRESCRIPTION_EDIT"
        | "PRESCRIPTION_DELETE"
        | "TREATMENT_CREATE"
        | "TREATMENT_EDIT"
        | "TREATMENT_DELETE"
        | "CLINICAL_COURSE_VIEW"
        | "CLINICAL_COURSE_EDIT"
        | "CLINICAL_VISIT_CREATE"
        | "CLINICAL_VISIT_EDIT"
        | "CLINICAL_VISIT_DELETE"
        | "PORT_HEALTH_INFO_VIEW"
        | "PORT_HEALTH_INFO_EDIT"
        | "POPULATION_MANAGE"
        | "LINE_LISTING_CONFIGURE"
        | "LINE_LISTING_CONFIGURE_NATION"
        | "AGGREGATE_REPORT_VIEW"
        | "AGGREGATE_REPORT_EXPORT"
        | "AGGREGATE_REPORT_EDIT"
        | "SEE_PERSONAL_DATA_IN_JURISDICTION"
        | "SEE_PERSONAL_DATA_OUTSIDE_JURISDICTION"
        | "SEE_SENSITIVE_DATA_IN_JURISDICTION"
        | "SEE_SENSITIVE_DATA_OUTSIDE_JURISDICTION"
        | "CAMPAIGN_VIEW"
        | "CAMPAIGN_EDIT"
        | "CAMPAIGN_ARCHIVE"
        | "CAMPAIGN_DELETE"
        )[];
}

export interface InfrastructureChangeDatesDto {
    regionChangeDate?: string;
    districtChangeDate?: string;
    communityChangeDate?: string;
    facilityChangeDate?: string;
    pointOfEntryChangeDate?: string;
    userChangeDate?: string;
    diseaseClassificationChangeDate?: string;
    diseaseConfigurationChangeDate?: string;
    userRoleConfigurationChangeDate?: string;
    featureConfigurationChangeDate?: string;
}

export interface OutbreakDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    district?: DistrictReferenceDto;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    startDate?: string;
    endDate?: string;
    reportingUser?: UserReferenceDto;
    reportDate?: string;
}

export interface PathogenTestDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    sample: SampleReferenceDto;
    testedDisease:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    testedDiseaseDetails?: string;
    testType:
        | "ANTIBODY_DETECTION"
        | "ANTIGEN_DETECTION"
        | "RAPID_TEST"
        | "CULTURE"
        | "HISTOPATHOLOGY"
        | "ISOLATION"
        | "IGM_SERUM_ANTIBODY"
        | "IGG_SERUM_ANTIBODY"
        | "INCUBATION_TIME"
        | "INDIRECT_FLUORESCENT_ANTIBODY"
        | "DIRECT_FLUORESCENT_ANTIBODY"
        | "MICROSCOPY"
        | "NEUTRALIZING_ANTIBODIES"
        | "PCR_RT_PCR"
        | "GRAM_STAIN"
        | "LATEX_AGGLUTINATION"
        | "CQ_VALUE_DETECTION"
        | "SEQUENCING"
        | "OTHER";
    testTypeText?: string;
    testDateTime: string;
    lab: FacilityReferenceDto;
    labDetails?: string;
    labUser: UserReferenceDto;
    testResult: "INDETERMINATE" | "PENDING" | "NEGATIVE" | "POSITIVE";
    testResultText: string;
    testResultVerified: boolean;
    fourFoldIncreaseAntibodyTiter?: boolean;
    serotype?: string;
    cqValue?: number;
}

export interface PrescriptionDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    therapy?: TherapyReferenceDto;
    prescriptionDate?: string;
    prescriptionStart?: string;
    prescriptionEnd?: string;
    prescribingClinician?: string;
    prescriptionType?:
        | "DRUG_INTAKE"
        | "ORAL_REHYDRATION_SALTS"
        | "BLOOD_TRANSFUSION"
        | "RENAL_REPLACEMENT_THERAPY"
        | "IV_FLUID_THERAPY"
        | "OXYGEN_THERAPY"
        | "INVASIVE_MECHANICAL_VENTILATION"
        | "VASOPRESSORS_INOTROPES"
        | "OTHER";
    prescriptionDetails?: string;
    typeOfDrug?: "ANTIMICROBIAL" | "ANTIVIRAL" | "OTHER";
    frequency?: string;
    dose?: string;
    route?: "ORAL" | "IV" | "RECTAL" | "TOPICAL" | "OTHER";
    routeDetails?: string;
    additionalNotes?: string;
}

export interface TherapyReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface ContactReferenceDto {
    uuid?: string;
    caption?: string;
    contactName?: PersonName;
    caseName?: PersonName;
}

export interface PersonName {
    firstName?: string;
    lastName?: string;
}

export interface SampleDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    associatedCase?: CaseReferenceDto;
    associatedContact?: ContactReferenceDto;
    labSampleID?: string;
    fieldSampleID?: string;
    sampleDateTime: string;
    reportDateTime?: string;
    reportingUser: UserReferenceDto;
    reportLat?: number;
    reportLon?: number;
    reportLatLonAccuracy?: number;
    sampleMaterial:
        | "BLOOD"
        | "SERA"
        | "STOOL"
        | "NASAL_SWAB"
        | "THROAT_SWAB"
        | "NP_SWAB"
        | "RECTAL_SWAB"
        | "CEREBROSPINAL_FLUID"
        | "CRUST"
        | "TISSUE"
        | "URINE"
        | "CORNEA_PM"
        | "SALIVA"
        | "URINE_PM"
        | "NUCHAL_SKIN_BIOPSY"
        | "SPUTUM"
        | "ENDOTRACHEAL_ASPIRATE"
        | "BRONCHOALVEOLAR_LAVAGE"
        | "BRAIN_TISSUE"
        | "OTHER";
    sampleMaterialText?: string;
    samplePurpose: "EXTERNAL" | "INTERNAL";
    lab: FacilityReferenceDto;
    labDetails?: string;
    shipmentDate?: string;
    shipmentDetails?: string;
    receivedDate?: string;
    specimenCondition?: "ADEQUATE" | "NOT_ADEQUATE";
    noTestPossibleReason?: string;
    comment?: string;
    sampleSource?: "HUMAN" | "ANIMAL" | "ENVIRONMENT";
    referredTo?: SampleReferenceDto;
    shipped?: boolean;
    received?: boolean;
    pathogenTestResult?: "INDETERMINATE" | "PENDING" | "NEGATIVE" | "POSITIVE";
    pathogenTestingRequested?: boolean;
    additionalTestingRequested?: boolean;
    requestedPathogenTests?: (
        | "ANTIBODY_DETECTION"
        | "ANTIGEN_DETECTION"
        | "RAPID_TEST"
        | "CULTURE"
        | "HISTOPATHOLOGY"
        | "ISOLATION"
        | "IGM_SERUM_ANTIBODY"
        | "IGG_SERUM_ANTIBODY"
        | "INCUBATION_TIME"
        | "INDIRECT_FLUORESCENT_ANTIBODY"
        | "DIRECT_FLUORESCENT_ANTIBODY"
        | "MICROSCOPY"
        | "NEUTRALIZING_ANTIBODIES"
        | "PCR_RT_PCR"
        | "GRAM_STAIN"
        | "LATEX_AGGLUTINATION"
        | "CQ_VALUE_DETECTION"
        | "SEQUENCING"
        | "OTHER"
        )[];
    requestedAdditionalTests?: (
        | "HAEMOGLOBINURIA"
        | "PROTEINURIA"
        | "HEMATURIA"
        | "ARTERIAL_VENOUS_BLOOD_GAS"
        | "ALT_SGPT"
        | "AST_SGOT"
        | "CREATININE"
        | "POTASSIUM"
        | "UREA"
        | "HAEMOGLOBIN"
        | "TOTAL_BILIRUBIN"
        | "CONJ_BILIRUBIN"
        | "WBC_COUNT"
        | "PLATELETS"
        | "PROTHROMBIN_TIME"
        )[];
    requestedOtherPathogenTests?: string;
    requestedOtherAdditionalTests?: string;
}

export interface ReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface TaskDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    taskContext: "CASE" | "CONTACT" | "EVENT" | "GENERAL";
    caze?: CaseReferenceDto;
    event?: EventReferenceDto;
    contact?: ContactReferenceDto;
    taskType:
        | "ACTIVE_SEARCH_FOR_OTHER_CASES"
        | "CASE_ISOLATION"
        | "CASE_INVESTIGATION"
        | "CASE_MANAGEMENT"
        | "CASE_BURIAL"
        | "CONTACT_TRACING"
        | "SAMPLE_COLLECTION"
        | "CONTACT_INVESTIGATION"
        | "CONTACT_FOLLOW_UP"
        | "ANIMAL_TESTING"
        | "EVENT_INVESTIGATION"
        | "TREATMENT_CENTER_ESTABLISHMENT"
        | "ENVIRONMENTAL_HEALTH_ACTIVITIES"
        | "DECONTAMINATION_DISINFECTION_ACTIVITIES"
        | "QUARANTINE_PLACE"
        | "VACCINATION_ACTIVITIES"
        | "ANIMAL_DEPOPULATION"
        | "OTHER"
        | "DAILY_REPORT_GENERATION"
        | "SURVEILLANCE_REPORT_GENERATION"
        | "WEEKLY_REPORT_GENERATION";
    priority?: "HIGH" | "NORMAL" | "LOW";
    dueDate: string;
    suggestedStart?: string;
    taskStatus?: "PENDING" | "DONE" | "REMOVED" | "NOT_EXECUTABLE";
    statusChangeDate?: string;
    perceivedStart?: string;
    creatorUser?: UserReferenceDto;
    creatorComment?: string;
    assigneeUser: UserReferenceDto;
    assigneeReply?: string;
    closedLat?: number;
    closedLon?: number;
    closedLatLonAccuracy?: number;
    contextReference?: ReferenceDto;
}

export interface PrescriptionReferenceDto {
    uuid?: string;
    caption?: string;
}

export interface TreatmentDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    therapy?: TherapyReferenceDto;
    treatmentDateTime?: string;
    executingClinician?: string;
    treatmentType?:
        | "DRUG_INTAKE"
        | "ORAL_REHYDRATION_SALTS"
        | "BLOOD_TRANSFUSION"
        | "RENAL_REPLACEMENT_THERAPY"
        | "IV_FLUID_THERAPY"
        | "OXYGEN_THERAPY"
        | "INVASIVE_MECHANICAL_VENTILATION"
        | "VASOPRESSORS_INOTROPES"
        | "OTHER";
    treatmentDetails?: string;
    typeOfDrug?: "ANTIMICROBIAL" | "ANTIVIRAL" | "OTHER";
    dose?: string;
    route?: "ORAL" | "IV" | "RECTAL" | "TOPICAL" | "OTHER";
    routeDetails?: string;
    additionalNotes?: string;
    prescription?: PrescriptionReferenceDto;
}

export interface VisitDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    person: PersonReferenceDto;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    visitDateTime: string;
    visitUser: UserReferenceDto;
    visitStatus: "UNAVAILABLE" | "UNCOOPERATIVE" | "COOPERATIVE";
    visitRemarks?: string;
    symptoms?: SymptomsDto;
    reportLat?: number;
    reportLon?: number;
    reportLatLonAccuracy?: number;
}

export interface WeeklyReportDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    reportingUser?: UserReferenceDto;
    reportDateTime?: string;
    district?: DistrictReferenceDto;
    community?: CommunityReferenceDto;
    healthFacility?: FacilityReferenceDto;
    assignedOfficer?: UserReferenceDto;
    totalNumberOfCases?: number;
    year?: number;
    epiWeek?: number;
    reportEntries?: WeeklyReportEntryDto[];
}

export interface WeeklyReportEntryDto {
    creationDate?: string;
    changeDate?: string;
    uuid?: string;
    disease?:
        | "AFP"
        | "CHOLERA"
        | "CONGENITAL_RUBELLA"
        | "CSM"
        | "DENGUE"
        | "EVD"
        | "GUINEA_WORM"
        | "LASSA"
        | "MEASLES"
        | "MONKEYPOX"
        | "NEW_INFLUENZA"
        | "PLAGUE"
        | "POLIO"
        | "UNSPECIFIED_VHF"
        | "WEST_NILE_FEVER"
        | "YELLOW_FEVER"
        | "RABIES"
        | "ANTHRAX"
        | "CORONAVIRUS"
        | "PNEUMONIA"
        | "MALARIA"
        | "TYPHOID_FEVER"
        | "ACUTE_VIRAL_HEPATITIS"
        | "NON_NEONATAL_TETANUS"
        | "HIV"
        | "SCHISTOSOMIASIS"
        | "SOIL_TRANSMITTED_HELMINTHS"
        | "TRYPANOSOMIASIS"
        | "DIARRHEA_DEHYDRATION"
        | "DIARRHEA_BLOOD"
        | "SNAKE_BITE"
        | "RUBELLA"
        | "TUBERCULOSIS"
        | "LEPROSY"
        | "LYMPHATIC_FILARIASIS"
        | "BURULI_ULCER"
        | "PERTUSSIS"
        | "NEONATAL_TETANUS"
        | "ONCHOCERCIASIS"
        | "DIPHTERIA"
        | "TRACHOMA"
        | "YAWS_ENDEMIC_SYPHILIS"
        | "MATERNAL_DEATHS"
        | "PERINATAL_DEATHS"
        | "INFLUENZA_A"
        | "INFLUENZA_B"
        | "H_METAPNEUMOVIRUS"
        | "RESPIRATORY_SYNCYTIAL_VIRUS"
        | "PARAINFLUENZA_1_4"
        | "ADENOVIRUS"
        | "RHINOVIRUS"
        | "ENTEROVIRUS"
        | "M_PNEUMONIAE"
        | "C_PNEUMONIAE"
        | "OTHER"
        | "UNDEFINED";
    numberOfCases?: number;
}

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
    public baseUrl: string = "/sormas-rest";
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
 * @title SORMAS REST API
 * @version 1.42.0-SNAPSHOT
 * @baseUrl /sormas-rest
 */
export class SormasSwaggerApi<SecurityDataType = any> extends HttpClient<SecurityDataType> {
    additionaltests = {
        /**
         * @tags Additional Test Controller
         * @name getByUuids
         * @request POST:/additionaltests/query
         */
        getByUuids: (data: string[], params?: RequestParams) =>
            this.request<any, AdditionalTestDto[]>(`/additionaltests/query`, "POST", params, data),

        /**
         * @tags Additional Test Controller
         * @name getAllActiveUuids
         * @request GET:/additionaltests/uuids
         */
        getAllActiveUuids: (params?: RequestParams) => this.request<any, string[]>(`/additionaltests/uuids`, "GET", params),

        /**
         * @tags Additional Test Controller
         * @name getAllAdditionalTests
         * @request GET:/additionaltests/all/{since}
         */
        getAllAdditionalTests: (since: number, params?: RequestParams) =>
            this.request<any, AdditionalTestDto[]>(`/additionaltests/all/${since}`, "GET", params),

        /**
         * @tags Additional Test Controller
         * @name postAdditionalTests
         * @request POST:/additionaltests/push
         */
        postAdditionalTests: (data: AdditionalTestDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/additionaltests/push`, "POST", params, data),
    };
    aggregatereports = {
        /**
         * @tags Aggregate Report Controller
         * @name getByUuids_1
         * @request POST:/aggregatereports/query
         */
        getByUuids1: (data: string[], params?: RequestParams) =>
            this.request<any, AggregateReportDto[]>(`/aggregatereports/query`, "POST", params, data),

        /**
         * @tags Aggregate Report Controller
         * @name getAllAggregateReports
         * @request GET:/aggregatereports/all/{since}
         */
        getAllAggregateReports: (since: number, params?: RequestParams) =>
            this.request<any, AggregateReportDto[]>(`/aggregatereports/all/${since}`, "GET", params),

        /**
         * @tags Aggregate Report Controller
         * @name postAggregateReports
         * @request POST:/aggregatereports/push
         */
        postAggregateReports: (data: AggregateReportDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/aggregatereports/push`, "POST", params, data),

        /**
         * @tags Aggregate Report Controller
         * @name getAllUuids
         * @request GET:/aggregatereports/uuids
         */
        getAllUuids: (params?: RequestParams) => this.request<any, string[]>(`/aggregatereports/uuids`, "GET", params),
    };
    cases = {
        /**
         * @tags Case Controller
         * @name getByUuids_2
         * @request POST:/cases/query
         */
        getByUuids2: (data: string[], params?: RequestParams) =>
            this.request<any, CaseDataDto[]>(`/cases/query`, "POST", params, data),

        /**
         * @tags Case Controller
         * @name getAllCases
         * @request GET:/cases/all/{since}
         */
        getAllCases: (since: number, params?: RequestParams) =>
            this.request<any, CaseDataDto[]>(`/cases/all/${since}`, "GET", params),

        /**
         * @tags Case Controller
         * @name getAllCasesWithExtendedChangeDateFilters
         * @request GET:/cases/allWithExtendedChangeDateFilters/{since}
         */
        getAllCasesWithExtendedChangeDateFilters: (since: number, params?: RequestParams) =>
            this.request<any, CaseDataDto[]>(`/cases/allWithExtendedChangeDateFilters/${since}`, "GET", params),

        /**
         * @tags Case Controller
         * @name postCases
         * @request POST:/cases/push
         */
        postCases: (data: CaseDataDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/cases/push`, "POST", params, data),

        /**
         * @tags Case Controller
         * @name getArchivedUuidsSince
         * @request GET:/cases/archived/{since}
         */
        getArchivedUuidsSince: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/cases/archived/${since}`, "GET", params),

        /**
         * @tags Case Controller
         * @name getDeletedUuidsSince
         * @request GET:/cases/deleted/{since}
         */
        getDeletedUuidsSince: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/cases/deleted/${since}`, "GET", params),

        /**
         * @tags Case Controller
         * @name getAllUuids_1
         * @request GET:/cases/uuids
         */
        getAllUuids1: (params?: RequestParams) => this.request<any, string[]>(`/cases/uuids`, "GET", params),
    };
    classification = {
        /**
         * @tags Classification Controller
         * @name getAll
         * @request GET:/classification/all/{since}
         */
        getAll: (since: number, params?: RequestParams) =>
            this.request<any, DiseaseClassificationCriteriaDto[]>(`/classification/all/${since}`, "GET", params),
    };
    clinicalvisits = {
        /**
         * @tags Clinical Visit Controller
         * @name getByUuids_3
         * @request POST:/clinicalvisits/query
         */
        getByUuids3: (data: string[], params?: RequestParams) =>
            this.request<any, ClinicalVisitDto[]>(`/clinicalvisits/query`, "POST", params, data),

        /**
         * @tags Clinical Visit Controller
         * @name getAllActiveUuids_1
         * @request GET:/clinicalvisits/uuids
         */
        getAllActiveUuids1: (params?: RequestParams) => this.request<any, string[]>(`/clinicalvisits/uuids`, "GET", params),

        /**
         * @tags Clinical Visit Controller
         * @name getAllVisits
         * @request GET:/clinicalvisits/all/{since}
         */
        getAllVisits: (since: number, params?: RequestParams) =>
            this.request<any, ClinicalVisitDto[]>(`/clinicalvisits/all/${since}`, "GET", params),

        /**
         * @tags Clinical Visit Controller
         * @name postVisits
         * @request POST:/clinicalvisits/push
         */
        postVisits: (data: ClinicalVisitDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/clinicalvisits/push`, "POST", params, data),
    };
    communities = {
        /**
         * @tags Community Controller
         * @name getAll_1
         * @request GET:/communities/all/{since}
         */
        getAll1: (since: number, params?: RequestParams) =>
            this.request<any, CommunityDto[]>(`/communities/all/${since}`, "GET", params),

        /**
         * @tags Community Controller
         * @name getByUuids_4
         * @request POST:/communities/query
         */
        getByUuids4: (data: string[], params?: RequestParams) =>
            this.request<any, CommunityDto[]>(`/communities/query`, "POST", params, data),

        /**
         * @tags Community Controller
         * @name getAllUuids_2
         * @request GET:/communities/uuids
         */
        getAllUuids2: (params?: RequestParams) => this.request<any, string[]>(`/communities/uuids`, "GET", params),
    };
    contacts = {
        /**
         * @tags Contact Controller
         * @name getByUuids_5
         * @request POST:/contacts/query
         */
        getByUuids5: (data: string[], params?: RequestParams) =>
            this.request<any, ContactDto[]>(`/contacts/query`, "POST", params, data),

        /**
         * @tags Contact Controller
         * @name getAllActiveUuids_2
         * @request GET:/contacts/uuids
         */
        getAllActiveUuids2: (params?: RequestParams) => this.request<any, string[]>(`/contacts/uuids`, "GET", params),

        /**
         * @tags Contact Controller
         * @name getDeletedUuidsSince_1
         * @request GET:/contacts/deleted/{since}
         */
        getDeletedUuidsSince1: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/contacts/deleted/${since}`, "GET", params),

        /**
         * @tags Contact Controller
         * @name getAllContacts
         * @request GET:/contacts/all/{since}
         */
        getAllContacts: (since: number, params?: RequestParams) =>
            this.request<any, ContactDto[]>(`/contacts/all/${since}`, "GET", params),

        /**
         * @tags Contact Controller
         * @name postContacts
         * @request POST:/contacts/push
         */
        postContacts: (data: ContactDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/contacts/push`, "POST", params, data),
    };
    diseaseconfigurations = {
        /**
         * @tags Disease Configuration Controller
         * @name getByUuids_6
         * @request POST:/diseaseconfigurations/query
         */
        getByUuids6: (data: string[], params?: RequestParams) =>
            this.request<any, DiseaseConfigurationDto[]>(`/diseaseconfigurations/query`, "POST", params, data),

        /**
         * @tags Disease Configuration Controller
         * @name getAllDiseaseConfigurations
         * @request GET:/diseaseconfigurations/all/{since}
         */
        getAllDiseaseConfigurations: (since: number, params?: RequestParams) =>
            this.request<any, DiseaseConfigurationDto[]>(`/diseaseconfigurations/all/${since}`, "GET", params),

        /**
         * @tags Disease Configuration Controller
         * @name getAllUuids_3
         * @request GET:/diseaseconfigurations/uuids
         */
        getAllUuids3: (params?: RequestParams) =>
            this.request<any, string[]>(`/diseaseconfigurations/uuids`, "GET", params),
    };
    districts = {
        /**
         * @tags District Controller
         * @name getAll_2
         * @request GET:/districts/all/{since}
         */
        getAll2: (since: number, params?: RequestParams) =>
            this.request<any, DistrictDto[]>(`/districts/all/${since}`, "GET", params),

        /**
         * @tags District Controller
         * @name getByUuids_7
         * @request POST:/districts/query
         */
        getByUuids7: (data: string[], params?: RequestParams) =>
            this.request<any, DistrictDto[]>(`/districts/query`, "POST", params, data),

        /**
         * @tags District Controller
         * @name getAllUuids_4
         * @request GET:/districts/uuids
         */
        getAllUuids4: (params?: RequestParams) => this.request<any, string[]>(`/districts/uuids`, "GET", params),
    };
    eventparticipants = {
        /**
         * @tags Event Participant Controller
         * @name getByUuids_8
         * @request POST:/eventparticipants/query
         */
        getByUuids8: (data: string[], params?: RequestParams) =>
            this.request<any, EventParticipantDto[]>(`/eventparticipants/query`, "POST", params, data),

        /**
         * @tags Event Participant Controller
         * @name getAllEventParticipantsAfter
         * @request GET:/eventparticipants/all/{since}
         */
        getAllEventParticipantsAfter: (since: number, params?: RequestParams) =>
            this.request<any, EventParticipantDto[]>(`/eventparticipants/all/${since}`, "GET", params),

        /**
         * @tags Event Participant Controller
         * @name postEventParticipants
         * @request POST:/eventparticipants/push
         */
        postEventParticipants: (data: EventParticipantDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/eventparticipants/push`, "POST", params, data),

        /**
         * @tags Event Participant Controller
         * @name getAllActiveUuids_3
         * @request GET:/eventparticipants/uuids
         */
        getAllActiveUuids3: (params?: RequestParams) =>
            this.request<any, string[]>(`/eventparticipants/uuids`, "GET", params),
    };
    events = {
        /**
         * @tags Event Controller
         * @name getByUuids_9
         * @request POST:/events/query
         */
        getByUuids9: (data: string[], params?: RequestParams) =>
            this.request<any, EventDto[]>(`/events/query`, "POST", params, data),

        /**
         * @tags Event Controller
         * @name getAllActiveUuids_4
         * @request GET:/events/uuids
         */
        getAllActiveUuids4: (params?: RequestParams) => this.request<any, string[]>(`/events/uuids`, "GET", params),

        /**
         * @tags Event Controller
         * @name getArchivedUuidsSince_1
         * @request GET:/events/archived/{since}
         */
        getArchivedUuidsSince1: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/events/archived/${since}`, "GET", params),

        /**
         * @tags Event Controller
         * @name getDeletedUuidsSince_2
         * @request GET:/events/deleted/{since}
         */
        getDeletedUuidsSince2: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/events/deleted/${since}`, "GET", params),

        /**
         * @tags Event Controller
         * @name getAllEvents
         * @request GET:/events/all/{since}
         */
        getAllEvents: (since: number, params?: RequestParams) =>
            this.request<any, EventDto[]>(`/events/all/${since}`, "GET", params),

        /**
         * @tags Event Controller
         * @name postEvents
         * @request POST:/events/push
         */
        postEvents: (data: EventDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/events/push`, "POST", params, data),
    };
    visitsExternal = {
        /**
         * @tags External Visits Controller
         * @name getVersion
         * @request GET:/visits-external/version
         */
        getVersion: (params?: RequestParams) => this.request<any, string>(`/visits-external/version`, "GET", params),

        /**
         * @tags External Visits Controller
         * @name isValidPersonUuid
         * @request GET:/visits-external/person/{personUuid}/isValid
         */
        isValidPersonUuid: (personUuid: string, params?: RequestParams) =>
            this.request<any, boolean>(`/visits-external/person/${personUuid}/isValid`, "GET", params),

        /**
         * @tags External Visits Controller
         * @name postExternalVisits
         * @request POST:/visits-external
         */
        postExternalVisits: (data: ExternalVisitDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/visits-external`, "POST", params, data),
    };
    facilities = {
        /**
         * @tags Facility Controller
         * @name getByUuids_10
         * @request POST:/facilities/query
         */
        getByUuids10: (data: string[], params?: RequestParams) =>
            this.request<any, FacilityDto[]>(`/facilities/query`, "POST", params, data),

        /**
         * @tags Facility Controller
         * @name getAllByRegion
         * @request GET:/facilities/region/{regionUuid}/{since}
         */
        getAllByRegion: (regionUuid: string, since: number, params?: RequestParams) =>
            this.request<any, FacilityDto[]>(`/facilities/region/${regionUuid}/${since}`, "GET", params),

        /**
         * @tags Facility Controller
         * @name getAllWithoutRegion
         * @request GET:/facilities/general/{since}
         */
        getAllWithoutRegion: (since: number, params?: RequestParams) =>
            this.request<any, FacilityDto[]>(`/facilities/general/${since}`, "GET", params),

        /**
         * @tags Facility Controller
         * @name getAllUuids_5
         * @request GET:/facilities/uuids
         */
        getAllUuids5: (params?: RequestParams) => this.request<any, string[]>(`/facilities/uuids`, "GET", params),
    };
    featureconfigurations = {
        /**
         * @tags Feature Configuration Controller
         * @name getByUuids_11
         * @request POST:/featureconfigurations/query
         */
        getByUuids11: (data: string[], params?: RequestParams) =>
            this.request<any, FeatureConfigurationDto[]>(`/featureconfigurations/query`, "POST", params, data),

        /**
         * @tags Feature Configuration Controller
         * @name getAllFeatureConfigurations
         * @request GET:/featureconfigurations/all/{since}
         */
        getAllFeatureConfigurations: (since: number, params?: RequestParams) =>
            this.request<any, FeatureConfigurationDto[]>(`/featureconfigurations/all/${since}`, "GET", params),

        /**
         * @tags Feature Configuration Controller
         * @name getAllUuids_6
         * @request GET:/featureconfigurations/uuids
         */
        getAllUuids6: (params?: RequestParams) =>
            this.request<any, string[]>(`/featureconfigurations/uuids`, "GET", params),

        /**
         * @tags Feature Configuration Controller
         * @name getDeletedUuids
         * @request GET:/featureconfigurations/deleted/{since}
         */
        getDeletedUuids: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/featureconfigurations/deleted/${since}`, "GET", params),
    };
    info = {
        /**
         * @tags Info Controller
         * @name getVersion_1
         * @request GET:/info/version
         */
        getVersion1: (params?: RequestParams) => this.request<any, string>(`/info/version`, "GET", params),

        /**
         * @tags Info Controller
         * @name getLocale
         * @request GET:/info/locale
         */
        getLocale: (params?: RequestParams) => this.request<any, string>(`/info/locale`, "GET", params),

        /**
         * @tags Info Controller
         * @name getAppUrl
         * @request GET:/info/appurl
         */
        getAppUrl: (query?: { appVersion?: string }, params?: RequestParams) =>
            this.request<any, string>(`/info/appurl${this.addQueryParams(query)}`, "GET", params),

        /**
         * @tags Info Controller
         * @name isCompatibleToApi
         * @request GET:/info/checkcompatibility
         */
        isCompatibleToApi: (query?: { appVersion?: string }, params?: RequestParams) =>
            this.request<any, "COMPATIBLE" | "TOO_OLD" | "TOO_NEW">(
                `/info/checkcompatibility${this.addQueryParams(query)}`,
                "GET",
                params,
            ),
    };
    infrastructure = {
        /**
         * @tags Infrastructure Controller
         * @name getInfrastructureSyncData
         * @request POST:/infrastructure/sync
         */
        getInfrastructureSyncData: (data: InfrastructureChangeDatesDto, params?: RequestParams) =>
            this.request<any, InfrastructureSyncDto>(`/infrastructure/sync`, "POST", params, data),
    };
    outbreaks = {
        /**
         * @tags Outbreak Controller
         * @name getActiveSince
         * @request GET:/outbreaks/active/{since}
         */
        getActiveSince: (since: number, params?: RequestParams) =>
            this.request<any, OutbreakDto[]>(`/outbreaks/active/${since}`, "GET", params),

        /**
         * @tags Outbreak Controller
         * @name getActiveUuids
         * @request GET:/outbreaks/uuids
         */
        getActiveUuids: (params?: RequestParams) => this.request<any, string[]>(`/outbreaks/uuids`, "GET", params),

        /**
         * @tags Outbreak Controller
         * @name getInactiveUuidsSince
         * @request GET:/outbreaks/inactive/{since}
         */
        getInactiveUuidsSince: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/outbreaks/inactive/${since}`, "GET", params),
    };
    pathogentests = {
        /**
         * @tags Pathogen Test Controller
         * @name getByUuids_12
         * @request POST:/pathogentests/query
         */
        getByUuids12: (data: string[], params?: RequestParams) =>
            this.request<any, PathogenTestDto[]>(`/pathogentests/query`, "POST", params, data),

        /**
         * @tags Pathogen Test Controller
         * @name getAllActiveUuids_5
         * @request GET:/pathogentests/uuids
         */
        getAllActiveUuids5: (params?: RequestParams) => this.request<any, string[]>(`/pathogentests/uuids`, "GET", params),

        /**
         * @tags Pathogen Test Controller
         * @name getDeletedUuidsSince_3
         * @request GET:/pathogentests/deleted/{since}
         */
        getDeletedUuidsSince3: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/pathogentests/deleted/${since}`, "GET", params),

        /**
         * @tags Pathogen Test Controller
         * @name getBySampleUuids
         * @request POST:/pathogentests/query/samples
         */
        getBySampleUuids: (data: string[], params?: RequestParams) =>
            this.request<any, PathogenTestDto[]>(`/pathogentests/query/samples`, "POST", params, data),

        /**
         * @tags Pathogen Test Controller
         * @name getAllPathogenTests
         * @request GET:/pathogentests/all/{since}
         */
        getAllPathogenTests: (since: number, params?: RequestParams) =>
            this.request<any, PathogenTestDto[]>(`/pathogentests/all/${since}`, "GET", params),

        /**
         * @tags Pathogen Test Controller
         * @name postPathogenTests
         * @request POST:/pathogentests/push
         */
        postPathogenTests: (data: PathogenTestDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/pathogentests/push`, "POST", params, data),
    };
    persons = {
        /**
         * @tags Person Controller
         * @name getByUuids_13
         * @request POST:/persons/query
         */
        getByUuids13: (data: string[], params?: RequestParams) =>
            this.request<any, PersonDto[]>(`/persons/query`, "POST", params, data),

        /**
         * @tags Person Controller
         * @name getAllPersons
         * @request GET:/persons/all/{since}
         */
        getAllPersons: (since: number, params?: RequestParams) =>
            this.request<any, PersonDto[]>(`/persons/all/${since}`, "GET", params),

        /**
         * @tags Person Controller
         * @name postPersons
         * @request POST:/persons/push
         */
        postPersons: (data: PersonDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/persons/push`, "POST", params, data),

        /**
         * @tags Person Controller
         * @name getAllUuids_7
         * @request GET:/persons/uuids
         */
        getAllUuids7: (params?: RequestParams) => this.request<any, string[]>(`/persons/uuids`, "GET", params),
    };
    pointsofentry = {
        /**
         * @tags Point Of Entry Controller
         * @name getAll_3
         * @request GET:/pointsofentry/all/{since}
         */
        getAll3: (since: number, params?: RequestParams) =>
            this.request<any, PointOfEntryDto[]>(`/pointsofentry/all/${since}`, "GET", params),

        /**
         * @tags Point Of Entry Controller
         * @name getByUuids_14
         * @request POST:/pointsofentry/query
         */
        getByUuids14: (data: string[], params?: RequestParams) =>
            this.request<any, PointOfEntryDto[]>(`/pointsofentry/query`, "POST", params, data),

        /**
         * @tags Point Of Entry Controller
         * @name getAllUuids_8
         * @request GET:/pointsofentry/uuids
         */
        getAllUuids8: (params?: RequestParams) => this.request<any, string[]>(`/pointsofentry/uuids`, "GET", params),
    };
    prescriptions = {
        /**
         * @tags Prescription Controller
         * @name getByUuids_15
         * @request POST:/prescriptions/query
         */
        getByUuids15: (data: string[], params?: RequestParams) =>
            this.request<any, PrescriptionDto[]>(`/prescriptions/query`, "POST", params, data),

        /**
         * @tags Prescription Controller
         * @name getAllActiveUuids_6
         * @request GET:/prescriptions/uuids
         */
        getAllActiveUuids6: (params?: RequestParams) => this.request<any, string[]>(`/prescriptions/uuids`, "GET", params),

        /**
         * @tags Prescription Controller
         * @name postPrescriptions
         * @request POST:/prescriptions/push
         */
        postPrescriptions: (data: PrescriptionDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/prescriptions/push`, "POST", params, data),

        /**
         * @tags Prescription Controller
         * @name getAllPrescriptions
         * @request GET:/prescriptions/all/{since}
         */
        getAllPrescriptions: (since: number, params?: RequestParams) =>
            this.request<any, PrescriptionDto[]>(`/prescriptions/all/${since}`, "GET", params),
    };
    regions = {
        /**
         * @tags Region Controller
         * @name getAll_4
         * @request GET:/regions/all/{since}
         */
        getAll4: (since: number, params?: RequestParams) =>
            this.request<any, RegionDto[]>(`/regions/all/${since}`, "GET", params),

        /**
         * @tags Region Controller
         * @name getByUuids_16
         * @request POST:/regions/query
         */
        getByUuids16: (data: string[], params?: RequestParams) =>
            this.request<any, RegionDto[]>(`/regions/query`, "POST", params, data),

        /**
         * @tags Region Controller
         * @name getAllUuids_9
         * @request GET:/regions/uuids
         */
        getAllUuids9: (params?: RequestParams) => this.request<any, string[]>(`/regions/uuids`, "GET", params),
    };
    samples = {
        /**
         * @tags Sample Controller
         * @name getByUuids_17
         * @request POST:/samples/query
         */
        getByUuids17: (data: string[], params?: RequestParams) =>
            this.request<any, SampleDto[]>(`/samples/query`, "POST", params, data),

        /**
         * @tags Sample Controller
         * @name getAllActiveUuids_7
         * @request GET:/samples/uuids
         */
        getAllActiveUuids7: (params?: RequestParams) => this.request<any, string[]>(`/samples/uuids`, "GET", params),

        /**
         * @tags Sample Controller
         * @name getDeletedUuidsSince_4
         * @request GET:/samples/deleted/{since}
         */
        getDeletedUuidsSince4: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/samples/deleted/${since}`, "GET", params),

        /**
         * @tags Sample Controller
         * @name getAllSamples
         * @request GET:/samples/all/{since}
         */
        getAllSamples: (since: number, params?: RequestParams) =>
            this.request<any, SampleDto[]>(`/samples/all/${since}`, "GET", params),

        /**
         * @tags Sample Controller
         * @name getByCaseUuids
         * @request POST:/samples/query/cases
         */
        getByCaseUuids: (data: string[], params?: RequestParams) =>
            this.request<any, SampleDto[]>(`/samples/query/cases`, "POST", params, data),

        /**
         * @tags Sample Controller
         * @name postSamples
         * @request POST:/samples/push
         */
        postSamples: (data: SampleDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/samples/push`, "POST", params, data),
    };
    tasks = {
        /**
         * @tags Task Controller
         * @name getAll_5
         * @request GET:/tasks/all/{since}
         */
        getAll5: (since: number, params?: RequestParams) =>
            this.request<any, TaskDto[]>(`/tasks/all/${since}`, "GET", params),

        /**
         * @tags Task Controller
         * @name getByUuids_18
         * @request POST:/tasks/query
         */
        getByUuids18: (data: string[], params?: RequestParams) =>
            this.request<any, TaskDto[]>(`/tasks/query`, "POST", params, data),

        /**
         * @tags Task Controller
         * @name getAllActiveUuids_8
         * @request GET:/tasks/uuids
         */
        getAllActiveUuids8: (params?: RequestParams) => this.request<any, string[]>(`/tasks/uuids`, "GET", params),

        /**
         * @tags Task Controller
         * @name postTasks
         * @request POST:/tasks/push
         */
        postTasks: (data: TaskDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/tasks/push`, "POST", params, data),
    };
    treatments = {
        /**
         * @tags Treatment Controller
         * @name getByUuids_19
         * @request POST:/treatments/query
         */
        getByUuids19: (data: string[], params?: RequestParams) =>
            this.request<any, TreatmentDto[]>(`/treatments/query`, "POST", params, data),

        /**
         * @tags Treatment Controller
         * @name getAllActiveUuids_9
         * @request GET:/treatments/uuids
         */
        getAllActiveUuids9: (params?: RequestParams) => this.request<any, string[]>(`/treatments/uuids`, "GET", params),

        /**
         * @tags Treatment Controller
         * @name getAllTreatments
         * @request GET:/treatments/all/{since}
         */
        getAllTreatments: (since: number, params?: RequestParams) =>
            this.request<any, TreatmentDto[]>(`/treatments/all/${since}`, "GET", params),

        /**
         * @tags Treatment Controller
         * @name postTreatments
         * @request POST:/treatments/push
         */
        postTreatments: (data: TreatmentDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/treatments/push`, "POST", params, data),
    };
    users = {
        /**
         * @tags User Controller
         * @name getAll_6
         * @request GET:/users/all/{since}
         */
        getAll6: (since: number, params?: RequestParams) =>
            this.request<any, UserDto[]>(`/users/all/${since}`, "GET", params),

        /**
         * @tags User Controller
         * @name getByUuids_20
         * @request POST:/users/query
         */
        getByUuids20: (data: string[], params?: RequestParams) =>
            this.request<any, UserDto[]>(`/users/query`, "POST", params, data),

        /**
         * @tags User Controller
         * @name getAllUuids_10
         * @request GET:/users/uuids
         */
        getAllUuids10: (params?: RequestParams) => this.request<any, string[]>(`/users/uuids`, "GET", params),
    };
    userroles = {
        /**
         * @tags User Role Config Controller
         * @name getAll_7
         * @request GET:/userroles/all/{since}
         */
        getAll7: (since: number, params?: RequestParams) =>
            this.request<any, UserRoleConfigDto[]>(`/userroles/all/${since}`, "GET", params),

        /**
         * @tags User Role Config Controller
         * @name getAllUuids_11
         * @request GET:/userroles/uuids
         */
        getAllUuids11: (params?: RequestParams) => this.request<any, string[]>(`/userroles/uuids`, "GET", params),

        /**
         * @tags User Role Config Controller
         * @name getDeletedUuids_1
         * @request GET:/userroles/deleted/{since}
         */
        getDeletedUuids1: (since: number, params?: RequestParams) =>
            this.request<any, string[]>(`/userroles/deleted/${since}`, "GET", params),
    };
    visits = {
        /**
         * @tags Visit Controller
         * @name getByUuids_21
         * @request POST:/visits/query
         */
        getByUuids21: (data: string[], params?: RequestParams) =>
            this.request<any, VisitDto[]>(`/visits/query`, "POST", params, data),

        /**
         * @tags Visit Controller
         * @name getAllActiveUuids_10
         * @request GET:/visits/uuids
         */
        getAllActiveUuids10: (params?: RequestParams) => this.request<any, string[]>(`/visits/uuids`, "GET", params),

        /**
         * @tags Visit Controller
         * @name getAllVisits_1
         * @request GET:/visits/all/{since}
         */
        getAllVisits1: (since: number, params?: RequestParams) =>
            this.request<any, VisitDto[]>(`/visits/all/${since}`, "GET", params),

        /**
         * @tags Visit Controller
         * @name postVisits_1
         * @request POST:/visits/push
         */
        postVisits1: (data: VisitDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/visits/push`, "POST", params, data),
    };
    weeklyreports = {
        /**
         * @tags Weekly Report Controller
         * @name getByUuids_22
         * @request POST:/weeklyreports/query
         */
        getByUuids22: (data: string[], params?: RequestParams) =>
            this.request<any, WeeklyReportDto[]>(`/weeklyreports/query`, "POST", params, data),

        /**
         * @tags Weekly Report Controller
         * @name postWeeklyReports
         * @request POST:/weeklyreports/push
         */
        postWeeklyReports: (data: WeeklyReportDto[], params?: RequestParams) =>
            this.request<any, ("OK" | "TOO_OLD" | "ERROR")[]>(`/weeklyreports/push`, "POST", params, data),

        /**
         * @tags Weekly Report Controller
         * @name getAllWeeklyReports
         * @request GET:/weeklyreports/all/{since}
         */
        getAllWeeklyReports: (since: number, params?: RequestParams) =>
            this.request<any, WeeklyReportDto[]>(`/weeklyreports/all/${since}`, "GET", params),

        /**
         * @tags Weekly Report Controller
         * @name getAllUuids_12
         * @request GET:/weeklyreports/uuids
         */
        getAllUuids12: (params?: RequestParams) => this.request<any, string[]>(`/weeklyreports/uuids`, "GET", params),
    };
}
