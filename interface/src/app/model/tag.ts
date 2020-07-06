import {TagType} from './tag-type';

export interface Tag {
  id: number;
  name: string;
  description: string;
  type: TagType;
}
