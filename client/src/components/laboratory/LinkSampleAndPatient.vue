<template>
  <a-card style="width: 500px; margin: 2rem auto; min-height: 300px">
    <a-form
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
      :colon="false"
      @submit="handleSubmit"
    >
      <a-form-item label="Patienten-ID">
        <a-input
          v-decorator="[
            'note',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie die Patienten-ID ein.'
                }
              ]
            }
          ]"
          placeholder="z.B 1337-4237-9438"
        />
      </a-form-item>
      <a-form-item label="Proben-ID">
        <a-input
          v-decorator="[
            'note',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie Ihre Proben-ID ein.'
                }
              ]
            }
          ]"
          placeholder="z.B ae48-hr43-sk97"
        />
      </a-form-item>
      <a-form-item label="Kommentar">
        <a-textarea
          placeholder="Kommentar hinzufügen"
          :autoSize="{ minRows: 3, maxRows: 5 }"
        />
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
  name: "LinkSampleAndPatient",
  methods: {
    handleSubmit(e) {
      e.preventDefault();

      // TODO: Send request to connect patient with sample
      console.log("Handle connect patient with sample");

      const patientId = "198424989813";
      const sampleId = "91029310923";

      // TODO: Handle response from server
      const response = { type: "success" };
      const { type } = response;

      // Check notification type (success, info, warning, error)
      const notification = {};
      if (type === "success") {
        notification.message = "Verknüpfung erfolgreich.";
        notification.description = `Patienten ID: ${patientId} & Proben ID: ${sampleId}.`;
      } else if (type === "error") {
        notification.message = "Es is ein Fehler aufgetreten.";
        notification.description = `Patienten ID: ${patientId} & Proben ID: ${sampleId}.`;
      }
      this.$notification[type](notification);
    }
  }
};
</script>

<style></style>
