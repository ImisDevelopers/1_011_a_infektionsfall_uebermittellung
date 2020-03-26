<template>
  <div>
    <a-tabs defaultActiveKey="1" v-model="activeKey">
      <a-tab-pane tab="1. Anmeldung" key="1" :disabled="isLoggedIn">
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
        <LinkTestResultAndPatient :laboratoryId="laboratoryId" />
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
      isLoggedIn: false,
      laboratoryId: ""
    };
  },
  methods: {
    onLoginSuccess(laboratoryId) {
      this.laboratoryId = laboratoryId;
      this.isLoggedIn = true;
      this.activeKey = "2";
    }
  }
};
</script>

<style></style>
