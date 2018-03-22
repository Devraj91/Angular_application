import { Injectable } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';
import {TaxConfig} from '../../models/taxconfig.model';
import { Observable,Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { ApiRequestService } from '../../services/api/api-request.service';
import { TranslateService } from './translate.service';

@Injectable()
export class TaxConfigService {
constructor(private apiRequest: ApiRequestService,private translate:TranslateService) { }
 //
getTaxConfigs(page?:number, size?:number): Observable<any> {
        let me = this;
        let params: URLSearchParams = new URLSearchParams();
        params.set('page', typeof page === "number"? page.toString():"0");
        params.set('size', typeof page === "number"? size.toString():"1000");
        let taxConfigListSubject = new Subject<any>(); 
        this.apiRequest.get('tax/get',params).
        subscribe(jsonResp => {
            taxConfigListSubject.next(jsonResp.content); 
        });
    return taxConfigListSubject;
}
//
addTaxConfig(taxconfig: TaxConfig): Observable<any> {
    let taxConfigSubject = new Subject<any>(); 
    this.apiRequest.post('tax', taxconfig).subscribe(jsonResp=>{
        console.log("Response Receive :"+JSON.stringify(jsonResp)); 
        taxConfigSubject.next(jsonResp);       
    },error=>{
      this.handleError(error);
    });
    return taxConfigSubject;   
}
//
updateTaxConfig(taxconfig: TaxConfig): Observable<any> {   
    return this.apiRequest.put('tax', taxconfig);
}
//
deleteTaxConfig(id: Number): Observable<any> {
    return this.apiRequest.delete('tax' + `/` + id)
}
//
private handleError(error: Response | any) {
    console.error(JSON.stringify(error));
  return Observable.throw(error || 'Server Error');
  }
}