import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { provideHttpClient, withFetch, HTTP_INTERCEPTORS} from '@angular/common/http';


import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EditCountriesComponent } from './edit-countries/edit-countries.component';
import { LoginComponent } from './auth/login/login.component';
import { AuthGuard } from './core/guards/auth/auth.guard';
import { JwtInterceptor } from './core/interceptors/jwt.interceptor';
import { TravellerService } from './core/services/admin-service/traveller.service';
import { AdminControlsComponent } from './admin-controls/admin-controls.component';


@NgModule({
    imports: [
      BrowserModule,
      AppRoutingModule,
      DashboardComponent,        // ✅ Import standalone component
      EditCountriesComponent,    // ✅ Import standalone component
      LoginComponent,            // ✅ Import standalone component
      AppComponent,
      AdminControlsComponent
    ],
    providers: [
      AuthGuard, 
      { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }, 
      provideHttpClient(withFetch()), // ✅ Enable Fetch API
      TravellerService            // ✅ Provide service
    ] 
  })
  export class AppModule { }
