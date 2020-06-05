<template>
  <a-card>
    <a-timeline
      mode="left"
      style="text-align: left; margin-left: 40px;"
      v-if="incidents.length"
    >
      <!-- List all the events recorded corresponding to the patient over time -->
      <a-timeline-item
        :color="timelineColor(incident.eventType)"
        v-for="incident in incidents"
        :key="incident.id + incident.versionTimestamp"
      >
        {{ formatDate(incident.eventDate) }},
        {{ eventTypes.find((type) => type.id === incident.eventType).label }}
        <div v-if="incident.versionUser">
          erfasst {{ formatTimestamp(incident.versionTimestamp) }} durch
          {{ incident.versionUser.institution.name }}
        </div>
        <div v-else>
          erfasst {{ formatTimestamp(incident.versionTimestamp) }}
        </div>
      </a-timeline-item>
    </a-timeline>
  </a-card>
</template>
<script lang="ts">
import Vue from 'vue'
import { Timestamp, PatientLogDto } from '../../api/SwaggerApi'
import moment from 'moment'
import { eventTypes, EventTypeItem } from '../../models/event-types'
import { PatientStatus, Incident } from '../../models/index'

interface State {
  eventTypes: EventTypeItem[]
  incidents: Incident[]
}

export default Vue.extend({
  name: 'History',
  components: {},
  props: {
    allIncidents: {
      type: Object as () => PatientLogDto,
    },
  },
  data(): State {
    return {
      eventTypes,
      incidents: [],
    }
  },
  watch: {
    allIncidents: {
      immediate: true,
      handler(newI: PatientLogDto) {
        this.incidents = Object.values(newI)
          .flat()
          .sort((a: Incident, b: Incident) => {
            return (
              a.eventDate!.localeCompare(b.eventDate!) ||
              a.versionTimestamp!.localeCompare(b.versionTimestamp!)
            )
          })
      },
    },
  },
  methods: {
    formatDate: (date: string) => {
      const momentTimestamp = moment(date)
      if (!momentTimestamp.isValid()) {
        return 'Unbekannt'
      }
      return momentTimestamp.format('DD.MM.YYYY')
    },
    formatTimestamp: (timestamp: Timestamp) => {
      const momentTimestamp = moment(timestamp)
      if (!momentTimestamp.isValid()) {
        return 'Unbekannt'
      }
      return moment(timestamp).format('DD.MM.YYYY HH:mm')
    },
    timelineColor: (eventType: PatientStatus) => {
      switch (eventType) {
        case 'TEST_FINISHED_POSITIVE':
          return 'red'
        case 'TEST_FINISHED_NEGATIVE':
          return 'green'
        default:
          return 'grey'
      }
    },
  },
})
</script>
<style scoped lang="scss"></style>
