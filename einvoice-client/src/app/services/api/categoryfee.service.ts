import { Injectable } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';
import { Observable,Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { ApiRequestService } from '../../services/api/api-request.service';
import { error } from 'util';
import { TranslateService } from './translate.service';
import { CategoryFee } from '../../models/categoryFee.model';

@Injectable()
export class CategoryFeeService {

constructor(private apiRequest: ApiRequestService,private translate:TranslateService) { }
 
//
getCategoryFees(page?:number, size?:number): Observable<any> {
    //Create Request URL params
    let me = this;
    let params: URLSearchParams = new URLSearchParams();
    params.set('page', typeof page === "number"? page.toString():"0");
    params.set('size', typeof page === "number"? size.toString():"1000");
    let categoryFeeListSubject = new Subject<any>(); 
    this.apiRequest.get('categoryfee/get',params).
    subscribe(jsonResp => {
        categoryFeeListSubject.next(jsonResp.content); 
    });
return categoryFeeListSubject;
}
//
addCategoryFee(categoryFee: CategoryFee): Observable<any> {
    let taxConfigSubject = new Subject<any>(); 
    this.apiRequest.post('categoryfee', categoryFee).subscribe(jsonResp=>{
        console.log("Response Receive :"+JSON.stringify(jsonResp)); 
        taxConfigSubject.next(jsonResp);       
    },error=>{
      this.handleError(error);
    });
    return taxConfigSubject;   
}
//
updateCategoryFee(categoryFee: CategoryFee): Observable<any> {   
    return this.apiRequest.put('categoryfee', categoryFee);
}
//
deleteCategoryFee(id: Number): Observable<any> {
    return this.apiRequest.delete('categoryfee' + `/` + id)
}
//
private handleError(error: Response | any) {
    console.error(JSON.stringify(error));
  return Observable.throw(error || 'Server Error');
  }
}