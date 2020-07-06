import {Component, OnInit} from '@angular/core';
import {User} from '../../../model/users/user';
import {ToolsService} from '../../../core/services/tools.service';
import {Observable} from 'rxjs';
import {FormControl} from '@angular/forms';
import {TooltipPosition} from '@angular/material/tooltip';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users$: Observable<User[]>;
  positionOptions: TooltipPosition = 'right';
  position = new FormControl(this.positionOptions);

  constructor(private toolsService: ToolsService) { }

  ngOnInit() {
    this.users$ = this.toolsService.getUsers();
  }
}
