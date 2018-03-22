import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

countryId:Number;
stateId:Number;
cityId:Number;
  constructor(private router: Router) { }

  ngOnInit() {
  }

  recieveCountry($event){
   this.countryId=$event;
  }
  recieveState($event){
    this.stateId=$event;
  }
  recieveCity($event){
        this.cityId=$event;
  }
}
