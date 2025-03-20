import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../core/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

/**
 * EditCountriesComponent allows travellers to modify their associated list of countries.
 * <p>
 * This component provides functionality to load all available countries, load the traveller's visited countries,
 * and save changes by adding or removing countries. It also facilitates navigation to the dashboard.
 * </p>
 * 
 * <b>Features:</b>
 * - Load and display all available countries from the backend.
 * - Manage a traveller's travelled countries by adding/removing selections.
 * - Save changes and send updated data to the backend.
 * - Navigate back to the dashboard.
 * 
 * @see AuthService
 * @see HttpClient
 * @see Router
 * @see CommonModule
 */
@Component({
  selector: 'app-edit-countries',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-countries.component.html',
  styleUrls: ['./edit-countries.component.scss']
})
export class EditCountriesComponent implements OnInit {
  allCountries: any[] = [];
  selectedCountryIds: Set<number> = new Set<number>();
  initialCountryIds: Set<number> = new Set<number>();  // ✅ Declare initialCountryIds
  travellerId: number | null = null; // ✅ Ensure we have a traveler ID

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
   * This method loads the traveller's ID and initializes data related to countries.
   * </p>
   */
  ngOnInit() {
    this.loadTravellerId();
  }

  /** ✅ First, load the logged-in traveler's ID */
  loadTravellerId(): void {
    const travellerId = localStorage.getItem('travellerId'); // ✅ Get stored ID
    if (!travellerId) {
      console.error('Traveller ID not found!');
      return;
    }
  
    const headers = this.authService.getAuthHeaders();
    this.http.get<any>(`http://localhost:8080/api/travellers/${travellerId}`, { headers }).subscribe({
      next: (traveller) => {
        this.travellerId = traveller.id;
        this.loadAllCountries();
        this.loadTravelledCountries();
      },
      error: (err) => console.error('Error loading traveller ID', err)
    });
  }
  
  

  /** ✅ Load all available countries */
  loadAllCountries(): void {
    this.http.get<any[]>('http://localhost:8080/api/countries')
      .subscribe({
        next: (countries) => this.allCountries = countries,
        error: (err) => console.error('Error loading countries', err)
      });
  }

  /** ✅ Loads the list of countries the traveller has visited.
   * <p>
   * This method fetches the traveller's travelled countries and updates the selected countries list.
   * </p>
   */
  loadTravelledCountries(): void {
    if (!this.travellerId) return;

    const headers = this.authService.getAuthHeaders();
    this.http.get<any>(`http://localhost:8080/api/travellers/${this.travellerId}`, { headers })
      .subscribe({
        next: (traveller) => {
          if (traveller.traveledCountries && Array.isArray(traveller.traveledCountries)) {
            this.selectedCountryIds = new Set(traveller.traveledCountries.map((c: any) => c.id));
            this.initialCountryIds = new Set(this.selectedCountryIds); // ✅ Save initial state
          }
        },
        error: (err) => console.error("Error loading traveled countries", err)
      });
  }

  /** ✅ Checks if a specific country is currently selected.
   * 
   * @param countryId the ID of the country to check
   * @return true if the country is selected, false otherwise
   */
  isCountrySelected(countryId: number): boolean {
    return this.selectedCountryIds.has(countryId);
  }

  /** ✅ oggles the selection of a country.
   * <p>
   * Adds the country to the selection if it's not selected, or removes it if it is.
   * </p>
   * 
   * @param countryId the ID of the country to toggle
   */
  toggleCountrySelection(countryId: number): void {
    if (this.selectedCountryIds.has(countryId)) {
      this.selectedCountryIds.delete(countryId);
    } else {
      this.selectedCountryIds.add(countryId);
    }
  }

  /** ✅ Saves changes to the traveller's travelled countries by sending the updated list to the backend.
   * <p>
   * If successful, the user is redirected back to the dashboard.
   * </p>
   */
  saveChanges(): void {
    if (!this.travellerId) {
      console.error('❌ Traveller ID is missing.');
      return;
    }
  
    const headers = this.authService.getAuthHeaders();
    const requestBody = { countryIds: Array.from(this.selectedCountryIds) }; // ✅ Wrap the array inside an object
  
    console.log("📤 Sending payload:", JSON.stringify(requestBody));
  
    this.http.post(`http://localhost:8080/api/travellers/${this.travellerId}/add`, requestBody, { headers })
    .subscribe({
        next: (response) => {
          console.log('✅ Countries added successfully!', response);

          // ✅ Redirect user to the dashboard after saving
        this.router.navigate(['/dashboard']);
        },
        error: (err) => console.error('❌ Error saving changes', err)
      });
  }

   /**
   * Redirects the user to the dashboard.
   */
  goToDashboard(): void {
    this.router.navigate(['/dashboard']);
  }
  
  
}
