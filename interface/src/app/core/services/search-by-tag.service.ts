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
    const param = new HttpParams().set('tags', tags.toString());
    return this.apiService.get('/search/entities', param);
  }
  getEntities(tags: number[], releases: string[]): Observable<EnoviaEntity[]> {
    const params = new HttpParams().set('tags', tags.toString()).set('releases', releases.toString());
    return this.apiService.get('/search/releases/entities', params);
  }
  getReleases(): Observable<string[]> {
    return this.apiService.get('/search/releases');
  }
  getExportXlsx(tags: number[]): Observable<Blob> {
    const param = new HttpParams().set('tags', tags.toString());
    return this.apiService.getXlxs('/search/export/xlsx', param);
  }
  getReleasesExportXlsx(tags: number[], release: string[]): Observable<Blob> {
    const params = new HttpParams().set('tags', tags.toString()).set('releases', release.toString());
    return this.apiService.getXlxs('/releases/export/xlsx', params);
  }
}
