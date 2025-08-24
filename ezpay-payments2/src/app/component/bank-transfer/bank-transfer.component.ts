import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BankServiceImpl } from '../../service/bank-service-imp.service';
import { BankTransaction } from '../../model/bank-transaction.model';

/**
 * BankTransferComponent
 * ---------------------
 * This component handles the Bank Transfer feature of the application.
 * It provides a form to initiate a transfer, validates inputs,
 * shows recent transactions, and redirects the user to the PIN entry screen.
 * 
 * Author: Aditi Roy
 */
@Component({
  selector: 'app-bank-transfer',
  templateUrl: './bank-transfer.component.html',
  styleUrls: ['./bank-transfer.component.css'],
  standalone: false
})
export class BankTransferComponent implements OnInit {

  /** Reactive form for bank transfer */
  bankTransferForm!: FormGroup;

  /** Flag to indicate loading state during transaction submission */
  isLoading: boolean = false;

  /** Stores recent transactions (last 5) for quick display */
  recentTransactions: BankTransaction[] = [];

  constructor(
    private fb: FormBuilder,
    private bankService: BankServiceImpl,
    private router: Router
  ) {}

  /** Initialize form and load recent transactions */
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

  /**
   * Custom validator to ensure recipientAccount and confirmAccount fields match
   * @param group - FormGroup containing the form controls
   */
  accountMatchValidator(group: FormGroup) {
    const acc = group.get('recipientAccount')?.value;
    const confirmAcc = group.get('confirmAccount')?.value;
    return acc === confirmAcc ? null : { accountsMismatch: true };
  }

  /**
   * Initiates a bank transfer transaction by calling service
   * On success → navigates to PIN entry screen with transaction ID
   * On failure → shows an alert
   */
  initiateTransaction(): void {
    if (this.bankTransferForm.valid) {
      const transaction: Omit<BankTransaction, 'id' | 'status' | 'transactionDate'> = {
        senderAccountNumber: '1234567890', // TODO: Replace with logged-in user's account
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
          // Navigate to PIN entry screen with transaction ID & type
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
      // Highlight invalid fields
      this.bankTransferForm.markAllAsTouched();
    }
  }

  /**
   * Loads the most recent 5 bank transactions
   */
  loadRecentTransactions(): void {
    this.bankService.getRecentTransactions(5).subscribe({
      next: (txs: BankTransaction[]) => this.recentTransactions = txs,
      error: (err: any) => console.error('Error loading recent transactions:', err)
    });
  }

  /**
   * Navigates to the full bank transaction history screen
   */
  toggleHistory(): void {
    this.router.navigate(['/bank-transaction-history']);
  }

  /**
   * Utility getter for form controls
   */
  get f() {
    return this.bankTransferForm.controls;
  }

  /**
   * Utility to format date for UI display
   * @param date - Transaction date
   * @returns formatted date string
   */
  formatDate(date: Date): string {
    return new Date(date).toLocaleString('en-IN', { dateStyle: 'short', timeStyle: 'short' });
  }

  /**
   * Returns appropriate CSS class based on transaction status
   * @param status - SUCCESS | FAILED | PENDING
   */
  getStatusClass(status: 'SUCCESS' | 'FAILED' | 'PENDING'): string {
    switch (status) {
      case 'SUCCESS': return 'badge-success';
      case 'FAILED': return 'badge-danger';
      case 'PENDING': return 'badge-warning';
      default: return 'badge-secondary';
    }
  }
}
