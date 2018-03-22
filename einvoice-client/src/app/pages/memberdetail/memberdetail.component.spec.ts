import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberdetailComponent } from './memberdetail.component';

describe('MemberdetailComponent', () => {
  let component: MemberdetailComponent;
  let fixture: ComponentFixture<MemberdetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MemberdetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
