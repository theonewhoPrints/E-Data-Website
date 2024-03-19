import { Component } from '@angular/core';
import { SchemeService } from '../scheme.service';
import { Scheme } from '../scheme';

@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: ['./cupboard.component.css']
})
export class CupboardComponent {
  schemes: Scheme[] =  [];

  constructor(private schemeService: SchemeService) { } ;

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
