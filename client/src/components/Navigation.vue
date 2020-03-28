<template>
  <a-menu theme="dark" mode="horizontal" :selectedKeys="[key]" v-if="authenticationStore.user">
    <a-menu-item v-for="item in items" :key="item.key" @click="onClick">
      <router-link :to="item.url">
        <a-icon :type="item.type" />
        <span class="nav-text">{{ item.text }}</span>
      </router-link>
    </a-menu-item>
  </a-menu>
</template>

<script>
import { authenticationStore } from "../util/auth";

const items = [
  {
    key: "0",
    url: "/prototype/register-patient",
    type: "user-add",
    text: "Patient Registrieren"
  },
  {
    key: "2",
    url: "/prototype/link-test-and-patient",
    type: "deployment-unit",
    text: "Probe Zuordnen"
  },
  {
    key: "3",
    url: "/prototype/link-test-result",
    type: "experiment",
    text: "Testresultat Zuordnen"
  },
  {
    key: "4",
    url: "/prototype/patient-overview",
    type: "user",
    text: "Patienten-Daten"
  },
  {
    key: "5",
    url: "/prototype/all-data",
    type: "team",
    text: "Alle Patienten"
  },
  {
    key: "6",
    url: "/prototype/public-statistics",
    type: "stock",
    text: "Öffentliche Statistiken"
  },
  {
    key: "7",
    url: "/prototype/register-institution",
    type: "user-add",
    text: "Nutzer hinzufügen"
  },
  {
    key: "8",
    url: "/prototype/login",
    type: "logout",
    text: "Logout"
  },

];

export default {
  name: "Navigation",
  data() {
    return {
      key: "1",
      items,
      authenticationStore: authenticationStore
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
