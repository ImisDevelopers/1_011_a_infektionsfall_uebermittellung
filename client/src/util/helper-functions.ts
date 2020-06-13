import moment from 'moment'
import { Patient, InstitutionImpl } from '../api/SwaggerApi'

export function getDate(date: string) {
  if (date !== undefined && date !== null && date !== 'null')
    return moment(date).format('DD.MM.YYYY')
  else return 'Keine Angabe'
}
