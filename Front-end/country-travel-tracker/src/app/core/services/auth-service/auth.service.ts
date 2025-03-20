import { Inject, PLATFORM_ID, Injectable } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode'; 

interface AuthResponse {
  token: string;
  travellerId: number;
}


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private travellerApiUrl = 'http://localhost:8080/api/travellers';
  private tokenKey = 'authToken';

  private authSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.isAuthenticated());
  public isAuthenticatedObs: Observable<boolean> = this.authSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: object
  ) {}
  

  // ✅ Login and store token
  login(username: string, password: string) {
    return this.http.post<AuthResponse>(`${this.apiUrl}/authenticate`, { username, password }).pipe(
      tap((response) => {
        console.log('🔥 Full Backend Response:', response);

        if (isPlatformBrowser(this.platformId)) {
          if (response.token) {
            localStorage.setItem(this.tokenKey, response.token);
            console.log('✅ Token Stored:', response.token);
          } else {
            console.error('❌ No token received!');
          }

          if (response.travellerId) {
            localStorage.setItem('travellerId', response.travellerId.toString());
            console.log('✅ Traveller ID Stored:', response.travellerId);
          } else {
            console.error('🚨 No travellerId received in response!');
          }
        }
      }),
      tap(() => {
        const token = localStorage.getItem(this.tokenKey);
        if (isPlatformBrowser(this.platformId)) {
          const token = localStorage.getItem(this.tokenKey);
          if (token) this.router.navigate(['/dashboard']);
        }
      })
    );
  }

  // ✅ Extract Traveller ID from JWT (Fixing the issue)
  getDecodedToken(): any {
    const token = this.getAuthToken();
    if (!token) return null;

    try {
      return jwtDecode(token);
    } catch (error) {
      console.error('❌ Error decoding token:', error);
      return null;
    }
  }

  // ✅ Get Traveller ID from decoded token
  getTravellerId(): string | null {
    if (!isPlatformBrowser(this.platformId)) {
      console.warn('⚠️ localStorage is not available (Not in browser)');
      return null;
    }
    return localStorage.getItem('travellerId');
  }

  // ✅ Get Auth Token
  getAuthToken(): string | null {
    if (!isPlatformBrowser(this.platformId)) {
      console.warn('⚠️ localStorage is not available (Not in browser)');
      return null;
    }
    return localStorage.getItem('authToken');
  }

  isAuthenticated(): boolean {
    return isPlatformBrowser(this.platformId) && !!this.getAuthToken(); // ✅ Ensure this is a function
  }

  // ✅ Logout user
  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem('travellerId');
    }
    this.authSubject.next(false);
    this.router.navigate(['/login']);
  }

  // ✅ Get Authorization Headers
  getAuthHeaders(): HttpHeaders {
    const token = this.getAuthToken();
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json',
    });
  }

  register(userData: any) {
    return this.http.post('http://localhost:8080/api/travellers/save', userData);
  }


  getUserRole(): string | null {
    const token = this.getAuthToken();
    if (!token) return null;
  
    try {
      const decodedToken: any = jwtDecode(token);
      console.log('🔑 Decoded Token:', decodedToken); // ✅ Debugging log
      return decodedToken.role || null; // Ensure the token contains the 'role'
    } catch (error) {
      console.error('❌ Error decoding token:', error);
      return null;
    }
  }
  
  isAdmin(): boolean {
    const role = this.getUserRole();
    console.log('👀 Checking admin status:', role); // ✅ Debugging log
    return role === 'ADMIN';
  }
  
  
}
