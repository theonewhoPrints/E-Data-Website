import {Component, OnInit} from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
  user: User | undefined;

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private router: Router
  ) {}

  login(name: string): void {
    name = name.trim();
    if (!name) { return; }
    
    this.userService.getUserName(name).subscribe(result => {
      if (result === name) {
        // User found, perform the login logic here
        this.messageService.add('Login successful, user: ' + result);
        this.router.navigate(['/dashboard']);
      } else {
        // User not found, display a message
        this.messageService.add(`Login failed: user not found`);
      }
    });
  }

}
