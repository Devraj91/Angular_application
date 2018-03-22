import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable,Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { ApiRequestService } from '../../services/api/api-request.service';
import { error } from 'util';
import { Country } from '../../models/country.model';
import { State } from '../../models/state.model';
import { City } from '../../models/city.model';
 
@Injectable()
export class CscService {

  constructor(
    private router:Router,
    private apiRequest: ApiRequestService
) {
  this.apiRequest.get("/country/get");
}

  getCountries() {
    return [
     new Country(1, 'USA' ),
     new Country(2, 'India' ),
     new Country(3, 'Australia' )
    ];
  }
   
  getStates() {
   return [
     new State(1, 1, 'Arizona' ),
     new State(2, 1, 'Alaska' ),
     new State(3, 1, 'Florida'),
     new State(4, 1, 'Hawaii'),
     new State(5, 2, 'Gujarat' ),
     new State(6, 2, 'Goa'),
     new State(7, 2, 'Punjab' ),
     new State(8, 3, 'Queensland' ),
     new State(9, 3, 'South Australia' ),
     new State(10, 3, 'Tasmania')
    ];
  }

  getCities() {
    return [
      new City(1, 1, 'Arizona' ),
      new City(2, 1, 'Alaska' ),
      new City(3, 1, 'Florida'),
      new City(4, 1, 'Hawaii'),
      new City(5, 2, 'Gujarat' ),
      new City(6, 2, 'Goa'),
      new City(7, 2, 'Punjab' ),
      new City(8, 3, 'Queensland' ),
      new City(9, 3, 'South Australia' ),
      new City(10, 3, 'Tasmania')
     ];
   }
}