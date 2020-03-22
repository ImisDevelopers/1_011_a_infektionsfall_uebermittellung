<template>
  <div class="wrapper">
    <div v-if="!response">
      <!--  Anweisungen -->
      <div>
        <h3 :wrapperCol="{span: 22, offset: 2}">
          Nehmen Sie hier neue Patienten auf. Bitte füllen Sie den Bogen vollständig aus. <br>
        </h3>
      </div>

      <a-form :form="form" :layout="'horizontal'" :labelCol="{span: 6}" :wrapperCol="{span: 14, offset: 2}" @submit="handleSubmit">
        <a-row>
          <a-col :md="12">
            <a-form-item label="Nachname">
              <a-input v-decorator="['lastName', { rules: [{ required: true }] }]" />
            </a-form-item>
            <a-form-item label="Vorname">
              <a-input v-decorator="['firstName', { rules: [{ required: true }] }]" />
            </a-form-item>
            <a-form-item label='Geschlecht'>
              <a-radio-group v-decorator="['gender', { rules: [{ required: true }] }]" buttonStyle="solid">
                <a-radio value='male'>Männl.</a-radio>
                <a-radio value='female'>Weibl.</a-radio>
                <a-radio value='divers'>Div.</a-radio>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="Geburtsdatum">
              <a-date-picker :format="dateFormat" v-decorator="['dateOfBirth', { rules: [{ required: true }] }]"
                             placeholder="Datum wählen"
              />
            </a-form-item>
            <a-form-item label="Krankenkasse">
              <a-input v-decorator="['insuranceCompany']" />
            </a-form-item>
            <a-form-item label="Versichertenr.">
              <a-input v-decorator="['insuranceMembershipNumber']" />
            </a-form-item>
          </a-col>
          <a-col :md="12">
            <a-form-item label="E-mail">
              <a-input v-decorator="['email', { rules: [{ required: true }] }]" />
            </a-form-item>
            <a-form-item label="Telefon">
              <a-input v-decorator="['phoneNumber', { rules: [{ required: true }] }]" />
            </a-form-item>
            <a-form-item label="Straße">
              <a-input v-decorator="['street', { rules: [{ required: true }] }]" />
            </a-form-item>
            <a-form-item label="Hausnr.">
              <a-input v-decorator="['houseNumber', { rules: [{ required: true }] }]" />
            </a-form-item>
            <a-form-item label="PLZ">
              <a-input v-decorator="['postalCode', { rules: [{ required: true }] }]" />
            </a-form-item>
            <a-form-item label="Ort">
              <a-input v-decorator="['city', { rules: [{ required: true }] }]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-collapse>
          <a-collapse-panel header="Infektionskette" key="1">
            <a-form-item label="Haben Sie Kontakt mit einem bestätigten Corona-Patienten gehabt?"
                         :labelCol="{div: 24}" :wrapperCol="{div: 24}" >
              <a-radio-group v-decorator="['coronaContacts', { rules: [{ required: false }] }]" >
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item label="Haben Sie sich in einem / mehreren der folgenden Risikogebiete für Coronavirus aufgehalten?"
                         :labelCol="{div: 24}" :wrapperCol="{div: 24}" >
                <span v-for="(riskArea, idx) in RISK_AREAS" :key="idx">
                  <a-col :span="12">
                    <a-checkbox v-model="selectedRiskAreas[riskArea.key]">{{riskArea.description}}</a-checkbox>
                   </a-col>
                </span>
            </a-form-item>
            <!-- ggf. Aufenthaltszeitraum ergänzen -->
          </a-collapse-panel>

          <a-collapse-panel header="Symptome" key="2">
            <a-form-item label="Leiden sie unter einem oder mehreren der folgenden Symptome?"
                         :labelCol="{div: 24}" :wrapperCol="{div: 24}" >
                <span v-for="(symptom, idx) in SYMPTOMS" :key="idx">
                  <a-col :span="12">
                    <a-checkbox v-model="selectedSymptoms[symptom.key]">{{symptom.description}}</a-checkbox>
                   </a-col>
                </span>
            </a-form-item>

            <a-form-item label="Wie schnell sind die Beschwerden aufgetreten?"
                         :labelCol="{div: 24}" :wrapperCol="{div: 24}" >
              <a-radio-group v-decorator="['speedOfSymptomsOutbreak', { rules: [{ required: false }] }]" >
                <a-radio value="suddenly">Plötzlich, innerhalb von einem Tag</a-radio>
                <a-radio value="slow">Langsam, innerhalb von mehreren Tagen</a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item label="Haben Sie für diese Saison eine Influenza-Impfung erhalten?"
                         :labelCol="{div: 24}" :wrapperCol="{div: 24}" >
              <a-radio-group v-decorator="['fluImmunization', { rules: [{ required: false }] }]" >
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
              </a-radio-group>
            </a-form-item>

          </a-collapse-panel>
          <a-collapse-panel header="Vorerkrankungen" key="3">
            <a-form-item label="Haben Sie aufgrund einer Erkrankung ein geschwächtes Immunsystem?"
                         :labelCol="{div: 24}" :wrapperCol="{div: 24}" >
              <a-radio-group v-decorator="['weakenedImmuneSystem', { rules: [{ required: false }] }]" >
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item label="Leiden Sie an einer / mehreren der folgenden Erkrankungen?"
                         :labelCol="{div: 24}" :wrapperCol="{div: 24}" >
                <span v-for="(illness, idx) in ILLNESSES" :key="idx">
                  <a-col :span="12">
                    <a-checkbox v-model="selectedPreIllnesses[illness.key]">{{illness.description}}</a-checkbox>
                   </a-col>
                </span>
            </a-form-item>
          </a-collapse-panel>
        </a-collapse>

        <!-- Datenschutzerklärung Bestätigung-->
        <br>
        <a-form-item>
          <a-checkbox @change="onCheck">Datenschutzerklärung gelesen und verstanden</a-checkbox>
        </a-form-item>

        <!-- Submit Button -->
        <a-form-item :wrapperCol="{ span: 10, offset: 2 }" >
          <a-row>
            <a-col>
              <a-button type="primary" html-type="submit">
                Nichts Anordnen - nur anlegen
              </a-button>
            </a-col>
            <a-col>
              <a-button type="primary" html-type="submit"> <!-- Platzhalter-->
                Quarantäne Anordnen
              </a-button>
            </a-col>
            <a-col>
              <a-button type="primary" html-type="submit" style="background-color: green"> <!-- Platzhalter-->
                 Test+Quarantäne Anordnen
              </a-button>
            </a-col>
          </a-row>
        </a-form-item>
      </a-form>
    </div>
    <div v-else>
      <div>Der Patient wurde erfolgreich registriert.</div>
      <br>
      <div>Die Patienten Id lautet: {{response.id}}</div>
    </div>
  </div>
