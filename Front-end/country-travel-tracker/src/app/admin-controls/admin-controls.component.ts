import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TravellerService } from '../core/services/admin-service/traveller.service';
import { AuthService } from '../core/services/auth-service/auth.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

/**
 * AdminControlsComponent provides administrative controls for managing travellers.
 * <p>
 * This component enables admin users to view traveller details, manage traveller data,
 * and perform actions such as filtering, pagination, deactivation, restoration, and deletion of travellers.
 * </p>
 * 
 * <b>Features:</b>
 * - View all travellers and fetch detailed information for individual travellers.
 * - Filter and paginate travellers for efficient data management.
 * - Perform admin actions like deactivating, restoring, and deleting travellers.
 * - Navigate back to the dashboard.
 * 
 * @see TravellerService
 * @see AuthService
 * @see Router
 * @see CommonModule
 * @see FormsModule
 */
@Component({
  selector: 'app-admin-controls',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-controls.component.html',
  styleUrl: './admin-controls.component.scss'
})
export class AdminControlsComponent implements OnInit {
  travellers: any[] = [];
  isAdmin: boolean = false;
  currentPage: number = 0; // Tracks the current page for pagination
  totalPages: number = 0;
  totalElements: number = 0;

  /**
   * Initializes the component with required dependencies.
   * 
   * @param travellerService the {@link TravellerService} for managing traveller data
   * @param authService the {@link AuthService} for handling authentication
   * @param router the {@link Router} for navigation
   */
  constructor(private travellerService: TravellerService, private authService: AuthService, private router: Router) {}

