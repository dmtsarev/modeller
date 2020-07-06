import {Tag} from './tag';

export interface TagType {
  id: number;
  name: string;
  description: string;
  tags: Tag[];
  color: string;
}
