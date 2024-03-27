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
  selectedOrder: string = ''; // Track the selected sorting order

  constructor(private schemeService: SchemeService, private cartService: CartService) { }

  ngOnInit(): void {
    this.getSchemes();
    
  }

  getSchemes(): void {
    this.schemeService.getSchemes()
      .subscribe(schemes => {
        // Apply sorting based on the selected order
        if (this.selectedOrder === 'Price: High to Low') {
          this.schemes = schemes.slice(0, 5).sort((a, b) => b.fundgoal - a.fundgoal);
        } else if (this.selectedOrder === 'Price: Low to High') {
          this.schemes = schemes.slice(0, 5).sort((a, b) => a.fundgoal - b.fundgoal);
        } else {
          this.schemes = schemes.slice(0, 5); // Default order
        }
      });
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

  orders=[
    { id: '1', name: 'Price: High to Low' },
    { id: '2', name: 'Price: Low to High' },
    { id: '3', name: 'Most Relevant' },
    { id: '4', name: 'order 4' }
  ]

  selectedDay: string = '';
 
  selectChangeHandler (event: any) {
    this.selectedOrder = event.target.value;
    this.selectedDay = event.target.value;
    
  }






}

