import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Project} from '../../model/project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private apiService: ApiService) { }
  getProjects(): Observable<Project[]> {
    return this.apiService.get('/projects');
  }
  getProjectById(projectId: number): Observable<Project> {
    return this.apiService.get('/projects/' + projectId);
  }
  saveProject(project: Project): Observable<Project> {
    return this.apiService.post('/projects', project);
  }
}
