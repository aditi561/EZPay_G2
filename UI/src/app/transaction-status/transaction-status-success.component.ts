import { Component } from '@angular/core';

@Component({
  selector: 'app-transaction-status-success',
  standalone: true,
  templateUrl: './transaction-status-success.component.html',
  styleUrls: ['./transaction-status-success.component.css']
})
export class TransactionStatusSuccessComponent {
  onDone() {
    console.log('Done clicked');
  }
}