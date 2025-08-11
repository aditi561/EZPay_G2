import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-status-success',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transaction-status-success.component.html',
  styleUrls: ['./transaction-status-success.component.css']
})
export class TransactionStatusSuccessComponent {
  onDone() {
    console.log('Done clicked');
  }
}