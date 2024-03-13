import { Injectable } from '@angular/core';
import { StorageService } from 'src/_services/storage.service';
import { MessageService } from './message.service';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  constructor(private storageService: StorageService) { }
  user = this.storageService.getUser();
  username = '';
  role = '';

  updateProfile(): void {
    this.storageService.user$.subscribe(user => {
      this.username = user[1];
      this.role = user[2];
    });
  }
}
