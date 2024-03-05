import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';

@Component({
  selector: 'app-scheme-detail',
  templateUrl: './scheme-detail.component.html',
  styleUrls: ['./scheme-detail.component.css']
})
export class SchemeDetailComponent implements OnInit{
  scheme: Scheme | undefined;

  constructor(
    private route: ActivatedRoute,
    private schemeService: SchemeService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getScheme();
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
}