import { TestBed } from '@angular/core/testing';

import { TagTableService } from './tag-table.service';

describe('TagTableService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TagTableService = TestBed.get(TagTableService);
    expect(service).toBeTruthy();
  });
});
