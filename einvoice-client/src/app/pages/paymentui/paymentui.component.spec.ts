import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentuiComponent } from './paymentui.component';

describe('PaymentuiComponent', () => {
  let component: PaymentuiComponent;
  let fixture: ComponentFixture<PaymentuiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentuiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentuiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
