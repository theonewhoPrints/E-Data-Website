import { Component, Injectable } from '@angular/core';
import { ProfileService } from '../profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

@Injectable({
  providedIn:'root'
})
export class ProfileComponent {
  constructor(public profileService: ProfileService) { }
}
