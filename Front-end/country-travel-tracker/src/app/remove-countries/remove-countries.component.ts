import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../core/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

/**
 * RemoveCountriesComponent allows travellers to remove countries from their list of visited countries.
 * <p>
 * This component provides functionality to fetch the traveller's travelled countries, display them,
 * and remove selected countries. It also includes navigation back to the dashboard.
 * </p>
 * 
 * <b>Features:</b>
 * - Load and display the traveller's travelled countries.
 * - Remove a selected country from the traveller's list.
 * - Reload the updated data dynamically after making changes.
 * - Navigate back to the dashboard.
 * 
 * @see AuthService
 * @see HttpClient
 * @see Router
 * @see CommonModule
 */
@Component({
  selector: 'app-remove-countries',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './remove-countries.component.html',
  styleUrls: ['./remove-countries.component.scss']
})
export class RemoveCountriesComponent implements OnInit {
  travelledCountries: any[] = [];
  travellerId: string | null = null;

  /**
   * Initializes the component with required dependencies.
   * 
   * @param http the {@link HttpClient} instance for making HTTP requests
   * @param authService the {@link AuthService} for handling authentication and headers
   * @param router the {@link Router} for navigation
   */
  constructor(private http: HttpClient, private authService: AuthService, private router: Router) {}

  /**
   * Lifecycle hook that runs after the component is initialized.
   * <p>
   * This method retrieves the traveller's ID and loads their travelled countries data.
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
   * Loads the traveller's data, including their list of travelled countries.
   * <p>
   * Retrieves data from the backend and updates the `travelledCountries` property.
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

          if (data.countries) {
            this.travelledCountries = data.countries;
            
            // ✅ Check if country data contains flag and code
            this.travelledCountries.forEach(country => {
              console.log(`🌍 ${country.name} - Code: ${country.code}, Flag: ${country.flagUrl ?? '❌ MISSING'}`);
            });

          } else {
            console.warn("⚠️ No 'countries' field in API response!");
          }
        },
        error: (err) => console.error('❌ Error loading traveller data:', err)
      });
  }

  /**
   * Removes a specific country from the traveller's travelled countries list.
   * <p>
   * Sends a PATCH request to the backend and reloads the data on success.
   * </p>
   * 
   * @param countryId the ID of the country to remove
   */
  removeCountry(countryId: number): void {
    if (!this.travellerId) return;
  
    const headers = this.authService.getAuthHeaders();
    
    this.http.patch(`http://localhost:8080/api/travellers/${this.travellerId}/remove-country/${countryId}`, {}, { 
      headers, 
      responseType: 'text' // ✅ Fix JSON parse error
    })
    .subscribe({
      next: () => {
        console.log(`✅ Country ID ${countryId} removed!`);
  
        // ✅ Reload data immediately
        this.loadTravellerData();
  
        // ✅ Force component to reload
        this.router.navigateByUrl('/dashboard', { skipLocationChange: true }).then(() => {
          this.router.navigate(['/remove-countries']);
        });
      },
      error: (err) => console.error("❌ Error removing country", err)
    });
  }
  
  
  /**
   * Navigates the user back to the dashboard.
   */
  goToDashboard(): void {
    this.router.navigate(['/dashboard']);
  }
}
