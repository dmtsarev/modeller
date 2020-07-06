import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToolsService} from '../../../core/services/tools.service';
import {Tag} from '../../../model/tag';
import {MatDialog} from '@angular/material';
import {CreateTagsDialogComponent} from './create-tags-dialog/create-tags-dialog.component';
import {Observable} from 'rxjs';
import {TagType} from '../../../model/tag-type';
import {CreateTagsTypeDialogComponent} from './create-tags-type-dialog/create-tags-type-dialog.component';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.css']
})
export class TagsComponent implements OnInit, OnDestroy {
  tagsType$: Observable<TagType[]>;
  tagsTypeMap = new Map<string, Tag[]>();

  constructor(private toolsService: ToolsService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.tagsType$ = this.toolsService.getTagTypesWithNone();
  }

  ngOnDestroy(): void {
    this.tagsTypeMap.clear();
  }

  loadTagToTagsTypeMap(name: string) {
    if (!name) {return; }
    this.toolsService.getTagsByTagsType(name).subscribe(e => {
      this.tagsTypeMap = this.tagsTypeMap.set(name, e);
    });
  }

  openDialogCreateTag() {
    const dialogRef = this.dialog.open(CreateTagsDialogComponent);
    dialogRef.afterClosed().subscribe((type) => {
      this.loadTagToTagsTypeMap(type);
    });
  }

  openDialogCreateTagType() {
    const dialogRef = this.dialog.open(CreateTagsTypeDialogComponent);
    dialogRef.afterClosed().subscribe(() =>  this.tagsType$ = this.toolsService.getTagTypesWithNone());
  }

  openDialogSettingsTagType(tagType: TagType) {
    const dialogRef = this.dialog.open(CreateTagsTypeDialogComponent,
      {data: tagType});
    dialogRef.afterClosed().subscribe(() =>  this.tagsType$ = this.toolsService.getTagTypesWithNone());
  }

  openSettings(tag: Tag) {
    const dialogRef = this.dialog.open(CreateTagsDialogComponent,
      {data: tag});
    dialogRef.afterClosed().subscribe((type) =>  this.loadTagToTagsTypeMap(type));
  }

  getTags(tag: TagType) {
    this.loadTagToTagsTypeMap(tag.name);
  }

  removeTagsType(tagType: TagType) {
    this.toolsService.deleteTagsType(tagType)
      .subscribe(() => this.tagsType$ = this.toolsService.getTagTypes());
  }

  removeTags(tags: Tag) {
    this.toolsService.deleteTags(tags).subscribe(() => {
      const type = tags.type ? tags.type.name : 'None';
      this.loadTagToTagsTypeMap(type);
    });
  }
}
