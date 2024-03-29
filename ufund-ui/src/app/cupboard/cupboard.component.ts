import { Component } from '@angular/core';
import { SchemeService } from '../scheme.service';
import { StorageService } from 'src/_services/storage.service';
import { MessageService } from '../message.service';
import { Router }  from '@angular/router'
import { Scheme } from '../scheme';

@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: ['./cupboard.component.css']
})
export class CupboardComponent {
  schemes: Scheme[] =  [];
  username?: string;
  role = '';

  constructor(private schemeService: SchemeService,
              private storageService: StorageService,
              public messageService: MessageService,
              public router: Router) { } ;

  ngOnInit(): void {
    this.getSchemes();

    this.storageService.user$.subscribe(user => {
      this.username = user.name;
      this.role = user.role;
    });

    if(this.role == 'ROLE_HELPER') {
      
      this.messageService.add(`RESTRICTED ACCESS`);
      this.router.navigate(['/dashboard']);
    }
  }

  getSchemes(): void {
    this.schemeService.getSchemes()
    .subscribe(scheme => this.schemes = scheme);
  }

  add(title: string, fundgoal: number): void {
    title = title.trim();
    if (!title) { return; }
    if (!fundgoal || fundgoal <= 0) { return; }
    this.schemeService.addScheme({ name: this.username, title: title, fundgoal: fundgoal} as Scheme)
      .subscribe(scheme => {
        this.schemes.push(scheme);
      });
  }

  delete(scheme: Scheme): void {
    this.schemes = this.schemes.filter(s => s !== scheme);
    this.schemeService.deleteScheme(scheme.id).subscribe();
  }
  
}
