import { Component } from '@angular/core';

@Component({
  selector: 'app-pin-entry',
  templateUrl: './pin-entry.component.html',
  standalone:false,
  styleUrls: ['./pin-entry.component.css']
})
export class PinEntryComponent {
  pin: string = '';
  pinVerified: boolean = false;
  errorMessage: string = '';

  verifyPin(): void {
    if (this.pin.length !== 4 || !/^\d+$/.test(this.pin)) {
      this.errorMessage = 'Please enter a valid 4-digit PIN.';
    } else {
      this.errorMessage = '';
      this.pinVerified = true;
      alert('PIN verified successfully!');
      // Here you can navigate or call API for further verification
    }
  }
}
