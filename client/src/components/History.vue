<template>
    
    <a-card>
        <a-timeline
            mode="left"
            style="text-align: left; margin-left: 40px;"
            v-if="allIncidents.length"
        >
            <!-- List all the events recorded corresponding to the patient over time -->
            <a-timeline-item
            :color="timelineColor(incident.eventType)"
            v-for="incident in allIncidents"
            :key="incident.id + incident.versionTimestamp"
            >
            {{ formatDate(incident.eventDate) }},
            {{
                eventTypes.find((type) => type.id === incident.eventType)
                .label
            }}
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
import { Incident, Timestamp } from '../api/SwaggerApi'
import moment from 'moment'
import { eventTypes } from '@/models/event-types'


function formatTimestamp(timestamp: Timestamp): string {
      const momentTimestamp = moment(timestamp)
      if (momentTimestamp.isValid()) {
        return moment(timestamp).format('DD.MM.YYYY HH:mm')
      } else {
        return 'Unbekannt'
      }
    }
function formatDate(date: string): string {
    const momentTimestamp = moment(date)
    if (momentTimestamp.isValid()) {
    return momentTimestamp.format('DD.MM.YYYY')
    } else {
    return 'Unbekannt'
    }
}
function timelineColor(eventType: any) {
    switch (eventType) {
    case 'TEST_FINISHED_POSITIVE':
        return 'red'
    case 'TEST_FINISHED_NEGATIVE':
        return 'green'
    default:
        return 'grey'
    }
}

export default Vue.extend({
  name: 'History',
  components: {
  },
  props: ['allIncidents'],
  data() {
    return {
        eventTypes
    }
  },
  methods: {
      formatDate,
      formatTimestamp,
      timelineColor
  },
})
</script>
<style scoped lang="scss">
</style>
