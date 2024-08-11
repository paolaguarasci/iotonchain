import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LayoutVoidComponent } from './layout-void.component';

describe('LayoutVoidComponent', () => {
  let component: LayoutVoidComponent;
  let fixture: ComponentFixture<LayoutVoidComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LayoutVoidComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LayoutVoidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
