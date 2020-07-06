import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {distinctUntilChanged, filter, map} from 'rxjs/operators';
import {BreadCrumb} from '../../../model/bread-crumb';
import {of} from 'rxjs';
import {EventsServiceService} from '../../../services/events-service.service';

@Component({
  selector: 'app-bread-crumbs',
  templateUrl: './bread-crumbs.component.html',
  styleUrls: ['./bread-crumbs.component.css']
})
export class BreadCrumbsComponent implements OnInit {
  breadcrumbs$;
  constructor(private eventService: EventsServiceService) { }

  ngOnInit() {
    this.breadcrumbs$ = this.eventService.routeChanged.pipe(map(event => this.buildBreadCrumb(event)));
  /*  this.breadcrumbs$ = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      distinctUntilChanged(),
      map(event => this.buildBreadCrumb(this.activatedRoute.root))
    );*/
  }

  buildBreadCrumb(route: ActivatedRoute, url: string = '/',
                  breadcrumbs: Array<BreadCrumb> = []): Array<BreadCrumb> {
    // If no routeConfig is avalailable we are on the root path
    // const label = route.routeConfig ? route.routeConfig.data['breadcrumb'] : 'Home';
    // const path = route.snapshot.url.length > 0 ? route.snapshot.url[route.snapshot.url.length - 1] : ''
    // console.log(path);
    // In the routeConfig the complete path is not available,
    // so we rebuild it each time
    const newBreadcrumbs = [...breadcrumbs];
    url === '' ? url = '/' : '';
    let nextUrl = '';
    route.snapshot.url.forEach(item => {
      nextUrl = `${url}${item.path}/`;
      url = nextUrl;
      // finalUrl += nextUrl;
      const breadcrumb: BreadCrumb = {
        label: item.path,
        url: nextUrl
      };
      newBreadcrumbs.push(breadcrumb);
    });
    if (route.firstChild) {
      // If we are not on our current path yet,
      // there will be more children to look after, to build our breadcumb
      return this.buildBreadCrumb(route.firstChild, nextUrl, newBreadcrumbs);
    }
    return newBreadcrumbs;
  }

}
