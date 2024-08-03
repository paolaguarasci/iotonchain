import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotarizeListComponent } from './notarize-list.component';

describe('NotarizeListComponent', () => {
  let component: NotarizeListComponent;
  let fixture: ComponentFixture<NotarizeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotarizeListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotarizeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
