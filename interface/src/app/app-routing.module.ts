import { NgModule } from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {ReleaseHistoryComponent} from './components/main-board/release-history/release-history.component';
import {DataModelBoardComponent} from './components/main-board/data-model-board/data-model-board.component';
import {LoginComponent} from './components/login/login.component';
import {MainBoardComponent} from './components/main-board/main-board.component';
import {NoAuthGuard} from './services/no-auth-guard.service';
import {ToolsComponent} from './components/tools/tools.component';
import {TagsComponent} from './components/tools/tags/tags.component';
import {UsersComponent} from './components/tools/users/users.component';
import {ProjectSettingsComponent} from './components/tools/project-settings/project-settings.component';
import {ProjectViewComponentComponent} from './components/tools/project-settings/project-view-component/project-view-component.component';
import {ProjectCreateComponentComponent} from './components/tools/project-settings/project-create-component/project-create-component.component';
import {ProjectsComponent} from './components/main-board/projects/projects.component';

const routes: Routes = [

  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'tools',
    component: ToolsComponent,
    canActivate: [NoAuthGuard],
    children: [
      {
        path: 'tags',
        component: TagsComponent
      },
      {
        path: 'users',
        component: UsersComponent
      },
      {
        path: 'projects',
        component: ProjectSettingsComponent,
      }
      ]
  },
  {
    path: 'tools/projects/view',
    component: ProjectViewComponentComponent
  },
  {
    path: 'tools/projects/create',
    component: ProjectCreateComponentComponent
  },
  {
    path: 'projects',
    component: ProjectsComponent,
    canActivate: [NoAuthGuard],
  },
  {
    path: 'projects/:projectId',
    component: MainBoardComponent,
    canActivate: [NoAuthGuard],
    children: [
      {
        path: 'releases',
        component: ReleaseHistoryComponent,
      },
      {
        path: 'releases/:releaseId',
        component: DataModelBoardComponent,
      },
    ]
  },
  {
    path: '',
    component: ProjectsComponent,
    canActivate: [NoAuthGuard],
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes, {
    // preload all modules; optionally we could
    // implement a custom preloading strategy for just some
    // of the modules (PRs welcome 😉)
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
