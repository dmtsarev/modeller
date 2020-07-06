import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Tag} from '../../model/tag';
import {ApiService} from './index';
import {TagType} from '../../model/tag-type';
import {map} from 'rxjs/operators';
import {User} from '../../model/users/user';

@Injectable({
  providedIn: 'root'
})
export class ToolsService {

  constructor(private apiService: ApiService) {}

  getTags(): Observable<Tag[]> {
    return this.apiService.get('/tools/tags');
  }

  getTagsByTagsType(tagName: string): Observable<Tag[]> {
    return this.apiService.get(`/tools/tags/types/${tagName}`);
  }

  getTagTypes(): Observable<TagType[]> {
    return this.apiService.get('/tools/tags/types');
  }

  getTagTypesWithNone(): Observable<TagType[]> {
    const noneTag: TagType = {
      id: 0,
      name: 'None',
      description: null,
      tags: [null],
      color: null
    };
    return this.apiService.get('/tools/tags/types').pipe(map(items => {
      const result: TagType[] = [noneTag];
      result.push(...items);
      return result;
    }));
  }

  getUsers(): Observable<User[]> {
    return this.apiService.get('/users');
  }

  saveTag(tag: Tag): Observable<Tag> {
    return this.apiService.post('/tools/tags', tag);
  }

  saveTagType(tagType: TagType): Observable<TagType> {
    return this.apiService.post('/tools/tags/types', tagType);
  }

  updateTagType(tagType: TagType): Observable<TagType> {
    console.log(`/tools/tags/types/${tagType.id}`);
    return this.apiService.put(`/tools/tags/types/${tagType.id}`, tagType);
  }

  deleteTags(tags: Tag): Observable<any> {
    return this.apiService.delete(`/tools/tags/${tags.id}`);
  }

  deleteTagsType(tagsType: TagType): Observable<any> {
    return this.apiService.delete(`/tools/tags/types/${tagsType.name}`);
  }
}
