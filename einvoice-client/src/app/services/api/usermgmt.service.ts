import { Injectable } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';
import {Usermgmt} from '../../models/usermgmt.model';
import { Observable,Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { ApiRequestService } from '../../services/api/api-request.service';
import { TranslateService } from './translate.service';

@Injectable()   
export class UsermgmtService {

  constructor(private apiRequest: ApiRequestService,private translate:TranslateService) { }

getUsers(): Observable<any> {
    let me = this;
    let UserListSubject = new Subject<any>(); 
    // let params: URLSearchParams = new URLSearchParams();
    // params.set('page', typeof page === "number"? page.toString():"0");
    // params.set('size', typeof page === "number"? size.toString():"1000");
    this.apiRequest.get('user/get/all').subscribe(jsonResp => {
        UserListSubject.next(jsonResp);         
    });
  return UserListSubject;
}

getRegions(): Observable<any> {
    let me = this;
    let RegionListSubject = new Subject<any>(); 
    this.apiRequest.get('branch/get/all').subscribe(jsonResp => {
        RegionListSubject.next(jsonResp);         
    });
  return RegionListSubject;
}

addUser(user: Usermgmt): Observable<any> {
    console.log("CITYYYYYYYYY",user);
    let UserListSubject = new Subject<any>(); 
    this.apiRequest.post('user/add', user).subscribe(jsonResp=>{
        console.log("Response Receive :"+JSON.stringify(jsonResp)); 
        UserListSubject.next(jsonResp);       
    },error=>{
      this.handleError(error);
    });
    return UserListSubject;   
}

updateUser(user: Usermgmt): Observable<any> {   
    return this.apiRequest.put('user/update', user);
}

// deleteUser(id: Number): Observable<any> {
//     console.log("IDDDDDDDDDDDDDD",id);
//     return this.apiRequest.delete('user' + `/` +id );
// }

private handleError(error: Response | any) {
    console.error(JSON.stringify(error));
  return Observable.throw(error || 'Server Error');
  }
}
