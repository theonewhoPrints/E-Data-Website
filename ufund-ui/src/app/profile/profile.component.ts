import { Component, Injectable, OnInit } from '@angular/core';
import { StorageService } from 'src/_services/storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

@Injectable({
  providedIn:'root'
})
export class ProfileComponent implements OnInit{
  constructor(public storageService: StorageService) { }

  user = this.storageService.getUser();
  username = '';
  role = '';

  ngOnInit(): void {
    this.storageService.user$.subscribe(user => {
      this.username = user[1];
      this.role = user[2];
    });
  }
}
