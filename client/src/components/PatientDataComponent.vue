<template>
  <div>
    <!--  Anweisungen -->
    <div>
      Sehr geehrte Mitbürgerinnen und Mitbürger,
      <br>
      <br>
      Sie füllen diesen Fragebogen aus, weil Sie grippale Beschwerden haben. Von den Beschwerden her lässt sich eine Infektion mit dem Corona-Virus kaum von den weit verbreiteten Erregern der Grippe und Erkältung unterscheiden.
      <br>
      <br>
      Daher möchten wir Sie nicht nur nach Ihren Beschwerden, sondern auch nach kürzlichen Kontakten zu bestätigten COVID-19-Fällen und kürzlichen Reisen fragen. Damit kann die Ärztin bzw. der Arzt besser einschätzen, ob ein Risiko für eine Coronavirus-Infektion besteht.
      <br>
      <br>
      Wir bitten Sie daher, die folgenden Fragen gewissenhaft auszufüllen. Nach dem Abschicken der Befragung erhalten Sie das Ergebnis in Form eines Hinweises über den weiteren Ablauf.
      <br>
    </div>

    <!-- Stammdatenerfassung -->
    <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" @submit="handleSubmit">
      <a-form-item label="Gender">
        <a-select v-decorator="[
            'gender',
            { rules: [{ required: true, message: 'Please select your gender!' }] },
          ]"
          placeholder="Select an option and change input text above"
          >
          <a-select-option value="weiblich">weiblich</a-select-option>
          <a-select-option value="männlich">männlich</a-select-option>
          <a-select-option value="divers">divers</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="Geburtsdatum">
        <a-date-picker :format="dateFormat" />
      </a-form-item>
      <a-form-item label="Name">
        <a-input v-decorator="['name', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="Vorname">
        <a-input v-decorator="['firstName', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="Straße">
        <a-input v-decorator="['street', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="Hausnr.">
        <a-input v-decorator="['houseNumber', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="PLZ">
        <a-input v-decorator="['zip', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="Ort">
        <a-input v-decorator="['city', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="E-mail">
        <a-input v-decorator="['email', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="Telefon">
        <a-input v-decorator="['phone', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="Krankenkasse">
        <a-input v-decorator="['insuranceCompany']" />
      </a-form-item>
      <a-form-item label="Versichertennr.">
        <a-input v-decorator="['insuranceMembershipNumber']" />
      </a-form-item>

      <!-- Teil 2 -->
      <!-- Leiden Sie an Husten? -->
      <a-form-item label="Leiden Sie an Husten?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Leiden Sie an Abgeschlagenheit? -->
      <a-form-item label="Leiden Sie an Abgeschlagenheit?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Leiden Sie an Fieber? -->
      <a-form-item label="Leiden Sie an Fieber?">
        <a-select>
          <a-select-option value="false">Nein, ich habe kein Fieber</a-select-option>
          <a-select-option value="less-38deg">Ja, bis 38°C</a-select-option>
          <a-select-option value="more-38deg">Ja, über 38°C</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Leiden Sie an akuter Luftnot? -->
      <a-form-item label="Leiden Sie an akuter Luftnot?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Leiden Sie an Halsschmerzen oder Halskratzen? -->
      <a-form-item label="Leiden Sie an Halsschmerzen oder Halskratzen?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein, ich habe keine Halsschmerzen</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Leiden Sie an Schnupfen?-->
      <a-form-item label="Leiden Sie an Schnupfen?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein, ich habe keinen Schnupfen</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Leiden Sie an Gliederschmerzen? -->
      <a-form-item label="Leiden Sie an Gliederschmerzen?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein, ich habe keine Gliederschmerzen</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Leiden Sie an Kopfschmerzen? -->
      <a-form-item label="Leiden Sie an Kopfschmerzen?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein, ich habe keine Kopfschmerzen</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Leiden Sie an Durchfall? -->
      <a-form-item label="Leiden Sie an Durchfall?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein, ich habe keinen Durchfall</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Wie schnell sind die Beschwerden aufgetreten? -->
      <a-form-item label="Wie schnell sind die Beschwerden aufgetreten?">
        <a-select>
          <a-select-option value="suddenly">Plötzlich, innerhalb von einem Tag</a-select-option>
          <a-select-option value="slow">Langsam, innerhalb von mehreren Tagen</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Haben Sie sich wirklich in einem Risikogebiet für Coronavirus aufgehalten? -->
      <a-form-item label="Haben Sie sich wirklich in einem Risikogebiet für Coronavirus aufgehalten?">
        <a-select mode="multiple">
          <a-select-option value="Iran">Ja, im Iran</a-select-option>
          <a-select-option value="China">Ja, in China</a-select-option>
          <a-select-option value="Southcorea">Ja, in Südkorea</a-select-option>
          <a-select-option value="France">Ja, in Frankreich</a-select-option>
          <a-select-option value="Austria">Ja, in Österreich</a-select-option>
          <a-select-option value="Spain">Ja, in Spanien</a-select-option>
          <a-select-option value="USA">Ja, in USA: Kalofornien, Washington oder New York</a-select-option>
          <a-select-option value="false">Nein</a-select-option>
        </a-select>
      </a-form-item>

      <!-- ggf. Aufenthaltszeitraum ergänzen -->

      <!-- Haben Sie Kontakt mit einem bestätigten Corona-Patienten gehabt? -->
      <a-form-item label="Haben Sie Kontakt mit einem bestätigten Corona-Patienten gehabt?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Sie sehen eine Liste an Vorerkrankungen. An welchen Erkrankungen leiden Sie? -->
      <a-form-item label="Sie sehen eine Liste an Vorerkrankungen. An welchen Erkrankungen leiden Sie?">
				<a-select mode="multiple">
          <a-select-option value="heart disease">Herzerkrankungen</a-select-option>
          <a-select-option value="lung disease">Lungenerkrankungen</a-select-option>
          <a-select-option value="circulatory disorder">Kreislauf-/Gefäßerkrankungen</a-select-option>
          <a-select-option value="blood disease">Bluterkrankungen oder erhöhte Blutungsneigung</a-select-option>
          <a-select-option value="cancer">Krebserkrankungen</a-select-option>
          <a-select-option value="bone disease">Erkrankungen der Knochen, Muskeln oder des Bindegewebes</a-select-option>
          <a-select-option value="mental illness">Psychische, neurologische Erkrankungen</a-select-option>
          <a-select-option value="metabolic disease">Stoffwechselerkrankungen (z.B. Diabetes)</a-select-option>
          <a-select-option value="enteropathy">Magen-/Darmerkrankungen</a-select-option>
          <a-select-option value="hepatic disease">Erkrankungen der Leber oder Gallenwege</a-select-option>
          <a-select-option value="kidney disease">Nieren-/Harnwegserkrankungen</a-select-option>
          <a-select-option value="other disease">Andere Vorerkrankungen</a-select-option>
          <a-select-option value="false">Keine Vorerkrankungen</a-select-option>
        </a-select>
			</a-form-item>

      <!-- Haben Sie aufgrund einer Erkrankung ein geschwächtes Immunsystem? -->
      <a-form-item label="Haben Sie aufgrund einer Erkrankung ein geschwächtes Immunsystem?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Haben Sie für diese Saison eine Influenza-Impfung erhalten? -->
      <a-form-item label="Haben Sie für diese Saison eine Influenza-Impfung erhalten?">
        <a-select>
          <a-select-option value="true">Ja</a-select-option>
          <a-select-option value="false">Nein</a-select-option>
        </a-select>
      </a-form-item>

      <!-- Datenschutzerklärung Bestätigung-->
      <a-form-item>
				<a-checkbox>Ich erkläre mich mit der Übermittlung meiner Daten zur weiteren Verarbeitung einverstanden.</a-checkbox>
      </a-form-item>

      <!-- Submit Button -->
      <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
        <a-button type="primary" html-type="submit">
          Submit
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<!-- Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new -->

