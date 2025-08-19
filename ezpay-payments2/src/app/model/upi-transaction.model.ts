export interface UpiTransaction {
  id: string;
  upiId: string;
  amount: number;
  remarks?: string;
  transactionDate: Date;
  status: 'SUCCESS' | 'FAILED' | 'PENDING';
  transactionType: 'DEBIT' | 'CREDIT';
  referenceNumber: string;
}