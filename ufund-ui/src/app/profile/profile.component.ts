import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Scheme } from '../scheme';
import { SchemeService } from '../scheme.service';
import { UserService } from '../user.service';
import { Observable, Subject } from 'rxjs';
import { StorageService } from 'src/_services/storage.service';
import { PictureService } from '../picture.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  user: String[] = [];

  name: string = '';
  role?: string;
  imgUrl?: string;
  imageToShow: any;
  selectedFile: File | null = null;

  constructor(
    private route: ActivatedRoute,
    private schemeService: SchemeService,
    private storageService: StorageService,
    private userService: UserService,
    private pictureService: PictureService,
    private messageService: MessageService,
  ) {}


  ngOnInit(): void {
    this.getUser();
  }
  
  getUser(): void {
    const name = this.route.snapshot.paramMap.get('name');
    if (name !== null) {
    this.userService.getUser(name).subscribe(user => {
      this.name = user[1];
      this.role = user[2];
      this.getProfilePicture();
    });
    }
  }

  getProfilePicture() {
    this.pictureService.getPictureByName(this.name).subscribe(data => {
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

  onSubmit() {
    const formData = new FormData();
    this.messageService.add('Submitting: ' + this.selectedFile);
    if (this.selectedFile !== null) {
      formData.append("image", this.selectedFile, this.selectedFile.name);
      this.pictureService.uploadPicture(this.selectedFile.name, formData).subscribe({
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
}
