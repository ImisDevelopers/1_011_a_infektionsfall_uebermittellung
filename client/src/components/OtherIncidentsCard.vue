<template>
    <a-card align="left" title="Quarantäne, Hospitalisierung">
        <div v-if='!quarantineIncidents.length'>
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

        <br>

        <div v-if='!hospitalizationIncidents.length'>
            Keine Holpitalisierung.
        </div>
        <div v-for="item in hospitalizationIncidents" :key="item.id">
            Holpitalisierung
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
                    <td>{{ item.intensiveCare ? "Ja" : "Nein" }}</td>
                </tr>
            </table>
        </div>

    </a-card>
</template>

<script lang="ts">
import moment, { Moment } from 'moment'

function getDate(date: string) {
    if (date!==undefined && date!==null && date!=='null')
        return moment(date).format('DD.MM.YYYY')
    else
        return 'Keine Angabe'
}

export default {
    name: 'OtherIncidentsCard',
    props: ['quarantineIncidents', 'hospitalizationIncidents'],
    data () {
        return {
        }
    },
    methods: {
        getDate,
    }
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