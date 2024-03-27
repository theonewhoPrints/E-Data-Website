import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SchemeService } from '../scheme.service';
import { UserService } from '../user.service';
import { StorageService } from 'src/_services/storage.service';
import { PictureService } from '../picture.service';
import { MessageService } from '../message.service';

import {readAndCompressImage } from 'browser-image-resizer';

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

  username = '';
  userRole = '';

  


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

    this.storageService.user$.subscribe(user => {
      this.username = user[1];
      this.userRole = user[2];
    });
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
}
