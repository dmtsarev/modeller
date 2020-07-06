import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {HttpParams} from '@angular/common/http';
import {EnoviaEntity} from '../../model/enovia-entity';
import {EntityGraph} from '../../model/entity-graph';

@Injectable()
export class EntityService {

  constructor(private apiService: ApiService) { }

  getEntities(releaseId: number, name: string, searchWord: string): Observable<EnoviaEntity[]>  {
    const params = new HttpParams().set('releaseId', releaseId.toString())
      .set('type', name)
      .set('searchWord', searchWord);

    return this.apiService.get('/entity', params);
  }
  getEntity(entityId: number): Observable<EnoviaEntity>  {
    return this.apiService.get('/entity/' + entityId);
  }
  saveDescription(entityId: number, entity: EnoviaEntity): Observable<EnoviaEntity>  {
    return this.apiService.put('/entity/' + entityId, entity);
  }

  getEntityGraph(entityId: number): Observable<EntityGraph> {
    return this.apiService.get('/entity/' + entityId + '/graph');
  }
}
