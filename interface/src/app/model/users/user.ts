import {Project} from '../project';
import {Role} from './role';

export interface User {
  id: number;
  userName: string;
  role: Role;
  project: Project;
}
