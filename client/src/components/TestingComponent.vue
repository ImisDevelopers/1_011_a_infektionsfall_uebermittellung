<template>
  <a-card style="max-width: 1020px">
    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="Stammdaten" key="1">
        <PatientDataComponent />
      </a-tab-pane>
      <a-tab-pane tab="Untersuchungen" key="2" forceRender>
        <a-timeline>
          <!-- List all the events recorded corresponding to the patient over time -->
          <a-timeline-item>2020-03-01 Stammdaten Angelegt</a-timeline-item>
          <a-timeline-item>2020-03-01 Patient als Risikokontakt zur Erstanamnese vorstellig geworden</a-timeline-item>
          <a-timeline-item>2020-03-01 Coronavirus Test: ID-12yt2109t9023810293</a-timeline-item>
          <a-timeline-item
            color="green"
          >2020-03-02 Befund unauffällig (Test: ID-12yt2109t9023810293)</a-timeline-item>
          <a-timeline-item>2020-03-08 Patient aufgrund von akuten Beschwerden erneut vorstellig geworden</a-timeline-item>
          <a-timeline-item>2020-03-08 Coronavirus Test: ID-581a-bqwlkab9031499</a-timeline-item>
          <a-timeline-item color="red">2020-03-09 Befund positiv (Test: ID-581a-bqwlkab9031499)</a-timeline-item>
          <a-timeline-item>2020-03-09 Gesundheitamt Neustadt verhängt Quarantäne</a-timeline-item>
          <a-timeline-item>2020-03-17 Patient zur Coronakontrolluntersuchung vorstellig geworden</a-timeline-item>
          <a-timeline-item
            slot="dot"
            type="clock-circle-o"
            style="font-size: 16px;"
          >2020-03-17 Coronavirus Test: ID-581a-bqwlkab9031499</a-timeline-item>
        </a-timeline>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
//  TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu

import PatientDataComponent from "./PatientDataComponent.vue"; // Stammdatenerhebung nach Vorbild:  https://my.living-apps.de/gateway/apps/5e6b6ac2a94d7e7d40bb4827/new

export default {
  name: "TestingComponent",
  props: {
    msg: String
  },
  data() {
    return {
      entities: [],
      content: ""
    };
  },
  methods: {},
  components: {
    PatientDataComponent
  }
};
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
