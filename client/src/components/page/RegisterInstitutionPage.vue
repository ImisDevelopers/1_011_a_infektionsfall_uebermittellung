<template>
  <div>
    <div>
      Registrieren Sie hier Ihre eigene Instutition in IMIS.
    </div>

    <a-form
      :form="form"
      :label-col="{ span: 5 }"
      :wrapper-col="{ span: 12 }"
      @submit="handleSubmit"
    >
      <a-form-item label="Typ">
        <a-select
          v-decorator="[
            'institutionType',
            {
              rules: [
                {
                  required: true,
                  message: 'Please select your institution type!'
                }
              ]
            }
          ]"
          placeholder="Bitte wählen..."
        >
          <a-select-option value="LABORATORY">Labor</a-select-option>
          <a-select-option value="DOCTORS_OFFICE">Arztpraxis</a-select-option>
          <a-select-option value="CLINIC">Klinik</a-select-option>
          <a-select-option value="TEST_SITE">Teststelle</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="Name">
        <a-input v-decorator="['name', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="Straße">
        <a-input v-decorator="['street', { rules: [{ required: true }] }]" />
      </a-form-item>
      <a-form-item label="Hausnr.">
        <a-input
          v-decorator="['houseNumber', { rules: [{ required: true }] }]"
        />
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
        <a-input
          v-decorator="['phoneNumber', { rules: [{ required: true }] }]"
        />
      </a-form-item>
      <a-form-item label="Anmerkungen">
        <a-input v-decorator="['comment']" />
      </a-form-item>

      <!-- Datenschutzerklärung Bestätigung-->
      <a-form-item>
        <a-checkbox @change="onCheck"
          >Ich erkläre mich mit der Übermittlung dieser Daten zur weiteren
          Verarbeitung einverstanden.</a-checkbox
        >
      </a-form-item>

      <!-- Submit Button -->
      <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
        <a-button type="primary" html-type="submit">
          Registrieren
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import Api from "../../api/Api";
import { randomizeProperties } from "../../util/randomize";

export default {
  name: "RegisterInstitutionPage",
  data() {
    return {
      form: this.$form.createForm(this, { name: "coordinated" })
    };
  },
  methods: {
    onCheck(e) {
      this.checked = e.target.checked;
    },
    handleSubmit(e) {
      e.preventDefault();

      this.form.validateFields((err, values) => {
        // TODO: Remove this when we go to production
        randomizeProperties(
          [
            "name",
            "email",
            "phoneNumber",
            "street",
            "houseNumber",
            "zip",
            "city"
          ],
          values
        );

        if (this.checked === true) {
          Api.postInstitution(values)
            .then(() => {
              this.$message.info("Created your Institution!");
            })
            .catch(err => this.$message.info(err));
        }
      });
    }
  }
};
</script>

<style scoped></style>
