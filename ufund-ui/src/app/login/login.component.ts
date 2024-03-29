import {Component, OnInit} from '@angular/core';

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
    private storageService: StorageService,
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
    
    this.userService.getUser(name).subscribe(user => {
      if (user.name === name) {
        this.router.navigate(['/dashboard']);
        this.storageService.saveUser(user);
      } else {
        this.messageService.add(`Login failed: user not found`);
      }
    });
  }
}
