import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
flag=false;
  constructor() { }

  ngOnInit() {
  }
setflag(){
  this.flag=true;
  console.log("chnaged" ,this.flag)
}
  
}
