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
}

