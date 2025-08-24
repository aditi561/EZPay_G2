import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay } from 'rxjs/operators';
import { UpiTransaction } from '../model/upi-transaction.model';
import { UpiServiceDAO } from './upi-service-dao';

@Injectable({
  providedIn: 'root'
})
export class UpiServiceImpl implements UpiServiceDAO {
  
  // Dummy data storage using ArrayList pattern
  private transactions: UpiTransaction[] = [
    {
      id: 'TXN001',
      upiId: 'john.doe@paytm',
      amount: 1500,
      remarks: 'Grocery shopping',
      transactionDate: new Date('2024-01-15T10:30:00'),
      status: 'SUCCESS',
      transactionType: 'DEBIT',
      referenceNumber: 'REF2024011501'
    },
    {
      id: 'TXN002',
      upiId: 'alice@phonepe',
      amount: 2500,
      remarks: 'Rent payment',
      transactionDate: new Date('2024-01-14T14:20:00'),
      status: 'SUCCESS',
      transactionType: 'DEBIT',
      referenceNumber: 'REF2024011402'
    },
    {
      id: 'TXN003',
      upiId: 'bob@googlepay',
      amount: 500,
      remarks: 'Coffee',
      transactionDate: new Date('2024-01-13T09:15:00'),
      status: 'FAILED',
      transactionType: 'DEBIT',
      referenceNumber: 'REF2024011303'
    },
    {
      id: 'TXN004',
      upiId: 'john.doe@paytm',
      amount: 3000,
      remarks: 'Electricity bill',
      transactionDate: new Date('2024-01-12T16:45:00'),
      status: 'SUCCESS',
      transactionType: 'DEBIT',
      referenceNumber: 'REF2024011204'
    },
    {
      id: 'TXN005',
      upiId: 'sarah@bhim',
      amount: 750,
      remarks: 'Book purchase',
      transactionDate: new Date('2024-01-11T11:30:00'),
      status: 'PENDING',
      transactionType: 'DEBIT',
      referenceNumber: 'REF2024011105'
    },
    {
      id: 'TXN006',
      upiId: 'mike@paytm',
      amount: 5000,
      remarks: 'Salary credit',
      transactionDate: new Date('2024-01-10T08:00:00'),
      status: 'SUCCESS',
      transactionType: 'CREDIT',
      referenceNumber: 'REF2024011006'
    },
    {
      id: 'TXN007',
      upiId: 'alice@phonepe',
      amount: 1200,
      remarks: 'Mobile recharge',
      transactionDate: new Date('2024-01-09T13:25:00'),
      status: 'SUCCESS',
      transactionType: 'DEBIT',
      referenceNumber: 'REF2024010907'
    },
    {
      id: 'TXN008',
      upiId: 'john.doe@paytm',
      amount: 800,
      remarks: 'Restaurant bill',
      transactionDate: new Date('2024-01-08T20:10:00'),
      status: 'SUCCESS',
      transactionType: 'DEBIT',
      referenceNumber: 'REF2024010808'
    },
    {
      id: 'TXN009',
      upiId: 'emma@googlepay',
      amount: 2000,
      remarks: 'Gift',
      transactionDate: new Date('2024-01-07T15:00:00'),
      status: 'SUCCESS',
      transactionType: 'CREDIT',
      referenceNumber: 'REF2024010709'
    },
    {
      id: 'TXN010',
      upiId: 'bob@googlepay',
      amount: 450,
      remarks: 'Taxi fare',
      transactionDate: new Date('2024-01-06T18:30:00'),
      status: 'SUCCESS',
      transactionType: 'DEBIT',
      referenceNumber: 'REF2024010610'
    }
  ];

  constructor() { }

  // Create a new UPI transaction
  createTransaction(transaction: Partial<UpiTransaction>): Observable<UpiTransaction> {
    const newTransaction: UpiTransaction = {
      id: this.generateTransactionId(),
      upiId: transaction.upiId || '',
      amount: transaction.amount || 0,
      remarks: transaction.remarks,
      transactionDate: new Date(),
      status: 'PENDING',
      transactionType: 'DEBIT',
      referenceNumber: this.generateReferenceNumber()
    };

    // Add to the beginning of the array (most recent first)
    this.transactions.unshift(newTransaction);
    
    // Simulate API delay
    return of(newTransaction).pipe(delay(1000));
  }

