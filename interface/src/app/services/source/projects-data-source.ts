import {Project} from '../../model/project';
import {DataSource} from '@angular/cdk/table';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {CollectionViewer} from '@angular/cdk/collections';
import {catchError, finalize } from 'rxjs/operators';
import {ProjectService} from '../../core/services/project.service';

export class ProjectsDataSource implements DataSource<Project> {
  private projectSubject = new BehaviorSubject<Project[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();
  private countingSubject = new BehaviorSubject<boolean>(false);
  public nonEmpty$ = this.countingSubject.asObservable();
  constructor(private projectService: ProjectService) {}
  connect(collectionViewer: CollectionViewer): Observable<Project[] | ReadonlyArray<Project>> {
    return this.projectSubject.asObservable();
  }
  disconnect(collectionViewer: CollectionViewer): void {
    this.projectSubject.complete();
    this.loadingSubject.complete();
  }
  loadProjects() {
    this.loadingSubject.next(true);
    this.projectService.getProjects().pipe(
      catchError(err => {
        console.log(err);
        return throwError(err);
      }),
      finalize(() => {
        this.loadingSubject.next(false);
      }))
      .subscribe(
        projects => {
          if (projects.length > 0) {
            this.countingSubject.next(true);
          }
          this.projectSubject.next(projects);
        });
  }
}
