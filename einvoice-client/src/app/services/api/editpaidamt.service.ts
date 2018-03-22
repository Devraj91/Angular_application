import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';
import { Observable, ReplaySubject, Subject } from 'rxjs/Rx';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Injectable()
export class EditPaidAmtService {
    constructor(private apiRequest: ApiRequestService, private translate: TranslateService, private http: Http,
        private toasterService: ToasterService ) {}
 

       
}
