import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListBakeryComponent } from './list-bakery.component';

describe('ListBakeryComponent', () => {
  let component: ListBakeryComponent;
  let fixture: ComponentFixture<ListBakeryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListBakeryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListBakeryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
