import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SchemesComponent } from './schemes/schemes.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SchemeDetailComponent } from './scheme-detail/scheme-detail.component';
import { CartComponent } from './cart/cart.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: SchemeDetailComponent },
  { path: 'schemes', component: SchemesComponent },
  { path: 'cart', component: CartComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
