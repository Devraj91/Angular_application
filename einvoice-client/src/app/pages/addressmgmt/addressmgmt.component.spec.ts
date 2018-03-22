import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddressmgmtComponent } from './addressmgmt.component';

describe('AddressmgmtComponent', () => {
  let component: AddressmgmtComponent;
  let fixture: ComponentFixture<AddressmgmtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddressmgmtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddressmgmtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
