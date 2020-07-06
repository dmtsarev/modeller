import { Injectable } from '@angular/core';
import {BehaviorSubject, Subscription} from 'rxjs';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {distinctUntilChanged, filter, map} from 'rxjs/operators';

@Injectable()
export class EventsServiceService {
  public routeChanged: BehaviorSubject<ActivatedRoute>;
  private subscription = new Subscription();
  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
    this.routeChanged = new BehaviorSubject<ActivatedRoute>(this.activatedRoute);
    this.subscription.add(
      this.router.events.pipe(
        filter(event => event instanceof NavigationEnd),
        distinctUntilChanged(),
        map(event => {
          console.log(event);
          this.routeChanged.next(this.activatedRoute);
        })
      ).subscribe()
    );
  }
}
