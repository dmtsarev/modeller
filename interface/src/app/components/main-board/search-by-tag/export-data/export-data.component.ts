import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {SearchByTagService} from '../../../../core/services/search-by-tag.service';
import {FormControl} from '@angular/forms';
import {Tag} from '../../../../model/tag';

@Component({
  selector: 'app-export-data',
  templateUrl: './export-data.component.html',
  styleUrls: ['./export-data.component.css']
})
export class ExportDataComponent implements OnInit {
  all = '0';

  @Input() releasesForm: FormControl;
  @Input() nameTags: Tag[];

  constructor(private searchByTagService: SearchByTagService) { }

  ngOnInit() {
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
