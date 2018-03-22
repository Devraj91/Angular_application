import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentPageExpireComponent } from './payment-page-expire.component';

describe('PaymentPageExpireComponent', () => {
  let component: PaymentPageExpireComponent;
  let fixture: ComponentFixture<PaymentPageExpireComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentPageExpireComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentPageExpireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
