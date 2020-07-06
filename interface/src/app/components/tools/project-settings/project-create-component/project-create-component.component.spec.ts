import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectCreateComponentComponent } from './project-create-component.component';

describe('ProjectCreateComponentComponent', () => {
  let component: ProjectCreateComponentComponent;
  let fixture: ComponentFixture<ProjectCreateComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectCreateComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectCreateComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
