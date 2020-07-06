import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {ToolsService} from '../../../core/services/tools.service';
import {Tag} from '../../../model/tag';
import {Observable} from 'rxjs';
import {TagType} from '../../../model/tag-type';
import {FormBuilder, FormGroup} from '@angular/forms';
import {EntityService} from '../../../core/services/entity.service';
import {EnoviaEntity} from '../../../model/enovia-entity';

@Component({
  selector: 'app-tag-picker-dialog',
  templateUrl: './tag-picker-dialog.component.html',
  styleUrls: ['./tag-picker-dialog.component.css']
})
export class TagPickerDialogComponent implements OnInit {
  removable = true;
  selectable = true;
  entity: EnoviaEntity;
  selectedTagList: Tag[] = [];
  tags$: Observable<Tag[]>;
  tagTypes$: Observable<TagType[]>;
  tagPicker: FormGroup;
  constructor(public dialogRef: MatDialogRef<TagPickerDialogComponent>, private toolsService: ToolsService,
              @Inject(MAT_DIALOG_DATA) public data: any, public fb: FormBuilder, private entityService: EntityService) { }

  ngOnInit() {
    this.tagPicker = this.fb.group({
      type: [null],
      tag: [null]
    });
    this.entity = this.data;
    this.selectedTagList.push(...this.entity.tags);
    this.tagTypes$ = this.toolsService.getTagTypes();
    this.type.valueChanges.subscribe(value => {
      this.tags$ = this.toolsService.getTagsByTagsType(value.name);
    });
  }
  addTagToList() {
    if (this.tag.value == null) {return; }
    const index = this.selectedTagList.indexOf(this.tag.value);
    if (index >= 0 ) {
      return;
    }
    this.selectedTagList.push(this.tag.value);
  }
  get type() {
    return this.tagPicker.get('type');
  }
  get tag() {
    return this.tagPicker.get('tag');
  }
  remove(tag: Tag): void {
    const index = this.selectedTagList.indexOf(tag);
    if (index >= 0) {
      this.selectedTagList.splice(index, 1);
    }
  }
  onSubmit() {
    this.entity.tags = this.selectedTagList;
    console.log(this.entity);
    this.entityService.saveDescription(this.entity.id, this.entity).subscribe(() => {
      this.dialogRef.close();
    });
  }
}
