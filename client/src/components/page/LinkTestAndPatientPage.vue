<template>
  <div>
    <a-tabs defaultActiveKey="1" v-model="activeKey">
      <a-tab-pane tab="1. Laboranmeldung" key="1" :disabled="isLoggedIn">
        <Login
          @on-login-success="onLoginSuccess"
          :title="
            'Bitte melden Sie sich hier mit Laborkennung und Passwort an um einen Test einem Patienten zuzuordnen.'
          "
        />
      </a-tab-pane>
      <a-tab-pane
        tab="2. Test zuordnen"
        key="2"
        forceRender
        :disabled="!isLoggedIn"
      >
        <LinkTestAndPatient :laboratoryId="laboratoryId" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import Login from "../laboratory/Login";
import LinkTestAndPatient from "../laboratory/LinkTestAndPatient";

export default {
  name: "LinkTestAndPatientPage",
  components: {
    Login,
    LinkTestAndPatient
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

<style scoped></style>
