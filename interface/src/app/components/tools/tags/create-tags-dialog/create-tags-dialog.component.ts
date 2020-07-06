import {Component, Inject, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {ToolsService} from '../../../../core/services/tools.service';
import {TagType} from '../../../../model/tag-type';
import {Tag} from '../../../../model/tag';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-create-tags-dialog',
  templateUrl: './create-tags-dialog.component.html',
  styleUrls: ['./create-tags-dialog.component.css']
})
export class CreateTagsDialogComponent implements OnInit {
  tagForm: FormGroup;
  tagTypes$: Observable<TagType[]>;

  constructor(public dialogRef: MatDialogRef<CreateTagsDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Tag,
              public toolsService: ToolsService, private fb: FormBuilder) {}

  ngOnInit() {
    this.createForm();
    this.tagTypes$ = this.toolsService.getTagTypes();
  }
  createForm() {
    this.tagForm = this.fb.group({
      name: [null, [
        Validators.required,
        Validators.maxLength(256)
        ]
      ],
      description: [null],
      type: this.fb.group({
        name: ['None']
      })
    });
    if (this.data) {
      this.tagForm.patchValue(this.data);
    }
  }
  onSubmit() {
    if (this.tagForm.invalid) {
      return;
    }
    const tag = this.tagForm.value;
    if (this.data) {
      tag.id = this.data.id;
      this.toolsService.saveTag(tag).subscribe(() => this.dialogRef.close(tag.type.name));
    } else {
      this.toolsService.saveTag(tag).subscribe(() => this.dialogRef.close(tag.type.name));
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
  get name() {
    return this.tagForm.get('name');
  }
}
