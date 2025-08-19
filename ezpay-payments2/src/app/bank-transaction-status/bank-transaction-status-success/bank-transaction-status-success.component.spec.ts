import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankTransactionStatusSuccessComponent } from './bank-transaction-status-success.component';

describe('BankTransactionStatusSuccessComponent', () => {
  let component: BankTransactionStatusSuccessComponent;
  let fixture: ComponentFixture<BankTransactionStatusSuccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BankTransactionStatusSuccessComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankTransactionStatusSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
