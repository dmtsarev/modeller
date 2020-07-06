import { Component, OnInit } from '@angular/core';
import {ProjectService} from '../../core/services/project.service';
import {Observable} from 'rxjs';
import {Project} from '../../model/project';

@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.css']
})
export class LeftMenuComponent implements OnInit {

  public projects$: Observable<Project[]>;
  constructor(private projectService: ProjectService) { }

  ngOnInit() {
    this.projects$ = this.projectService.getProjects();
  }

}
