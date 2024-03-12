import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/_services/storage.service';
import { MessageService } from './message.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{
  title = 'Only Villains';
  private roles: string[] = [];
  isLoggedIn = false;
  //showAdminBoard = false;
  //showModeratorBoard = false;
  username?: string;

  constructor(public router: Router, private storageService: StorageService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if(!this.isLoggedIn) 
    {
      this.messageService.add(`Not logged in, redirecting...`);
      this.router.navigate(['/login']);
    }

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      //this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      //this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
  }

  logout(): void {
    this.storageService.clean();
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }
}