<script>
export default {
  name: 'PatientDataComponent',
  props: {
    msg: String,
  },
  data() {
    return {
      content: "",
      dateFormat: 'DD/MM/YYYY',
      entities: [],
      formLayout: 'horizontal',
      form: this.$form.createForm(this, { name: 'coordinated' }),
      selected: null,
      risk_areas: [
        { value: null, text: 'Please select an option' },
        {"Iran": "Ja, im Iran" },
        {"China": "Ja, in China" },
        {"Southcorea": "Ja, in Südkorea" },
        {"France": "Ja, in Frankreich" },
        {"Austria": "Ja, in Österreich" },
        {"Spain": "Ja, in Spanien" },
        {"USA": "Ja, in USA: Kalofornien, Washington oder New York" },
        {"false": "Nein" },
      ],
      pre_illnesses: [
        {"heart disease": "Herzerkrankungen"},
        {"circulatory disorder": "Kreislauf-/Gefäßerkrankungen"},
        {"lung disease": "Lungenerkrankungen"},
        {"blood disease": "Bluterkrankungen oder erhöhte Blutungsneigung"},
        {"cancer": "Krebserkrankungen"},
        {"bone disease": "Erkrankungen der Knohen, Muskeln oder des Bindegewebes"},
        {"mental illness": "Psychische, neurologische Erkrankungen"},
        {"metabolic disease": "Stoffwechselerkrankungen (z.B. Diabetes)"},
        {"enteropathy": "Magen-/Darmerkrankungen"},
        {"hepatic disease": "Erkrankungen der Leber oder Gallenwege"},
        {"kidney disease": "Nieren-/Harnwegserkrankungen"},
        {"other disease": "Andere Vorerkrankungen"},
        {"false": "Keine Vorerkrankungen"},
      ]
    }
  },
  methods: {
    create(e) {
      e.preventDefault();
      if (this.content.length > 0) {
        fetch('/exampleEntities',
                {
                  method: 'POST',
                  body: JSON.stringify({ content: this.content }),
                  headers: {
                    'Content-Type': 'application/json'
                  }
                })
        .then((response) => {
          return response.json();
        })
        .then((entity) => {
          this.entities.push(entity);
        });
        this.content = "";
      }
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
        }
      });
    },
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 20px 10px;
}
span {
  margin: 10px;
}
</style>
