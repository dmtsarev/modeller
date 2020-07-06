import {Release} from '../../model/release';
import {DataSource} from '@angular/cdk/table';
import {CollectionViewer} from '@angular/cdk/collections';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {ReleaseService} from '../../core/services/release.service';
import {catchError, finalize} from 'rxjs/operators';

export class ReleaseDataSource implements DataSource<Release> {
  private releaseSubject = new BehaviorSubject<Release[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();
  constructor(private releaseService: ReleaseService) {}
  connect(collectionViewer: CollectionViewer): Observable<Release[] | ReadonlyArray<Release>> {
    return this.releaseSubject.asObservable();
  }
  disconnect(collectionViewer: CollectionViewer): void {
    this.releaseSubject.complete();
    this.loadingSubject.complete();
  }
  loadReleases(projectId: number) {
    this.loadingSubject.next(true);
    this.releaseService.getReleases(projectId).pipe(
        catchError(err => {
          console.log(err);
          return throwError(err);
        }),
        finalize(() => {
          this.loadingSubject.next(false);
        }))
      .subscribe(
      releases => this.releaseSubject.next(releases));
  }
}
