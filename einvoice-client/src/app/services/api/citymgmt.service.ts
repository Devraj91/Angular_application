import { Injectable } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';
import {CityMapping} from '../../models/citymapping.model';
import { Observable,Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { ApiRequestService } from '../../services/api/api-request.service';
import { TranslateService } from './translate.service';

@Injectable()   
export class CitymgmtService {

  constructor(private apiRequest: ApiRequestService,private translate:TranslateService) { }

getCities(page?:number, size?:number): Observable<any> {
    let me = this;
    let CityListSubject = new Subject<any>(); 
    let params: URLSearchParams = new URLSearchParams();
    params.set('page', typeof page === "number"? page.toString():"0");
    params.set('size', typeof page === "number"? size.toString():"1000");
    this.apiRequest.get('cityregionmapping/get/all',params).subscribe(jsonResp => {
        CityListSubject.next(jsonResp);         
    });
  return CityListSubject;
}

getRegions(): Observable<any> {
    let me = this;
    let RegionListSubject = new Subject<any>(); 
    this.apiRequest.get('branch/get/all').subscribe(jsonResp => {
        RegionListSubject.next(jsonResp);         
    });
  return RegionListSubject;
}

addCity(city: CityMapping): Observable<any> {
    console.log("CITYYYYYYYYY",city);
    let CityListSubject = new Subject<any>(); 
    this.apiRequest.post('cityregionmapping', city).subscribe(jsonResp=>{
        console.log("Response Receive :"+JSON.stringify(jsonResp)); 
        CityListSubject.next(jsonResp);       
    },error=>{
      this.handleError(error);
    });
    return CityListSubject;   
}
//
updateCity(city: CityMapping): Observable<any> {   
    return this.apiRequest.put('cityregionmapping', city);
}

// deleteCity(id: Number): Observable<any> {
//     console.log("IDDDDDDDDDDDDDD",id);
//     return this.apiRequest.delete('cityregionmapping' + `/` +id );
// }

private handleError(error: Response | any) {
    console.error(JSON.stringify(error));
  return Observable.throw(error || 'Server Error');
  }
}
