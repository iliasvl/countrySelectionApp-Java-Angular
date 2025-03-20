import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'; // ✅ Import Routes & RouterModule
import { DashboardComponent } from './dashboard/dashboard.component'; // ✅ Import Dashboard
import { EditCountriesComponent } from './edit-countries/edit-countries.component'; // ✅ Import Edit Countries Page
import { LoginComponent } from './auth/login/login.component'; // ✅ Import Login Page
import { RegisterComponent } from './auth/register/register.component'; // ✅ Import Register Page
import { AuthGuard } from './core/guards/auth/auth.guard'; // ✅ Import Auth Guard
import { RemoveCountriesComponent } from './remove-countries/remove-countries.component';
import { AdminGuard } from './core/guards/admin/admin.guard'; // ✅ Import Admin Guard
import { AdminControlsComponent } from './admin-controls/admin-controls.component';


export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' }, // Default redirect
  { path: 'login', component: LoginComponent }, // Public Login Page
  { path: 'register', component: RegisterComponent }, // Public Register Page
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }, // Protected Dashboard
  { path: 'edit-countries', component: EditCountriesComponent, canActivate: [AuthGuard] }, // Protected Edit Page
  { path: 'remove-countries', component: RemoveCountriesComponent, canActivate: [AuthGuard] }, 
  { path: 'admin-controls', component: AdminControlsComponent, canActivate: [AdminGuard] },// Protected controls for admins Page
  { path: '**', redirectTo: '/dashboard' } // Redirect unknown routes
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
