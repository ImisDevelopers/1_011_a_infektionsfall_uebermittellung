export type TestType = 'PCR' | 'ANTIBODY';

export interface TestTypeItem {
  id: TestType;
  label: string;
  icon: string;
}

export const testTypes: TestTypeItem[] = [
  {
    id: 'ANTIBODY',
    label: 'Antikörper',
    icon: 'search',
  }, {
    id: 'PCR',
    label: 'PCR',
    icon: 'link',
  },
]
