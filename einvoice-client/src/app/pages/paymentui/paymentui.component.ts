import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { PaymentService } from '../../services/api/payment.service';
import { AppConfig } from '../../app-config';
import {Payment} from '../../models/payment.model';
import { ToasterService } from 'angular2-toaster/src/toaster.service';
import {FormGroup, FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-paymentui',
  templateUrl: './paymentui.component.html',
  styleUrls: ['./paymentui.component.css']
})
export class PaymentuiComponent implements OnInit {
  body:any={};
  encRequest:any;
  encResponse:any;
  accessCode:any= 'AVPG01EK30BW94GPWB';
  isRequest:boolean=true;
  invoiceId:any;
  ccavanueReq:any;
  paymentdetails;
  payment;
  Arrtds = ['2', '10'];
  selamttds;
  seltaxtds;
  amt;
  tax;
  amtTds;
  taxTds;
  memberName;
  finalTax=0;
  amtPaid;
  finalAmt=0;
  paymentForm: FormGroup;
  baseAmount_flag:boolean=true;

  constructor(private activatedRoute: ActivatedRoute,private paymentService:PaymentService,private toasterService:ToasterService, private router: Router,private appConfig: AppConfig) {
    this.paymentdetails=new Array<Payment>(); 
  }
  
  ngOnInit() {
    console.log('iiiiiiiiiiiiiiiiIn ngOnInit');
    // subscribe to router event
    this.activatedRoute.queryParams.subscribe((params: Params) => {
       this.body.token = params['token'];
      console.log('token=>'+this.body.token);
      this.invoiceId=params['invoiceId'];
      console.log('invoiceId=>'+this.invoiceId);
      this.amt=0;
      this.tax=0.18*(this.amt);
      this.seltaxtds=true;
      this.selamttds=true;
      this.setAmt(this.amt);
      this.setTax(this.tax);
      this.setFinalAmt();
   
       
 this.loadPaymentDetails(this.invoiceId);
   if(!this.amt || this.amt==0){
    this.baseAmount_flag=false;
   }
      
    });
   //
    if(this.isRequest){
    this.validatePaymentUrl();
    //assign unique id
   // this.body.order_id=new Date().getMilliseconds();
  //  this.body.merchant_id=47;
  //  this.body.currency='INR';
  //  this.body.redirect_url=this.appConfig.baseApiPath+"#/ccavResponseHandler";
  //  this.body.cancel_url=this.appConfig.baseApiPath+"#/ccavResponseHandler";
  //  this.body.language='EN';
    }
  }
//


private loadPaymentDetails(invoiceID){
    this.paymentService.getAmount(invoiceID).subscribe(res=>{
      this.paymentdetails = res;
      this.seltaxtds=true;
      this.selamttds=true;
      this.memberName=this.paymentdetails.memberName;
      this.amt=this.paymentdetails.balanceAmount;
      this.tax=0.18*(this.amt);
      this.setAmt(this.amt);
      this.setTax(this.tax);
      this.setFinalAmt();
      console.log("JSONNNNNNN",JSON.stringify(res));
  },error=>{
      console.error(error);
      this.toasterService.pop("error","","Unable to load payment details");
  });
  // this.paymentdetails=this.paymentService.getAmount();
  // console.log("HE&UYYYYYYYY",JSON.stringify(this.paymentdetails));
   }


validatePaymentUrl(){
  //check url is expired or not
  this.paymentService.checkUrlExpire(this.invoiceId).subscribe(res=>{
    console.log("res=>"+res);
    if(res.message==='true'){
      console.log("Page Expire =>"+res.message);
      //redirect expiry page
      this.router.navigate(['app-payment-page-expire']);
    }
  },err=>{
    console.error("err=>"+err);
  });
}
//
processingPayment(){
  console.log(this.body);
  this.payment = {
    memberName : this.paymentdetails.memberName,
    invoiceId: this.invoiceId,
    memberId:this.paymentdetails.memberId,
    amt:this.amt,
    finalAmt:this.finalAmt,
    tax:this.tax,
    finalTax:this.finalTax,
    amtPaid:this.amtPaid
   };
   console.log(this.payment);
  //save in backend , get encrypt request
  this.paymentService.saveCcavanueReq(this.body).subscribe(res=>{
    console.log(res);
    this.encRequest=res.message;
  },err=>{
    console.error("Opps caught problem, unable to process payment,Please try again");
  });
  console.log("encRequest=>"+this.encRequest);
 this.isRequest=false;
}

redirectCCAvenue()
{
  console.log('Redirecting to CC Avenue ');
  let redirect=document.getElementById('redirect');
  }




selAmtTds(amttds) {
  this.amtTds=amttds;
  let tdsamt = ((amttds / 100) * this.amt);
  this.finalAmt = this.amt - tdsamt;
  this.setFinalAmt();
}

enableAmtTds() {
  this.selamttds = !this.selamttds;
  this.amt=this.paymentdetails.balanceAmount;
  this.setAmt(this.amt);
  this.setFinalAmt();
}

enableTaxTds() {
  this.seltaxtds = !this.seltaxtds;
  this.tax=0.18*(this.amt);
  this.setTax(this.tax);
  this.setFinalAmt();


}
selTaxTds(taxtds) {
 console.log("taxtds",taxtds);
 this.taxTds = taxtds;
 let tdstaxamt = ((taxtds / 100) * this.tax);
 this.finalTax = this.tax - tdstaxamt;
 this.setFinalAmt();

}
setAmt(amt){
  console.log("total amt ",amt);
  this.amt=amt;
  this.finalAmt = amt;
}

setTax(tax){
  console.log("total tax ",tax);
  this.tax=tax;
  this.finalTax=tax;
}

setFinalAmt(){
  this.amtPaid=this.finalAmt+this.finalTax;
}

}