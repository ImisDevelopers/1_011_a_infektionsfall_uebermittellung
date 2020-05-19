export type TestMaterial = 'RACHENABSTRICH' | 'NASENABSTRICH' | 'VOLLBLUT'

export interface TestMaterialItem {
  id: TestMaterial
  label: string
}

export const testMaterials: TestMaterialItem[] = [
  {
    id: 'RACHENABSTRICH',
    label: 'Rachenabstrich',
  },
  {
    id: 'NASENABSTRICH',
    label: 'Nasenabstrich',
  },
  {
    id: 'VOLLBLUT',
    label: 'Vollblut',
  },
]
