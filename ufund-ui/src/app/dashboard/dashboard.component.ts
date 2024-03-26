import { Component, OnInit } from '@angular/core';
import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  schemes: Scheme[] = [];
  schemeTitles: string[] = [];

  constructor(private schemeService: SchemeService, private cartService: CartService) { }

  ngOnInit(): void {
    this.getSchemes();
    
  }

  getSchemes(): void {
    this.schemeService.getSchemes()
      .subscribe(schemes => this.schemes = schemes.slice(0, 5));
  }


  addToCart(scheme : Scheme): void {
    this.cartService.addToCart(scheme);
  }


  onSortEvent(event: string): void {
    // Handle sorting logic based on the emitted event
    console.log('Sort event received:', event);
    // Implement your sorting logic here, for example, call a service method with sort parameters
    // You can implement sorting logic here based on the event received from the sort filter component
  }





}

