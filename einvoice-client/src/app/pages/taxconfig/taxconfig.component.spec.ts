import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TaxconfigComponent } from './taxconfig.component';

describe('TaxconfigComponent', () => {
  let component: TaxconfigComponent;
  let fixture: ComponentFixture<TaxconfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaxconfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaxconfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
