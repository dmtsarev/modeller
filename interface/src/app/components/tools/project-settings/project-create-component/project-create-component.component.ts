import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GitService} from '../../../../core/services/git.service';
import {BehaviorSubject, of, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {ProjectService} from '../../../../core/services/project.service';
import {Project} from '../../../../model/project';
import {MatSnackBar} from '@angular/material';
import {Router} from '@angular/router';

@Component({
  selector: 'app-project-create-component',
  templateUrl: './project-create-component.component.html',
  styleUrls: ['./project-create-component.component.css']
})
export class ProjectCreateComponentComponent implements OnInit {
  createProjectForm: FormGroup;
  loadSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadSubject.asObservable();
  constructor(private fb: FormBuilder, private gitService: GitService, private projectService: ProjectService, private route: Router,
              private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.createProjectForm = this.fb.group( {
      projectName: [null, [
        Validators.required,
        Validators.maxLength(256)
      ]],
      description: [],
      git: this.fb.group({
        uri: [null, [
          Validators.required
        ]],
        username: [null],
        password: [null]
      })
    });
  }
  createProject() {
    const projectData: Project = this.createProjectForm.value;
    projectData.gitPath = projectData.git.uri;
    this.projectService.saveProject(projectData).pipe(catchError( err => {
        this.openShackBarOnError('Не удалось создать проект', null);
        return throwError(err);
      }
    )).subscribe(() => { this.route.navigate(['/tools/projects']); });
  }
  openShackBarOnError(message: string, action: string) {
    this.snackBar.open(message, action, {duration: 2000});
  }
  cancel() {
    this.route.navigate(['/tools/projects']);
  }
  testConnection() {
    this.loadSubject.next(true);
    this.gitService.testConnection(this.git.value).pipe(
      catchError(err => {
        this.git.setErrors({connectionFailed: true});
        return of(err);
      })
    ).subscribe().add(() => {
      this.loadSubject.next(false);
    });
  }
  get git() {
    return this.createProjectForm.get('git');
  }
}
