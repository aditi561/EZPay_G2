import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-status-failure',
  standalone: true,
  imports: [CommonModule],
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