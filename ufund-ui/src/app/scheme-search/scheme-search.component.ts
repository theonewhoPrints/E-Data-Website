import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';

@Component({
  selector: 'app-scheme-search',
  templateUrl: './scheme-search.component.html',
  styleUrls: [ './scheme-search.component.css' ]
})
export class SchemeSearchComponent implements OnInit {
  schemes$!: Observable<Scheme[]>;
  private searchTerms = new Subject<string>();

  constructor(private schemeService: SchemeService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
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