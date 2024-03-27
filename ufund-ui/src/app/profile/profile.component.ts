import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SchemeService } from '../scheme.service';
import { UserService } from '../user.service';
import { StorageService } from 'src/_services/storage.service';
import { PictureService } from '../picture.service';
import { MessageService } from '../message.service';

import {readAndCompressImage } from 'browser-image-resizer';
import { User, UserImpl } from '../user';
import { catchError, tap } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Location } from '@angular/common';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  profile: User;

  imageToShow: any;
  selectedFile: File | null = null;

  username = '';
  userRole = '';

  


  constructor(
    private route: ActivatedRoute,
    private schemeService: SchemeService,
    private storageService: StorageService,
    private userService: UserService,
    private pictureService: PictureService,
    private messageService: MessageService,
    private location: Location
  ) 
  {
    // necessary to avoid null pointer exception
    this.profile = new UserImpl(-1, '', '', '', []); 
  }


  ngOnInit(): void {
    this.getUser();

    this.storageService.user$.subscribe(user => {
      this.username = user.name;
      this.userRole = user.role;
    });
  }

  
  /** Log a SchemeService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`Profile: ${message}`);
  }
  
  getUser(): void {
    const name = this.route.snapshot.paramMap.get('name');
    if (name !== null) {
    this.userService.getUser(name).subscribe(user => {
      this.profile = user;
      this.messageService.add('Achievements: ' + this.profile.achievements);
      this.getProfilePicture();
    });
    }
  }

  

  getProfilePicture() {
    this.pictureService.getPictureByName(this.profile.name).subscribe(data => {
      let reader = new FileReader();
      reader.addEventListener("load", () => {
        this.imageToShow = reader.result;
      }, false);
  
      if (data) {
        reader.readAsDataURL(data);
      }
    });
  }


  onFileSelected(event: any) {
    this.selectedFile = <File>event.target.files[0];
    this.messageService.add('File selected: ' + this.selectedFile.name);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.profile) {
      this.userService.updateUser(this.profile).subscribe();
      //this.userService.updateAchievements(this.profile.name, this.profile.achievements).subscribe();
    }
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

  onSubmit() {
    const formData = new FormData();
    this.messageService.add('Submitting: ' + this.selectedFile);
    if (this.selectedFile !== null) {
      //formData.append("image", this.selectedFile, this.selectedFile.name);
      this.pictureService.uploadPicture(this.username, this.selectedFile.name, this.selectedFile).subscribe({
        next: (response) => {
          // Handle the response here
        },
        error: (err) => {
          // Handle the error here. This could be showing a user-friendly message or logging the error.
          console.error('There was an error!', err);
        }
      /*
      this.pictureService.uploadPicture(formData).subscribe(response => {
        // Handle response here
      });
      */
      });
    }
  }

  addAchievement() {
    this.profile.achievements.push('');
  }

  removeAchievement(index: number) {
    this.profile.achievements.splice(index, 1);
  }

  // fixes achievement list rendering
  trackByFn(index: any, item: any) {
    return index;
  }
}
