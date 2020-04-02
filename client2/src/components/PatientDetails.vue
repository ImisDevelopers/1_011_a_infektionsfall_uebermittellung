<template style="margin: auto">
  <div>
    <a-input-search placeholder="Suche Patienten" style="width: 100%; max-width: 1020px"/>
    <div style="max-width: 1020px; margin: auto">
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="Stammdaten" key="1">
          <!-- display user data here-->
          <div>
            <a-row :gutter="8">
              <a-col span="8">
                <a-card title="Allgemein" bordered="false" align="left" :extra="uuid">
                  <table style="border-collapse: separate; border-spacing:15px">
                    <tr>
                      <td>Vorname:</td><td>{{patient.firstName}}</td>
                    </tr>
                    <tr>
                      <td>Nachname:</td><td>{{patient.lastName}}</td>
                    </tr>
                    <tr>
                      <td>Geburtsdatum:</td><td>{{patient.dateOfBirth}}</td>
                    </tr>
                    <tr>
                      <td>Geschlecht:</td><td>{{patient.gender}}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
              <a-col span="8">
                <a-card title="Adresse" bordered="false" align="left">
                  <table style="border-collapse: separate; border-spacing:15px">
                    <tr>
                      <td>Straße:</td><td>{{patient.street}}</td>
                    </tr>
                    <tr>
                      <td>Hausnummer:</td><td>{{patient.houseNumber}}</td>
                    </tr>
                    <tr>
                      <td>PLZ:</td><td>{{patient.zip}}</td>
                    </tr>
                    <tr>
                      <td>Ort:</td><td>{{patient.city}}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
              <a-col span="8">
                <a-card title="Kontakt & Versicherung" bordered="false" align="left">
                  <table style="border-collapse: separate; border-spacing:15px">
                    <tr>
                      <td>Telefonnummer:</td><td>{{patient.phoneNumber}}</td>
                    </tr>
                    <tr>
                      <td>Email:</td><td><a href="">{{patient.email}}</a></td>
                    </tr>
                    <tr>
                      <td>Versicherung:</td><td>{{insuranceCompany}}</td>
                    </tr>
                    <tr>
                      <td>V-Nr:</td><td>{{insuranceMembershipNumber}}</td>
                    </tr>
                  </table>
                </a-card>
              </a-col>
            </a-row>
            <a-row :gutter="8" style="margin-top: 8px;">
              <a-col span="24">
                <a-card title="Status: Genesen" align="left">
                  <a-row :gutter="8" style="margin-top: 8px;">
                    <a-col span="6">
                      Erstaufnahme:
                    </a-col>
                    <a-col span="6">
                      10.3.2020
                    </a-col>
                    <a-col span="6">
                      Testdatum:
                    </a-col>
                    <a-col span="6">
                      22.3.2020
                    </a-col>
                  </a-row>
                  <a-divider />
                  <a-row type="flex" justify="end" :gutter="8" style="margin-top: 8px;">
                    <a-col>
                      <a-button type="primary" @click="requestTestAgain('success')">Test erneut anordnen</a-button>
                    </a-col>
                  </a-row>
                </a-card>
              </a-col>
            </a-row>
          </div>
          <br>
          <br>
          <!--<a-table :columns="columns" :dataSource="data"> </a-table>-->
        </a-tab-pane>
        <a-tab-pane tab="Verlauf" key="2" forceRender>
          <a-card>
            <a-timeline style="text-align: left; margin-left: 40px">
              <!-- List all the events recorded corresponding to the patient over time -->
              <a-timeline-item>10.03.2020 Patient aufgenommen, Test angeordnet</a-timeline-item>
              <a-timeline-item>11.03.2020 Coronavirus Test: ID-12yt2109t9023810293</a-timeline-item>
              <a-timeline-item color="red">11.03.2020 Befund positiv (ID-12yt2109t9023810293)</a-timeline-item>
              <a-timeline-item>11.03.2020 Gesundheitamt Neustadt verhängt Quarantäne</a-timeline-item>
              <a-timeline-item>15.03.2020 Erneute Testanordnung nach Quarantäne</a-timeline-item>
              <a-timeline-item>15.03.2020 Coronavirus Test: ID-12yt2109t1523810293</a-timeline-item>
              <a-timeline-item color="green">16.03.2020 Befund unauffällig (ID-12yt2109t1523810293)</a-timeline-item>
              <a-timeline-item>21.03.2020 Erneute Testanordnung nach Quarantäne</a-timeline-item>
              <a-timeline-item>21.03.2020 Coronavirus Test: ID-12yt2109t152ab10293</a-timeline-item>
              <a-timeline-item color="green">22.03.2020 Befund unauffällig (ID-12yt2109t152ab10293)</a-timeline-item>
              <a-timeline-item color="green">22.03.2020 Patient genesen</a-timeline-item>
            </a-timeline>
          </a-card>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script>

export default {
  name: 'PatientDetail',
  props: {
    patient: {
      type: Object,
      default: {},
    },
  },
  methods: {
    requestTestAgain (type) {
      // const patID = "12389384"

      // Check notification type (success, info, warning, error)
      if (type === 'success') {
        var notification = {
          message: 'Der Test wurde erneut angefordert.',
          // description:
          // `Patienten ID: ${patID}`,
        }
      }

      // Show notification
      this.$notification[type](notification)
    },
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h3 {
    margin: 20px 10px;
  }
  span {
    margin: 10px;
  }
</style>
