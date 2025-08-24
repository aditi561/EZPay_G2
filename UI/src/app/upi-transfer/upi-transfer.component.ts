import { Component } from '@angular/core';

@Component({
  selector: 'app-upi-transfer',
  templateUrl: './upi-transfer.component.html',
  standalone: false,
  styleUrls: ['./upi-transfer.component.css']
})
export class UpiTransferComponent {
  upiId: string;
  amount: number;
  remarks?: string;

  constructor() {
    this.upiId ='';
    this.amount = 0;
  }

  printData(){
    console.log('UPI ID:', this.upiId);
    console.log('Amount:', this.amount);
    console.log('Remarks:', this.remarks);
  }

 
}
