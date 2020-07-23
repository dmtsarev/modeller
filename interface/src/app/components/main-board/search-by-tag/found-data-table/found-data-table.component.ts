import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Observable} from 'rxjs';
import {EnoviaEntity} from '../../../../model/enovia-entity';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {EntityService} from '../../../../core/services/entity.service';
import {MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-found-data-table',
  templateUrl: './found-data-table.component.html',
  styleUrls: ['./found-data-table.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class FoundDataTableComponent implements OnInit {
  displayedColumns = ['id', 'entityName', 'type', 'release', 'tags'];
  enoviaEntity$: Observable<EnoviaEntity>;
  expandedElement: EnoviaEntity | null;
  selectable = true;
  removable = true;

  @Input() dataSource: MatTableDataSource<EnoviaEntity[]>;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private entityService: EntityService) { }

  ngOnInit() {
    this.dataSource.sort = this.sort;
  }

  showElement(nodeId: number, expandedElement) {
    if (expandedElement != null) {
      this.enoviaEntity$ = this.entityService.getEntity(nodeId);
    }
  }
}
