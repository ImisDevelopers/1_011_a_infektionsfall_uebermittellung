<template>
  <a-menu theme="dark" mode="horizontal" :defaultSelectedKeys="['1']">
    <a-menu-item key="1" v-on:click="menumode = 1">
      <router-link to="/register-patient">
        <a-icon type="user-add" />
        <span class="nav-text">Patient Aufnehmen</span>
      </router-link>
    </a-menu-item>

    <a-menu-item key="2" v-on:click="menumode = 2">
      <router-link to="/link-sample-and-patient">
        <a-icon type="deployment-unit" />
        <span class="nav-text">Probe Zuordnen</span>
      </router-link>
    </a-menu-item>

    <a-menu-item key="3" v-on:click="menumode = 3">
      <router-link to="/link-test-result">
        <a-icon type="experiment" />
        <span class="nav-text">Laborresultate</span>
      </router-link>
    </a-menu-item>

    <a-menu-item key="4" v-on:click="menumode = 4">
      <router-link to="/patient-overview">
        <a-icon type="user" />
        <span class="nav-text">Patienten-Daten</span>
      </router-link>
    </a-menu-item>

    <a-menu-item key="5" v-on:click="menumode = 6">
      <router-link to="/all-data">
        <a-icon type="team" />
        <span class="nav-text">Alle Patienten</span>
      </router-link>
    </a-menu-item>

    <a-menu-item key="6" v-on:click="menumode = 7">
      <router-link to="/public-statistics">
        <a-icon type="stock" />
        <span class="nav-text">Öffentliche Statistiken</span>
      </router-link>
    </a-menu-item>

    <a-menu-item key="7" v-on:click="menumode = 7">
      <router-link to="/add-institution">
        <a-icon type="home" />
        <span class="nav-text">Instutition hinzufügen</span>
      </router-link>
    </a-menu-item>
  </a-menu>
</template>

<script>
export default {
  name: "Navigation",
  data: function() {
    return {
      menumode: 1
    };
  },
  methods: {
    onCollapse(collapsed, type) {
      console.log(collapsed, type);
    },
    onBreakpoint(broken) {
      console.log(broken);
    }
  }
};
</script>

<style></style>
