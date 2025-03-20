import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth-service/auth.service';



@Injectable({
  providedIn: 'root',
})
export class TravellerService {
  private baseUrl = 'http://localhost:8080/api/travellers'; // Backend API base URL

  constructor(private http: HttpClient, private authService: AuthService) {} // Inject AuthService

  // ✅ Get all travelers (Admin only)
  getAllTravellers(): Observable<any[]> {
    const headers = this.authService.getAuthHeaders(); // Use AuthService to get headers
    return this.http.get<any[]>(this.baseUrl, { headers });
  }  


  // ✅ Get a single traveler by ID
  getTraveller(travellerId: number): Observable<any> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<any>(`${this.baseUrl}/${travellerId}`, { headers });
  }

  // ✅ Filter travelers and get them paginated (Admin only)
  getFilteredPaginatedTravellers(filters: { uuid?: string; active?: boolean }, page: number, size: number = 10): Observable<any> {
    const headers = this.authService.getAuthHeaders();
    const requestBody = { 
      ...filters,  // Add the filter properties
      page,        // Add the current page
      size         // Add the page sizeri
    };
    //const params = { page: page.toString(), pageSize: size.toString() }; // Add pagination query parameters
    //return this.http.post<any>(`${this.baseUrl}/all/paginated`, filters, { headers, params, withCredentials: true });
    return this.http.post<any>(
      `${this.baseUrl}/all/paginated`, 
      requestBody,  // Send the combined object as the request payload
      { headers, withCredentials: true } // Include headers and credentials
    );
  }
  
  
  

  // ✅ Deactivate a traveler (Admin only)
  deactivateTraveller(travellerId: number): Observable<string> {
    const headers = this.authService.getAuthHeaders();
    return this.http.patch<string>(`${this.baseUrl}/${travellerId}/deactivate`, {}, { headers, responseType: 'text' as 'json' });
  }
  

  // ✅ Restore a deactivated traveler (Admin only)
  restoreTraveller(travellerId: number): Observable<string> {
    const headers = this.authService.getAuthHeaders();
    return this.http.patch<string>(`${this.baseUrl}/${travellerId}/restore`, {}, { headers, responseType: 'text' as 'json' });
  }

  // ✅ Permanently delete a traveler (Admin only)
  deleteTraveller(travellerId: number): Observable<string> {
    const headers = this.authService.getAuthHeaders();
    return this.http.delete<string>(`${this.baseUrl}/${travellerId}/delete`, { headers, responseType: 'text' as 'json' });
  }



  // ------ Not used because getFilteredPaginatedTravellers covers both filtering and pagination ------
  // getPaginatedTravellers(page: number = 0, size: number = 2): Observable<any> {
  //   const headers = this.authService.getAuthHeaders();
  //   return this.http.get<any>(`${this.baseUrl}/paginated?page=${page}&size=${size}`, { headers });
  // }


  // ------ Not used because getFilteredPaginatedTravellers covers both filtering and pagination ------
  // getFilteredTravellers(filters: any): Observable<any[]> {
  //   const headers = this.authService.getAuthHeaders();
  //   return this.http.post<any[]>(`${this.baseUrl}/all`, filters, { headers });
  // }
}
