import { Injectable } from '@angular/core';
import {ApiService} from './api.service';
import {Observable, of} from 'rxjs';
import {catchError, mapTo, tap} from 'rxjs/operators';
import {Token} from '../model/token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  private loggedUser: string;

  constructor(private apiService: ApiService) {}
  login(user: { username: string, password: string }): Observable<boolean> {
    return this.apiService.postAuth('/authenticate', user).pipe(tap(token => {
      return this.doLoginUser(user.username, token);
    }),
      mapTo(true),
      catchError(() => {
        return of(false);
      })
      );
  }

  isLoggedIn() {
    return !!this.getJwtToken();
  }


  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  private doLoginUser(username: string, token: Token) {
    this.loggedUser = username;
    this.storeTokens(token);
  }

  public doLogoutUser() {
    this.loggedUser = null;
    this.removeTokens();
  }

  private getRefreshToken() {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  private storeJwtToken(jwt: string) {
    localStorage.setItem(this.JWT_TOKEN, jwt);
  }

  private storeTokens(token: Token) {
    localStorage.setItem(this.JWT_TOKEN, token.token);
    // localStorage.setItem(this.REFRESH_TOKEN, token.refreshToken);
  }

  private removeTokens() {
    localStorage.removeItem(this.JWT_TOKEN);
    // localStorage.removeItem(this.REFRESH_TOKEN);
  }

}

