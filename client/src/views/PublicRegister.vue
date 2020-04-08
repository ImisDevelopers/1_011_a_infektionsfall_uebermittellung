<template>
  <div class="public-register-outer-container">
    <a-card class="card">
      <div style="display: flex; align-items: center; justify-content: center">
        <img
          src="../assets/logo.png"
          aspect-ratio="1.7"
          style="vertical-align: middle; height: 100px; margin-right: 25px"
        />
        <h1 style="margin: 0">Selbstregistrierung</h1>
      </div>
      <div style="margin-top: 35px">
        <a-steps :current="current" style="margin-bottom: 20px">
          <a-step v-for="item in steps" :key="item.title" :title="item.title"/>
        </a-steps>

        <a-form :form="form" :layout="'horizontal'"
                :labelCol="{ sm: { span: 8 },  xs: { span: 24 }  }"
                :wrapperCol="{xs: { span: 24 }, sm: { span: 16 }}">

          <!-- Symptome -->
          <div :style="{ display: current === 0 ? 'block' : 'none' }">
            <h2>Welche der folgenden Symptome hatten Sie in den letzten 24h?</h2>
            <div style="display: flex">
              <span style="flex: 1 1 auto"/>
              <a-form-item>
                <a-checkbox-group v-decorator="['symptoms']" :options="symptoms" class="checkbox-group"/>
              </a-form-item>
              <span style="flex: 1 1 auto"/>
            </div>
          </div>

          <!-- Exposition -->
          <div :style="{ display: current === 1 ? 'block' : 'none' }">
            <h2>Welche Formen der Exposition treffen auf Sie zu?</h2>
            <div style="display: flex">
              <span style="flex: 1 1 auto"/>
              <div style="display: flex; flex-direction: column">
                <a-form-item>
                  <a-checkbox-group v-decorator="['exposures']" :options="exposures" class="checkbox-group"
                                    @change="exposuresChanged"/>
                </a-form-item>
                <a-form-item style="padding-left: 35px">
                  <a-checkbox-group v-decorator="['exposureLocation']" :options="exposureLocation"
                                    class="checkbox-group" :disabled="disableExposureLocation"/>
                </a-form-item>
              </div>
              <span style="flex: 1 1 auto"/>
            </div>
          </div>

          <!-- Risiken -->
          <div :style="{ display: current === 2 ? 'block' : 'none' }">
            <h2>Welche Vorerkrankungen und Risikofaktoren treffen auf Sie zu?</h2>
            <div style="display: flex">
              <span style="flex: 1 1 auto"/>
              <a-form-item>
                <a-checkbox-group v-decorator="['preIllnesses']" :options="preIllnesses" class="checkbox-group"/>
              </a-form-item>
              <span style="flex: 1 1 auto"/>
            </div>
          </div>

          <!-- Persönliche Daten -->
          <div :style="{ display: current === 3 ? 'block' : 'none' }" class="data-form">
            <h2>Bitte erfassen Sie nun Ihre persönlichen Daten.</h2>
            <div>
              <a-row>
                <a-col :lg="12" :sm="24">
                  <a-form-item label="Vorname">
                    <a-input
                      v-decorator="['firstName', { rules: [{
                        required: true,
                        message: 'Bitte Vornamen eingeben',
                      }]}]"
                    />
                  </a-form-item>
                  <a-form-item label="Nachname">
                    <a-input
                      v-decorator="['lastName', { rules: [{
                        required: true,
                        message: 'Bitte Nachnamen eingeben'
                      }]}]"
                    />
                  </a-form-item>
                  <a-form-item label="Geschlecht">
                    <a-radio-group
                      v-decorator="['gender', { rules: [{
                        required: true,
                        message: 'Bitte Geschlecht eingeben',
                      }]}]"
                      buttonStyle="solid"
                    >
                      <a-radio value="male">Männl.</a-radio>
                      <a-radio value="female">Weibl.</a-radio>
                      <a-radio value="divers">Div.</a-radio>
                    </a-radio-group>
                  </a-form-item>
                  <a-form-item label="Geburtsdatum">
                    <a-date-picker
                      :format="dateFormat"
                      v-decorator="['dateOfBirth', { rules: [{
                        required: true,
                        message: 'Bitte Geburtsdatum eingeben',
                      }]}]"
                      placeholder="Datum wählen"
                    />
                  </a-form-item>
                  <a-form-item label="Krankenkasse (optional)">
                    <a-input v-decorator="['insuranceCompany']"/>
                  </a-form-item>
                  <a-form-item label="Versichertenr. (optional)">
                    <a-input v-decorator="['insuranceMembershipNumber']"/>
                  </a-form-item>
                </a-col>
                <a-col :lg="12" :sm="24">
                  <a-form-item label="E-mail">
                    <a-input
                      v-decorator="['email', { rules: [{
                        required: true,
                        message: 'Bitte Email eingeben',
                      }] }]"
                    />
                  </a-form-item>
                  <a-form-item label="Telefon">
                    <a-input
                      v-decorator="[
                      'phoneNumber',
                      { rules: [{
                        required: true,
                        message: 'Bitte Telefonnummer eingeben',
                      }]}
                    ]"
                    />
                  </a-form-item>
                  <a-form-item label="Straße">
                    <a-input
                      v-decorator="['street', { rules: [{
                        required: true,
                        message: 'Bitte Straße eingeben',
                      }] }]"
                    />
                  </a-form-item>
                  <a-form-item label="Hausnr.">
                    <a-input
                      v-decorator="['houseNumber', { rules: [{
                        required: true,
                        message: 'Bitte Hausnummer eingeben',
                      }]}]"
                    />
                  </a-form-item>
                  <a-form-item label="PLZ">
                    <a-input
                      v-decorator="['zip', { rules: [{
                        required: true,
                        message: 'Bitte PLZ eingeben',
                      }] }]"
                    />
                  </a-form-item>
                  <a-form-item label="Ort">
                    <a-input
                      v-decorator="['city', { rules: [{
                        required: true,
                        message: 'Bitte Ort eingeben',
                      }] }]"
                    />
                  </a-form-item>
                </a-col>
              </a-row>
            </div>
          </div>

          <!-- Finish -->
          <div :style="{ display: current === 4 ? 'block' : 'none' }">
            <div v-if="!createdPatient">
              <h2>Sie haben es fast geschafft!</h2>
              <a-form-item>
                <a-checkbox @change="onCheckedChange">
                  Ich erkläre mich mit der Übermittlung meiner Daten zur weiteren
                  Verarbeitung einverstanden.
                </a-checkbox>
                <span v-if="showCheckedError" style="color: red">Bitte bestätigen</span>
              </a-form-item>
              <a-button
                style="width: 200px; margin-bottom: 25px;"
                type="primary"
                shape="round"
                size="large"
                block
                @click="save"
              >
                <a-icon type="save"/>
                Daten übermitteln
              </a-button>
            </div>
            <div v-if="createdPatient">
              <h2>Geschafft!</h2>
              <div style="margin-bottom: 20px; display: flex; align-items: center; justify-content: center">
                <a-icon type="check-circle" :style="{ fontSize: '38px', color: '#08c' }"
                        style="margin-right: 25px"/>
                <span>Sie erhalten in Kürze eine Email zur Bestätigung.</span>
              </div>
            </div>
          </div>

        </a-form>

      </div>

      <div class="button-row">
        <a-button
          style="flex: 0 1 150px;"
          type="primary"
          shape="round"
          size="large"
          block
          :style="{ visibility: current === 0 || createdPatient ? 'hidden' : 'visible' }"
          @click="prev"
        >
          <a-icon type="arrow-left"/>
          Zurück
        </a-button>
        <a-button
          style="flex: 0 1 150px;"
          type="primary"
          shape="round"
          size="large"
          block
          :style="{ visibility: current === 4 ? 'hidden' : 'visible' }"
          @click="next"
        >
          Weiter
          <a-icon type="arrow-right"/>
        </a-button>
      </div>
    </a-card>

  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import Api from '@/api'
