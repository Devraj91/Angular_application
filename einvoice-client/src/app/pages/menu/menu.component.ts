import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  @Input() menuList: Array<Object>;
  @Output() menuToggle = new EventEmitter();
  isExpended = false;
  
  constructor() { }

  ngOnInit(){
    console.log('Menu List is: ', this.menuList);
  }

 
}