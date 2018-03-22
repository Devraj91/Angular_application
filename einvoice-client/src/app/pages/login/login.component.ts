import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/api/login.service';
import { Router } from '@angular/router';
import { error } from 'util';

@Component({
	selector   : 'app-login',
	templateUrl: './login.component.html',
    styleUrls  : [ './login.scss'],
})

export class LoginComponent implements OnInit {
    model: any = {};
    errMsg:string = '';
    constructor(
        private router: Router,
        private loginService: LoginService) { }

    ngOnInit() {
        // reset login status
        this.loginService.logout(false);
    }

    login() {
        console.log("Remember me -->"+this.model.rememberMe);
        this.loginService.getToken(this.model.username, this.model.password)
            .subscribe(resp => {
                console.log("login Ressponse--"+resp);
                if(resp.success){
                    this.router.navigate([resp.landingPage]);
                }else {
				this.errMsg = resp.message;
                  }
                  
                }
            );
    }

    onSignUp(){
      this.router.navigate(['signup']);
    }

    //
    forgetPassword(){
        console.log("forget password proccessing for : "+this.model.useremail);
        this.loginService.forgetPassword(this.model.useremail).subscribe(res=>{
         console.log(JSON.stringify(res));
         alert(res.message);
        },error=>{
            alert("Oops! Try Again. Thanks!")
        });
        
    }
}
