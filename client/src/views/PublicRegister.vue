<template>
  <div class="public-register-outer-container">
    <a-card class="card">
      <div style="position: relative;">
        <div
          style="
            position: absolute;
            left: 5pt;
            display: flex;
            align-items: center;
            height: 100%;
          "
        >
          <a-button type="primary" icon="home" :ghost="true" @click="gotoHome"
            >Zur Startseite</a-button
          >
        </div>
        <div
          style="display: flex; align-items: center; justify-content: center;"
        >
          <img
            aspect-ratio="1.7"
            src="../assets/logo.png"
            style="vertical-align: middle; height: 100px; margin-right: 25px;"
          />
          <h1 style="margin: 0;">Selbstregistrierung</h1>
        </div>
      </div>
      <div style="margin-top: 35px;">
        <a-steps
          id="scroll-anchor"
          style="margin-bottom: 20px;"
          class="steps"
          @change="(current) => (this.current = current)"
          :current="current"
          :direction="stepsDirection"
        >
          <a-step
            :key="item.title"
            :title="item.title"
            :disabled="
              maxCurrent < steps.findIndex((elem) => elem.title === item.title)
            "
            v-for="item in steps"
          />
        </a-steps>

        <a-form :form="form" layout="vertical">
          <!-- Symptome -->
          <div :style="{ display: current === 0 ? 'block' : 'none' }">
            <h2>
              Welche der folgenden Symptome hatten Sie in den letzten 24h?
            </h2>
            <div style="display: flex;">
              <div>
                <a-form-item style="margin-bottom: 0;">
                  <a-checkbox-group
                    :options="symptoms"
                    class="checkbox-group"
                    v-decorator="['symptoms']"
                  />
                </a-form-item>
                <div class="checkbox-group">
                  <div
                    style="
                      display: flex;
                      align-items: center;
                      align-self: stretch;
                    "
                  >
                    <a-checkbox v-model="showOtherSymptoms">Andere:</a-checkbox>
                    <a-form-item style="flex: 1 1 100%; margin-bottom: 0;">
                      <a-input
                        :disabled="!showOtherSymptoms"
                        v-decorator="['symptomsOther']"
                      />
                    </a-form-item>
                  </div>
                </div>
              </div>
              <span style="flex: 1 1 auto;" />
            </div>
          </div>

          <!-- Exposition -->
          <div :style="{ display: current === 1 ? 'block' : 'none' }">
            <h2>Welche Formen der Exposition treffen auf Sie zu?</h2>
            <div style="display: flex;">
              <div style="display: flex; flex-direction: column;">
                <a-form-item>
                  <a-checkbox-group
                    :options="exposures"
                    class="checkbox-group"
                    v-model="selectedExposures"
                    v-decorator="['exposures']"
                  />
                </a-form-item>
                <a-form-item style="padding-left: 35px;">
                  <a-checkbox-group
                    :disabled="disableExposureLocation"
                    :options="exposureLocation"
                    class="checkbox-group"
                    v-decorator="['exposureLocation']"
                  />
                </a-form-item>
              </div>
              <span style="flex: 1 1 auto;" />
            </div>
          </div>

          <!-- Risiken -->
          <div :style="{ display: current === 2 ? 'block' : 'none' }">
            <h2>
              Welche Vorerkrankungen und Risikofaktoren treffen auf Sie zu?
            </h2>
            <div style="display: flex;">
              <div>
                <a-form-item style="margin-bottom: 0;">
                  <a-checkbox-group
                    :options="preIllnesses"
                    class="checkbox-group"
                    v-decorator="['preIllnesses']"
                  />
                </a-form-item>
                <div class="checkbox-group">
                  <div
                    style="
                      display: flex;
                      align-items: center;
                      align-self: stretch;
                    "
                  >
                    <a-checkbox v-model="showOtherPreIllnesses"
                      >Andere:</a-checkbox
                    >
                    <a-form-item style="flex: 1 1 100%; margin-bottom: 0;">
                      <a-input
                        :disabled="!showOtherPreIllnesses"
                        v-decorator="['preIllnessesOther']"
                      />
                    </a-form-item>
                  </div>
                </div>
              </div>
              <span style="flex: 1 1 auto;" />
            </div>
          </div>

          <!-- Persönliche Daten -->
          <div
            :style="{ display: current === 3 ? 'block' : 'none' }"
            class="data-form"
          >
            <h2>Bitte erfassen Sie nun Ihre persönlichen Daten.</h2>
            <PatientStammdaten :form="form" />
          </div>

          <!-- Finish -->
          <div :style="{ display: current === 4 ? 'block' : 'none' }">
            <div v-if="!createdPatient">
              <h2>
                <a-icon
                  :style="{ fontSize: '36px', color: 'lightgray' }"
                  style="margin-right: 7px;"
                  type="check-circle"
                />
                Abschließen und Daten übermitteln
              </h2>
              <div
                style="
                  margin-bottom: 34px;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                "
              ></div>
              <a-form-item>
                <a-checkbox @change="onCheckedChange">
                  Ich erkläre mich mit der Übermittlung meiner Daten zur
                  weiteren Verarbeitung einverstanden.
                </a-checkbox>
                <span style="color: red;" v-if="showCheckedError"
                  >Bitte bestätigen</span
                >
              </a-form-item>
            </div>
            <div v-if="createdPatient">
              <h2>Geschafft!</h2>
              <div
                style="
                  margin-bottom: 20px;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                "
              >
                <a-icon
                  :style="{ fontSize: '38px', color: '#08c' }"
                  style="margin-right: 25px;"
                  type="check-circle"
                />
                <span>Sie erhalten in Kürze eine Email zur Bestätigung.</span>
              </div>
              <a-button type="primary" @click="gotoHome"
                ><a-icon type="home" /> Zur Startseite</a-button
              >
            </div>
          </div>
        </a-form>
      </div>

      <div class="button-row">
        <a-button
          :style="{
            visibility: current === 0 || createdPatient ? 'hidden' : 'visible',
          }"
          @click="prev"
          block
          class="button-row-button"
          shape="round"
          size="large"
          style="flex: 0 1 150px;"
          type="primary"
        >
          <a-icon type="arrow-left" />
          Zurück
        </a-button>
        <a-button
          v-if="current !== 4"
          @click="next"
          block
          class="button-row-button"
          shape="round"
          size="large"
          style="flex: 0 1 150px;"
          type="primary"
        >
          Weiter
          <a-icon type="arrow-right" />
        </a-button>
        <a-button
          v-if="current === 4"
          @click="save"
          :disabled="!checked"
          block
          class="button-row-button"
          shape="round"
          size="large"
          style="flex: 0 1 150px;"
          type="primary"
        >
          <a-icon>
            <svg
              viewBox="64 64 896 896"
              focusable="false"
              class=""
              data-icon="send"
              width="1em"
              height="1em"
              fill="currentColor"
              aria-hidden="true"
            >
              <path
                d="M931.4 498.9L94.9 79.5c-3.4-1.7-7.3-2.1-11-1.2a15.99 15.99 0 00-11.7 19.3l86.2 352.2c1.3 5.3 5.2 9.6 10.4 11.3l147.7 50.7-147.6 50.7c-5.2 1.8-9.1 6-10.3 11.3L72.2 926.5c-.9 3.7-.5 7.6 1.2 10.9 3.9 7.9 13.5 11.1 21.5 7.2l836.5-417c3.1-1.5 5.6-4.1 7.2-7.1 3.9-8 .7-17.6-7.2-21.6zM170.8 826.3l50.3-205.6 295.2-101.3c2.3-.8 4.2-2.6 5-5 1.4-4.2-.8-8.7-5-10.2L221.1 403 171 198.2l628 314.9-628.2 313.2z"
              />
            </svg>
          </a-icon>
          Absenden
        </a-button>
      </div>
    </a-card>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import Api from '@/api'
