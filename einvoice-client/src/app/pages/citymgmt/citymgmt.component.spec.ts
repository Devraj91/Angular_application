import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CitymgmtComponent } from './citymgmt.component';

describe('CitymgmtComponent', () => {
  let component: CitymgmtComponent;
  let fixture: ComponentFixture<CitymgmtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CitymgmtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CitymgmtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
