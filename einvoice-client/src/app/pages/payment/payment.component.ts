import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { PaymentService } from '../../services/api/payment.service';
import { AppConfig } from '../../app-config';
import {Payment} from '../../models/payment.model';
import { ToasterService } from 'angular2-toaster/src/toaster.service';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import { Http } from '@angular/http';


@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  params: any;
  body:any={};
  encRequest:any;
  encResponse:any;
  accessCode:any= 'AVPG01EK30BW94GPWB';
  isRequest:boolean=true;
  invoiceId:any;
  ccavanueReq:any;
  paymentdetails;
  Arrtds = ['2', '10'];
  selamttds;
  amt=0;
  tax;
  amtTds;
  memberName;
  amtPaid;
  finalAmt=0;
  payment;
  paymentForm: FormGroup;
  baseAmount_flag:boolean=true;

  constructor(private activatedRoute: ActivatedRoute,
      private paymentService:PaymentService,
      private toasterService:ToasterService, 
      private router: Router,
      private appConfig: AppConfig,
      private http: Http) {
    this.paymentdetails = new Array<Payment>(); 
  }
  
  ngOnInit() {
    console.log('In ngOnInit');
    // subscribe to router event
    this.activatedRoute.queryParams.subscribe((params: Params) => {
       this.body.token = params['token'];
      console.log('token=>'+this.body.token);
      this.invoiceId=params['invoiceId'];
      console.log('invoiceId=>'+this.invoiceId);
      this.selamttds=true;
      this.setAmt(this.amt);
      this.setFinalAmt(); 
     this.loadPaymentDetails(this.invoiceId);
    });
   //
    if(this.isRequest){
    this.validatePaymentUrl();
    // assign unique id
   this.body.order_id = new Date().getMilliseconds();
   this.body.merchant_id = 45;
   this.body.currency = 'INR';
   this.body.redirect_url = this.appConfig.baseApiPath + '#/ccavResponseHandler';
   this.body.cancel_url = this.appConfig.baseApiPath + '#/ccavResponseHandler';
   this.body.language = 'EN';
    }
  }
//


private loadPaymentDetails(invoiceID){
    this.paymentService.getAmount(invoiceID).subscribe(res=>{
      console.log(' loading payment', res);
      this.paymentdetails = res;

      this.selamttds=true;
      this.memberName=this.paymentdetails.memberName;
      this.amt=this.paymentdetails.invoiceAmt;
      this.tax = this.paymentdetails.taxAmt;
      this.setAmt(this.amt);
      this.setFinalAmt();
      if(this.amt==0 ){
        console.log('what');
        this.baseAmount_flag=true;
      }
      else{
        this.baseAmount_flag=false;
      }
      console.log('JSONNNNNNN',JSON.stringify(res));
  },error=>{
      console.error(error);
      this.toasterService.pop('error','','Unable to load payment details');
  });
  // this.paymentdetails=this.paymentService.getAmount();
  // console.log("HE&UYYYYYYYY",JSON.stringify(this.paymentdetails));
   }


validatePaymentUrl(){
  //check url is expired or not
  this.paymentService.checkUrlExpire(this.invoiceId).subscribe(res=>{
    console.log('res=>'+res);
    if(res.message==='true'){
      console.log('Page Expire =>'+res.message);
      //redirect expiry page
      this.router.navigate(['app-payment-page-expire']);
    }
  },err=>{
    console.error('err=>'+err);
  });
}
//
processingPayment(){
  this.payment = {
    memberName : this.paymentdetails.memberName,
    invoiceId: this.invoiceId,
    memberId:this.paymentdetails.memberId,
    amt:this.amt,
    finalAmt:this.finalAmt,
    tax:this.tax,
    amtPaid:this.amtPaid,
    transactionType:'AUTOMATIC'

   };
   this.body.memberName = this.paymentdetails.memberName;
   this.body.invoiceId = this.invoiceId;
   this.body.member = {memberId: this.paymentdetails.memberId};
   this.body.amount = this.amt;
   this.body.finalAmt = this.finalAmt;
   this.body.tax = this.tax;
   this.body.amtPaid = this.amtPaid;
   this.body.transactionType = 'AUTOMATIC';
  console.log('body as params', this.body);
  // save in backend , get encrypt request
  this.paymentService.saveCcavanueReq(this.body).subscribe(res => {
    console.log('req from service', res);
    this.encRequest = res.message;
    console.log('enc req - ', this.encRequest);

    this.http.get(this.encRequest);

  }, err => {
    console.error('Opps caught problem, unable to process payment,Please try again');
  });
 this.isRequest = false;
}

redirectCCAvenue()
{
  console.log('Redirecting to CC Avenue ');
  const redirect = document.getElementById('redirect');
  }




selAmtTds(amttds) {
  this.amtTds=amttds;
  let tdsamt = ((amttds / 100) * this.amt);
  this.finalAmt = this.amt - tdsamt;
  this.setFinalAmt();
}

enableAmtTds() {
  this.selamttds = !this.selamttds;
  this.amt=this.paymentdetails.invoiceAmt;
  this.setAmt(this.amt);
  this.setFinalAmt();
}



setAmt(amt){
  console.log('total amt ',amt);
  this.amt=amt;
  this.finalAmt = amt;
}



setFinalAmt(){
  this.amtPaid=this.finalAmt+this.tax;
}

}