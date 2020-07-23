import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntityDetailsXmlComponent } from './entity-details-xml.component';

describe('EntityDetailsXmlComponent', () => {
  let component: EntityDetailsXmlComponent;
  let fixture: ComponentFixture<EntityDetailsXmlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntityDetailsXmlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntityDetailsXmlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
