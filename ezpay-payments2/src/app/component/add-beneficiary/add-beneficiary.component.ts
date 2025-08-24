import { Component } from '@angular/core';

@Component({
  selector: 'app-add-beneficiary',
  standalone: false,
  templateUrl: './add-beneficiary.component.html',
  styleUrl: './add-beneficiary.component.css'
})
export class AddBeneficiaryComponent {
  beneficiary = {
    name: '',
    accountNumber: '',
    bankName: '',
    ifscCode: '',
    email: '',
    phone: ''
  };

  beneficiaries: any[] = [];

  submitForm() {
    if (this.beneficiary.name && this.beneficiary.accountNumber && this.beneficiary.bankName && this.beneficiary.ifscCode) {
      this.beneficiaries.push({ ...this.beneficiary });
      this.beneficiary = { name: '', accountNumber: '', bankName: '', ifscCode: '', email: '', phone: '' };
    }
  }
}
