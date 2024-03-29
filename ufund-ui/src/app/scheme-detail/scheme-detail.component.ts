import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';
import { CartService } from '../cart.service';
import { Observable, Subject } from 'rxjs';
import { StorageService } from 'src/_services/storage.service';

@Component({
  selector: 'app-scheme-detail',
  templateUrl: './scheme-detail.component.html',
  styleUrls: ['./scheme-detail.component.css']
})
export class SchemeDetailComponent implements OnInit{
  scheme: Scheme | undefined;
  message: string = ""
  schemes$!: Observable<Scheme[]>;
  role = '';
  username?: string;

  constructor(
    private route: ActivatedRoute,
    private schemeService: SchemeService,
    private location: Location,
    private cartService: CartService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    this.getScheme();
    
    this.storageService.user$.subscribe(user => {
      this.username = user.name;
      this.role = user.role;
  });
  }
  
  getScheme(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.schemeService.getScheme(id)
      .subscribe(scheme => this.scheme = scheme);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.scheme) {
      this.schemeService.updateScheme(this.scheme)
        .subscribe(() => this.goBack());
    }
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
}