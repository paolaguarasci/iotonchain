import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComapnyBatchListComponent } from './product-list.component';

describe('ProductListComponent', () => {
  let component: ComapnyBatchListComponent;
  let fixture: ComponentFixture<ComapnyBatchListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComapnyBatchListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComapnyBatchListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
