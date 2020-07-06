import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TagPickerDialogComponent } from './tag-picker-dialog.component';

describe('TagPickerDialogComponent', () => {
  let component: TagPickerDialogComponent;
  let fixture: ComponentFixture<TagPickerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TagPickerDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TagPickerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
