import {Tag} from '../../model/tag';
import {DataSource} from '@angular/cdk/table';
import {CollectionViewer} from '@angular/cdk/collections';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {catchError, finalize} from 'rxjs/operators';
import {ToolsService} from '../../core/services/tools.service';

export class TagsDataSource implements DataSource<Tag> {

  private tagSubject = new BehaviorSubject<Tag[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  public loading$ = this.loadingSubject.asObservable();

  constructor(private toolService: ToolsService) {}

  connect(collectionViewer: CollectionViewer): Observable<Tag[] | ReadonlyArray<Tag>> {
    return this.tagSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.tagSubject.complete();
    this.loadingSubject.complete();
  }

  loadTags() {
    this.loadingSubject.next(true);
    this.toolService.getTags().pipe(
      catchError(err => {
        return throwError(err);
      }),
      finalize(() => {
        this.loadingSubject.next(false);
      }))
      .subscribe(tags => this.tagSubject.next(tags));
  }
}
