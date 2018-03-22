import { TestBed, inject } from '@angular/core/testing';

import { AddressmgmtService } from './addressmgmt.service';

describe('AddressmgmtService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AddressmgmtService]
    });
  });

  it('should be created', inject([AddressmgmtService], (service: AddressmgmtService) => {
    expect(service).toBeTruthy();
  }));
});
