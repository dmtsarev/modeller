import {Component, Input, OnInit} from '@angular/core';
import {EnoviaEntity} from '../../../model/enovia-entity';
import {EntityService} from '../../../core/services/entity.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MatDialog} from '@angular/material';
import {GraphDialogComponent} from '../graph-dialog/graph-dialog.component';
import {TagPickerDialogComponent} from '../tag-picker-dialog/tag-picker-dialog.component';

@Component({
  selector: 'app-entity-details',
  templateUrl: './entity-details.component.html',
  styleUrls: ['./entity-details.component.css']
})
export class EntityDetailsComponent implements OnInit {
  private htmlContent;
  public descriptionConfig;
  public ematrixConfig;
  public mouseOnEditor = false;
  public readonly config = {
    editable: true,
    enableToolbar: true,
    height: 'auto',
    imageEndPoint: '',
    minHeight: '300',
    minWidth: '0',
    placeholder: 'Enter text here...',
    showToolbar: true,
    spellcheck: true,
    toolbar: [
      ['bold', 'italic', 'underline', 'strikeThrough', 'superscript', 'subscript'],
      ['fontName', 'fontSize', 'color'],
      ['justifyLeft', 'justifyCenter', 'justifyRight', 'justifyFull', 'indent', 'outdent'],
      ['cut', 'copy', 'delete', 'removeFormat', 'undo', 'redo'],
      ['paragraph', 'blockquote', 'removeBlockquote', 'horizontalLine', 'orderedList', 'unorderedList'],
      ['link', 'unlink', 'image', 'video']]
  };
  public readonly configReadOnly = {
    editable: false,
    enableToolbar: false,
    showToolbar: false,
    minHeight: '300'
  };
  @Input() entity: EnoviaEntity;
  private entityForm: FormGroup;
  constructor(private entityService: EntityService, private formBuilder: FormBuilder, public dialog: MatDialog) {
    this.descriptionConfig = this.configReadOnly;
    this.ematrixConfig = this.configReadOnly;
  }
  ngOnInit() {
    this.entityForm = this.formBuilder.group({
      entityName: []
    });
    console.log(this.entity);
    this.entityForm.patchValue(this.entity);
  }
  onSubmit() {
    this.saveDescription();
  }
  saveDescription() {
    this.entityService.saveDescription(this.entity.id, this.entity).subscribe();
  }
  openDialog(): void {
    console.log('entity id to graph:' + this.entity.id);
    const dialogRef = this.dialog.open(GraphDialogComponent, {
      width: '800px',
      height: '600px',
      data: {
        entityId: this.entity.id
      }
    });
  }
  openTagPickerDialog() {
    if (this.entity.tags == null) {
      this.entity.tags = [];
    }
    const dialogRef = this.dialog.open(TagPickerDialogComponent, {
      data: this.entity
    });
  }
  setCongif(item: any) {
    this.descriptionConfig = this.config;
  }
  mouseEnter() {
    this.mouseOnEditor = true;
  }
  mouseLeave() {
    this.mouseOnEditor = false;
  }
  setReadConfig() {
    if (!this.mouseOnEditor) {
      this.descriptionConfig = this.configReadOnly;
    }
  }
}
