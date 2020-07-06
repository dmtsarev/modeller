import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Release} from '../../model/release';
import {HttpParams} from '@angular/common/http';

@Injectable()
export class ReleaseService {

  constructor(private apiService: ApiService) { }
  getReleases(projectId: number): Observable<Release[]> {
    const params = new HttpParams().set('projectId', projectId.toString());
    return this.apiService.get('/releases', params);
  }
  getRelease(releaseId: number): Observable<Release>  {
    return this.apiService.get('/releases/' + releaseId);
  }
}
