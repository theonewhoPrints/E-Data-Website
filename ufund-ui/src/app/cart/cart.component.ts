import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Scheme } from '../scheme';
import { CartService } from '../cart.service';
import { ChangeDetectorRef } from '@angular/core';
import { CartItem } from './cartItem';
import { SchemeService } from '../scheme.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart: CartItem[] = [];

  checkoutSuccess = false;

  constructor(private titleService: Title, private cartService: CartService, private schemeService: SchemeService) { }

  ngOnInit(): void {
    this.getCart();
    // Sets the title for the browser cart page!!!
    this.titleService.setTitle('Villain Cart');
  }

  getCart(): void {
    this.cart= [];
    var tempCart : Scheme[] = this.cartService.getCart();
    for (let i = 0; i < tempCart.length; i++){
      this.cart.push({scheme: tempCart[i], donateAmount: 0})
    }
  }

  removeFromCart(scheme: Scheme): void {
    this.cartService.removeFromCart(scheme);
    this.getCart();
  }

  checkout(): void {
    for (let i = 0; i < this.cart.length; i++){
      if (this.cart[i].donateAmount <= 0 || this.cart[i].donateAmount > this.cart[i].scheme.fundgoal){return;}
    }
    for (let i = 0; i < this.cart.length; i++){
      this.schemeService.updateScheme({...this.cart[i].scheme, fundgoal: this.cart[i].scheme.fundgoal - this.cart[i].donateAmount} as Scheme).subscribe();
    }
    this.cartService.clearCart();
    this.getCart(); // Refresh the cart display
    this.checkoutSuccess = true
   }
 }
