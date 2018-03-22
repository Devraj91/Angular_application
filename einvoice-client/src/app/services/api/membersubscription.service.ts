import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';

@Injectable()
export class MemberSubscrptionService {
    constructor(private apiRequest: ApiRequestService,private translate:TranslateService) {}

    getMembers(page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: URLSearchParams = new URLSearchParams();
        params.set('page', typeof page === "number"? page.toString():"0");
        params.set('size', typeof page === "number"? size.toString():"1000");
       let memberListSubject = new Subject<any>();
       this.apiRequest.get('member/get/',params).
       subscribe(jsonResp => {
        let returnObj = jsonResp.content.map(function(v, i, a){
            let newRow = Object.assign({}, v, {
                membershipStart  : me.translate.getDateStringByInputString(v.membershipStart),
                membershipEnd   : me.translate.getDateStringByInputString(v.membershipEnd)
            });
            return newRow;
        });
        memberListSubject.next(returnObj); 
    });
return memberListSubject;
    }

//
getCategories():Observable<any>{
    return this.apiRequest.get("member/get/category/all").catch(this.handleError)
}

//
membersByCat(category):Observable<any>{
    return this.apiRequest.get("member/get/category/"+category).catch(this.handleError);
    }
//
private handleError(error: Response | any) {
        console.error(error.message || error);
        return Observable.throw(error.message || error);
  }
 // 
  updateSubscription(category,memberId,subscriptionFee):Observable<any>{
    return this.apiRequest.put('member/updateSubscription/'+category+"/"+memberId+"/"+subscriptionFee,null).catch(_=>{this.handleError
    throw _;
    });
  }
}
