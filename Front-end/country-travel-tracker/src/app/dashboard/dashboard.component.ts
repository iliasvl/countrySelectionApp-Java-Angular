import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../core/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

/**
 * DashboardComponent is the main dashboard view of the application.
 * <p>
 * This component is responsible for displaying the traveller's data, such as the list of visited countries,
 * total travelled/untravelled countries, and providing navigation to different features like editing countries,
 * removing countries, and admin controls.
 * </p>
 * 
 * <b>Features:</b>
 * - Load traveller data and display it dynamically.
 * - Handle user authentication and logout functionality.
 * - Navigate to additional pages such as "Edit Countries", "Remove Countries", and "Admin Controls".
 * 
 * @see AuthService
 * @see HttpClient
 * @see Router
 * @see CommonModule
 */
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  countries: any[] = [];
  totalTravelled = 0;
  totalUntravelled = 0;
  travellerId: string | null = null;

  /**
   * Initializes the component with required dependencies.
   * 
   * @param http the {@link HttpClient} instance for making HTTP requests
   * @param authService the {@link AuthService} for handling authentication
   * @param router the {@link Router} for navigation
   */
  constructor(private http: HttpClient, public authService: AuthService, private router: Router) {}

  /**
   * Lifecycle hook that runs after the component is initialized.
   * <p>
   * This method attempts to retrieve the traveller's ID and, if found, loads their data.
   * </p>
   */
  ngOnInit(): void {
    this.travellerId = this.authService.getTravellerId();

    if (!this.travellerId) {
      console.error("❌ Traveller ID not found! Retrying...");
      setTimeout(() => {
        this.travellerId = this.authService.getTravellerId();
        if (this.travellerId) {
          console.log("✅ Traveller ID found:", this.travellerId);
          this.loadTravellerData();
        } else {
          console.error("❌ Traveller ID still not found after retry!");
        }
      }, 1000);
    } else {
      console.log("✅ Traveller ID:", this.travellerId);
      this.loadTravellerData();
    }
  }


  /**
   * Loads traveller-specific data, such as visited countries, total travelled, and total untravelled.
   * <p>
   * Makes an HTTP GET request to fetch traveller data and handles the response or errors.
   * </p>
   */
  loadTravellerData(): void {
    if (!this.travellerId) {
      console.error("🚨 Cannot load data, travellerId is null!");
      return;
    }

    const headers = this.authService.getAuthHeaders();

    this.http.get<any>(`http://localhost:8080/api/travellers/${this.travellerId}`, { headers })
      .subscribe({
        next: (data) => {
          console.log("📥 Received traveller data:", data);
          console.log("📥 Dashboard - Full Countries JSON:", JSON.stringify(data.countries, null, 2));



          if (data.countries) {
            this.countries = data.countries;  // Store travelled countries correctly
            this.totalTravelled = data.countries.length;
          } else {
            console.warn("⚠️ No 'countries' field in API response!");
          }

          const totalCountriesInDatabase = 249;
          this.totalUntravelled = totalCountriesInDatabase - this.totalTravelled;
        },
        error: (err) => console.error('❌ Error loading traveller data:', err)
      });
  }

  /**
   * Logs the user out of the application.
   * <p>
   * This method clears the authentication token and redirects the user to the login page.
   * </p>
   */
  logout(): void {
    this.authService.logout();  // Call AuthService to clear token
    this.router.navigate(['/login']);  // Redirect to login page
  }
  
  /**
   * Navigates the user to the "Edit Countries" page.
   */
  goToEditCountries(): void {
    this.router.navigate(['/edit-countries']);
  }

  /**
   * Navigates the user to the "Remove Countries" page.
   */
  goToRemoveCountries(): void {
    this.router.navigate(['/remove-countries']);
  }


  /**
   * Only admins can view and access this route
   * Navigates the user to the "Admin Controls" page.
   * <p>
   * This functionality is restricted to admin users only.
   * </p>
   */
  navigateToAdminControls() {
    this.router.navigate(['/admin-controls']);
  }
  
}