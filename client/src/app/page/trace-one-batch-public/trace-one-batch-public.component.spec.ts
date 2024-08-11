import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraceOneBatchPublicComponent } from './trace-one-batch-public.component';

describe('TraceOneBatchPublicComponent', () => {
  let component: TraceOneBatchPublicComponent;
  let fixture: ComponentFixture<TraceOneBatchPublicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TraceOneBatchPublicComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TraceOneBatchPublicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
