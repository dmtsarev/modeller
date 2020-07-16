import { TestBed } from '@angular/core/testing';

import { SearchByTagService } from './search-by-tag.service';

describe('SearchByTagService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SearchByTagService = TestBed.get(SearchByTagService);
    expect(service).toBeTruthy();
  });
});
