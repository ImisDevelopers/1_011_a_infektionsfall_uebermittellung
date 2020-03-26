<template>
  <a-menu theme="dark" mode="horizontal" :selectedKeys="[key]">
    <a-menu-item v-for="item in items" :key="item.key" @click="onClick">
      <router-link :to="item.url">
        <a-icon :type="item.type" />
        <span class="nav-text">{{ item.text }}</span>
      </router-link>
    </a-menu-item>
  </a-menu>
</template>

<script>
const items = [
  {
    key: "0",
    url: "/register-institution",
    type: "home",
    text: "Instutition Registrieren"
  },
  {
    key: "1",
    url: "/register-patient",
    type: "user-add",
    text: "Patient Registrieren"
  },
  {
    key: "2",
    url: "/link-test-and-patient",
    type: "deployment-unit",
    text: "Probe Zuordnen"
  },
  {
    key: "3",
    url: "/link-test-result",
    type: "experiment",
    text: "Testresultat Zuordnen"
  },
  { key: "4", url: "/patient-overview", type: "user", text: "Patienten-Daten" },
  { key: "5", url: "/all-data", type: "team", text: "Alle Patienten" },
  {
    key: "6",
    url: "/public-statistics",
    type: "stock",
    text: "Öffentliche Statistiken"
  },
];

export default {
  name: "Navigation",
  data() {
    return {
      key: "1",
      items
    };
  },
  methods: {
    onClick(e) {
      this.key = e.key;
    }
  },
  created() {
    const itemIndex = this.items.findIndex(
      item => item.url === window.location.pathname
    );
    if (itemIndex !== -1) {
      this.key = this.items[itemIndex].key;
    } else {
      this.key = "1";
    }
  }
};
</script>

<style></style>
