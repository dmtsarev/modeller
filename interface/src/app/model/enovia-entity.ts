import {Tag} from './tag';

export interface EnoviaEntity {
  id: number;
  type: string;
  entityName: string;
  description: string;
  ematrixHTML: string;
  tags: Tag[];
  child?: EnoviaEntity[];
}
