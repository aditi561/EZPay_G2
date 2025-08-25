import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UpiTransferComponent } from './component/upi-transfer/upi-transfer.component';
import { FormsModule } from '@angular/forms';
import { BankTransferComponent } from './component/bank-transfer/bank-transfer.component';
import { PinEntryComponent } from './component/pin-entry/pin-entry.component';
import { WelcomeComponent } from './component/welcome/welcome.component';
import { TransactionStatusFailureComponent } from './component/transaction-status/transaction-status-failure.component';
import { TransactionStatusSuccessComponent } from './component/transaction-status/transaction-status-success.component';
// import { TransactionHistoryComponent } from './component/transaction-history/transaction-history.component';
import { NavbarComponent } from './component/navbar/navbar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
// import { BankTransactionHistoryComponent } from './component/bank-transaction-history/bank-transaction-history.component';

@NgModule({
  declarations: [
    WelcomeComponent,
    UpiTransferComponent,
    BankTransferComponent,
    PinEntryComponent,
    TransactionStatusFailureComponent,
    TransactionStatusSuccessComponent,
    //TransactionHistoryComponent,
    NavbarComponent,
    AppComponent,
    // BankTransactionHistoryComponent
  ], // This array should be empty or contain other non-standalone components
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}



