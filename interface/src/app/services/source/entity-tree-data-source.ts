import {BehaviorSubject, merge, Observable} from 'rxjs';
import {CollectionViewer, SelectionChange} from '@angular/cdk/collections';
import {map} from 'rxjs/operators';
import {EnoviaEntity} from '../../model/enovia-entity';
import {EnoviaEntityFlatNode} from '../../model/enovia-entity-flat-node';
import {FlatTreeControl} from '@angular/cdk/tree';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material';
import {EntityService} from '../../core/services/entity.service';

export class EntityTreeDataSource {

  get data(): EnoviaEntityFlatNode[] { return this.enoviaEntitySubject.value; }

  set data(value: EnoviaEntityFlatNode[]) {
    this.treeControl.dataNodes = value;
    this.enoviaEntitySubject.next(value);
  }

  private searchWord = '';
  private enoviaEntitySubject = new BehaviorSubject<EnoviaEntityFlatNode[]>([]);
  treeControl: FlatTreeControl<EnoviaEntityFlatNode> = new FlatTreeControl<EnoviaEntityFlatNode>(node => node.level,
      node => node.expandable);
  private flatTreeDataSource: MatTreeFlatDataSource<EnoviaEntity, EnoviaEntityFlatNode>;
  transformFunction: (node: EnoviaEntity, nodeLevel: number) => EnoviaEntityFlatNode;
  private readonly treeFlattener: MatTreeFlattener<EnoviaEntity, EnoviaEntityFlatNode>;
  private rootNodes = ['attributeDef', 'interface', 'relationship', 'type', 'policy', 'program'];



  constructor(private entityService: EntityService, private releaseId: number) {
    this.transformFunction = (node: EnoviaEntity, level: number) => {
      return new EnoviaEntityFlatNode(node.id, node.type, node.entityName, level);
    };
    this.treeFlattener = new MatTreeFlattener<EnoviaEntity, EnoviaEntityFlatNode>(this.transformFunction,
        node => node.level,
      node => node.expandable,
      null);
    this.flatTreeDataSource = new MatTreeFlatDataSource<EnoviaEntity, EnoviaEntityFlatNode>(this.treeControl,
      this.treeFlattener);
    this.data = this.rootNodes.map(name => new EnoviaEntityFlatNode(0, 'type', name, 0, true));
  }

  handleTreeControl(change: SelectionChange<EnoviaEntityFlatNode>) {
    if (change.added) {
      change.added.forEach(node => this.toggleNode(node, true));
    }
    if (change.removed) {
      change.removed.slice().reverse().forEach(node => this.toggleNode(node, false));
    }
  }

  toggleNode(node: EnoviaEntityFlatNode, expand: boolean) {
    const index = this.data.indexOf(node);
    node.isLoading = true;
    if (expand) {
      this.entityService.getEntities(this.releaseId, node.name, this.searchWord).toPromise().then(items => {
        const tmpItems = items.map(item => this.transformFunction(item, 1));
        this.data.splice(index + 1, 0, ...tmpItems);
        node.isLoading = false;
        this.enoviaEntitySubject.next(this.data);
      });
    } else {
      let count = 0;
      for (let i = index + 1; i < this.data.length
      && this.data[i].level > node.level; i++, count++) {}
      this.data.splice(index + 1, count);
      node.isLoading = false;
    }
    this.enoviaEntitySubject.next(this.data);
  }

  connect(collectionViewer: CollectionViewer): Observable<EnoviaEntityFlatNode[]> {
    this.treeControl.expansionModel.changed.subscribe( change => { if ((change as SelectionChange<EnoviaEntityFlatNode>).added ||
      (change as SelectionChange<EnoviaEntityFlatNode>).removed) {
      this.handleTreeControl(change as SelectionChange<EnoviaEntityFlatNode>);
      }
    });
    return merge(collectionViewer.viewChange, this.enoviaEntitySubject).pipe(map(() => this.data));
  }

  rebuildTreeControlForSearch(searchWord: string) {
    this.searchWord = searchWord;
    this.treeControl.collapseAll();
  }

  disconnect(collectionViewer: CollectionViewer) {this.enoviaEntitySubject.complete(); }
}
