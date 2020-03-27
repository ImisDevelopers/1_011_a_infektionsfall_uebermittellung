<template>
  <a-card style="width: 500px; margin: 2rem auto; min-height: 300px">
    <div v-if="!createdLabTest">
      <a-form
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
        :colon="false"
        :form="form"
        @submit="handleSubmit"
      >
        <a-form-item label="Patienten-ID">
          <a-input
            v-decorator="[
              'patientId',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte geben Sie die Patienten-ID ein.'
                  }
                ]
              }
            ]"
            placeholder="z.B 43A78F0B"
          />
        </a-form-item>
        <a-form-item label="Test-ID">
          <a-input
            v-decorator="[
              'testId',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte geben Sie die Test-ID ein.'
                  }
                ]
              }
            ]"
            placeholder="z.B ae48-hr43-sk97"
          />
        </a-form-item>
        <a-form-item label="Kommentar">
          <a-textarea
            v-decorator="['comment']"
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
    </div>
    <div v-else>
      <div>Der Test wurde erfolgreich angelegt.</div>
      <br />
      <div>Test ID: {{ createdLabTest.id }}</div>
      <div>Test Status: {{ createdLabTest.testStatus }}</div>
    </div>
  </a-card>
</template>

<script>
import Api from "../../api/Api";

export default {
  name: "LinkTestAndPatient",
  props: {
    laboratoryId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      form: this.$form.createForm(this),
      createdLabTest: null
    };
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();

      this.form.validateFields((err, values) => {
        if (err) {
          return;
        }

        const request = {
          ...values,
          laboratoryId: this.laboratoryId
        };

        Api.postLabTest(request)
          .then(labTest => {
            this.createdLabTest = labTest;

            const notification = {
              message: "Test angelegt und verknüpft.",
              description:
                "Der Test wurde erfolgreich angelegt und mit dem Patienten verknüpft."
            };
            this.$notification["success"](notification);
          })
          .catch(err => {
            const notification = {
              message: "Fehler beim Anlegen des Tests.",
              description: err.message
            };
            this.$notification["error"](notification);
          });
      });
    }
  }
};
</script>

<style></style>
