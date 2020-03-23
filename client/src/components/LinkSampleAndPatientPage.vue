<template>
  <div>
    <a-tabs defaultActiveKey="1" v-model="activeKey">
      <a-tab-pane tab="1. Anmeldung" key="1">
        <Login @on-login-success="onLoginSuccess" />
      </a-tab-pane>
      <a-tab-pane tab="2. Probe zuordnen" key="2" forceRender>
        <LinkSampleAndPatient />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

// Labs erstellen Berichte zu Tests/ProbeIDs

<script>
import Login from "./laboratory/Login";
import LinkSampleAndPatient from "./laboratory/LinkSampleAndPatient";

export default {
  name: "LinkSampleAndPatientPage",
  components: {
    Login,
    LinkSampleAndPatient
  },
  data() {
    return {
      content: "",
      activeKey: "1",
      comment: "",
      headers: {
        authorization: "authorization-text"
      }
    };
  },
  methods: {
    onLoginSuccess() {
      console.log("HIER");
      this.isLoggedIn = true;
      this.activeKey = "2";
    },
    handleLink(e) {
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
    },
    handleChange({ file, fileList }) {
      if (file.status !== "uploading") {
        console.log(file, fileList);
      }
    }
  }
};
</script>

<style scoped></style>
