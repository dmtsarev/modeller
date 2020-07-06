import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';

export interface GitConnection {
  repositoryId: number;
  uri: string;
  username: string;
  password: string;
}

@Injectable({providedIn: 'root'})
export class GitService {


  constructor(private apiService: ApiService) { }

  testConnection(connection: GitConnection): Observable<any> {
    return this.apiService.post('/repository/check', connection);
  }
  saveGitConnection(connection: GitConnection): Observable<GitConnection> {
    return this.apiService.post('/repository', connection);
  }
}