import { Patient } from '@/api/ImisSwaggerApi'
import { Option } from '@/models'
import { SYMPTOMS } from '@/models/symptoms'
import { PRE_ILLNESSES } from '@/models/pre-illnesses'
import PatientStammdaten from '@/components/form-groups/PatientStammdaten.vue'
import { EXPOSURE_LOCATIONS, EXPOSURES_PUBLIC } from '@/models/exposures'
import {CaseDataDto, PersonDto, RequestParams, SormasSwaggerApi} from "@/api/SormasSwaggerApi";

interface State {
  form: any
  current: number
  maxCurrent: number
  createdPatient: Patient | null
  symptoms: Option[]
  exposures: Option[]
  selectedExposures: string[]
  exposureLocation: Option[]
  preIllnesses: Option[]
  steps: any[]
  checked: boolean
  showCheckedError: boolean
  showOtherSymptoms: boolean
  showOtherPreIllnesses: boolean
}

export default Vue.extend({
  name: 'PublicRegisterComponent',
  components: {
    PatientStammdaten,
  },
  data(): State {
    return {
      form: this.$form.createForm(this),
      current: 0,
      maxCurrent: 0,
      createdPatient: null,
      symptoms: SYMPTOMS,
      preIllnesses: PRE_ILLNESSES,
      exposures: EXPOSURES_PUBLIC,
      selectedExposures: [],
      exposureLocation: EXPOSURE_LOCATIONS,
      steps: [
        {
          title: 'Symptome',
        },
        {
          title: 'Exposition',
        },
        {
          title: 'Risiken',
          description: 'Vorerkrankungen und Risikofaktoren',
        },
        {
          title: 'Persönliche Daten',
        },
        {
          title: 'Abschließen',
        },
      ],
      checked: false,
      showCheckedError: false,
      showOtherSymptoms: false,
      showOtherPreIllnesses: false,
    }
  },
  computed: {
    stepsDirection() {
      return window.innerWidth >= 700 ? 'horizontal' : 'vertical'
    },
    disableExposureLocation(): boolean {
      return !this.selectedExposures.includes('CONTACT_WITH_CORONA_CASE')
    },
  },
  methods: {
    scrollToFormTop() {
      const anchor = document.getElementById('scroll-anchor') as Element
      anchor.scrollIntoView()

      /*
      // Scroll all parents
      let currElem: Element = scrollAnchor
      let container: Element | null = currElem.parentElement
      while (container) {
        container.scrollTo({ top: currElem.offsetY })

        currElem = container
        container = currElem.parentElement
      }
      */
    },
    prev() {
      this.current--
      this.scrollToFormTop()
    },
    next() {
      const showNext = () => {
        this.current++
        this.maxCurrent = Math.max(this.current, this.maxCurrent)
        this.scrollToFormTop()
      }
      if (this.current === 3) {
        this.form.validateFields((err: any) => {
          if (!err) {
            showNext()
          }
        })
      } else {
        showNext()
      }
    },
    save() {
      if (!this.checked) {
        this.showCheckedError = true
        return
      }
      this.form.validateFields((err: any, values: any) => {
        if (err) {
          console.error(err)
          return
        }

        const oldImisReq = {
          ...values,
          dateOfBirth: values.dateOfBirth.format('YYYY-MM-DD'),
          patientStatus: 'REGISTERED',
          fluImmunization: false, // TODO: Do we need a form field for this?
          speedOfSymptomsOutbreak: '', // TODO: Do we need a form field for this?
          // TODO: exposures is not listed here, so currently we "misuse" risk areas...
          riskAreas: [],
        }

        if (!oldImisReq.symptoms) {
          oldImisReq.symptoms = []
        }
        if (!oldImisReq.preIllnesses) {
          oldImisReq.preIllnesses = []
        }
        if (values.exposures) {
          oldImisReq.riskAreas = oldImisReq.riskAreas.concat(values.exposures)
        }
        if (this.showOtherSymptoms) {
          oldImisReq.symptoms.push(values.symptomsOther)
        }
        if (this.showOtherPreIllnesses) {
          oldImisReq.preIllnesses.push(values.preIllnessesOther)
        }
        if (values.exposureLocation) {
          oldImisReq.riskAreas = oldImisReq.riskAreas.concat(
            values.exposureLocation.map(
              (location: string) => 'CONTACT_WITH_CORONA_' + location
            )
          )
        }
        oldImisReq.weakenedImmuneSystem = !!oldImisReq.preIllnesses // TODO: Do we need this field?
          .find((illness: string) => illness === 'IMMUNODEFICIENCY')
        oldImisReq.coronaContacts = !!oldImisReq.riskAreas // TODO: DO we need this field?
          .find((riskArea: string) =>
            riskArea.startsWith('CONTACT_WITH_CORONA')
          )




          // ==== SORMAS POST ====
          const now = new Date().toISOString().split('.')[0]

          function uuidv4() {
              return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(
                      /[xy]/g,
                      function (c) {
                          const r = (Math.random() * 16) | 0,
                                  v = c == 'x' ? r : (r & 0x3) | 0x8
                          return v.toString(16)
                      }
              ).toUpperCase()
          }

          const personUuid = uuidv4()
          const caseUuid = uuidv4()

          const birthDate = oldImisReq.dateOfBirth.split('-')

          const apiPerson: PersonDto = {
              creationDate: now,
              changeDate: now,
              uuid: personUuid,

              firstName: oldImisReq.firstName,
              lastName: oldImisReq.lastName,
              sex: oldImisReq.gender.toUpperCase(),

              birthdateDD: birthDate[2],
              birthdateMM: birthDate[1],
              birthdateYYYY: birthDate[0]
          }


          const apiCase: CaseDataDto = {
              creationDate: now,
              changeDate: now,
              uuid: caseUuid,
              disease: "CORONAVIRUS",
              person: {
                  uuid: personUuid
              },
              reportDate: now,
              caseClassification: "NOT_CLASSIFIED",
              investigationStatus: "PENDING",
              region: {
                  uuid: "SXAJMX-GJU72R-POK2TS-VR7NKGHY",
              },
              district: {
                  uuid: "UCS4I7-ZGJHFO-X4RRTG-5DXO2BN4"
              },
              healthFacility: {
                  uuid: "W7EFRL-NADHDB-MK4PZ3-6DFIKGRY"
              },
              reportingUser: {
                  uuid: "XZUG2B-SWS5CB-SMNI4T-WZOECAZQ"
              }
          };

          const headers: RequestParams = {
              headers: {
                  "Content-Type": "application/json",
                  'Authorization': 'Basic ' + btoa('SurvOff:SurvOff')
              },

          };

          let sormasSwaggerApi: SormasSwaggerApi = new SormasSwaggerApi<any>();
          sormasSwaggerApi.persons
                  .postPersons([apiPerson], headers)
                  .then(function (r) {
                      console.log("Posted person: " + r)
                  })
                  .then(function (r) {
                      sormasSwaggerApi.cases.postCases([apiCase], headers)
                  })
                  .then(function (r) {
                      console.log("Posted case: " + r)
                  })

      })
    },
    onCheckedChange(e: Event) {
      if (!e?.target) {
        return
      }
      this.checked = (e.target as any).checked
      if (this.checked) {
        this.showCheckedError = false
      }
    },
    gotoHome() {
      this.$router.push({ path: '/' })
    },
  },
})
</script>

