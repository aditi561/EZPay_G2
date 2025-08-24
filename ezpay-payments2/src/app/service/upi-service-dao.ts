import { Observable } from 'rxjs';
import { UpiTransaction } from '../model/upi-transaction.model';

export interface UpiServiceDAO {
  // Create a new UPI transaction
  createTransaction(transaction: Partial<UpiTransaction>): Observable<UpiTransaction>;
  
  // Get all transactions
  getAllTransactions(): Observable<UpiTransaction[]>;
  
  // Get transaction by ID
  getTransactionById(id: string): Observable<UpiTransaction | undefined>;
  
  // Get transactions by UPI ID
  getTransactionsByUpiId(upiId: string): Observable<UpiTransaction[]>;
  
  // Get transactions by status
  getTransactionsByStatus(status: 'SUCCESS' | 'FAILED' | 'PENDING'): Observable<UpiTransaction[]>;
  
  // Update transaction status
  updateTransactionStatus(id: string, status: 'SUCCESS' | 'FAILED' | 'PENDING'): Observable<UpiTransaction>;
  
  // Delete transaction (for testing purposes)
  deleteTransaction(id: string): Observable<boolean>;
  
  // Get transaction history for a specific date range
  getTransactionsByDateRange(startDate: Date, endDate: Date): Observable<UpiTransaction[]>;
}