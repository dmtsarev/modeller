import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {EnoviaEntityFlatNode} from '../../../model/enovia-entity-flat-node';
import {EntityService} from '../../../core/services/entity.service';
import {EntityTreeDataSource} from '../../../services/source/entity-tree-data-source';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-data-model-tree',
  templateUrl: './data-model-tree.component.html',
  styleUrls: ['./data-model-tree.component.css']
})
export class DataModelTreeComponent implements OnInit {
  @Output() elementSelected = new EventEmitter<number>();
  dataSource: EntityTreeDataSource;
  hasChild = (_: number, nodeData: EnoviaEntityFlatNode) => nodeData.expandable;

  constructor(private entityService: EntityService, private route: ActivatedRoute) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('releaseId');
    console.log(id);
    this.dataSource = new EntityTreeDataSource(this.entityService, Number(id));
  }
  nodeClicked(node: EnoviaEntityFlatNode) {
    this.elementSelected.emit(node.id);
  }

  searchWord(val: string) {
    this.dataSource.rebuildTreeControlForSearch(val);
  }
}
