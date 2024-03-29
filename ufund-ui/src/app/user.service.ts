import { Injectable } from '@angular/core';
import { HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { User } from './user';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }
  
  
  private userURL = 'http://localhost:8080/users';  // URL to users

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  /** Log a UserService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`UserService: ${message}`);
  }

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


  // main methods
  
  /** GET users from the server */
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userURL)
      .pipe(
        tap(_ => this.log('fetched users')),
        catchError(this.handleError<User[]>('getUsers', []))
      );
  }

  /** GET scheme by name. Will 404 if name not found */
  getUser(name: string): Observable<User> {
    const url = `${this.userURL}/query?name=${name}`;
    return this.http.get<User>(url).pipe(
      tap(_ => this.log(`fetched user name=${name}`)),
      catchError(this.handleError<User>(`getUser name=${name}`))
    );
  }

  /**
   * Get the name of a user.
   * @param name - The name of the user.
   * @returns An observable of the user's name.
   */
  getUserName(name: string): Observable<string> {
    const url = `${this.userURL}/query?name=${name}`;
    return this.http.get<User>(url).pipe(
      map(user => user.name), // Extract the name from the user object
      tap(_ => this.log(`fetched user name=${name}`)),
      catchError(this.handleError<string>(`getUser name=${name}`))
    );
  }


  /* GET users whose name contains search term */
searchUsers(term: string): Observable<User[]> {
  if (!term.trim()) {
    // if not search term, return empty user array.
    return of([]);
  }
  const lowercaseTerm = term.toLowerCase(); // Convert search term to lowercase
  return this.http.get<User[]>(`${this.userURL}/query?name=${lowercaseTerm}`).pipe(
    tap(x => x.length ?
      this.log(`found users matching "${term}"`) :
      this.log(`no users matching "${term}"`)),
    catchError(this.handleError<User[]>('searchUsers', []))
  );
}

  /**
   * Update a user.
   * @param user - The user object to update.
   * @returns An observable of the updated user.
   */
  updateUser(user: User): Observable<any> {
    return this.http.put(this.userURL, user, this.httpOptions).pipe(
      tap(_ => this.log(`updated user name=${user.name}`)),
      catchError(this.handleError<any>('updateUser'))
    );
  }

  /**
   * Update a user's achievements.
   * @param name - The name of the user.
   * @param achievements - The array of achievements to update.
   * @returns An observable of the updated achievements.
   */
  updateAchievements(name: string, achievements: string[]): Observable<any> {
    return this.http.put(`${this.userURL}/achievements?name=${name}`, achievements, this.httpOptions).pipe(
      tap(_ => this.log(`updated achievements name=${name}`)),
      catchError(this.handleError<any>('updateAchievements'))
    );
  }
}