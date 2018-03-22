import { Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';

import {Observable, Subject} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';

@Injectable()
export class EmailtemplateService {
  constructor(private apiRequest: ApiRequestService,private translate:TranslateService) {}

  private saveEmailTemplateUrl = "emailTemplate/saveEmailTemplate";
  private getTemplateTypesUrl = "emailTemplate/getEmailTemplateTypes";
  private getTemplateByTemplateTypeUrl ="emailTemplate/getEmailTemplateByTemplate?template=";
  private getSenderDetailUrl="emailTemplate/getSenderDetails"
  private getEmailCategoriesUrl ="emailTemplate/getEmailCategories";
  private getRegionsUrl = "emailTemplate/getRegions"
  private sendMailUrl = "emailTemplate/sendMail";
  private getMembersUrl="emailTemplate/getMembers"
 
 saveTemplate(emailTemplate):Observable<any> {
    let body =JSON.stringify(emailTemplate); 
   return this.apiRequest.post(this.saveEmailTemplateUrl,body)
   .catch(this.handleError);
  }

  getAllTemplateTypes():Observable<any>{
    return this.apiRequest.get(this.getTemplateTypesUrl)
    .catch(this.handleError);
       
  }

  getTemplateByTemplate(template:any):Observable<any>{
    return this.apiRequest.get(this.getTemplateByTemplateTypeUrl+template)
    .catch(this.handleError);
  }

  getSenderDetails(){
    return this.apiRequest.get(this.getSenderDetailUrl)
    .catch(this.handleError);
  }

  getEmailCategories():Observable<any>{
    return this.apiRequest.get(this.getEmailCategoriesUrl)
    .catch(this.handleError);
  }

  getRegions(): Observable<any> {
    const me = this;
    const RegionListSubject = new Subject<any>();
    this.apiRequest.get('branch/get/all').subscribe(jsonResp => {
        RegionListSubject.next(jsonResp);
    });
  return RegionListSubject;
}

  getMembers():Observable<any>{
    return this.apiRequest.get(this.getMembersUrl)
    .catch(this.handleError);
 }

  getEmailCategory() {
   return [
     { value: 1, category: 'All Members'},
     { value: 2, category: 'Member Email'},
     { value: 3, category: 'Region'},
     ];
 }
 
 //
 sendMail(emailTemplate):Observable<any>{
  console.log("Email Data : {} "+emailTemplate);
  return this.apiRequest.post('emailTemplate/sendMail',emailTemplate)
  .catch(this.handleError);
 }

 //
 private handleError(error: Response | any) {
  console.error(error.message || error);
  return Observable.throw(error.message || error);
}

}
