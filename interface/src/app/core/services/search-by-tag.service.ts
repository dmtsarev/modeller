import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Tag} from '../../model/tag';
import {EnoviaEntity} from '../../model/enovia-entity';
import {HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SearchByTagService {

  constructor(private apiService: ApiService) { }
  getTags(): Observable<Tag[]> {
    return this.apiService.get('/search/tags');
  }
  getAllReleasesEntities(tags: number[]): Observable<EnoviaEntity[]> {
    console.log( tags.toString());
    const params = new HttpParams().set('tags', tags.toString());
    return this.apiService.get('/search/entities', params);
  }
  getEntities(tags: number[], releases: string[]): Observable<EnoviaEntity[]> {
    const params = new HttpParams().set('tags', tags.toString()).set('releases', releases.toString());
    return this.apiService.get('/search/releases/entities', params);
  }
  getReleases(): Observable<string[]> {
    return this.apiService.get('/search/releases');
  }
}
