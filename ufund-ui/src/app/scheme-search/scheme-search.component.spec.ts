import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchemeSearchComponent } from './scheme-search.component';

describe('SchemeSearchComponent', () => {
  let component: SchemeSearchComponent;
  let fixture: ComponentFixture<SchemeSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchemeSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchemeSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
