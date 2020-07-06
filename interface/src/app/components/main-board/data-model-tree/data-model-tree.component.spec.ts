import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataModelTreeComponent } from './data-model-tree.component';

describe('DataModelTreeComponent', () => {
  let component: DataModelTreeComponent;
  let fixture: ComponentFixture<DataModelTreeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataModelTreeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataModelTreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
