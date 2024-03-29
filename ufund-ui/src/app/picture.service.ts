import { Injectable } from '@angular/core';
import { HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { throwError } from 'rxjs';

import { User } from './user';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { from } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import {readAndCompressImage } from 'browser-image-resizer';
import { Subject } from 'rxjs';

const picConfig = {
  quality: 1.0,
  maxWidth: 512,
  maxHeight: 512,
  autoRotate: true,
  debug: true
};

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

  // Event to emit when a picture is uploaded
  pictureUploaded = new Subject<void>();
  uploadPicture(username: string, imageName: string, file: File): Observable<any> {
    this.messageService.add('PictureService: uploading picture...');
    return from(readAndCompressImage(file, picConfig)).pipe(
      switchMap(compressedImage => {
        const formData = new FormData();
        formData.append('image', compressedImage, imageName);
        return this.http.post(this.pictureURL + '/' + username + '/' +  imageName, formData, { responseType: 'blob' });
      }),
      tap(() => {
        // Add this line to emit an event when a picture is uploaded
        this.pictureUploaded.next();
      }),
      catchError(error => {
        console.error('There was an error!', error);
        return throwError('Something bad happened; please try again later.');
      })
    );
  }
}


