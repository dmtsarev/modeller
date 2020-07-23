import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FoundDataTableComponent } from './found-data-table.component';

describe('FoundDataTableComponent', () => {
  let component: FoundDataTableComponent;
  let fixture: ComponentFixture<FoundDataTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FoundDataTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoundDataTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
