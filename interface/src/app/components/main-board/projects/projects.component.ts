import { Component, OnInit } from '@angular/core';
import {MatSnackBar} from '@angular/material';
import {ActivatedRoute} from '@angular/router';
import {ProjectsDataSource} from '../../../services/source/projects-data-source';
import {ProjectService} from '../../../core/services/project.service';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {
  dataSource: ProjectsDataSource;
  displayedColumns = ['seqNo', 'projectName'];

  constructor(private projectService: ProjectService, private snackBar: MatSnackBar, private route: ActivatedRoute) {  }

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
