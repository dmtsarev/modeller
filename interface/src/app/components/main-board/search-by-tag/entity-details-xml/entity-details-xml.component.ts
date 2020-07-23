import {Component, Input, OnInit} from '@angular/core';
import {EnoviaEntity} from '../../../../model/enovia-entity';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-entity-details-xml',
  templateUrl: './entity-details-xml.component.html',
  styleUrls: ['./entity-details-xml.component.css']
})
export class EntityDetailsXmlComponent implements OnInit {
  public ematrixConfig;
  public readonly configReadOnly = {
    editable: false,
    enableToolbar: false,
    showToolbar: false,
    minHeight: '300'
  };
  @Input() entity: EnoviaEntity;
  constructor(public dialog: MatDialog) {
    this.ematrixConfig = this.configReadOnly;
  }
  ngOnInit() {
  }
}