</template>

<!-- Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new -->

<script>

  const SYMPTOMS = [
    {key: 'SORE_THROAT', description: 'Halsschmerzen'},
    {key: 'HEADACHES', description: 'Kopfschmerzen'},
    {key: 'FATIGUE', description: 'Abgeschlagenheit'},
    {key: 'COUGHT', description: 'Husten'},
    {key: 'COLD', description: 'Schnupfen'},
    {key: 'LIMB_PAIN', description: 'Gliederschmerzen'},
    {key: 'DIARRHEA', description: 'Durchfall'},
    {key: 'SHORTNESS_OF_BREATH', description: 'Luftnot'},
    {key: 'LESS_38_DEG', description: 'Fieber bis 38°C'},
    {key: 'MORE_38_DEG', description: 'Fieber über 38°C'}
  ];

  const ILLNESSES = [
    { key: "HEART_DISEASE", description: "Herzerkrankungen"},
    { key: "LUNG_DISEASE", description: "Lungenerkrankungen"},
    { key: "CIRCULATORY_DISORDER", description: "Kreislauf-/Gefäßerkrankungen"},
    { key: "BLOOD_DISEASE", description: "Bluterkrankungen oder erhöhte Blutungsneigung"},
    { key: "CANCER", description: "Krebserkrankungen"},
    { key: "BONE_DISEASE", description: "Erkrankungen der Knochen, Muskeln oder des Bindegewebes"},
    { key: "MENTAL_ILLNESS", description: "Psychische, neurologische Erkrankungen"},
    { key: "METABOLIC_DISEASE", description: "Stoffwechselerkrankungen (z.B. Diabetes)"},
    { key: "ENTEROPATHY", description: "Magen-/Darmerkrankungen"},
    { key: "HEPATIC_DISEASE", description: "Erkrankungen der Leber oder Gallenwege"},
    { key: "KIDNEY_DISEASE", description: "Nieren-/Harnwegserkrankungen"},
    { key: "OTHER_DISEASE", description: "Andere Vorerkrankungen"}
  ];

  const RISK_AREAS = [
    {key: "IRAN", description: "Iran" },
    {key: "ITALY", description: "Italien" },
    {key: "CHINA", description: "China" },
    {key: "SOUTHCOREA", description: "Südkorea" },
    {key: "FRACE", description: "Frankreich" },
    {key: "AUSTRIA", description: "Österreich" },
    {key: "SPAIN", description: "Spanien" },
    {key: "USA", description: "USA: Kalofornien, Washington oder New York" }
  ];

  export default {
    name: 'PatientDataComponent',
    data() {
      const selectedSymptoms = {};
      SYMPTOMS.forEach(symptom => { selectedSymptoms[symptom.key] = false });

      const selectedPreIllnesses = {};
      ILLNESSES.forEach(illness => { selectedPreIllnesses[illness.key] = false });

      const selectedRiskAreas = {};
      RISK_AREAS.forEach(riskArea => { selectedRiskAreas[riskArea.key] = false });

      return {
        dateFormat: 'DD/MM/YYYY',
        form: this.$form.createForm(this, { name: 'coordinated' }),
        SYMPTOMS,
        selectedSymptoms,
        ILLNESSES,
        selectedPreIllnesses,
        RISK_AREAS,
        selectedRiskAreas,
        response: null
      }
    },
    methods: {
      onCheck(e) {
        this.checked = e.target.checked;
      },
      handleSubmit(e) {
        e.preventDefault();

        const symptoms = SYMPTOMS.filter(symptom => this.selectedSymptoms[symptom.key]).map(symptom => symptom.key);
        const preIllnesses = ILLNESSES.filter(illness => this.selectedPreIllnesses[illness.key]).map(illness => illness.key);
        const riskAreas = RISK_AREAS.filter(riskArea => this.selectedRiskAreas[riskArea.key]).map(riskArea => riskArea.key);

        this.form.validateFields((err, values) => {
          if (!this.checked) {
            // TODO: Show user that this has to be accepted
            console.error("Must accept.");
            return;
          }

          if (!err) {
            const request = {
              ...values,
              dateOfBirth: values['dateOfBirth'].format("YYYY-MM-DD") + " 00",
              symptoms,
              preIllnesses,
              riskAreas
            };

            fetch("/patients", {
              method: "POST",
              body: JSON.stringify(request),
              headers: {
                'Content-Type': 'application/json'
              }})
            .then((response) => response.json())
            .then((data) => {
              this.response = data;
            })
          }
        });
      }
    }
  }
</script>

<style scoped>
  .wrapper {
    text-align: left;
    padding: 24px;
    width: 100%;
  }
</style>