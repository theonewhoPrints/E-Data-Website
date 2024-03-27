import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatButtonModule} from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
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
import { MaterialModule } from './material.module';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule
  ],
  exports: [
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule
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

export class MaterialModule {}
