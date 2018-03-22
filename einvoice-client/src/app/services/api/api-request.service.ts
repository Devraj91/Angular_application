import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions,ResponseContentType, URLSearchParams,RequestMethod } from '@angular/http';
import { Router } from '@angular/router';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { UserInfoService, LoginInfoInStorage} from '../../services/user-info.service';
import { AppConfig } from '../../app-config';


@Injectable()
export class ApiRequestService {

    private headers:Headers;
    private requestOptions:RequestOptions;

    constructor(
        private appConfig:AppConfig,
        private http: Http,
        private router:Router,
        private userInfoService:UserInfoService
    ) {}

    /**
     * This is a Global place to add all the request headers for every REST calls
     */
    appendAuthHeader():Headers {
        let headers = new Headers({'Content-Type': 'application/json'});
        let token = this.userInfoService.getStoredToken();
        if (token !==null) {
            headers.append("X-AUTH-TOKEN", token);
        }
        return headers;
    }

    /**
     * This is a Global place to define all the Request Headers that must be sent for every ajax call
     */
    getRequestOptions(requestMethod, url:string, urlParam?:URLSearchParams, body?:Object):RequestOptions {
        let options = new RequestOptions({
            headers: this.appendAuthHeader(),
            method : requestMethod,
            url    : this.appConfig.baseApiPath + url
        });
        if (urlParam){
            options = options.merge({ params: urlParam});
        }
        if (body){
            options = options.merge({body: JSON.stringify(body)});
        }
        console.log("Req :=> "+JSON.stringify(options));
        return options;
    }

    get(url:string, urlParams?:URLSearchParams):Observable<any>{
        let me = this;
        let requestOptions = this.getRequestOptions(RequestMethod.Get, url, urlParams);
        return this.http.request(new Request(requestOptions))
            .map(resp => resp.json())
            .catch(function(error:any){
                console.error(JSON.stringify(error));
                return Observable.throw(error || 'Server error')
            });
    }

    post(url:string, body:Object):Observable<any>{
        let me = this;
        let requestOptions = this.getRequestOptions(RequestMethod.Post, url, undefined, body);
        return this.http.request(new Request(requestOptions))
            .map(resp => resp.json())
            .catch(function(error:any){
                console.error(JSON.stringify(error))
              return Observable.throw(error || 'Server error')
            });
    }

    put(url:string, body:Object):Observable<any>{
        let me = this;
        let requestOptions = this.getRequestOptions(RequestMethod.Put, url, undefined, body);
        return this.http.request(new Request(requestOptions))
            .map(resp => resp.json())
            .catch(function(error:any){
                console.error(JSON.stringify(error))
                return Observable.throw(error || 'Server error')
            });
    }

    delete(url:string):Observable<any>{
        let me = this;
        let requestOptions = this.getRequestOptions(RequestMethod.Delete, url);
        return this.http.request(new Request(requestOptions))
            .map(resp => resp.json())
            .catch(function(error:any){
                console.error(JSON.stringify(error))
                return Observable.throw(error || 'Server error')
            });
    }

    options(url:string ,body:Object,urlParams?:URLSearchParams):Observable<any>{
        let me = this;
        let requestOptions = this.getRequestOptions(RequestMethod.Post, url, urlParams, body);
        return this.http.request(new Request(requestOptions))
            .map(resp => resp.json())
            .catch(function(error:any){
                console.error(JSON.stringify(error))
              return Observable.throw(error || 'Server error')
            });
    }
    getBinary(url:string, urlParams?:URLSearchParams):Observable<any>{
        let me = this;        
        let requestOptions = this.getRequestOptions(RequestMethod.Get, url, urlParams);
       requestOptions.responseType=ResponseContentType.ArrayBuffer;
        return this.http.request(new Request(requestOptions))
            .map(resp => resp)
            .catch(function(error:any){
               console.error(JSON.stringify(error));
                return Observable.throw(error || 'Server error')
            });
    }
}
