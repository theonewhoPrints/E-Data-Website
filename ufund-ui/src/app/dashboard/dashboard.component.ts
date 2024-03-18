import { Component, OnInit } from '@angular/core';
import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  schemes: Scheme[] = [];
  schemeTitles: string[] = [];

  constructor(private schemeService: SchemeService) { }

  ngOnInit(): void {
    this.getSchemes();
    // this.getSchemesTitles();
  }

  getSchemes(): void {
    this.schemeService.getSchemes()
      .subscribe(schemes => this.schemes = schemes.slice(0, 5));
  }

  // getSchemesTitles(): void {
  //   this.schemeService.getSchemesTitles()
  //     .subscribe(Titlesschemes => this.schemes = Titlesschemes.slice(0, 5));
  // }
}
