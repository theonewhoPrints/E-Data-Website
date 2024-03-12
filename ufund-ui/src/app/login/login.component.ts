import {Component, OnInit} from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { StorageService } from 'src/_services/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  // ...

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private router: Router,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.messageService.add(`Logged in, redirecting to dashboard...`);
      this.router.navigate(['/dashboard']);
    }
  }


  login(name: string): void {
    name = name.trim();
    if (!name) { return; }
    
    this.userService.getUserName(name).subscribe(result => {
      if (result === name) {
        // User found, perform the login logic here
        this.messageService.add('Login successful, user: ' + result);
        this.router.navigate(['/dashboard']);
        this.userService.getUser(name).subscribe(user => {
          this.storageService.saveUser(user);
        });
      } else {
        // User not found, display a message
        this.messageService.add(`Login failed: user not found`);
      }
    });
  }

}
