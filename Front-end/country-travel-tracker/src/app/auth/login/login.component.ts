import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

/**
 * LoginComponent handles user authentication for the application.
 * <p>
 * This component allows users to input their login credentials, validates the input,
 * and sends the data to the backend for authentication. Upon successful login,
 * the user is redirected to the dashboard.
 * </p>
 * 
 * <b>Features:</b>
 * - Input validation for required fields.
 * - Sends login credentials to the backend service.
 * - Displays error messages for failed login attempts.
 * - Provides navigation to the registration page.
 * 
 * @see AuthService
 * @see Router
 * @see FormsModule
 * @see CommonModule
 */
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
    username: string = '';
    password: string = '';
    errorMessage: string = '';
    isSubmitting: boolean = false;

    
    /**
   * Initializes the component with required dependencies.
   * 
   * @param authService the {@link AuthService} for handling authentication
   * @param router the {@link Router} for navigation
   */
  constructor(private authService: AuthService, private router: Router) {}


  /**
   * Handles the login process by sending the username and password to the backend.
   * <p>
   * If authentication is successful, the user is redirected to the dashboard.
   * In case of failure, an error message is displayed.
   * </p>
   */
  onSubmit(): void {
    this.isSubmitting = true;
    this.errorMessage = '';  // Clear previous errors
  
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        console.log("✅ Login successful:", response);
        if (response?.token) {
          this.router.navigate(['/dashboard']);  // Redirect on success
        } else {
          console.error("❌ No token received!");
          this.errorMessage = 'Authentication failed. No token received.';
          this.isSubmitting = false;
        }
      },
      error: (err) => {
        console.error("Login error:", err);  // ✅ Debugging error response
        this.errorMessage = 'Invalid credentials. Please try again.';
        this.isSubmitting = false;
      }
    });

  
  }


  /**
   * Navigates the user to the registration page.
   */
  goToRegister(): void {
    this.router.navigate(['/register']);  // Navigate to the register page
}

}
