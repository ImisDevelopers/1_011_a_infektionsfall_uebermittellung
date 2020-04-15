const safeParseResponse = <T = any, E = any>(response: Response): Promise<T> =>
  response
    .json()
    .then((data) => data)
    .catch((e) => response.text)

export interface PlzFields {
  plz: string;
  note: string; // city
}

export interface Plz {
  fields: PlzFields;
}

export async function getPlzs(plz: string): Promise<Plz[]> {
  return fetch('https://public.opendatasoft.com/api/records/1.0/search/?dataset=postleitzahlen-deutschland&facet=plz&q=' + plz, {
    method: 'GET',
  }).then(async (response) => {
    const data = await safeParseResponse<any, any>(response)
    if (!response.ok) throw data
    return data.records
  })
}
