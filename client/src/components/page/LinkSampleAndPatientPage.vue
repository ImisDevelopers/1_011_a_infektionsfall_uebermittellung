<template>
  <div>
    <a-tabs defaultActiveKey="1" v-model="activeKey">
      <a-tab-pane tab="1. Laboranmeldung" key="1">
        <Login
          @on-login-success="onLoginSuccess"
          :title="
            'Bitte melden Sie sich hier mit Kennung und Passwort an um eine Probe einem Patienten zuzuordnen.'
          "
        />
      </a-tab-pane>
      <a-tab-pane
        tab="2. Probe zuordnen"
        key="2"
        forceRender
        :disabled="!isLoggedIn"
      >
        <LinkSampleAndPatient />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import Login from "../laboratory/Login";
import LinkSampleAndPatient from "../laboratory/LinkSampleAndPatient";

export default {
  name: "LinkSampleAndPatientPage",
  components: {
    Login,
    LinkSampleAndPatient
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

<style scoped></style>
