export type TestType = 'PCR' | 'ANTIBODY';

export interface TestTypeItem {
  id: TestType;
  label: string;
}

export const testTypes: TestTypeItem[] = [
  {
    id: 'ANTIBODY',
    label: 'Antikörper',
  }, {
    id: 'PCR',
    label: 'PCR',
  },
]
