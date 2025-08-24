import { Component } from '@angular/core';
import { RouterLink } from '@angular/router'; // Import RouterLink for navigation

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [RouterLink], // Import RouterLink so you can use it in the template
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent {
  // No logic needed here for a static welcome page
}