import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BackeryComponent } from './backery.component';

describe('BackeryComponent', () => {
  let component: BackeryComponent;
  let fixture: ComponentFixture<BackeryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BackeryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BackeryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
