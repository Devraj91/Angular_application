import { TestBed, inject } from '@angular/core/testing';

import { PaymentuiService } from './paymentui.service';

describe('PaymentuiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PaymentuiService]
    });
  });

  it('should be created', inject([PaymentuiService], (service: PaymentuiService) => {
    expect(service).toBeTruthy();
  }));
});
