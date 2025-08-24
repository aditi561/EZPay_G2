import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankTransactionStatusFailureComponent } from './bank-transaction-status-failure.component';

describe('BankTransactionStatusFailureComponent', () => {
  let component: BankTransactionStatusFailureComponent;
  let fixture: ComponentFixture<BankTransactionStatusFailureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BankTransactionStatusFailureComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankTransactionStatusFailureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
