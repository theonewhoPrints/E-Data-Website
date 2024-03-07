import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
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

  login(name: string): void {
    name = name.trim();
    if (!name) { return; }
  }
}
