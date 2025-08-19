import { Component , OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-bank-transfer',
  standalone: false,
  templateUrl: './bank-transfer.component.html',
  styleUrls: ['./bank-transfer.component.css']
})
export class BankTransferComponent implements OnInit {

  bankTransferForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.bankTransferForm = this.fb.group({
      recipientAccount: ['', [Validators.required, Validators.pattern(/^\d{10,18}$/)]],
      confirmAccount: ['', [Validators.required]],
      ifscCode: ['', [Validators.required, Validators.pattern(/^[A-Z]{4}0[A-Z0-9]{6}$/)]],
      recipientName: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]],
      remarks: ['']
    }, { validators: this.accountMatchValidator });
  }

  accountMatchValidator(group: FormGroup) {
    const acc = group.get('recipientAccount')?.value;
    const confirmAcc = group.get('confirmAccount')?.value;
    return acc === confirmAcc ? null : { accountsMismatch: true };
  }

  onSubmit(): void {
    if (this.bankTransferForm.valid) {
      console.log(this.bankTransferForm.value);
      alert('Payment initiated!');
    } else {
      this.bankTransferForm.markAllAsTouched();
    }
  }

  get f() {
    return this.bankTransferForm.controls;
  }
}
