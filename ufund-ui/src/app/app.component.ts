import { Component, OnInit, AfterViewInit } from '@angular/core';
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
  username?: string;
  role = '';

  isLoggedIn = false;

  constructor(public router: Router, private storageService: StorageService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if(!this.isLoggedIn) 
    {
      this.messageService.add(`Not logged in, redirecting...`);
      this.router.navigate(['/login']);
    }
      
    this.storageService.user$.subscribe(user => {
        this.username = user[1];
        this.role = user[2];
    });

  }

  logout(): void {
    this.storageService.clean();
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }

  orders=[
    { id: '1', name: 'order 1' },
    { id: '2', name: 'order 2' },
    { id: '3', name: 'order 3' },
    { id: '4', name: 'order 4' }
  ]

  selectedDay: string = '';
 
  selectChangeHandler (event: any) {
    this.selectedDay = event.target.value;
  }


  
}