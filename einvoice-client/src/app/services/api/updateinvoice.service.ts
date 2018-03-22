import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams, RequestMethod } from '@angular/http';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Injectable()
export class UpdateInvoiceService {
    constructor(private apiRequest: ApiRequestService,private translate:TranslateService,
        private toasterService: ToasterService) {}
    invoicedetail:any;

    getMembers(): Observable<any> {
       let me = this;
       let params: URLSearchParams = new URLSearchParams();
       let memberListSubject = new Subject<any>();
       me.apiRequest.get('invoice/invoiceDetails/',params).
       subscribe(jsonResp => {
        if(jsonResp.message=='No data available')
        {
            return memberListSubject;
        }
        else{
            console.log("$$$$$$$$$$$$$$",JSON.stringify(jsonResp));
        me.invoicedetail=jsonResp;
        let returnObj = this.invoicedetail.map(function(v, i, a){
            let newRow = Object.assign({}, v, {
                membershipID   : v.membershipID,
                name           : v.name,
                emailId        : v.emailId,
                invoiceAmt     : v.invoiceAmt,
                balanceAmt     : v.balanceAmt,
                invoiceId      : v.invoiceId,
                paidAmt        : v.paidAmt,
                poNumber       : v.poNumber,
                isTaxApplicable: v.isTaxApplicable,
                memberId       : v.memberId,
                address        : v.address,
                year           : v.year,
                isCancelable   : v.isCancelable,
                gstNo          : v.gstNo,
                toEmail        : v.toEmail
                        }
        );
            console.log('New Row'+newRow.membershipID+newRow.name);
            return newRow;
        });
        memberListSubject.next(returnObj); 
        }},error=>{
        console.error(error);
        return Observable.throw(error || 'Server error')
    });
    
    return memberListSubject;
    }

getMode(): Observable<any> {
    const paymentModes = new Subject<any>();
    this.apiRequest.get('/modeofpayment/get/all').subscribe(jsonResp => {
        paymentModes.next(jsonResp);
    });
    return paymentModes;
}

getTds(): Observable<any> {
    let me = this;
    let TDSListSubject = new Subject<any>(); 
    this.apiRequest.get('/tds/get/all').subscribe(jsonResp => {
        TDSListSubject.next(jsonResp);         
    });
  return TDSListSubject;
}

updateAmt(updatePaidAmt: Object):Observable<any>{
    console.log("Email Data : {} "+updatePaidAmt);
    return this.apiRequest.post('invoice/addInvoiceTransactionDetail',updatePaidAmt)
    .catch(this.handleError);
   }


   sendReceipt(member):Observable<any>{
    console.log("Member ID : {} "+JSON.stringify(member));
    return this.apiRequest.post('emailTemplate/sendReceipt',member)
    .catch(this.handleError);
   }

//
   cancel(cancelInvoice: Object) {
    this.apiRequest.post('invoice/cancel', cancelInvoice).subscribe(
        (data) => { console.log(data);this.toasterService.pop('success','','Invoice cancelled successfully.'); },
        err => { this.toasterService.pop('error','','Unable to cancel Invoice,Server Error'); }
    );
   }
//
   updatePO (updatePO: Object) {    
    this.apiRequest.post('invoice/editPo', updatePO)
             .subscribe(
        (data) => { this.toasterService.pop('success','','Updated Successfully.'); },
        err => { this.toasterService.pop('error','','Unable to Update,Server Error'); }
    );
   }
//
getInvoicePdf(invoiceId):Observable<any>{
    return this.apiRequest.getBinary('invoice/get/'+invoiceId);
}

getReceiptPdf(invoiceId):Observable<any>{
    return this.apiRequest.getBinary('/invoice/getReceipt/'+invoiceId);
}

private handleError(error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.message || error);
  }

}
