import { Component, OnInit } from '@angular/core';
import {ProjectsDataSource} from '../../../services/source/projects-data-source';
import {ProjectService} from '../../../core/services/project.service';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-project-settings',
  templateUrl: './project-settings.component.html',
  styleUrls: ['./project-settings.component.css']
})
export class ProjectSettingsComponent implements OnInit {
  dataSource: ProjectsDataSource;
  displayedColumns = ['projectName', 'projectDescription', 'projectStatus'];

  constructor(private projectService: ProjectService, private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.dataSource = new ProjectsDataSource(this.projectService);
    try {
      this.dataSource.loadProjects();
    } catch (e) {
      this.openShackBarOnError(e.toString(), '');
    }
  }

  openShackBarOnError(message: string, action: string) {
    this.snackBar.open(message, action, {duration: 2000});
  }

}
