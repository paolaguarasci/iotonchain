import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BatchSingleComponent } from './batch-single.component';

describe('BatchSingleComponent', () => {
  let component: BatchSingleComponent;
  let fixture: ComponentFixture<BatchSingleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BatchSingleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BatchSingleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
