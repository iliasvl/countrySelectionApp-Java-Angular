import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

/**
 * RegisterComponent handles user registration for the application.
 * <p>
 * This component allows users to input their registration details, validates the input, and sends the data
 * to the backend for registration. Upon successful registration, users are redirected to the login page.
 * </p>
 * 
 * <b>Features:</b>
 * - Input validation for required fields.
 * - Sends registration data to the backend service.
 * - Displays error or success messages based on the outcome.
 * - Provides navigation to the login page.
 * 
 * @see AuthService
 * @see Router
 * @see FormsModule
 * @see CommonModule
 */
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  firstname: string = '';
  lastname: string = '';
  username: string = '';
  password: string = '';
  dateOfBirth: string = '';
  gender: string = '';
  role: string = '';
  phone: string = '';
  address: string = '';
  city: string = '';
  country: string = '';

  errorMessage: string = '';
  successMessage: string = '';
  isSubmitting: boolean = false;

  /**
   * Initializes the component with required dependencies.
   * 
   * @param authService the {@link AuthService} for handling authentication and registration
   * @param router the {@link Router} for navigation
   */
  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Handles the registration process by validating input fields, preparing the payload,
   * and sending the data to the backend for registration.
   */
  onRegister(): void {
    if (!this.firstname || !this.lastname || !this.username || !this.password || 
        !this.dateOfBirth || !this.gender || !this.role || !this.phone || !this.address || 
        !this.city || !this.country) {
      this.errorMessage = "Please fill in all required fields.";
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    const userPayload = {
      isActive: true,
      user: { // ✅ Nest user-related fields inside "user"
        firstname: this.firstname,
        lastname: this.lastname,
        username: this.username,
        password: this.password,
        dateOfBirth: this.dateOfBirth,
        gender: this.gender,
        role: this.role
      },
      personalInfo: { // ✅ This is correctly structured
        phone: this.phone,
        address: this.address,
        city: this.city,
        country: this.country
      }
    };

    this.authService.register(userPayload).subscribe({
      next: (response) => {
        console.log("✅ Registration successful:", response);
        this.successMessage = 'Registration successful! Redirecting to login...';
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err) => {
        console.error("Registration error:", err);
        this.errorMessage = 'Registration failed. Please try again.';
        this.isSubmitting = false;
      }
    });
  }

  /**
   * Navigates the user to the login page.
   */
  goToLogin(): void {
    this.router.navigate(['/login']);  // Navigate to the register page
}
}
