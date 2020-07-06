import {Release} from './release';
import {GitConnection} from '../core/services/git.service';

export interface Project {
  projectId: number;
  projectName: string;
  description: string;
  releases: Release[];
  gitPath: string;
  gitConnectionId: number;
  status: string;
  git: GitConnection;
}
