import moment, { Moment } from 'moment'

export function getDate(date: string) {
  if (date !== undefined && date !== null && date !== 'null')
    return moment(date).format('DD.MM.YYYY')
  else return 'Keine Angabe'
}
