import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth-service/auth.service';

/**
 * JwtInterceptor adds a JSON Web Token (JWT) to the headers of outgoing HTTP requests.
 * <p>
 * This interceptor ensures that authenticated requests include the required JWT for secure API communication.
 * It retrieves the token from the {@link AuthService} and attaches it to the `Authorization` header.
 * </p>
 * 
 * <b>Features:</b>
 * - Automatically appends the JWT to outgoing HTTP requests if available.
 * - Works seamlessly with the Angular `HttpClient` module.
 * - Ensures secure and authorized communication with the backend.
 * 
 * @see AuthService
 * @see HttpInterceptor
 */
@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  /**
   * Initializes the interceptor with required dependencies.
   * 
   * @param authService the {@link AuthService} for retrieving the JWT token
   */
  constructor(private authService: AuthService) { }

  /**
   * Intercepts outgoing HTTP requests and appends the JWT to the `Authorization` header if available.
   * 
   * @param request the outgoing HTTP request
   * @param next the HTTP handler to pass the modified request to
   * @return an {@link Observable} of the HTTP event stream
   */
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getAuthToken();
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    return next.handle(request);
  }
}
