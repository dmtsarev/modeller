import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {EnoviaEntity} from '../../model/enovia-entity';

@Injectable({
  providedIn: 'root'
})
export class TagTableService {
  dataTable: Observable<EnoviaEntity[]>;

  constructor() { }
  setDataTable(dataTable: Observable<EnoviaEntity[]>) {
    this.dataTable = dataTable;
  }
  getDataTable(): Observable<EnoviaEntity[]> {
    return this.dataTable;
  }
}
