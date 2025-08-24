import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UpiTransferComponent } from './upi-transfer/upi-transfer.component';
import { FormsModule } from '@angular/forms';
import { BankTransferComponent } from './bank-transfer/bank-transfer.component';
import { PinEntryComponent } from './pin-entry/pin-entry.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { TransactionStatusFailureComponent } from './transaction-status/transaction-status-failure.component';
import { TransactionStatusSuccessComponent } from './transaction-status/transaction-status-success.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    WelcomeComponent,
    UpiTransferComponent,
    BankTransferComponent,
    PinEntryComponent,
    TransactionStatusFailureComponent,
    TransactionStatusSuccessComponent,
    AppComponent
  ], // This array should be empty or contain other non-standalone components
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}



