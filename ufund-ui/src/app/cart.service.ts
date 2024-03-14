import { Injectable } from '@angular/core';
import { Scheme } from './scheme';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: Scheme[] = [];

  constructor() { }

  addToCart(scheme: Scheme): void {
    this.cart.push(scheme);
  }

  removeFromCart(scheme: Scheme): void {
    const index = this.cart.indexOf(scheme);
    if (index !== -1) {
      this.cart.splice(index, 1);
    }
  }

  getCart(): Scheme[] {
    return this.cart;
  }
}
