import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectViewComponentComponent } from './project-view-component.component';

describe('ProjectViewComponentComponent', () => {
  let component: ProjectViewComponentComponent;
  let fixture: ComponentFixture<ProjectViewComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectViewComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectViewComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
