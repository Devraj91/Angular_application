import { Component, OnInit } from '@angular/core';
import { MemberSubscrptionService } from '../../services/api/membersubscription.service';
import { MemberdetailComponent } from '../memberdetail/memberdetail.component';
import {ToasterService} from 'angular2-toaster';

@Component({
  selector: 'app-membersubscription',
  templateUrl: './membersubscription.component.html',
  styleUrls: ['./membersubscription.component.css']
})
export class MembersubscriptionComponent implements OnInit {
members:any[];
categories:any[];
model: any = {};
memberId:any;
category:any;
subscriptionFee:any;
errMsg:string;

  constructor(private memberSubscrptionService:MemberSubscrptionService,private toasterService:ToasterService) { 
    this.loadAllCategory()
  }

  ngOnInit() {
  }

//
loadAllCategory(){
  console.log("loadAllCategory");
  this.memberSubscrptionService.getCategories().subscribe(jsonRes=>{
    console.log(jsonRes);
    this.categories=jsonRes;
  },err=>{
    this.toasterService.pop('error','','Unable to load Category');
    console.error("Unable to load : {} "+err);
  });
}
  //
  membersByCat(category){
    console.log("membersByCat");
    this.memberSubscrptionService.membersByCat(category).subscribe(jsonRes=>{
        console.log(jsonRes);
        this.members=jsonRes;
    },err=>{
      this.toasterService.pop('error','','Unable to load Member');
        console.error("Unable to load : {} "+err);
        this.errMsg=err;
    });
  }
  // 
  updateSubscription(){
    console.log("Input Model {}"+this.model);
    this.memberSubscrptionService.updateSubscription(this.model.category,this.model.memberId,this.model.subscriptionFee).subscribe(res=>{
      this.toasterService.pop('success', '', 'Subscription Successfully Updated');
      console.log("Successfully Updated");
    },err=>{
       this.toasterService.pop('error', '', 'UnSuccessfully Updated');
       console.error("UnSuccessfully Updated"+err);
    });
}
}
