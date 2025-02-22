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
  sortedSchemes: Scheme[] = []; // Track the sorted schemes

  constructor(private schemeService: SchemeService, private cartService: CartService) { }

  ngOnInit(): void {
    this.getSchemes();
  }

  getSchemes(): void {
    this.schemeService.getSchemes()
      .subscribe(schemes => {
        // Apply sorting based on the selected order
        this.schemes = schemes;
        this.sortSchemes();
      });
  }

  sortSchemes(): void {
    if (this.selectedOrder === 'Price: High to Low') {
      this.sortedSchemes = this.schemes.slice().sort((a, b) => b.fundgoal - a.fundgoal);
    } else if (this.selectedOrder === 'Price: Low to High') {
      this.sortedSchemes = this.schemes.slice().sort((a, b) => a.fundgoal - b.fundgoal);
    } else if (this.selectedOrder === 'Most Relevant') {
      // Custom sorting based on keyword relevance
      this.sortedSchemes = this.schemes.slice().sort((a, b) => {
        const keywords = ['blow up','freeze', 'burn', 'kidnap', 'rule', 'enslave', 'capture', 'destroy', 'annihilate', 'kill', 'rob', 'steal', 'assault', 'attack', 'murder', 'bomb', 'hostage', 'extort'];
        const countA = keywords.reduce((acc, keyword) => acc + (a.title.toLowerCase().includes(keyword) ? 1 : 0), 0);
        const countB = keywords.reduce((acc, keyword) => acc + (b.title.toLowerCase().includes(keyword) ? 1 : 0), 0);
        return countB - countA; // Sort in descending order of keyword count
      });
    } else {
      this.sortedSchemes = this.schemes.slice(); // Default order
    }
  }

  addToCart(scheme: Scheme): void {
    this.cartService.addToCart(scheme);
  }

  selectChangeHandler(event: any): void {
    this.selectedOrder = event.target.value;
    this.sortSchemes();
  }

  orders = [
    { id: '1', name: 'Default' },
    { id: '2', name: 'Price: High to Low' },
    { id: '3', name: 'Price: Low to High' },
    { id: '4', name: 'Most Relevant' }
  ];
}
