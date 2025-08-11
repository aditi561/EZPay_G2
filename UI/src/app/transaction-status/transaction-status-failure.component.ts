import { Component } from '@angular/core';

@Component({
  selector: 'app-transaction-status-failure',
  standalone: true,
  templateUrl: './transaction-status-failure.component.html',
  styleUrls: ['./transaction-status-failure.component.css']
})
export class TransactionStatusFailureComponent {
  onBack() {
    console.log('Back clicked');
  }

  onTryAgain() {
    console.log('Try again clicked');
  }

  onGoHome() {
    console.log('Go to home clicked');
  }
}