  /**
   * Lifecycle hook that runs after the component is initialized.
   * <p>
   * Checks if the user is an admin and loads traveller data if authorized.
   * </p>
   */
  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    if (this.isAdmin) {
      this.loadTravellers();
    }
  }

  // ✅ Fetches all travellers (Admin only)
  loadTravellers() {
    this.travellerService.getAllTravellers().subscribe((data) => {
      this.travellers = data;
    });
  }


  /** ✅ Retrieves detailed information for a specific traveller.
   * <p>
   * Displays the traveller's full name, personal information, and visited countries.
   * </p>
   * 
   * @param travellerId the ID of the traveller to fetch details for
   */
  getTravellerDetails(travellerId: number) {
    this.travellerService.getTraveller(travellerId).subscribe({
      next: (traveller) => {
        // Construct the full name
        const travellerName = `${traveller.user.firstname} ${traveller.user.lastname}`;
  
        // Get the personal information
        const personalInfo = `Address: ${traveller.personalInfo.address}, City: ${traveller.personalInfo.city}, Country: ${traveller.personalInfo.country}, Phone: ${traveller.personalInfo.phone}`;
  
        // Construct a list of country names
        const countriesVisited = traveller.countries.map((country: any) => country.name).join(", ");
  
        // Display the details
        alert(`Traveller Name: ${travellerName}\nPersonal Info: ${personalInfo}\nVisited Countries: ${countriesVisited}`);
        console.log('Traveller Details:', traveller); // Log the entire object for debugging
      },
      error: (err) => {
        console.error('Error fetching traveller details:', err);
        alert('Failed to fetch traveller details. Please try again.');
      }
    });
  }
  

  /** ✅ Fetches filtered and paginated travellers from the backend.
   * 
   * @param filters the filters to apply for querying travellers (e.g., UUID or active status)
   * @param page the page number to retrieve
   * @param pageSize the number of travellers per page (default is 10)
   */
  loadFilteredPaginatedTravellers(filters: { uuid?: string; active?: boolean }, page: number, pageSize: number = 10) {
    console.log(`📤 Sending API Request - Page: ${page}, Size: ${pageSize}`, filters);
  
    this.travellerService.getFilteredPaginatedTravellers(filters, page, pageSize).subscribe({
      next: (response) => {
        console.log(`📥 API Response - Page: ${page}`, response);
  
        this.travellers = response.data; 
        this.currentPage = page; // ✅ Manually setting the page, ignoring response.currentPage
        this.totalPages = response.totalPages; 
        this.totalElements = response.totalElements;
  
        console.log(`Filtered Paginated Travellers: Page ${this.currentPage}`, response);
      },
      error: (err) => {
        console.error('Error fetching filtered, paginated travellers:', err);
        alert('Failed to fetch filtered, paginated travellers. Please try again.');
      }
    });
  }
  
  
  /**
   * Navigates to the next page for pagination.
   * 
   * @param filters the filters applied for querying travellers
   */
  goToNextPage(filters: { uuid?: string; active?: boolean }) {
    if (this.currentPage + 1 < this.totalPages) {
      const nextPage = this.currentPage + 1; // Calculate next page
      console.log(`Requesting Page: ${nextPage}`); // Debugging
  
      this.loadFilteredPaginatedTravellers(filters, nextPage); // Fetch next page
    }
  }
  
  /**
   * Navigates to the previous page for pagination.
   * 
   * @param filters the filters applied for querying travellers
   */
  goToPreviousPage(filters: { uuid?: string; active?: boolean }) {
    if (this.currentPage > 0) {
      const prevPage = this.currentPage - 1; // Calculate previous page
      console.log(`Requesting Page: ${prevPage}`); // Debugging
  
      this.loadFilteredPaginatedTravellers(filters, prevPage); // Fetch previous page
    }
  }
  
  
  
  

  /** ✅ Deactivates a traveller's account.
   * 
   * @param travellerId the ID of the traveller to deactivate
   */
  deactivateTraveller(travellerId: number) {
    this.travellerService.deactivateTraveller(travellerId).subscribe({
      next: (response) => {
        alert(response); // Displays "Traveller was deactivated successfully."
        this.loadTravellers(); // Refresh the list after deactivating
      },
      error: (err) => {
        console.error('Error deactivating traveller:', err);
        alert('Failed to deactivate traveller. Please try again.');
      }
    });
  }
  

  /** ✅ Restores a previously deactivated traveller's account.
   * 
   * @param travellerId the ID of the traveller to restore
   */
  restoreTraveller(travellerId: number) {
    this.travellerService.restoreTraveller(travellerId).subscribe({
      next: (response) => {
        alert(response); // Displays "Traveller was restored successfully."
        this.loadTravellers(); // Refresh the list after restoring
      },
      error: (err) => {
        console.error('Error restoring traveller:', err);
        alert('Failed to restore traveller. Please try again.');
      }
    });
  }
  

  /** ✅ Permanently deletes a traveller from the system.
   * 
   * @param travellerId the ID of the traveller to delete
   */
  deleteTraveller(travellerId: number) {
    if (confirm('Are you sure you want to delete this traveller?')) {
      this.travellerService.deleteTraveller(travellerId).subscribe({
        next: (response) => {
          alert(response); // Displays "The user is deleted."
          this.loadTravellers(); // Refresh the list after deletion
        },
        error: (err) => {
          console.error('Error deleting traveller:', err);
          alert('Failed to delete traveller. Please try again.');
        }
      });
    }
  }

  /**
   * Navigates back to the dashboard.
   */
  goToDashboard(): void {
    this.router.navigate(['/dashboard']);
  }

    // --------- Not used because loadFilteredPaginatedTravellers covers both filtering and pagination ---------
  // loadPaginatedTravellers(page: number = this.currentPage, size: number = 2) {
  //   this.travellerService.getPaginatedTravellers(page, size).subscribe({
  //     next: (data) => {
  //       this.travellers = data.content;
  //       console.log('Pagination Info:', data); // Logs the paginated response
  //     },
  //     error: (err) => {
  //       console.error('Error fetching paginated travellers:', err);
  //       alert('Failed to load travellers. Please try again.');
  //     }
  //   });
  // }

  // nextPage() {
  //   this.currentPage++; // Increment the current page
  //   this.loadPaginatedTravellers(this.currentPage);
  // }

  // previousPage() {
  //   if (this.currentPage > 0) {
  //     this.currentPage--; // Decrement the current page
  //     this.loadPaginatedTravellers(this.currentPage);
  //   }
  // }

  // --------- Not used because loadFilteredPaginatedTravellers covers both filtering and pagination ---------
  // loadFilteredTravellers(filters: any) {
  //   this.travellerService.getFilteredTravellers(filters).subscribe({
  //     next: (travellers) => {
  //       this.travellers = travellers;
  //       console.log('Filtered Travellers:', travellers); // Log the filtered list
  //     },
  //     error: (err) => {
  //       console.error('Error fetching filtered travellers:', err);
  //       alert('Failed to fetch filtered travellers. Please try again.');
  //     }
  //   });
  // }
  
}
