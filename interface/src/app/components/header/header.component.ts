import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../core/services/auth.service';
import {of} from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

  get isAuthenticated() {
    return of(this.authService.isLoggedIn());
  }
  logout() {
    // this.authService;
    this.authService.doLogoutUser();
    this.router.navigate(['/login']);

  }

}
