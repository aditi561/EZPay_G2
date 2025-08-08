import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { PinEntryComponent } from './pin-entry.component';

describe('PinEntryComponent', () => {
  let component: PinEntryComponent;
  let fixture: ComponentFixture<PinEntryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PinEntryComponent ],
      imports: [ FormsModule ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PinEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should show error message for invalid PIN', () => {
    component.pin = '12';
    component.verifyPin();
    expect(component.errorMessage).toBe('Please enter a valid 4-digit PIN.');
  });

  it('should clear error message for valid PIN', () => {
    component.pin = '1234';
    component.verifyPin();
    expect(component.errorMessage).toBe('');
  });
});
