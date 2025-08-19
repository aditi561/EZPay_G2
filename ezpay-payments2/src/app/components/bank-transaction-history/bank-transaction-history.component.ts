import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BankServiceImpl } from '../../services/bank-services-imp.service';
import { BankTransaction } from '../../models/bank-transaction.model';

/**
 * Bank Transaction History Component
 *
 * Shows all bank transactions in tabular format with:
 * - Filtering by status
 * - Search by recipient account or name
 * - Transaction statistics
 */
@Component({
  selector: 'app-bank-transaction-history',
  templateUrl: './bank-transaction-history.component.html',
  styleUrls: ['./bank-transaction-history.component.css'],
  standalone: false
})
export class BankTransactionHistoryComponent implements OnInit {

  transactionHistory: BankTransaction[] = [];
  filteredTransactions: BankTransaction[] = [];
  isLoading: boolean = false;
  selectedFilter: string = 'all';
  searchTerm: string = '';

  constructor(
    private bankService: BankServiceImpl,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadTransactionHistory();
  }

  loadTransactionHistory(): void {
    this.isLoading = true;
    this.bankService.getAllTransactions().subscribe({
      next: (transactions) => {
        this.transactionHistory = transactions;
        this.filteredTransactions = transactions;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading bank transactions:', error);
        this.isLoading = false;
      }
    });
  }

  filterTransactions(status: string): void {
    this.selectedFilter = status;
    this.isLoading = true;

    if (status === 'all') {
      this.filteredTransactions = [...this.transactionHistory];
      this.isLoading = false;
    } else {
      this.bankService.getTransactionsByStatus(status as 'SUCCESS'|'FAILED'|'PENDING')
        .subscribe({
          next: (transactions) => {
            this.filteredTransactions = transactions;
            this.isLoading = false;
          },
          error: (err) => {
            console.error('Error filtering bank transactions:', err);
            this.isLoading = false;
          }
        });
    }
  }

  searchByRecipient(recipientName: string): void {
    this.searchTerm = recipientName;
    if (recipientName) {
      this.isLoading = true;
      this.bankService.getTransactionsByRecipient(recipientName).subscribe({
        next: (transactions) => {
          this.filteredTransactions = transactions;
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

  clearFilters(): void {
    this.selectedFilter = 'all';
    this.searchTerm = '';
    this.filteredTransactions = [...this.transactionHistory];
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'SUCCESS': return 'badge-success';
      case 'FAILED': return 'badge-danger';
      case 'PENDING': return 'badge-warning';
      default: return 'badge-secondary';
    }
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleString('en-IN', {
      dateStyle: 'short',
      timeStyle: 'short'
    });
  }

  getStatusCount(status: string): number {
    return this.filteredTransactions.filter(t => t.status === status).length;
  }

  goBack(): void {
    this.router.navigate(['/bank-transfer']);
  }

}
