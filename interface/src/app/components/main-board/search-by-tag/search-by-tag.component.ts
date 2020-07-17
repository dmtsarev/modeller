import {Component, ElementRef, OnInit, ViewChildren} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {EnoviaEntity} from '../../../model/enovia-entity';
import {ActivatedRoute} from '@angular/router';
import {ApiService} from '../../../core/services';
import {Tag} from '../../../model/tag';
import {SearchByTagService} from '../../../core/services/search-by-tag.service';
import {
  MatOptionSelectionChange,
  MatTableDataSource
} from '@angular/material';

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
  selectable = true;
  removable = true;
  nameTags: Tag[] = [];

  constructor(private route: ActivatedRoute,
              private apiService: ApiService,
              private searchByTagService: SearchByTagService,
              @ViewChildren('tagInput') private tagInput: ElementRef<HTMLInputElement>) {
  }

  ngOnInit() {
    this.tags = this.searchByTagService.getTags();
    this.tags.toPromise().then(p => p.forEach(j => console.log(j.type.color)));
  }

  callSomeFunction(event: MatOptionSelectionChange , tag: Tag) {
    if (event.isUserInput) {
      //this.myControl.reset();
      this.nameTags.push(tag);
      this.enoviaEntities = this.searchByTagService.getEntities(tag.name, true);
      this.enoviaEntities.subscribe(res => {
        this.dataSource.data = res;
      });
      this.tagInput.nativeElement.blur();
    }
  }

  remove(tag: Tag): void {
    const index = this.nameTags.indexOf(tag);

    if (index >= 0) {
      this.nameTags.splice(index, 1);
    }

    console.log(this.nameTags);

    this.enoviaEntities = this.enoviaEntities = this.searchByTagService.getEntities(tag.name, false);
    this.enoviaEntities.subscribe(res => {
      this.dataSource.data = res;
    });
  }
}
