import { Component, OnInit } from '@angular/core';
import {ReleaseDataSource} from '../../../services/source/release-data-source';
import {ReleaseService} from '../../../core/services/release.service';
import {MatSnackBar} from '@angular/material';
import {ActivatedRoute, Router} from '@angular/router';
import {Release} from '../../../model/release';

@Component({
  selector: 'app-release-history',
  templateUrl: './release-history.component.html',
  styleUrls: ['./release-history.component.css']
})
export class ReleaseHistoryComponent implements OnInit {
  dataSource: ReleaseDataSource;
  displayedColumns = ['seqNo', 'releaseName', 'description'];
  private projectId;

  constructor(private releaseService: ReleaseService, private snackBar: MatSnackBar, private route: ActivatedRoute, private router: Router) {  }

  ngOnInit() {
    this.route.parent.paramMap.subscribe(paramMap => {
      this.projectId = paramMap.get('projectId');
      this.dataSource = new ReleaseDataSource(this.releaseService);
      try {
        this.dataSource.loadReleases(Number(this.projectId));
      } catch (e) {
        this.openShackBarOnError(e.toString(), '');
      }
    });
  }
  openShackBarOnError(message: string, action: string) {
    this.snackBar.open(message, action, {duration: 2000});
  }

  getRelease(release: Release) {
    if (release.status !== 'CACHED') {
      this.releaseService.getRelease(release.releaseId).subscribe(() => {
        this.router.navigate( ['/projects/' + this.projectId + '/releases/' + release.releaseId]);
      });
    } else {
      this.router.navigate( ['/projects/' + this.projectId + '/releases/' + release.releaseId]);
    }
  }
}
