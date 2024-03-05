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

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.schemeService.addScheme({ name } as Scheme)
      .subscribe(scheme => {
        this.schemes.push(scheme);
      });
  }

  delete(scheme: Scheme): void {
    this.schemes = this.schemes.filter(s => s !== scheme);
    this.schemeService.deleteScheme(scheme.id).subscribe();
  }
  
}