import { Injectable } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';
import {Address} from '../../models/addressmgmt.model';
import { Observable,Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { ApiRequestService } from '../../services/api/api-request.service';
import { TranslateService } from './translate.service';

@Injectable()
export class AddressmgmtService {

  constructor(private apiRequest: ApiRequestService,private translate:TranslateService) { }

//   getAddresses(page?:number, size?:number): Observable<any> {
//     let me = this;
//     let params: URLSearchParams = new URLSearchParams();
//     params.set('page', typeof page === "number"? page.toString():"0");
//     params.set('size', typeof page === "number"? size.toString():"1000");
//     let AddressListSubject = new Subject<any>(); 
//     this.apiRequest.get('branch/get/all').
//     subscribe(jsonResp => {
//         AddressListSubject.next(jsonResp.content); 
//     });
// return AddressListSubject;
// }
getAddresses(): Observable<any> {
    let me = this;
   // let params: URLSearchParams = new URLSearchParams();
   // params.set('page', typeof page === "number"? page.toString():"0");
   // params.set('size', typeof page === "number"? size.toString():"1000");
    let AddressListSubject = new Subject<any>(); 
    this.apiRequest.get('branch/get/all').subscribe(jsonResp => {
        AddressListSubject.next(jsonResp);         
    },error=>{
        this.handleError(error);
      });
  return AddressListSubject;
}

addAddress(address: Address): Observable<any> {
    let AddressListSubject = new Subject<any>(); 
    this.apiRequest.post('branch', address).subscribe(jsonResp=>{
        console.log("Response Receive :"+JSON.stringify(jsonResp)); 
        AddressListSubject.next(jsonResp);       
    },error=>{
        
      this.handleError(error);
    });
    return AddressListSubject;   
}
//
updateAddress(address: Address): Observable<any> {   
    return this.apiRequest.put('branch', address);
}

deleteAddress(id: Number): Observable<any> {
    console.log("IDDDDDDDDDDDDDD",id);
    return this.apiRequest.delete('branch' + `/` +id );
}

private handleError(error: Response | any) {
    console.error(JSON.stringify(error.status));
    
  return Observable.throw(error || 'Server Error');
  }
}
