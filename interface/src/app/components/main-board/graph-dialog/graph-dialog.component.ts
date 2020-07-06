import {Component, Inject, OnInit, Optional} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {EntityService} from '../../../core/services/entity.service';

@Component({
  selector: 'app-graph-dialog',
  templateUrl: './graph-dialog.component.html',
  styleUrls: ['./graph-dialog.component.css']
})
export class GraphDialogComponent implements OnInit {
  nodes;
  edges;
  constructor(public dialogRef: MatDialogRef<GraphDialogComponent>, private entityService: EntityService,
              @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.entityService.getEntityGraph(this.data.entityId).subscribe(graph => {
      this.nodes = graph.nodes.map(item => {
        return {
          id: item.name,
          label: item.name,
          width: 200,
          height: 100
        };
      });
      this.edges = graph.edges.map(item => {
        let edgeName;
        if (item.type === 'relation') {
          edgeName = item.name;
        } else {
          edgeName = item.type;
        }
        return {
         id: item.name + item.sourceName + item.targetName,
         source: item.sourceName,
         target: item.targetName,
         label: edgeName
        };
      });
      console.log(this.nodes);
      console.log(this.edges);
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
