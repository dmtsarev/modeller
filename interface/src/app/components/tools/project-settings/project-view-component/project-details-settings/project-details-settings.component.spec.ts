import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectDetailsSettingsComponent } from './project-details-settings.component';

describe('ProjectDetailsSettingsComponent', () => {
  let component: ProjectDetailsSettingsComponent;
  let fixture: ComponentFixture<ProjectDetailsSettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectDetailsSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectDetailsSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
