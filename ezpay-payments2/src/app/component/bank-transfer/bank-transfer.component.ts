import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BankServiceImpl } from '../../service/bank-service-imp.service';
import { BankTransaction } from '../../model/bank-transaction.model';

/**
 * BankTransferComponent
 * ---------------------
 * Handles bank transfer operations including:
 * - Input validation via reactive forms
 * - Initiating bank transfers
 * - Displaying recent and full transaction history
 * - Navigation to PIN entry for authentication
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

  /** Recipient bank ID (format: username@bankname) */
  bankId: string;

  /** Transaction amount in INR (must be > 0) */
  amount: number;

  /** Reactive form for capturing bank transfer inputs */
  bankTransferForm!: FormGroup;

  /** Loading indicator for async operations */
  isLoading: boolean = false;

  /** Stores all transactions for history view */
  transactionHistory: BankTransaction[] = [];

  /** Stores the last 5 recent transactions */
  recentTransactions: BankTransaction[] = [];

  /** Controls visibility of transaction history */
  showHistory: boolean = false;

  /** Selected filter for transaction history ('all' | 'SUCCESS' | 'FAILED' | 'PENDING') */
  selectedFilter: string = 'all';

  constructor(
    private fb: FormBuilder,
    private bankService: BankServiceImpl,
    private router: Router
  ) {
    this.bankId = '';
    this.amount = 0;
  }

  /** Initializes form with validators and loads recent and all transactions */
  ngOnInit(): void {
    this.bankTransferForm = this.fb.group({
      recipientAccount: ['', [Validators.required, Validators.pattern(/^\d{10,18}$/)]],
      confirmAccount: ['', [Validators.required]],
      ifscCode: ['', [Validators.required, Validators.pattern(/^[A-Z]{4}0[A-Z0-9]{6}$/)]],
      recipientName: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]],
      remarks: ['']
    }, { validators: this.accountMatchValidator });

    this.loadBankTransactionHistory();
    this.loadRecentTransactions();
  }

  /**
   * Loads complete bank transaction history from the service
   * Updates transactionHistory array and manages loading state
   */
  loadBankTransactionHistory(): void {
    this.isLoading = true;
    this.bankService.getAllTransactions().subscribe({
      next: (transactions) => {
        this.transactionHistory = transactions;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading bank transactions:', error);
        this.isLoading = false;
      }
    });
  }

  /**
   * Loads the last 5 recent bank transactions
   */
  loadRecentTransactions(): void {
    this.bankService.getRecentTransactions(5).subscribe({
      next: (txs: BankTransaction[]) => this.recentTransactions = txs,
      error: (err: any) => console.error('Error loading recent transactions:', err)
    });
  }

  /**
   * Custom validator to ensure recipient account matches confirm account
   * @param group FormGroup containing account fields
   * @returns null if match, { accountsMismatch: true } if mismatch
   */
  accountMatchValidator(group: FormGroup) {
    const acc = group.get('recipientAccount')?.value;
    const confirmAcc = group.get('confirmAccount')?.value;
    return acc === confirmAcc ? null : { accountsMismatch: true };
  }

  /**
   * Initiates a bank transaction
   * - Creates a transaction object
   * - Calls bank service to process the transaction
   * - On success, navigates to PIN entry page
   * - On error, shows an alert and logs error
   */
  initiateTransaction(): void {
    if (this.bankTransferForm.valid) {
      const transaction: Omit<BankTransaction, 'id' | 'status' | 'transactionDate'> = {
        senderAccountNumber: '1234567890', // TODO: replace with logged-in user's account
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

  /** Navigate to the full transaction history page */
  toggleHistory(): void {
    this.router.navigate(['/bank-transaction-history']);
  }

  /** Returns form controls for template access */
  get f() {
    return this.bankTransferForm.controls;
  }

  /**
   * Formats transaction date into human-readable string
   * @param date Date object
   * @returns formatted string (en-IN)
   */
  formatDate(date: Date): string {
    return new Date(date).toLocaleString('en-IN', { dateStyle: 'short', timeStyle: 'short' });
  }

  /**
   * Returns CSS class for transaction status badges
   * @param status SUCCESS | FAILED | PENDING
   * @returns corresponding CSS class string
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
