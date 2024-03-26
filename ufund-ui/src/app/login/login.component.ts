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

  // Assuming you have already duplicated the first image at the end of your array
  imageSets: string[][] = [
  [
    'assets/Asura.jpg',
    'assets/Yujiro.jpg',
    'assets/Zamasu.jpg',
    'assets/Toji.jpg',
    'assets/Perfect Cell.jpg',
    'assets/Garou.jpg',
  ],
  // Repeat for other sets as needed
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
      // Check if we need to loop back to the first image
      if (this.currentImageIndex >= this.imageSets[this.currentSetIndex].length) {
        this.currentImageIndex = 0; // Reset to the first image
        this.currentSetIndex++; // Move to the next set
        if (this.currentSetIndex >= this.imageSets.length) {
          this.currentSetIndex = 0; // Loop back to the first set
        }
      }
      this.slideToImage(this.currentImageIndex);
    }, 3000); // Adjust interval timing as needed
  }
  
  slideToImage(index: number): void {
    const backgroundImages = document.querySelector('.slides') as HTMLElement;
    if (backgroundImages) {
      const newPosition = -index * 100; // Calculate the new position for the transition
      backgroundImages.style.transition = 'transform 0.5s ease'; // Ensure transition is always enabled
      backgroundImages.style.transform = `translateX(${newPosition}%)`; // Move to the new position
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
