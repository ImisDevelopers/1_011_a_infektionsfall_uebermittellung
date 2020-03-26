<template>
  <a-card style="width: 500px; margin: 2rem auto; min-height: 300px">
    <div v-if="!updatedLabTest">
      <a-form
        :form="form"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
        @submit="handleSubmit"
      >
        <a-form-item label="Test-ID">
          <a-input
            v-decorator="[
              'testId',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte geben Sie Ihre Test-ID ein.'
                  }
                ]
              }
            ]"
            placeholder="z.B 1337-4237-9438"
          />
        </a-form-item>
        <a-form-item label="Testresultat">
          <a-radio-group
            v-decorator="[
              'testResult',
              {
                rules: [
                  {
                    required: true,
                    message: 'Bitte geben Sie das Testresultat an.'
                  }
                ]
              }
            ]"
          >
            <a-radio value="positive">
              Positiv
            </a-radio>
            <a-radio value="negative">
              Negativ
            </a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="Kommentar">
          <a-textarea
            placeholder="Kommentar hinzufügen"
            :autoSize="{ minRows: 3, maxRows: 5 }"
            v-decorator="['comment']"
          />
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 24 }">
          <a-upload
            :accept="'pdf'"
            :beforeUpload="beforeUpload"
            :multiple="false"
          >
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
    </div>
    <div v-else>
      <div>Der Test wurde erfolgreich aktualisiert.</div>
      <br />
      <div>Test ID: {{ updatedLabTest.id }}</div>
      <div>Test Status: {{ updatedLabTest.testStatus }}</div>
    </div>
  </a-card>
</template>

<script>
import Api from "../../api/Api";

export default {
  name: "LinkTestResultAndPatient",
  data() {
    return {
      form: this.$form.createForm(this),
      updatedLabTest: null,
      fileBytes: null
    };
  },
  methods: {
    beforeUpload(file) {
      const setFileBytes = fileBytes => {
        this.fileBytes = fileBytes;
      };

      const reader = new FileReader();
      reader.onload = function(e) {
        const utf8 = unescape(encodeURIComponent(e.target.result));
        const array = [];
        for (let i = 0; i < utf8.length; i++) {
          array.push(utf8.charCodeAt(i));
        }
        setFileBytes(array);
      };
      reader.readAsDataURL(file);

      return false;
    },
    handleSubmit(e) {
      e.preventDefault();

      this.form.validateFields((err, values) => {
        if (err) {
          return;
        }

        const { testId } = values;
        const request = {
          status:
            values.testResult === "positive"
              ? "TEST_POSITIVE"
              : "TEST_NEGATIVE",
          comment: values.comment,
          file: this.fileBytes
        };

        Api.putLabTest(testId, request)
          .then(labTest => {
            this.updatedLabTest = labTest;

            const notification = {
              message: "Testergebnis hinzugefügt.",
              description: "Das Testergebnis wurde erfolgreich hinzugefügt."
            };
            this.$notification["success"](notification);
          })
          .catch(err => {
            const notification = {
              message: "Fehler beim Hinzufügen des Testergebnisses.",
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
