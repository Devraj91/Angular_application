import { Injectable } from '@angular/core';
import {Http, Response, RequestOptions, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { Subject } from 'rxjs/Subject';
import { Router } from '@angular/router';


@Injectable()
export class PaymentService {

  constructor(private apiRequest: ApiRequestService,private translate:TranslateService, private router: Router) {}
//
  checkUrlExpire(invoiceId):Observable<any>{
    let paySubject = new Subject<any>();
    this.apiRequest.get("invoice/checkUrlExpiry/"+invoiceId).subscribe(jsonResp => {
    console.log(jsonResp);
    paySubject.next(jsonResp);  
  },error=>{
    this.handleError(error);
  });
  return paySubject;
  }
//

  saveCcavanueReq(reqData):Observable<any>{
    console.log(reqData);
    let paySubject = new Subject<any>();
    this.apiRequest.post("invoice/payment/ccavanuereq",reqData).subscribe(res=>{
      console.log(res);
      paySubject.next(res);
      },err=>{
        this.handleError(err);
      });
    return paySubject;
  }
  
//
saveCcavanueRes(resData){
  console.log(resData);
  return this.apiRequest.post("invoice/payment/ccavanueres",resData).subscribe(res=>{
   
  },err=>{
    this.handleError(err);
  });
}

private handleError(error: Response | any) {
  console.error(JSON.stringify(error));
return Observable.throw(error || 'Server Error');
}

getAmount(invoiceID): Observable<any> {
  let me = this;
  let AmountSubject = new Subject<any>(); 
  this.apiRequest.get('invoice/paymentDetails/'+ invoiceID).subscribe(jsonResp => {
    console.log("Response Receivesssssssssssssssssssssssss :"+JSON.stringify(jsonResp));
    AmountSubject.next(jsonResp);         
     
  });
return AmountSubject;
}

getAmount1()
{
  return [
    { id: 1, amt: 5000, tax:300}
    ];
}
}

