import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../../services/auth-service/auth.service';

/**
 * AuthGuard ensures that only authenticated users can access certain routes within the application.
 * <p>
 * This route guard checks whether the user is logged in by verifying their authentication status.
 * If the user is not authenticated, they are redirected to the login page.
 * </p>
 * 
 * <b>Features:</b>
 * - Blocks access to protected routes for unauthenticated users.
 * - Redirects unauthenticated users to the login page.
 * - Logs the authentication check for debugging purposes.
 * 
 * @see AuthService
 * @see Router
 */
@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {

  /**
   * Initializes the guard with required dependencies.
   * 
   * @param authService the {@link AuthService} for checking the user's authentication status
   * @param router the {@link Router} for navigation
   */
  constructor(private authService: AuthService, private router: Router) {}


  /**
   * Determines whether the route can be activated based on the user's authentication status.
   * <p>
   * If the user is authenticated, access is granted. Otherwise, the user is redirected to the login page.
   * </p>
   * 
   * @return true if the user is authenticated, false otherwise
   */
  canActivate(): boolean {
    const isAuthenticated = this.authService.isAuthenticated();
    console.log("🛑 AuthGuard: isAuthenticated =", isAuthenticated);
    
    if (!isAuthenticated) {
      console.log("🔄 Redirecting to login...");
      this.router.navigate(['/login']);
      return false;
    }
  
    return true;
  }
  
}
