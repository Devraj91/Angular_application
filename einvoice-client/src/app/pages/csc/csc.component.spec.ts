import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CscComponent } from './csc.component';

describe('CscComponent', () => {
  let component: CscComponent;
  let fixture: ComponentFixture<CscComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CscComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CscComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
