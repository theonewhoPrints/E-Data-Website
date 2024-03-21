import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { SchemesComponent } from './schemes/schemes.component';
import { SchemeDetailComponent } from './scheme-detail/scheme-detail.component';
import { MessagesComponent } from './messages/messages.component';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { SchemeSearchComponent } from './scheme-search/scheme-search.component';
import { CartComponent } from './cart/cart.component';

// log in
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { CheckoutComponent } from './checkout/checkout.component';

//cupboard
import { CupboardComponent } from './cupboard/cupboard.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    SchemesComponent,
    SchemeDetailComponent,
    MessagesComponent,
    SchemeSearchComponent,
    CartComponent,
    LoginComponent,
    ProfileComponent,
    CupboardComponent,
    CheckoutComponent,
  ],
  providers: [],
  bootstrap: [ AppComponent ]
})
export class AppModule {
 }
