import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { LoginService } from '../../services/api/login.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
   newpassword :any ;
   confirmnewpassword:any;
   token:any;
   msg:any;

  constructor(private activatedRoute: ActivatedRoute,private loginService:LoginService) { }

  ngOnInit() {
    // subscribe to router event when parameter pass in path
    /*this.activatedRoute.params.subscribe((params: Params) => {
      console.log(params);
      this.token = params['token'];
      console.log(this.token);
      console.log(this.activatedRoute.snapshot.paramMap.get('token'))
    });*/
// subscribe to router event when parameter pass in query param
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      console.log(params);
      this.token = params['token'];
      console.log(this.token);
    });
  }
  //
  submitNewpassword(){
    this.loginService.submitNewPassword(this.newpassword,this.token).subscribe(json=>{
      console.log(json);
      this.msg=json.message;
    },err=>{
      console.error(err);
      this.msg=err.message;
    });
  }
}