<!-- Bitte kein scoped hinzufügen. -->
<!-- Damit die Usability (v.a. auf Touchgeräten) besser ist, -->
<!-- greifen wir etwas in das antdesign css ein. -->
<!-- Mit scoped geht das nicht. Wir verhindern, dass andere Views betroffen -->
<!-- sind, indem wir die Eingriffe nur unterhalb der -->
<!-- Klasse public-register-outer-container gelten lassen. -->
<style lang="scss">
.public-register-outer-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
  background: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)),
    url('../assets/wave-bg.svg');
  background-size: cover;
  color: rgba(255, 255, 255, 0.87);

  .button-row-button {
    box-shadow: 0 3px 3px -2px rgba(0, 0, 0, 0.2),
      0 3px 4px 0 rgba(0, 0, 0, 0.14), 0 1px 8px 0 rgba(0, 0, 0, 0.12);
  }

  .checkbox-group {
    display: flex;
    flex-direction: column;
    align-items: flex-start;

    .ant-checkbox-inner {
      width: 30px;
      height: 30px;
    }

    .ant-checkbox-inner::after {
      width: 8px;
      height: 15px;
      top: 48%;
    }

    .ant-checkbox-wrapper {
      margin: 5px;
      font-size: 18px;
      display: flex;
      align-items: center;
      text-align: left;
    }
  }

  .ant-btn:focus {
    background: #1890ff;
  }

  .card {
    max-width: 80%;
    margin: 4rem auto 0 auto;
    border-radius: 10px;
    box-shadow: 0 8px 9px -5px rgba(0, 0, 0, 0.2),
      0 15px 22px 2px rgba(0, 0, 0, 0.14), 0 6px 28px 5px rgba(0, 0, 0, 0.12);
    border: none;
  }

  .steps {
    text-align: start;
    padding-left: 10pt;
    padding-right: 10pt;
  }

  h1 {
    font-weight: bold;
    color: rgba(0, 0, 0, 0.78);
  }

  h2 {
    color: rgba(0, 0, 0, 0.7);
    margin: 35px 0 25px 0;
  }

  .button-row {
    background: rgb(211, 211, 211);
    display: flex;
    justify-content: space-around;
    padding: 10px 0;
    margin: 15px -24px -24px -24px;
    border-radius: 0 0 10px 10px;
  }

  @media (max-width: 750px) {
    h1 {
      display: none;
    }
    .card {
      max-width: 95%;
      margin: 3rem auto 3rem auto;
    }
    .data-form .ant-form-item-control-wrapper {
      width: 100%;
    }
  }

  @media (min-width: 1200px) {
    .card {
      max-width: 1000px;
      margin: 3rem auto 3rem auto;
    }
  }
}
</style>
