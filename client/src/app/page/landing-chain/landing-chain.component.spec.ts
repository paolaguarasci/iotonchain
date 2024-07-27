import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingChainComponent } from './landing-chain.component';

describe('LandingChainComponent', () => {
  let component: LandingChainComponent;
  let fixture: ComponentFixture<LandingChainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LandingChainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LandingChainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
