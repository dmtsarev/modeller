import {Tag} from './tag';
import {Release} from './release';

export interface EnoviaEntity {
  id: number;
  type: string;
  entityName: string;
  release: Release;
  description: string;
  ematrixHTML: string;
  tags: Tag[];
}
