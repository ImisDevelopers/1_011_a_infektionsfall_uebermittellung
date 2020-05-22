<template>
    <p>lalala</p>
</template>

<script lang="ts">
import moment, { Moment } from 'moment'

function getDate(date: string) {
    return moment(date).format('DD.MM.YYYY')
}

export default {
    name: 'OtherIncidentsCard',
    props: ['testIncidents'],
    data () {
        return {
        }
    },
    methods: {
        getDate,
    }
}
</script>

<style scoped>
.card-header {
  padding: 16px 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: rgba(0, 0, 0, 0.85);
  font-weight: 500;
  font-size: 16px;
  border-bottom: 1px solid #e8e8e8;
}
</style>