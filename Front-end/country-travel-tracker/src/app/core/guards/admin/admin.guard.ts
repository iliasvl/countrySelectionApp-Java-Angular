import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../../services/auth-service/auth.service';

/**
 * AdminGuard ensures that only admin users can access certain routes within the application.
 * <p>
 * This route guard checks whether the currently authenticated user has admin privileges. 
 * If the user is not an admin, they are redirected to the dashboard and denied access to the guarded route.
 * </p>
 * 
 * <b>Features:</b>
 * - Blocks unauthorized access to admin-only routes.
 * - Redirects non-admin users to the dashboard.
 * - Displays a warning message in the browser console for unauthorized attempts.
 * 
 * @see AuthService
 * @see Router
 */
@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {

  /**
   * Initializes the guard with required dependencies.
   * 
   * @param authService the {@link AuthService} for checking the user's admin status
   * @param router the {@link Router} for navigation
   */
  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Determines whether the route can be activated based on the user's admin status.
   * <p>
   * If the user has admin privileges, access is granted. Otherwise, the user is redirected to the dashboard.
   * </p>
   * 
   * @return true if the user is an admin, false otherwise
   */
  canActivate(): boolean {
    if (this.authService.isAdmin()) {
      return true;
    } else {
      console.warn('🚫 Access denied! Admins only.');
      this.router.navigate(['/dashboard']); // Redirect non-admins
      return false;
    }
  }
}
