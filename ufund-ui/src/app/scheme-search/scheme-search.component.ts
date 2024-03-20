import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-scheme-search',
  templateUrl: './scheme-search.component.html',
  styleUrls: [ './scheme-search.component.css' ]
})
export class SchemeSearchComponent implements OnInit {
  schemes$!: Observable<Scheme[]>;
  private searchTerms = new Subject<string>();
  message: string = ""

  constructor(private schemeService: SchemeService, private cartService: CartService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  addToCart(scheme: Scheme): void {
    const existingScheme = this.cartService.getCart().find(s => s.id === scheme.id);

    if (existingScheme) {
      console.log(`Scheme "${scheme.name}" is already in the cart.`);
      this.message = "Already added to cart";
    } else {
      this.cartService.addToCart(scheme);
      scheme.addedToCart = true
    }
  
    // Set the addedToCart flag for all schemes in the cart
    this.schemes$.subscribe(schemes => {
      schemes.forEach(s => {
        s.addedToCart = this.cartService.getCart().some(item => item.id === s.id);
      });
    });
  }

  ngOnInit(): void {
    this.schemes$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.schemeService.searchSchemes(term)),
    );
  }
}