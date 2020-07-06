import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataModelBoardComponent } from './data-model-board.component';

describe('DataModelBoardComponent', () => {
  let component: DataModelBoardComponent;
  let fixture: ComponentFixture<DataModelBoardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataModelBoardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataModelBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
