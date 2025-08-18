import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BankServiceImpl } from '../services/bank-services-imp.service';
import { BankTransaction } from '../models/bank-transaction.model';

@Component({
  selector: 'app-bank-transfer',
  templateUrl: './bank-transfer.component.html',
  styleUrls: ['./bank-transfer.component.css'],
  standalone: false
})
export class BankTransferComponent implements OnInit {

  bankTransferForm!: FormGroup;
  isLoading: boolean = false;
  recentTransactions: BankTransaction[] = [];

  constructor(
    private fb: FormBuilder,
    private bankService: BankServiceImpl,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.bankTransferForm = this.fb.group({
      recipientAccount: ['', [Validators.required, Validators.pattern(/^\d{10,18}$/)]],
      confirmAccount: ['', [Validators.required]],
      ifscCode: ['', [Validators.required, Validators.pattern(/^[A-Z]{4}0[A-Z0-9]{6}$/)]],
      recipientName: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]],
      remarks: ['']
    }, { validators: this.accountMatchValidator });

    this.loadRecentTransactions();
  }

  /** Validator to ensure recipientAccount and confirmAccount match */
  accountMatchValidator(group: FormGroup) {
    const acc = group.get('recipientAccount')?.value;
    const confirmAcc = group.get('confirmAccount')?.value;
    return acc === confirmAcc ? null : { accountsMismatch: true };
  }

  /** Initiate bank transaction and navigate to PIN entry */
  initiateTransaction(): void {
    if (this.bankTransferForm.valid) {
      const transaction: Omit<BankTransaction, 'id' | 'status' | 'transactionDate'> = {
        senderAccountNumber: '1234567890', // Replace with logged-in user's account
        recipientAccountNumber: this.bankTransferForm.value.recipientAccount,
        ifscCode: this.bankTransferForm.value.ifscCode,
        recipientName: this.bankTransferForm.value.recipientName,
        amount: this.bankTransferForm.value.amount,
        remarks: this.bankTransferForm.value.remarks,
        transactionType: 'DEBIT'
      };

      this.isLoading = true;

      this.bankService.createTransaction(transaction).subscribe({
        next: (tx: BankTransaction) => {
          this.isLoading = false;
          // Navigate to PIN entry with transaction ID
          this.router.navigate(['/enter-pin'], {
            queryParams: { transactionId: tx.id, type: 'bank' }
          });
        },
        error: (err: any) => {
          this.isLoading = false;
          console.error('Error creating transaction:', err);
          alert('Failed to initiate transaction. Please try again.');
        }
      });
    } else {
      this.bankTransferForm.markAllAsTouched();
    }
  }

  /** Load recent transactions (last 5) */
  loadRecentTransactions(): void {
    this.bankService.getRecentTransactions(5).subscribe({
      next: (txs: BankTransaction[]) => this.recentTransactions = txs,
      error: (err: any) => console.error('Error loading recent transactions:', err)
    });
  }

  /** Navigate to full transaction history */
  toggleHistory(): void {
    this.router.navigate(['/bank-transaction-history']);
  }

  /** Get form controls */
  get f() {
    return this.bankTransferForm.controls;
  }

  /** Format date for display */
  formatDate(date: Date): string {
    return new Date(date).toLocaleString('en-IN', { dateStyle: 'short', timeStyle: 'short' });
  }

  /** Return CSS class based on transaction status */
  getStatusClass(status: 'SUCCESS' | 'FAILED' | 'PENDING'): string {
    switch (status) {
      case 'SUCCESS': return 'badge-success';
      case 'FAILED': return 'badge-danger';
      case 'PENDING': return 'badge-warning';
      default: return 'badge-secondary';
    }
  }
}
