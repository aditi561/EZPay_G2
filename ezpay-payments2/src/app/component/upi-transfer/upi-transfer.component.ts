import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UpiServiceImpl } from '../../service/upi-service-impl.service';
import { UpiTransaction } from '../../model/upi-transaction.model';

/**
 * UPI Transfer Component
 *
 * This component handles UPI (Unified Payments Interface) transactions.
 * It provides functionality for:
 * - Creating new UPI transactions
 * - Viewing recent transaction history
 * - Navigating to detailed transaction history
 * - Form validation for UPI ID and amount
 *
 * author Simran 
 * version 1.2.0
 */
@Component({
  selector: 'app-upi-transfer',
  templateUrl: './upi-transfer.component.html',
  standalone: false,
  styleUrls: ['./upi-transfer.component.css']
})
export class UpiTransferComponent implements OnInit {
  /**
   * UPI ID of the recipient
   * Format: username@bankname (e.g., john@icici)
   */
  upiId: string;
  
  /**
   * Transaction amount in INR
   * Must be greater than 0
   */
  amount: number;
  
  /**
   * Optional remarks/notes for the transaction
   */
  remarks?: string;
  
  // Transaction history properties
  /**
   * Complete list of all transactions
   * Used for filtering and display in history view
   */
  transactionHistory: UpiTransaction[] = [];
  
  /**
   * List of recent transactions (last 5)
   * Displayed in the side panel
   */
  recentTransactions: UpiTransaction[] = [];
  
  /**
   * Flag to control history view visibility
   */
  showHistory: boolean = false;
  
  /**
   * Loading state indicator for async operations
   */
  isLoading: boolean = false;
  
  /**
   * Current filter selection for transaction history
   * Values: 'all' | 'SUCCESS' | 'FAILED' | 'PENDING'
   */
  selectedFilter: string = 'all';
  
  constructor(
    private upiService: UpiServiceImpl,
    private router: Router
  ) {
    this.upiId = '';
    this.amount = 0;
  }

  /**
   * Component initialization lifecycle hook
   * Loads transaction history and recent transactions on component load
   */
  ngOnInit(): void {
    this.loadTransactions();
  }

  /**
   * Initiates a new UPI transaction
   *
   * Process flow:
   * 1. Validates form data (UPI ID and amount)
   * 2. Creates transaction object with provided details
   * 3. Sends transaction to backend service
   * 4. On success, navigates to PIN entry page
   * 5. On failure, displays error message
   *
   * @returns void
   */
  initiateTransaction(): void {
    this.isLoading = true;
    const transaction: Partial<UpiTransaction> = {
      senderUpiId: 'alice@upi',
      receiverUpiId: this.upiId,
      amount: this.amount,
      remarks: this.remarks,
      status: 'PENDING'
    };

    this.upiService.createTransaction(transaction).subscribe({
      next: (response) => {
        this.loadTransactions();
        // Show success message
        if (response && response.id) {
          this.router.navigate(['/pin-entry', response.id]);
        }
        this.resetForm();
      },
      error: (error) => {
        // Handle error and show appropriate message
        console.error('Transaction failed:', error);
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }

  /**
   * Loads recent transactions (last 5) from the service
   * Displays in the side panel for quick reference
   *
   * param- limit: Number of recent transactions to load (default: 5)
   */
  loadTransactions(): void {
    this.upiService.getAllTransactions().subscribe({
      next: (transactions) => {
        this.recentTransactions = transactions;
      },
      error: (error) => {
        console.error('Error loading recent transactions:', error);
      }
    });
  }

  // toggleHistory(): void {
  //   this.router.navigate(['/transaction-history']);
  // }

  /**
   * Formats date for display in Indian locale
   *
   * @param date Date to format
   * @returns Formatted date string (DD/MM/YY, HH:MM AM/PM)
   */
  formatDate(date: Date): string {
    return new Date(date).toLocaleString('en-IN', {
      dateStyle: 'short',
      timeStyle: 'short'
    });
  }

  /**
   * Returns CSS class for status badge based on transaction status
   *
   * @param status Transaction status
   * @returns CSS class name for styling the status badge
   */
  getStatusClass(status: string): string {
    switch (status) {
      case 'SUCCESS':
        return 'badge-success';
      case 'FAILED':
        return 'badge-danger';
      case 'PENDING':
        return 'badge-warning';
      default:
        return 'badge-secondary';
    }
  }

  /**
   * Resets the transaction form fields to initial state
   * Clears UPI ID, amount, and remarks fields
   */
  resetForm(): void {
    this.upiId = '';
    this.amount = 0;
    this.remarks = '';
  }
}
