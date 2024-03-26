import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatSidenavModule, MatButtonModule, MatToolbarModule, MatIconModule, MatListModule ,MatMenuModule} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { SortFilterComponent } from './sort-filter/sort-filter.component';
import { HeaderComponent } from './filter/header/header.component';
import { SidebarComponent } from './filter/sidebar/sidebar.component';
import { Sidebar2Component } from './sidebar2/sidebar2.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
  
    
  ],

  exports:[
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    MatIconModule
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
    SortFilterComponent,
    HeaderComponent,
    SidebarComponent,
    Sidebar2Component,
    
  ],
  providers: [],
  bootstrap: [ AppComponent ]
})
export class AppModule {
 }
