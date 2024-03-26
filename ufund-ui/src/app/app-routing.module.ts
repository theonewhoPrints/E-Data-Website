import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SchemesComponent } from './schemes/schemes.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SchemeDetailComponent } from './scheme-detail/scheme-detail.component';
import { CartComponent } from './cart/cart.component';
import { LoginComponent } from './login/login.component';
import { CupboardComponent } from './cupboard/cupboard.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { Sidebar2Component } from './sidebar2/sidebar2.component';
import { SidebarComponent } from './filter/sidebar/sidebar.component';
const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: SchemeDetailComponent },
  { path: 'cart', component: CartComponent },
  { path: 'login', component: LoginComponent },
  { path: 'cupboard', component: CupboardComponent},
  { path: 'checkout', component: CheckoutComponent},
  { path: 'link1', component: Sidebar2Component},
  { path: 'link2', component: Sidebar2Component}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
