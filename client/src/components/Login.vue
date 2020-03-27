<template>
  <a-card style="width: 500px; margin: 4rem auto; min-height: 300px;">
    <a-form :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }" :form="form">
      <p class="login-header">Login</p>
      <a-form-item label="Kennung">
        <a-input
            v-decorator="[
            'id',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte geben Sie Ihre Kennung ein.'
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
            'password',
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
            placeholder="**********"
        />
      </a-form-item>
      <a-divider/>
      <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
        <a-button type="primary" html-type="submit" @click="handleLogin">
          Einloggen
        </a-button>
      </a-form-item>
    </a-form>
    <p class="test-acess">
      Testzugang <i><b>1234</b></i> mit Password <i><b>asdf</b></i>
    </p>
  </a-card>
</template>

<script>

export default {
  name: "Login",
  props: {},
  data() {
    return {
      form: this.$form.createForm(this)
    };
  },
  methods: {
    handleLogin(e) {
      e.preventDefault();

      this.form.validateFields((err, values) => {
        if (err) {
          return;
        }

        if (values.id === '1234' && values.password === "asdf") {
          // Query param
          let forwardToPath = this.$route.query.forwardTo;
          if (!forwardToPath) {
            // TODO: Where to forward to when user directly accessed the login page?
            forwardToPath = "/prototype/register-patient";
          }
          localStorage.setItem("user", "test"); // TODO: Call Backend
          this.$router.push({path: forwardToPath});
        } else {
          this.$notification["error"]({
            message: "Login Fehler",
            description: "Kennung und / oder Password nicht korrekt."
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.test-acess {
  color: red;
}

.login-header {
  font-size: 28px;
  font-weight: 300;
}
</style>
