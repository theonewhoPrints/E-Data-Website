import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/_services/storage.service';
import { MessageService } from './message.service';
import { ProfileService } from './profile.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{
  title = 'Only Villains';
  role = '';
  username?: string;

  isLoggedIn = false;

  showAdminBoard = false;
  showVillainBoard = false;
  showHelperBoard = false;

  constructor(public router: Router, private storageService: StorageService, private messageService: MessageService, private profileService: ProfileService) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    this.profileService.updateProfile();

    if(!this.isLoggedIn) 
    {
      this.messageService.add(`Not logged in, redirecting...`);
      this.router.navigate(['/login']);
    }

    if (this.isLoggedIn) 
    {
      this.storageService.user$.subscribe(user => {
        this.username = user[1];
        this.role = user[2];
      });

      this.showAdminBoard = this.role.includes('ROLE_ADMIN');
      this.showVillainBoard = this.role.includes('ROLE_VILLAIN');
      this.showHelperBoard = this.role.includes('ROLE_HELPER');
    }

  }

  logout(): void {
    this.storageService.clean();
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }
}