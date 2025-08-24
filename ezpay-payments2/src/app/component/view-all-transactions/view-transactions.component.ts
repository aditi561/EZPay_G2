import { Component, OnInit } from '@angular/core';

interface Transaction {
  id: number;
  recipientName: string;
  transactionDate: Date;
  amount: number;
  transactionType: 'CREDIT' | 'DEBIT';
  status: 'SUCCESS' | 'FAILED' | 'PENDING';
}

@Component({
  selector: 'app-view-transactions',
  templateUrl: './view-transactions.component.html',
  styleUrls: ['./view-transactions.component.css']
})
export class ViewTransactionsComponent implements OnInit {
  transactions: Transaction[] = [];
  filteredTransactions: Transaction[] = [];
  isLoading = true;
  searchTerm = '';
  activeFilter: 'ALL' | 'CREDIT' | 'DEBIT' = 'ALL';

  ngOnInit(): void {
    // This are Mock transactions (we can replace it with API call later)
    setTimeout(() => {
      this.transactions = [
        { id: 1, recipientName: 'John Doe', transactionDate: new Date('2025-08-20T10:30:00'), amount: 1500, transactionType: 'DEBIT', status: 'SUCCESS' },
        { id: 2, recipientName: 'Alice', transactionDate: new Date('2025-08-21T14:20:00'), amount: 2500, transactionType: 'DEBIT', status: 'SUCCESS' },
        { id: 3, recipientName: 'Bank Refund', transactionDate: new Date('2025-08-22T09:00:00'), amount: 500, transactionType: 'CREDIT', status: 'PENDING' }
      ];
      this.filteredTransactions = [...this.transactions];
      this.isLoading = false;
    }, 1000);
  }

  filterTransactions(type: 'ALL' | 'CREDIT' | 'DEBIT') {
    this.activeFilter = type;
    this.applyFilters();
  }

  applyFilters() {
    this.filteredTransactions = this.transactions.filter(tx => {
      const matchesType =
        this.activeFilter === 'ALL' || tx.transactionType === this.activeFilter;
      const matchesSearch =
        this.searchTerm === '' ||
        tx.recipientName.toLowerCase().includes(this.searchTerm.toLowerCase());
      return matchesType && matchesSearch;
    });
  }

  onSearch() {
    this.applyFilters();
  }

  formatDate(date: Date): string {
    return new Intl.DateTimeFormat('en-GB', {
      day: '2-digit', month: '2-digit', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    }).format(date);
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'SUCCESS': return 'badge-success';
      case 'FAILED': return 'badge-danger';
      case 'PENDING': return 'badge-warning';
      default: return 'badge-secondary';
    }
  }
}
