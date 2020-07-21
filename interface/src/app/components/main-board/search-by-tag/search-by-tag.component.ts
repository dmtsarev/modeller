import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {EnoviaEntity} from '../../../model/enovia-entity';
import {ActivatedRoute} from '@angular/router';
import {ApiService} from '../../../core/services';
import {Tag} from '../../../model/tag';
import {SearchByTagService} from '../../../core/services/search-by-tag.service';
import {
  MatOptionSelectionChange, MatSort,
  MatTableDataSource
} from '@angular/material';
import {map, scan, startWith} from 'rxjs/operators';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-search-by-tag',
  templateUrl: './search-by-tag.component.html',
  styleUrls: ['./search-by-tag.component.css'],
  // animations: [
  //   trigger('detailExpand', [
  //     state('collapsed', style({height: '0px', minHeight: '0'})),
  //     state('expanded', style({height: '*'})),
  //     transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
  //   ]),
  // ]
})
export class SearchByTagComponent implements OnInit {

  // expandedElement: EnoviaEntity | null;
  tags$: Observable<Tag[]>;
  enoviaEntities$: Observable<EnoviaEntity[]>;
  displayedColumns = ['id', 'entityName', 'type', 'tags'];
  dataSource = new MatTableDataSource();
  selectable = true;
  removable = true;
  nameTags: Tag[] = [];
  options: Tag[] = [];
  isNewSearch = true;
  releaseList$: Observable<string[]>;
  all = '0';
  myControl = new FormControl();
  releasesForm = new FormControl('', Validators.required);

  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private route: ActivatedRoute,
              private apiService: ApiService,
              private searchByTagService: SearchByTagService) {
  }

  ngOnInit() {
    this.dataSource.sort = this.sort;
    this.tags$ = this.searchByTagService.getTags();
    this.releaseList$ = this.searchByTagService.getReleases();

    this.tags$.subscribe((res) => {
      this.options = res.slice();
    });

    this.myControl.valueChanges.subscribe(value => { console.log(value); });

    this.tags$ = this.myControl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

  private _filter(value: string): Tag[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }

  chooseAll() {
    if (this.releasesForm.value && this.releasesForm.value.indexOf(this.all) >= 0) {
      this.releaseList$.toPromise().then(p => { this.releasesForm.patchValue([this.all].concat(p)); });
      if (this.nameTags.length !== 0) {
        this.startSearchTags();
      }
    } else {
      this.releasesForm.reset();
    }
  }

  chooseOne() {
    const values = this.releasesForm.value;
    if (values.indexOf(this.all) >= 0) {
      values.splice(values.indexOf(this.all, 1));
      this.releasesForm.patchValue(values);
    } else {
      this.releaseList$.toPromise().then(p => {
        if (p.length === values.length) {
          this.releasesForm.patchValue([this.all].concat(p));
        }
      });
    }

    if (this.nameTags.length !== 0) {
      this.startSearchTags();
    }
  }

  startSearchTags() {
    const idTags: number[] = [];
    this.nameTags.forEach(value => idTags.push(value.id));
    if (this.releasesForm.value && this.releasesForm.value.indexOf(this.all) >= 0) {
      this.enoviaEntities$ = this.searchByTagService.getAllReleasesEntities(idTags);
    } else {
      this.enoviaEntities$ = this.searchByTagService.getEntities(idTags, this.releasesForm.value);
    }
    this.enoviaEntities$.subscribe(res => {
      this.dataSource.data = res;
    });
  }

  onFocus() {
    // костыль, надо подумать как сделать лучше
    if (this.isNewSearch) {
      this.isNewSearch = false;
      this.myControl.setValue('');
    }
  }

  callSomeFunction(event: MatOptionSelectionChange , tag: Tag) {
    if (event.isUserInput) {
      if (this.nameTags.indexOf(tag) < 0) {
        this.nameTags.push(tag);
        if (this.releasesForm.valid.valueOf()) {
          this.startSearchTags();
        } else {
          this.releasesForm.markAsTouched();
        }
      }
    }
  }

  remove(tag: Tag): void {
    const index = this.nameTags.indexOf(tag);

    if (index >= 0) {
      this.nameTags.splice(index, 1);
    }

    console.log(this.nameTags);
    const idTags: number[] = [];
    this.nameTags.forEach(value => idTags.push(value.id));

    this.enoviaEntities$ = this.enoviaEntities$ = this.searchByTagService.getAllReleasesEntities(idTags);
    this.enoviaEntities$.subscribe(res => {
      this.dataSource.data = res;
    });
  }

  getXlxs() {
    if (this.releasesForm.valid.valueOf()) {
      const idTags: number[] = [];
      this.nameTags.forEach(value => idTags.push(value.id));
      let dataBlob: Observable<Blob>;
      if (this.releasesForm.value.indexOf(this.all) >= 0) {
        dataBlob = this.searchByTagService.getExportXlsx(idTags);
      } else {
        dataBlob = this.searchByTagService.getReleasesExportXlsx(idTags, this.releasesForm.value);
      }
      dataBlob.subscribe( value => {
        const blob = new Blob([value],
          { type: 'application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});

        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(blob);
          return;
        }

        const data = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = data;
        link.download = 'entities.xlsx';
        link.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));

        setTimeout(function() {
          window.URL.revokeObjectURL(data);
          link.remove();
        }, 100);
      });
    } else {
      this.releasesForm.markAsTouched();
    }
  }
}
