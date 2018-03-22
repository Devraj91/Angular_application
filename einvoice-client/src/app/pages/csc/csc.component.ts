import { Component, OnInit,EventEmitter,Output } from '@angular/core';
import { CscService } from '../../services/api/csc.service';
import { Country } from '../../models/country.model';
import { State } from '../../models/state.model';
import { City } from '../../models/city.model';

@Component({
  selector: 'country-loading',
  templateUrl: './csc.component.html',
  styleUrls: ['./csc.component.css']
})
export class CscComponent implements OnInit {

  selectedCountry:Country = new Country(0, 'India'); 
  selectedState:State = new State(0, 0,'Delhi'); 
  selectedCity:City = new City(0, 0,'Delhi'); 

  countries: Country[];
  states: State[];
  cities: City[];

  //define  to pass value to parent component
  @Output()countryEvent=new EventEmitter<Number>();
  @Output()stateEvent=new EventEmitter<Number>();
  @Output()cityEvent=new EventEmitter<Number>();
 
  constructor(private _cscService: CscService) {
    this.countries = this._cscService.getCountries();
  }
  onSelectCountry(cid) {
    this.states = this._cscService.getStates()
                 .filter((item)=> item.countryid == cid);
                 //pass value to parent
                 this.countryEvent.emit(cid);
  }
  onSelectState(sid) {
    this.cities = this._cscService.getCities()
                 .filter((item)=> item.stateid == sid);
                 //pass value to parent
                 this.stateEvent.emit(sid);
                 console.log(this.cities.length);
  }
  onSelectCity(cd) {
   //pass value to parent
   this.cityEvent.emit(cd);
  }
  ngOnInit() {
  }

}
