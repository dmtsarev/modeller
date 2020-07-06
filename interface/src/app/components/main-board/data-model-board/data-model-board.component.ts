import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {EnoviaEntity} from '../../../model/enovia-entity';
import {EntityService} from '../../../core/services/entity.service';

@Component({
  selector: 'app-data-model-board',
  templateUrl: './data-model-board.component.html',
  styleUrls: ['./data-model-board.component.css']
})
export class DataModelBoardComponent implements OnInit {

  public enoviaEntity$: Observable<EnoviaEntity>;

  constructor(private entityService: EntityService) { }
  private nodeId = 0;
  ngOnInit() {
  }
  showElement(nodeId: number) {
    this.nodeId = nodeId;
    this.enoviaEntity$ = this.entityService.getEntity(nodeId);
  }
}
