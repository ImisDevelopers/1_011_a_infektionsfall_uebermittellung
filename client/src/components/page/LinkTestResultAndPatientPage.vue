<template>
  <div>
    <a-tabs defaultActiveKey="1" v-model="activeKey">
      <a-tab-pane tab="1. Laboranmeldung" key="1">
        <Login
          @on-login-success="onLoginSuccess"
          :title="
            'Bitte melden Sie sich hier mit Laborkennung und Passwort an um einen Test als positiv / negativ zu kennzeichnen.'
          "
        />
      </a-tab-pane>
      <a-tab-pane
        tab="2. Testresultat zuordnen"
        key="2"
        forceRender
        :disabled="!isLoggedIn"
      >
        <LinkTestResultAndPatient />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import Login from "../laboratory/Login";
import LinkTestResultAndPatient from "../laboratory/LinkTestResultAndPatient";

export default {
  name: "LinkTestResultAndPatientPage",
  components: {
    Login,
    LinkTestResultAndPatient
  },
  data() {
    return {
      activeKey: "1",
      isLoggedIn: false
    };
  },
  methods: {
    onLoginSuccess() {
      this.isLoggedIn = true;
      this.activeKey = "2";
    }
  }
};
</script>

<style></style>
