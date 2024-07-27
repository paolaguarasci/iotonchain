import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraceOneBatchComponent } from './trace-one-batch.component';

describe('TraceOneBatchComponent', () => {
  let component: TraceOneBatchComponent;
  let fixture: ComponentFixture<TraceOneBatchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TraceOneBatchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TraceOneBatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
