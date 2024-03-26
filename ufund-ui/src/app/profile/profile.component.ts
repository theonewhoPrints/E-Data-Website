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
    this.messageService.add('Name is ' + this.name)
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

}
