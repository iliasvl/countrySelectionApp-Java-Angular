import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveCountriesComponent } from './remove-countries.component';

describe('RemoveCountriesComponent', () => {
  let component: RemoveCountriesComponent;
  let fixture: ComponentFixture<RemoveCountriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemoveCountriesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RemoveCountriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
