import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaroselloInfinitoComponent } from './carosello-infinito.component';

describe('CaroselloInfinitoComponent', () => {
  let component: CaroselloInfinitoComponent;
  let fixture: ComponentFixture<CaroselloInfinitoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaroselloInfinitoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaroselloInfinitoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
