import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UpiServiceImpl } from '../../services/upi-service-impl.service';
import { UpiTransaction } from '../../models/upi-transaction.model';

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
    this.loadTransactionHistory();
    this.loadRecentTransactions();
  }

  /**
   * Loads complete transaction history from the service
   * Updates the transactionHistory array with all transactions
   * Sets loading state during the operation
   */
  loadTransactionHistory(): void {
    this.isLoading = true;
    this.upiService.getAllTransactions().subscribe({
      next: (transactions) => {
        this.transactionHistory = transactions;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading transactions:', error);
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
  loadRecentTransactions(): void {
    this.upiService.getRecentTransactions(5).subscribe({
      next: (transactions) => {
        this.recentTransactions = transactions;
      },
      error: (error) => {
        console.error('Error loading recent transactions:', error);
      }
    });
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
    if (this.upiId && this.amount > 0) {
      const newTransaction = {
        upiId: this.upiId,
        amount: this.amount,
        remarks: this.remarks
      };

      this.isLoading = true;
      this.upiService.createTransaction(newTransaction).subscribe({
        next: (transaction) => {
          console.log('Transaction created:', transaction);
          this.isLoading = false;
          // Navigate to PIN entry with transaction ID
          this.router.navigate(['/enter-pin'], {
            queryParams: { transactionId: transaction.id }
          });
        },
        error: (error) => {
          console.error('Error creating transaction:', error);
          this.isLoading = false;
          alert('Failed to initiate transaction. Please try again.');
        }
      });
    }
  }

  /**
   * Navigates to the transaction history page
   * Called when user clicks "View All" button
   */
  toggleHistory(): void {
    this.router.navigate(['/transaction-history']);
  }

  /**
   * Filters transactions by their status
   *
   * @param status Transaction status to filter by ('all' | 'SUCCESS' | 'FAILED' | 'PENDING')
   */
  filterTransactions(status: string): void {
    this.selectedFilter = status;
    this.isLoading = true;
    
    if (status === 'all') {
      this.loadTransactionHistory();
    } else {
      this.upiService.getTransactionsByStatus(status as 'SUCCESS' | 'FAILED' | 'PENDING').subscribe({
        next: (transactions) => {
          this.transactionHistory = transactions;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error filtering transactions:', error);
          this.isLoading = false;
        }
      });
    }
  }

  /**
   * Searches transactions by UPI ID
   *
   * @param searchUpiId The UPI ID to search for
   */
  searchByUpiId(searchUpiId: string): void {
    if (searchUpiId) {
      this.isLoading = true;
      this.upiService.getTransactionsByUpiId(searchUpiId).subscribe({
        next: (transactions) => {
          this.transactionHistory = transactions;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error searching transactions:', error);
          this.isLoading = false;
        }
      });
    } else {
      this.loadTransactionHistory();
    }
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
   * Debug method to print form data to console
   * Used for development and testing purposes
   */
  printData(): void {
    console.log('UPI ID:', this.upiId);
    console.log('Amount:', this.amount);
    console.log('Remarks:', this.remarks);
  }
}
