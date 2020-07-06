import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ApiService} from './services';
import {ReleaseService} from './services/release.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {EntityService} from './services/entity.service';
import {TokenInterceptor} from './interceptors/token.interceptor';
import {ToolsService} from './services/tools.service';
import {AuthService} from './services/auth.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    ApiService,
    ReleaseService,
    EntityService,
    AuthService,
    ToolsService
  ]
})
export class CoreModule { }
