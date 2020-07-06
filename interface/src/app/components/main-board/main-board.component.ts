import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProjectService} from '../../core/services/project.service';

@Component({
  selector: 'app-main-board',
  templateUrl: './main-board.component.html',
  styleUrls: ['./main-board.component.css']
})
export class MainBoardComponent implements OnInit {

  private id;
  public project = {
    projectName: '',
    description: ''
  };

  constructor(private route: ActivatedRoute, private projectService: ProjectService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(data => {
      this.id = data.get('projectId');
      this.projectService.getProjectById(this.id).toPromise().then( result => {
          this.project = result;
        }
      );
    });
  }
}
