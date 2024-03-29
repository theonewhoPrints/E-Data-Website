import { Component, Injectable, OnInit } from '@angular/core';
import { StorageService } from 'src/_services/storage.service';
import { PictureService } from '../picture.service';
import { NgOptimizedImage } from '@angular/common';

@Component({
  selector: 'app-profile-bar',
  templateUrl: './profile-bar.component.html',
  styleUrls: ['./profile-bar.component.css']
})

@Injectable({
  providedIn:'root'
})
export class ProfileBarComponent implements OnInit{
  constructor(public storageService: StorageService, public pictureService: PictureService ) { }

  user = this.storageService.getUser();
  username = '';
  role = '';
  imageToShow: any;

  ngOnInit(): void {
    // Subscribe to the user$ observable to get the latest user data
    this.storageService.user$.subscribe(user => {
      this.username = user.name;
      this.role = user.role;
    });

    this.getProfilePicture();

    // Subscribe to the pictureUploaded event to update the profile picture when a new picture is uploaded
    this.pictureService.pictureUploaded.subscribe(() => {
      this.getProfilePicture();
    });
  }

  getProfilePicture() {
    this.pictureService.getPictureByName(this.username).subscribe(data => {
      let reader = new FileReader();
      reader.addEventListener("load", () => {
        this.imageToShow = reader.result;
      }, false);
  
      if (data) {
        reader.readAsDataURL(data);
      }
    });
  }

}
