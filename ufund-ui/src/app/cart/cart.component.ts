import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Scheme } from '../scheme';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart: Scheme[] = [];

  constructor(private titleService: Title, private cartService: CartService) { }

  ngOnInit(): void {
    this.getCart();
    // Sets the title for the browser cart page!!!
    this.titleService.setTitle('Villain Cart');
  }

  getCart(): void {
    this.cart = this.cartService.getCart();
  }

  removeFromCart(scheme: Scheme): void {
    this.cartService.removeFromCart(scheme);
    this.getCart();
  }
}
