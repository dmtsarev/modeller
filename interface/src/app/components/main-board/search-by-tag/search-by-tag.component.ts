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
import {filter, map, startWith} from 'rxjs/operators';

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
  options: Tag[] = [];
  isNewSearch = true;

  constructor(private route: ActivatedRoute,
              private apiService: ApiService,
              private searchByTagService: SearchByTagService,
              @ViewChildren('tagInput') private tagInput: ElementRef<HTMLInputElement>) {
  }

  ngOnInit() {
    this.tags = this.searchByTagService.getTags();
    this.tags.subscribe((res) => {
      this.options = res.slice();
    });

    this.myControl.valueChanges.subscribe(value => { console.log(value); });

    this.tags = this.myControl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

  onFocus() {
    // костыль, надо подумать как сделать лучше
    if (this.isNewSearch) {
      this.isNewSearch = false;
      this.myControl.setValue('');
    }
  }

  private _filter(value: string): Tag[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }

  callSomeFunction(event: MatOptionSelectionChange , tag: Tag) {
    if (event.isUserInput) {
      if (this.nameTags.indexOf(tag) < 0) {
        this.nameTags.push(tag);
        this.enoviaEntities = this.searchByTagService.getEntities(tag.id, true);
        this.enoviaEntities.subscribe(res => {
          this.dataSource.data = res;
        });
      }
    }
  }

  remove(tag: Tag): void {
    const index = this.nameTags.indexOf(tag);

    if (index >= 0) {
      this.nameTags.splice(index, 1);
    }

    console.log(this.nameTags);

    this.enoviaEntities = this.enoviaEntities = this.searchByTagService.getEntities(tag.id, false);
    this.enoviaEntities.subscribe(res => {
      this.dataSource.data = res;
    });
  }
}
