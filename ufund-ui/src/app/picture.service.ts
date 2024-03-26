import { Injectable } from '@angular/core';
import { HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { throwError } from 'rxjs';

import { User } from './user';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PictureService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }
  
  
  private pictureURL = 'http://localhost:8080/picture';  // URL to pictures


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

  public getPicture(imageName: string): Observable<Blob> {
    return this.http.get(this.pictureURL + '/' + imageName, { responseType: 'blob' });
  }

  public getPictureByName(username: string): Observable<Blob> {
    return this.http.get(this.pictureURL + '/' + username, { responseType: 'blob' });
  }

  uploadPicture(username: string, imageName: string, formData: FormData): Observable<any> {
    this.messageService.add('PictureService: uploading picture...');
    this.messageService.add('URL: ' + this.pictureURL + '/' + imageName);
    this.messageService.add('formData: ' + formData);
    return this.http.post(this.pictureURL + '/' + username + '/' +  imageName, formData).pipe(
      catchError(error => {
        // Handle the error here. You might want to log it or show a user-friendly message.
        console.error('There was an error!', error);
        return throwError('Something bad happened; please try again later.');
      })
    );
  }

}
