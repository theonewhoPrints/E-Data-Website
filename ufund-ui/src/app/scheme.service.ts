import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { Scheme } from './scheme';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class SchemeService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET schemes from the server */
  getSchemes(): Observable<Scheme[]> {
    return this.http.get<Scheme[]>(this.schemesUrl)
      .pipe(
        tap(_ => this.log('fetched schemes')),
        catchError(this.handleError<Scheme[]>('getSchemes', []))
      );
  }

  /** GET scheme by id. Will 404 if id not found */
  getScheme(id: number): Observable<Scheme> {
    const url = `${this.schemesUrl}/${id}`;
    return this.http.get<Scheme>(url).pipe(
      tap(_ => this.log(`fetched scheme id=${id}`)),
      catchError(this.handleError<Scheme>(`getScheme id=${id}`))
    );
  }

  /** Log a SchemeService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`SchemeService: ${message}`);
  }

  private schemesUrl = 'http://localhost:8080/villains';  // URL to web api

  /**
  * Handle Http operation that failed.
  * Let the app continue.
  *
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** PUT: update the scheme on the server */
  updateScheme(scheme: Scheme): Observable<any> {
    return this.http.put(this.schemesUrl, scheme, this.httpOptions).pipe(
      tap(_ => this.log(`updated scheme id=${scheme.id}`)),
      catchError(this.handleError<any>('updateScheme'))
    );
  }
  
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  /** POST: add a new scheme to the server */
  addScheme(scheme: Scheme): Observable<Scheme> {
    return this.http.post<Scheme>(this.schemesUrl, scheme, this.httpOptions).pipe(
      tap((newScheme: Scheme) => this.log(`added scheme w/ id=${newScheme.id}`)),
      catchError(this.handleError<Scheme>('addScheme'))
    );
  }

  /** DELETE: delete the scheme from the server */
  deleteScheme(id: number): Observable<Scheme> {
    const url = `${this.schemesUrl}/${id}`;

    return this.http.delete<Scheme>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted scheme id=${id}`)),
      catchError(this.handleError<Scheme>('deleteScheme'))
    );
  }

  /* GET schemes whose name contains search term */
  searchSchemes(term: string): Observable<Scheme[]> {
    if (!term.trim()) {
      // if not search term, return empty scheme array.
      return of([]);
    }
    return this.http.get<Scheme[]>(`${this.schemesUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
        this.log(`found schemes matching "${term}"`) :
        this.log(`no schemes matching "${term}"`)),
      catchError(this.handleError<Scheme[]>('searchSchemes', []))
    );
  }
}