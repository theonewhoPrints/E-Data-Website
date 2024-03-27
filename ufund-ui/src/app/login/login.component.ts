import {Component, OnInit, OnDestroy} from '@angular/core';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { StorageService } from 'src/_services/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy{
  musicState: boolean = false; // Add this line


  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private router: Router,
    private storageService: StorageService,
  ) {}

  ngOnInit(): void {
    document.body.classList.add('login-background');

    this.messageService.clear(); // Clear messages on component initialization
    if (this.storageService.isLoggedIn()) {
      this.messageService.add(`Logged in, redirecting to dashboard...`);
      this.router.navigate(['/dashboard']);
      }
  }

  ngOnDestroy(): void {
    // Remove background class from body
    document.body.classList.remove('login-background');

  }


  toggleMusic(): void {
    this.musicState = !this.musicState;
    const audioElement = document.getElementById('backgroundMusic') as HTMLAudioElement;
    const toggleElement = document.getElementById('switch');
    
    if (this.musicState) {
      audioElement.play();
      toggleElement?.classList.add('toggle-on');
    } else {
      audioElement.pause();
      toggleElement?.classList.remove('toggle-on');
    }
    
    localStorage.setItem('musicState', JSON.stringify(this.musicState)); // Save the current state
  }




  login(name: string): void {
    name = name.trim();
    if (!name) { return; }
    
    this.userService.getUser(name).subscribe(user => {
      if (user[1] === name) {
        this.router.navigate(['/dashboard']);
        this.storageService.saveUser(user);
      } else {
        this.messageService.add(`Login failed: user not found`);
      }
    });
  }
}
