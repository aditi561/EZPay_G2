import { Component } from '@angular/core';

@Component({
  selector: 'app-bank-transfer',
  standalone: false,
  templateUrl: './bank-transfer.component.html',
  styleUrls: ['./bank-transfer.component.css']
})
export class BankTransferComponent {
  accountNumber: string = '';
  ifscCode: string = '';
  amount: number = 0;
  remarks: string = '';
  recipientName: string = '';
  confirmAccountNumber: string = '';

  submitTransfer(): void {
    if (!this.accountNumber || !this.ifscCode || this.amount <= 0 || !this.recipientName) {
      alert('Please fill in all fields correctly.');
      return;
    }
    // Here you can call an API to process the bank transfer
    alert(`Bank transfer initiated for ${this.amount} to ${this.recipientName}.`);
    // Reset form fields after submission
    this.accountNumber = '';
    this.ifscCode = '';
    this.amount = 0;
    this.remarks = '';
    this.recipientName = '';
  }
}
