<template>
  <a-card style="width: 500px; margin: 2rem auto; min-height: 300px">
    <a-form :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <p>{{ title }}</p>
      <a-form-item label="Laborkennung">
        <a-input
          v-decorator="[
            'note',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie Ihre Laborkennung ein.'
                }
              ]
            }
          ]"
          placeholder="z.B 1337-4237-9438"
        />
      </a-form-item>
      <a-form-item label="Passwort">
        <a-input
          v-decorator="[
            'note',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie Ihr Passwort ein.'
                }
              ]
            }
          ]"
          type="password"
        />
      </a-form-item>
      <a-divider />
      <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
        <a-button type="primary" html-type="submit" @click="handleLogin">
          Einloggen
        </a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
// TODO: Use form from ant and to get the values from the <form> (see PatientDataCompontent)

export default {
  name: "Login",
  props: {
    title: String
  },
  methods: {
    handleLogin(e) {
      e.preventDefault();

      // TODO: Send login request
      console.log("Handle login.");

      this.$emit("on-login-success");
    }
  }
};
</script>

<style></style>
