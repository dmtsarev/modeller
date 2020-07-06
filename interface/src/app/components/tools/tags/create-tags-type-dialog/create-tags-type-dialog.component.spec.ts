import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTagsTypeDialogComponent } from './create-tags-type-dialog.component';

describe('CreateTagsTypeDialogComponent', () => {
  let component: CreateTagsTypeDialogComponent;
  let fixture: ComponentFixture<CreateTagsTypeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTagsTypeDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTagsTypeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
