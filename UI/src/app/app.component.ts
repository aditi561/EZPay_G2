import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router'; // Import RouterOutlet to display routed components
import { CommonModule } from '@angular/common'; // Import CommonModule for directives like ngIf, ngFor, etc.
import { WelcomeComponent } from './welcome/welcome.component'; // Import the WelcomeComponent

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, WelcomeComponent], // The welcome component is a dependency, so we import it
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ezpay';
}