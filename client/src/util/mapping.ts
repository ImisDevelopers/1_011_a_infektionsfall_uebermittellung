export declare interface MapperDefEntry {
  transform: (x: any) => any;
}
export declare type MapperDef = { [x: string]: ((x: any) => any | Partial<MapperDefEntry>) };

export function map(object: {[x: string]: any}, mapperDef: MapperDef) {
  return Object.fromEntries(Object.entries(object).map(
    (entry: [any, any]) => {
      const key = entry[0];
      let val = entry[1];

      let mapperEntry: any = mapperDef[key]

      if (mapperEntry) {
        if (typeof mapperEntry === 'function') {
          mapperEntry = { transform: mapperEntry as (value: any) => any }
        }

        if (Object.prototype.hasOwnProperty.call(mapperEntry, 'transform')) {
          val = mapperEntry.transform(val)
        }

        entry = [key, val]
      }

      return entry
    },
  ))
}