import { Patient } from '@/api/SwaggerApi'

interface State {
  form: any;
  current: number;
  dateFormat: string;
  createdPatient: Patient | null;
  symptoms: string[];
  exposures: string[];
  preIllnesses: string[];
  steps: any[];
  checked: boolean;
  showCheckedError: boolean;
  exposureLocation: string[];
  disableExposureLocation: boolean;
}

export default Vue.extend({
  name: 'PublicRegisterComponent',
  data(): State {
    return {
      form: this.$form.createForm(this),
      current: 0,
      dateFormat: 'DD/MM/YYYY',
      createdPatient: null,
      symptoms: [
        'Appetit- oder Gewichtsverlust',
        'Verlust des Geruchs- oder Geschmackssinnes',
        'Fieber',
        'Schmerzen',
        'Kopfschmerzen',
        'Muskelschmerzen',
        'Übelkeit',
        'Husten',
        'Atembeschwerden',
        'Atemnot',
        'Schnupfen',
      ],
      exposures: [
        'Medizinischer Heilberuf',
        'Arbeit in medizinischem Labor',
        'Aufenthalt in medizinischer Einrichtung in den letzten 14 Tagen vor der Erkrankung',
        'Enger Kontakt mit wahrscheinlichem oder bestätigtem Fall in den letzten 14 Tagen vor der Erkrankung',
      ],
      exposureLocation: [
        'in einer medizinischen Einrichtung',
        'im privaten Haushalt',
        'am Arbeitsplatz',
        'andere / sonstige',
      ],
      preIllnesses: [
        'Herz-Kreislauf (incl. Bluthochdruck)',
        'Diabetes',
        'Lebererkrankung',
        'neurologische / neuromuskuläre Erkrankung',
        'Immundefizit inkl. HIV',
        'Nierenerkrankung',
        'Chronische Lungenerkrankung (z.B. COPD)',
        'Krebserkrankung',
        'Schwangerschaft',
        'Trimester',
        'Postpartum (weniger als 6 Wochen)',
      ],
      steps: [
        {
          title: 'Symtpome',
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
      ],
      checked: false,
      showCheckedError: false,
      disableExposureLocation: true,
    }
  },
  methods: {
    prev() {
      this.current--
    },
    next() {
      if (this.current === 3) {
        this.form.validateFields((err: any) => {
          if (!err) {
            this.current++
          }
        })
      } else {
        this.current++
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
        console.log('Sending data:')
        console.log(values)

        const request = {
          ...values,
          dateOfBirth: values.dateOfBirth.format('YYYY-MM-DD') + ' 00',
          patientStatus: 'REGISTERED',
          fluImmunization: false, // TODO: Do we need a field for this?
          speedOfSymptomsOutbreak: '', // TODO: Do we need a field for this?
          coronaContacts: false, // TODO: Do we need a field for this?
          // TODO: exposures is not listed here
          riskAreas: [], // TODO: Do we need a field for this?
          weakenedImmuneSystem: false, // TODO: Do we need a field for this?
          riskOccupation: '', // TODO: Do we need a field for this?
        }
        if (!request.symptoms) {
          request.symptoms = []
        }
        if (!request.preIllnesses) {
          request.preIllnesses = []
        }
        Api.patients.addPatientUsingPost(request).then(patient => {
          this.createdPatient = patient
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
    exposuresChanged(checkedValues: string[]) {
      this.disableExposureLocation = !checkedValues.includes(this.exposures[3])
      console.log('Set to ' + this.disableExposureLocation)
    },
  },
})
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss">

  .public-register-outer-container {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    overflow: auto;
    background: linear-gradient(
        rgba(0, 0, 0, 0.3),
        rgba(0, 0, 0, 0.3)
    ), url("../assets/wave-bg.svg");
    background-size: cover;
    color: rgba(255, 255, 255, 0.87);

    .ant-form-item-control-wrapper {
      width: 100%;
    }

    .data-form .ant-form-item-control-wrapper {
      width: 66%;
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
      background: #1890ff
    }

    .card {
      max-width: 80%;
      margin: 4rem auto 0 auto;
      border-radius: 10px;
      box-shadow: 0 8px 9px -5px rgba(0, 0, 0, .2), 0 15px 22px 2px rgba(0, 0, 0, .14), 0 6px 28px 5px rgba(0, 0, 0, .12);
      border: none;
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
      .ant-steps {
        min-height: 1px;
        display: flex;
        justify-content: space-around;
      }
      .ant-steps-item-content {
        display: none !important;
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
