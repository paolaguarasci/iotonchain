import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateOneBatchComponent } from './create-one-batch.component';

describe('CreateOneBatchComponent', () => {
  let component: CreateOneBatchComponent;
  let fixture: ComponentFixture<CreateOneBatchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateOneBatchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateOneBatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
