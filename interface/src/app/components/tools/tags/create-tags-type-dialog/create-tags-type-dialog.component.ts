import {Component, OnInit, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToolsService} from '../../../../core/services/tools.service';
import {TagType} from '../../../../model/tag-type';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-create-tags-type-dialog',
  templateUrl: './create-tags-type-dialog.component.html',
  styleUrls: ['./create-tags-type-dialog.component.css']
})
export class CreateTagsTypeDialogComponent implements OnInit {
  tagTypeForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<CreateTagsTypeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: TagType,
              public toolsService: ToolsService,
              private fb: FormBuilder) {
  }
  ngOnInit(): void {
    this.tagTypeForm = this.fb.group({
      name: [null, [
        Validators.required,
        Validators.maxLength(256)
      ]],
      description: null,
      color: '#000105'
    });
    if (this.data) {
      this.tagTypeForm.patchValue(this.data);
    }
  }

  get name() {
    return this.tagTypeForm.get('name');
  }

  closeDialog() {
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.tagTypeForm.invalid) {
      return;
    }
    const tagType: TagType = this.tagTypeForm.value;
    if (this.data) {
      tagType.id = this.data.id;
      this.toolsService.updateTagType(tagType).subscribe(() => this.closeDialog());
    } else {
      this.toolsService.saveTagType(tagType).subscribe(() => this.closeDialog());
    }
  }
}
