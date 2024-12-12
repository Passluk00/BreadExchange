import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FallingBreadComponent } from './falling-bread.component';

describe('FallingBreadComponent', () => {
  let component: FallingBreadComponent;
  let fixture: ComponentFixture<FallingBreadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FallingBreadComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FallingBreadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
