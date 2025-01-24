import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListUserClickComponent } from './list-user-click.component';

describe('ListUserClickComponent', () => {
  let component: ListUserClickComponent;
  let fixture: ComponentFixture<ListUserClickComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListUserClickComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListUserClickComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
