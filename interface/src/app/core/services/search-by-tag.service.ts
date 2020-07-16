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
  getEntities(tag: string, action: boolean): Observable<EnoviaEntity[]> {
    const params = new HttpParams().set('tag', tag).set('action', action.toString());
    return this.apiService.get('/search', params);
  }
}
