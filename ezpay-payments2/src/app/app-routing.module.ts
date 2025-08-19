import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { UpiTransferComponent } from './components/upi-transfer/upi-transfer.component';
import { BankTransferComponent } from './components/bank-transfer/bank-transfer.component';
import { PinEntryComponent } from './components/pin-entry/pin-entry.component';
import { TransactionStatusSuccessComponent } from './components/transaction-status/transaction-status-success.component';
import { TransactionStatusFailureComponent } from './components/transaction-status/transaction-status-failure.component';
import { TransactionHistoryComponent } from './components/transaction-history/transaction-history.component';
import { BankTransactionHistoryComponent } from './components/bank-transaction-history/bank-transaction-history.component';
import { BankTransactionStatusSuccessComponent} from './components/bank-transaction-status-success/bank-transaction-status-success.component';
import { BankTransactionStatusFailureComponent } from './components/bank-transaction-status-failure/bank-transaction-status-failure.component';

const routes: Routes = [
  { path: '', component: WelcomeComponent },

  // Transfer routes
  { path: 'upi', component: UpiTransferComponent },
  { path: 'bank-transfer', component: BankTransferComponent },

  // PIN entry route
  { path: 'enter-pin', component: PinEntryComponent },

  // Transaction status routes
  { path: 'transaction-status/success', component: TransactionStatusSuccessComponent },
  { path: 'transaction-status/failure', component: TransactionStatusFailureComponent },

  // Bank-specific transaction status
  { path: 'bank-transaction-status/success', component: BankTransactionStatusSuccessComponent },
  { path: 'bank-transaction-status/failure', component: BankTransactionStatusFailureComponent },

  // History routes
  { path: 'transaction-history', component: TransactionHistoryComponent },
  { path: 'bank-transaction-history', component: BankTransactionHistoryComponent },

  // Fallback
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
