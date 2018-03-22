import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response, RequestOptions,URLSearchParams } from '@angular/http';
import { Router } from '@angular/router';

import { Observable,Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { UserInfoService, LoginInfoInStorage} from '../../services/user-info.service';
import { ApiRequestService } from '../../services/api/api-request.service';
import { error } from 'util';

export interface LoginRequestParam{
    username:string;
    password:string;
}

@Injectable()
export class LoginService {

    public landingPage:string = "/home/dashboard";
    constructor(
        private router:Router,
        private http: Http,
        private userInfoService: UserInfoService,
        private apiRequest: ApiRequestService
    ) {}


    getToken(username:string, password:string): Observable<any> {
        let me = this;

        let bodyData:LoginRequestParam = {
            "username": username,
            "password": password,
        }
        let loginDataSubject:Subject<any> = new Subject<any>(); // Will use this subject to emit data that we want after ajax login attempt
        let loginInfoReturn:LoginInfoInStorage; // Object that we want to send back to Login Page

        this.apiRequest.post('session', bodyData)
            .subscribe(
                jsonResp => {
                console.log("Response"+jsonResp);
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                  loginInfoReturn = {
                        "success"    : true,
                        "message"    : jsonResp.operationMessage,
                        "landingPage": this.landingPage,
                        "user"       : {
                            "userId"     : jsonResp.item.userId,
                            "email"      : jsonResp.item.email,
                            "displayName": jsonResp.item.email, //jsonResp.item.firstName + " " + jsonResp.item.lastName,
                            "token"      : jsonResp.item.token,
                        }
                    };

                    // store username and jwt token in session storage to keep user logged in between page refreshes
                    console.log("User : "+JSON.stringify(loginInfoReturn.user))
                    this.userInfoService.storeUserInfo(JSON.stringify(loginInfoReturn.user));
                }else {
                    loginInfoReturn = {
                        "success":false,
                        "message":jsonResp.operationMessage,
                        "landingPage":"/login"                       
                    };
                }
                console.log("loginInfoReturn--"+loginInfoReturn);
                loginDataSubject.next(loginInfoReturn);                
            }
        ,error=>{
            console.log(error.status);
            console.log(error.operationStatus);
            console.log(error.operationMessage);
            loginInfoReturn = {
                "success":false,
                "message":"Server error",
                "landingPage":"/login"                       
            };
            switch(error.status){
                    case 401:
                    loginInfoReturn.message = 'Username or password is incorrect';
                    break;
                    case 404:
                    loginInfoReturn.message = 'Service not found';
                    break;
                    case 408:
                    loginInfoReturn.message = 'Request Timedout';
                    break;
                    case 500:
                    loginInfoReturn.message = 'Internal Server Error';
                    break;
                    default:
                    loginInfoReturn.message = 'Server Error';
            }
            loginDataSubject.next(loginInfoReturn); 
        });
            return loginDataSubject;
    }

    logout(navigatetoLogout=true): void {
        // clear token remove user from local storage to log user out
        this.userInfoService.removeUserInfo();
        if(navigatetoLogout){
            this.router.navigate(["logout"]);
        }
    }
    
    //
    forgetPassword(username:string): Observable<any> {
        let forgetSubject = new Subject<any>();
        let params: URLSearchParams = new URLSearchParams();
        params.set('username', typeof username === "string"? username.toString():username);
        this.apiRequest.options('user/submitforgotPassword',params)
            .subscribe(json=>{
                console.log(json);               
                forgetSubject.next(json);
            },error=>{
                this.handleError(error) ;
            });
            return forgetSubject;
    }

    //
    submitNewPassword(newPassword:string,token:string){
        let forgetSubject = new Subject<any>();
        let params: URLSearchParams = new URLSearchParams();
        params.set('newpassword', typeof newPassword === "string"? newPassword.toString():newPassword);
        params.set('token', typeof token === "string"? token.toString():token);
        this.apiRequest.post('user/submitNewPassword',params)
            .subscribe(json=>{
                console.log(json);               
                forgetSubject.next(json);
            },error=>{
                this.handleError(error);
            });
            return forgetSubject;
    }

    private handleError(error: Response | any) {
        console.error(JSON.stringify(error));
      return Observable.throw(error || 'Server Error');
      }
}
