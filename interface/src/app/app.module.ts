import { BrowserModule } from '@angular/platform-browser';
import {NgModule, NO_ERRORS_SCHEMA} from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { LeftMenuComponent } from './components/left-menu/left-menu.component';
import { MainBoardComponent } from './components/main-board/main-board.component';
import { BoardMenuComponent } from './components/main-board/board-menu/board-menu.component';
import { ReleaseHistoryComponent } from './components/main-board/release-history/release-history.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RouterModule} from '@angular/router';
import {
  MatButtonModule,
  MatCardModule,
  MatChipsModule,
  MatDialogModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatProgressSpinnerModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTreeModule,
  MatPaginatorModule,
  MatSelectModule,
  MatExpansionModule,
  MatTooltipModule,
  MatProgressBarModule, MatAutocompleteModule
} from '@angular/material';
import { CdkColumnDef } from '@angular/cdk/table';
import {CoreModule} from './core';
import {AppRoutingModule} from './app-routing.module';
import { DataModelTreeComponent } from './components/main-board/data-model-tree/data-model-tree.component';
import { DataModelBoardComponent } from './components/main-board/data-model-board/data-model-board.component';
import { EntityDetailsComponent } from './components/main-board/entity-details/entity-details.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { LoginComponent } from './components/login/login.component';
import {NoAuthGuard} from './services/no-auth-guard.service';
import {NgxEditorModule} from 'ngx-editor';
import { GraphDialogComponent } from './components/main-board/graph-dialog/graph-dialog.component';
import {NgxGraphModule} from '@swimlane/ngx-graph';
import {TooltipModule} from '@swimlane/ngx-charts';
import { ToolsComponent } from './components/tools/tools.component';
import { TagsComponent } from './components/tools/tags/tags.component';
import { UsersComponent } from './components/tools/users/users.component';
import { CreateTagsDialogComponent } from './components/tools/tags/create-tags-dialog/create-tags-dialog.component';
import { CreateTagsTypeDialogComponent } from './components/tools/tags/create-tags-type-dialog/create-tags-type-dialog.component';
import { ColorPickerComponent } from './components/tools/color-picker/color-picker.component';
import { TagPickerDialogComponent } from './components/main-board/tag-picker-dialog/tag-picker-dialog.component';
import { ProjectSettingsComponent } from './components/tools/project-settings/project-settings.component';
import { ProjectViewComponentComponent } from './components/tools/project-settings/project-view-component/project-view-component.component';
import { ProjectCreateComponentComponent } from './components/tools/project-settings/project-create-component/project-create-component.component';
import { ProjectDetailsSettingsComponent } from './components/tools/project-settings/project-view-component/project-details-settings/project-details-settings.component';
import { BreadCrumbsComponent } from './components/common/bread-crumbs/bread-crumbs.component';
import { ProjectsComponent } from './components/main-board/projects/projects.component';
import {EventsServiceService} from './services/events-service.service';
import { SearchByTagComponent } from './components/main-board/search-by-tag/search-by-tag.component';
import { MatSortModule } from '@angular/material/sort';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LeftMenuComponent,
    MainBoardComponent,
    BoardMenuComponent,
    ReleaseHistoryComponent,
    DataModelTreeComponent,
    DataModelBoardComponent,
    EntityDetailsComponent,
    LoginComponent,
    GraphDialogComponent,
    ToolsComponent,
    TagsComponent,
    UsersComponent,
    CreateTagsDialogComponent,
    CreateTagsTypeDialogComponent,
    ColorPickerComponent,
    TagPickerDialogComponent,
    ProjectSettingsComponent,
    ProjectViewComponentComponent,
    ProjectCreateComponentComponent,
    ProjectDetailsSettingsComponent,
    BreadCrumbsComponent,
    ProjectsComponent,
    SearchByTagComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    MatGridListModule,
    MatTabsModule,
    MatSidenavModule,
    MatTableModule,
    CoreModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatToolbarModule,
    MatListModule,
    AppRoutingModule,
    MatTreeModule,
    MatIconModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    FormsModule,
    MatCardModule,
    NgxEditorModule,
    MatChipsModule,
    MatDialogModule,
    NgxGraphModule,
    TooltipModule,
    MatPaginatorModule,
    MatSelectModule,
    MatExpansionModule,
    MatTooltipModule,
    MatProgressBarModule,
    MatAutocompleteModule,
    MatSortModule
  ],
  providers: [CdkColumnDef, NoAuthGuard, EventsServiceService],
  bootstrap: [AppComponent],
  entryComponents: [GraphDialogComponent,
    CreateTagsDialogComponent,
    CreateTagsTypeDialogComponent,
    TagPickerDialogComponent],
  schemas: [NO_ERRORS_SCHEMA]
})
export class AppModule { }
