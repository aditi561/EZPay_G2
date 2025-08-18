import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeComponent } from './welcome/welcome.component';
import { UpiTransferComponent } from './upi-transfer/upi-transfer.component';
import { BankTransferComponent } from './bank-transfer/bank-transfer.component';
import { PinEntryComponent } from './pin-entry/pin-entry.component';
import { TransactionStatusSuccessComponent } from './transaction-status/transaction-status-success.component';
import { TransactionStatusFailureComponent } from './transaction-status/transaction-status-failure.component';
import { TransactionStatusPendingComponent } from './transaction-status/transaction-status-pending.component';
import { TransactionHistoryComponent } from './transaction-history/transaction-history.component';

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  // Define other routes here as you build them
  { path: 'upi', component: UpiTransferComponent },
  { path: 'bank-transfer', component: BankTransferComponent },
  { path: 'enter-pin' , component: PinEntryComponent},
  { path: 'transaction-status/success', component: TransactionStatusSuccessComponent},
  { path: 'transaction-status/failure', component: TransactionStatusFailureComponent},
  { path: 'transaction-status/pending', component: TransactionStatusPendingComponent },
  { path: 'transaction-history', component: TransactionHistoryComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
