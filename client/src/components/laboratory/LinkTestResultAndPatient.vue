<template>
  <a-card style="width: 500px; margin: 2rem auto; min-height: 300px">
    <a-form
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
      @submit="handleSubmit"
    >
      <a-form-item label="Proben-ID">
        <a-input
          v-decorator="[
            'note',
            {
              rules: [
                { required: true, message: 'Bitte geben Sie Ihre Test-ID ein.' }
              ]
            }
          ]"
          placeholder="z.B 1337-4237-9438"
        />
      </a-form-item>
      <a-form-item label="Testresultat">
        <a-radio-group v-decorator="['radio-group']">
          <a-radio value="true">
            Positiv
          </a-radio>
          <a-radio value="false">
            Negativ
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="Kommentar">
        <a-textarea
          placeholder="Kommentar hinzufügen"
          :autoSize="{ minRows: 3, maxRows: 5 }"
        />
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 24 }">
        <!-- TODO: FILE UPLOAD! -->
        <a-upload action="https://www.mocky.io/v2/5cc8019d300000980a055e76">
          <a-button> <a-icon type="upload" />Test Report hochladen</a-button>
        </a-upload>
      </a-form-item>
      <a-divider />
      <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
        <a-button type="primary" html-type="submit">
          Speichern
        </a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
// TODO: Use form from ant and to get the values from the <form> (see PatientDataCompontent)

export default {
  name: "LinkTestResultAndPatient",
  methods: {
    handleSubmit(e) {
      e.preventDefault();

      // TODO: Send request to connect patient with test result
      console.log("Handle connect patient with test result");

      const patientId = "198424989813";
      const testResult = "negative";

      // TODO: Handle response from server
      const response = { type: "success" };
      const { type } = response;

      // Check notification type (success, info, warning, error)
      const notification = {};
      if (type === "success") {
        notification.message = "Verknüpfung erfolgreich.";
        notification.description = `Patienten ID: ${patientId} & Test Resultat: ${testResult}.`;
      } else if (type === "error") {
        notification.message = "Es is ein Fehler aufgetreten.";
        notification.description = `Patienten ID: ${patientId} & Test Resultat: ${testResult}.`;
      }
      this.$notification[type](notification);
    }
  }
};
</script>

<style></style>
