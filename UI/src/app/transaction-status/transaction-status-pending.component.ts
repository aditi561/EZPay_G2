import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-status-pending',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transaction-status-pending.component.html',
  styleUrls: ['./transaction-status-pending.component.css']
})
export class TransactionStatusPendingComponent implements OnInit, OnDestroy {
  timeRemaining = 60; // 1 minute in seconds
  private timerInterval: any;

  ngOnInit() {
    this.startTimer();
  }

  ngOnDestroy() {
    if (this.timerInterval) {
      clearInterval(this.timerInterval);
    }
  }

  private startTimer() {
    this.timerInterval = setInterval(() => {
      if (this.timeRemaining > 0) {
        this.timeRemaining--;
      } else {
        clearInterval(this.timerInterval);
      }
    }, 1000);
  }

  formatTime(seconds: number): string {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
  }

  onBack() {
    console.log('Back clicked');
  }

  onCheckStatus() {
    console.log('Check status clicked');
  }

  onGoHome() {
    console.log('Go to home clicked');
  }
}