  // Get all transactions
  getAllTransactions(): Observable<UpiTransaction[]> {
    // Return a copy of the array to prevent external modifications
    return of([...this.transactions]).pipe(delay(500));
  }

  // Get transaction by ID
  getTransactionById(id: string): Observable<UpiTransaction | undefined> {
    const transaction = this.transactions.find(t => t.id === id);
    return of(transaction).pipe(delay(300));
  }

  // Get transactions by UPI ID
  getTransactionsByUpiId(upiId: string): Observable<UpiTransaction[]> {
    const filteredTransactions = this.transactions.filter(
      t => t.upiId.toLowerCase() === upiId.toLowerCase()
    );
    return of(filteredTransactions).pipe(delay(400));
  }

  // Get transactions by status
  getTransactionsByStatus(status: 'SUCCESS' | 'FAILED' | 'PENDING'): Observable<UpiTransaction[]> {
    const filteredTransactions = this.transactions.filter(t => t.status === status);
    return of(filteredTransactions).pipe(delay(400));
  }

  // Update transaction status
  updateTransactionStatus(id: string, status: 'SUCCESS' | 'FAILED' | 'PENDING'): Observable<UpiTransaction> {
    const transactionIndex = this.transactions.findIndex(t => t.id === id);
    
    if (transactionIndex === -1) {
      return throwError(() => new Error('Transaction not found'));
    }

    this.transactions[transactionIndex].status = status;
    return of(this.transactions[transactionIndex]).pipe(delay(500));
  }

  // Delete transaction (for testing purposes)
  deleteTransaction(id: string): Observable<boolean> {
    const initialLength = this.transactions.length;
    this.transactions = this.transactions.filter(t => t.id !== id);
    const deleted = this.transactions.length < initialLength;
    return of(deleted).pipe(delay(300));
  }

  // Get transaction history for a specific date range
  getTransactionsByDateRange(startDate: Date, endDate: Date): Observable<UpiTransaction[]> {
    const filteredTransactions = this.transactions.filter(t => {
      const transactionDate = new Date(t.transactionDate);
      return transactionDate >= startDate && transactionDate <= endDate;
    });
    return of(filteredTransactions).pipe(delay(400));
  }

  // Helper method to generate transaction ID
  private generateTransactionId(): string {
    const timestamp = Date.now();
    const random = Math.floor(Math.random() * 1000);
    return `TXN${timestamp}${random}`;
  }

  // Helper method to generate reference number
  private generateReferenceNumber(): string {
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const random = Math.floor(Math.random() * 100).toString().padStart(2, '0');
    return `REF${year}${month}${day}${random}`;
  }

  // Additional helper methods for statistics
  getTotalTransactionAmount(): Observable<number> {
    const total = this.transactions
      .filter(t => t.status === 'SUCCESS')
      .reduce((sum, t) => {
        if (t.transactionType === 'DEBIT') {
          return sum - t.amount;
        } else {
          return sum + t.amount;
        }
      }, 0);
    return of(total).pipe(delay(200));
  }

  getRecentTransactions(limit: number = 5): Observable<UpiTransaction[]> {
    const recent = this.transactions.slice(0, limit);
    return of(recent).pipe(delay(300));
  }

  // Method to simulate processing a pending transaction
  processTransaction(id: string): Observable<UpiTransaction> {
    const transactionIndex = this.transactions.findIndex(t => t.id === id);
    
    if (transactionIndex === -1) {
      return throwError(() => new Error('Transaction not found'));
    }

    // Simulate random success/failure (80% success rate)
    const isSuccess = Math.random() > 0.2;
    this.transactions[transactionIndex].status = isSuccess ? 'SUCCESS' : 'FAILED';
    
    return of(this.transactions[transactionIndex]).pipe(delay(2000));
  }
}