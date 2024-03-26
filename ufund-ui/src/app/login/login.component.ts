import {Component, OnInit, OnDestroy} from '@angular/core';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { StorageService } from 'src/_services/storage.service';
import { ElementRef, ViewChild } from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy{
  slideshowInterval: any; // Define slideshow interval variable
  currentImageIndex: number = 0;
  currentSetIndex: number = 0;

  // Define your image sets
  imageSets: string[][] = [
    ['assets/Asura.jpg', 'assets/Yujiro.jpg', 'assets/Zamasu.jpg', 'assets/Toji.jpg', 'assets/Perfect Cell.jpg', 'assets/Garou.jpg'],
    // Add more sets as needed
  ];



  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private router: Router,
    private storageService: StorageService,
  ) {}

  ngOnInit(): void {
    document.body.classList.add('login-background');
    this.startSlideshow();

    this.messageService.clear(); // Clear messages on component initialization
    if (this.storageService.isLoggedIn()) {
      this.messageService.add(`Logged in, redirecting to dashboard...`);
      this.router.navigate(['/dashboard']);
      }
  }

  ngOnDestroy(): void {
    // Remove background class from body
    document.body.classList.remove('login-background');
    // Stop the slideshow when the component is destroyed
    clearInterval(this.slideshowInterval);
  }

  startSlideshow(): void {
    this.slideshowInterval = setInterval(() => {
      this.currentImageIndex++;
      if (this.currentImageIndex >= this.imageSets[this.currentSetIndex].length) {
        this.currentImageIndex = 0;
        this.currentSetIndex++;
        if (this.currentSetIndex >= this.imageSets.length) {
          this.currentSetIndex = 0;
        }
      }
      this.setBackgroundImage(this.imageSets[this.currentSetIndex][this.currentImageIndex]);
    }, 5000); // Adjust interval timing as needed
  }

  setBackgroundImage(imageUrl: string): void {
    const backgroundImages = document.querySelector('.slides') as HTMLElement;
    if (backgroundImages) {
      backgroundImages.style.transform = `translateX(-${this.currentImageIndex * 100}%)`;
    }
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
