import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {EnoviaEntity} from '../../../model/enovia-entity';
import {EntityService} from '../../../core/services/entity.service';
import {ActivatedRoute} from '@angular/router';
import {ApiService} from '../../../core/services';
import {Tag} from '../../../model/tag';
import {SearchByTagService} from '../../../core/services/search-by-tag.service';
import {MatOptionSelectionChange, MatTableDataSource} from '@angular/material';
import {TagTableService} from '../../../core/services/tag-table.service';

@Component({
  selector: 'app-search-by-tag',
  templateUrl: './search-by-tag.component.html',
  styleUrls: ['./search-by-tag.component.css']
})
export class SearchByTagComponent implements OnInit {

  tags: Observable<Tag[]>;
  myControl = new FormControl();
  enoviaEntities: Observable<EnoviaEntity[]>;
  displayedColumns = ['Id', 'Name'];
  dataSource = new MatTableDataSource();

  constructor(private route: ActivatedRoute,
              private apiService: ApiService,
              private searchByTagService: SearchByTagService,
              private entityService: EntityService,
              private tagTableService: TagTableService) {
  }

  ngOnInit() {
    this.tags = this.searchByTagService.getTags();
  }

  callSomeFunction(event: MatOptionSelectionChange , nameTag: string) {
    if (event.isUserInput) {
      console.log(nameTag);
      this.enoviaEntities = this.searchByTagService.getEntities(nameTag, true);
      this.enoviaEntities.toPromise().then(p => p.forEach( j => console.log(j.entityName)));
      this.enoviaEntities.subscribe(res => {
        this.dataSource.data = res;
      });
      // this.tagTableService.setDataTable(this.enoviaEntitis);
      // this.tagTable = new TagTableComponent(this.tagTableService);
      // this.enoviaEntitis = this.searchByTagService.getEntities(1, 'type', nameTag);
    }
  }



}
