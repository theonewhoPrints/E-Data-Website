import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  constructor(private titleService: Title) { }

  ngOnInit(): void {
    // Sets the title for the browser cart page!!!
    this.titleService.setTitle('Villain Cart');
  }
}
