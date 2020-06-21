<template>
  <a-card align="left" title="Quarantäne, Hospitalisierung">
    <div v-if="!quarantineIncidents.length">
      Keine Quarantäne.
    </div>
    <div v-for="item in quarantineIncidents" :key="item.id">
      Quarantäne
      <table>
        <tr>
          <td class=".list-item">- Beginn:</td>
          <td>{{ getDate(item.eventDate) }}</td>
        </tr>
        <tr>
          <td>- Ende:</td>
          <td>{{ getDate(item.until) }}</td>
        </tr>
      </table>
    </div>

    <br />

    <div v-if="!hospitalizationIncidents.length">
      Keine Hospitalisierung.
    </div>
    <div v-for="item in hospitalizationIncidents" :key="item.id">
      Hospitalisierung
      <table>
        <tr>
          <td>- Beginn:</td>
          <td>{{ getDate(item.eventDate) }}</td>
        </tr>
        <tr>
          <td>- Ende:</td>
          <td>{{ getDate(item.releasedOn) }}</td>
        </tr>
        <tr>
          <td>- Intensiv:</td>
          <td>{{ item.intensiveCare ? 'Ja' : 'Nein' }}</td>
        </tr>
      </table>
    </div>
  </a-card>
</template>

<script lang="ts">
import { getDate } from '../../util/helper-functions'
import {
  HospitalizationIncident,
  QuarantineIncident,
} from '../../api/ImisSwaggerApi'

export default {
  name: 'QuarantineHospitalizationCard',
  props: {
    hospitalizationIncidents: {
      type: Array as () => HospitalizationIncident[],
    },
    quarantineIncidents: {
      type: Array as () => QuarantineIncident[],
    },
  },
  methods: {
    getDate,
  },
}
</script>

<style scoped lang="scss">
.list-item {
  border-left: 5em;
}
table {
  border-collapse: separate;
  border-spacing: 5px;

  tr {
    padding: 15px;

    td:first-of-type {
      padding-right: 15px;
    }
  }
}
table.compact {
  border-spacing: 15px 3px;
}
</style>
