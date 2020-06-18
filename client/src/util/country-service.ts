import { CountryDto } from '@/api/ImisSwaggerApi'
import Api from '@/api'

let countries: CountryDto[] = []

export async function getCountries(): Promise<CountryDto[]> {
  if (countries.length > 0) {
    return countries
  } else {
    const countriesBackend = await Api.getCountriesUsingGet()
    countries = countriesBackend
    return countriesBackend
  }
}
