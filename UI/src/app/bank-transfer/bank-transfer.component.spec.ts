import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankTransfer } from './bank-transfer.component';

describe('BankTransfer', () => {
  let component: BankTransfer;
  let fixture: ComponentFixture<BankTransfer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BankTransfer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankTransfer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
