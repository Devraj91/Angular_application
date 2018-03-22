import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MembersubscriptionComponent } from './membersubscription.component';

describe('MembersubscriptionComponent', () => {
  let component: MembersubscriptionComponent;
  let fixture: ComponentFixture<MembersubscriptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MembersubscriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MembersubscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
