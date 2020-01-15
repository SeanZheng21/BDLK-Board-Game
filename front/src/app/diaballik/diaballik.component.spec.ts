import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiaballikComponent } from './diaballik.component';

describe('DiaballikComponent', () => {
  let component: DiaballikComponent;
  let fixture: ComponentFixture<DiaballikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiaballikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiaballikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
