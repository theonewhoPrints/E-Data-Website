import { Component, OnInit } from '@angular/core';

import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';

@Component({
  selector: 'app-schemes',
  templateUrl: './schemes.component.html',
  styleUrls: ['./schemes.component.css']
})
export class SchemesComponent implements OnInit {
  schemes: Scheme[] = [];

  constructor(private schemeService: SchemeService) { }

  ngOnInit(): void {
    this.getSchemes();
  }

  getSchemes(): void {
    this.schemeService.getSchemes()
    .subscribe(scheme => this.schemes = scheme);
  }
  
}