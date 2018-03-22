import { Component, OnInit,TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MemberService } from '../../services/api/member.service';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Component({
  selector: 'app-memberdetail',
  templateUrl: './memberdetail.component.html',
  styleUrls: ['./memberdetail.component.css']
})
export class MemberdetailComponent implements OnInit {

  
  columns:any[];
  rows:any[];
  searchData:any[];
  pageSize:number=10;
  currentPage:number=0;
  isLastPageLoaded:boolean=false;
  isLoading:boolean=false;
  tableData;

  @ViewChild('memberDiscontinuedTpl') memberDiscontinuedTpl: TemplateRef<any>;
  
  constructor(private router: Router, private memberService: MemberService,private toasterService:ToasterService) {
    this.getPageData(false);
   }

  ngOnInit() {
      this.columns = [
        //{prop:"memberId"        , name: "MEMBER ID"          , width:100  },
        //{prop:"emailId"     , name: "EMAIL"       , width:280 },
        {prop:"membershipID" , name: "MEMBERSHIP ID"  , width:200 },
        {prop:"name"  , name: "MEMBER NAME"   , width:380 },
        {prop:"category" , name: "CATEGORY" , width:230 },
        {prop:"invoiceAmt"     , name: "INVOICE AMOUNT"       , width:230},
        {prop:"paidAmt"     , name: "PAID AMOUNT"       , width:230 },
        {prop:"balanceAmt"     , name: "BALANCE AMOUNT"       , width:230 },
        //{prop:"", name: "REGION"  , width:120 },
       // {prop:"isActive", name: "ACTIVE"  , width:200,cellTemplate: this.memberDiscontinuedTpl },
    ];

  }
//
  memberFilter(event,type) {

    const val = event.target.value.toLowerCase();
    if(!val)this.rows=this.tableData;
    console.log(type+" : Input search val : "+val);
    var temp;
    if(val===''){
        this.rows=this.tableData;  
    }
    else{
        if(event.keyCode===8){
            this.rows=this.tableData;
        }
    switch(type) {
        case "MembershipId":
        temp = this.rows.filter(function(d) {
            return d.membershipID.toLowerCase().indexOf(val) !== -1 || !val;
        });this.rows=temp;
        break;
        case "MemberName":
        temp = this.rows.filter(function(d) {
            return d.name.toLowerCase().indexOf(val) !== -1 || !val;
        });
        // console.log("BBBBBBBBBB - temp",JSON.stringify(temp));
        this.rows=temp;
        // console.log("BBBBBBBBBB - new rows",JSON.stringify(this.rows));
        break;
        default:
        // console.log("defaule case");
        this.rows=this.tableData;
    }  
   }
  }

//   getPageData(isAppend:boolean=false) {
//     console.log("Getting page data");
//             if (this.isLastPageLoaded===false){
//                 let me = this;
//                 me.isLoading=true;
//                 this.memberService.getMembers(this.currentPage,this.pageSize).subscribe((data) => {
//                     me.isLastPageLoaded=data.last;
//                     me.currentPage = data.currentPageNumber+1;
//                     this.tableData=data;
//                     if (isAppend===true){
//                         me.rows = me.rows.concat(data);
//                     }
//                     else{
//                         me.rows = data;
//                     }
//                     me.isLoading=false;
//                 },err=>{
//                     console.log("Unable to load member,Server Error");
//                     this.toasterService.pop('error','','Unable to load member,Server Error');
//                 });
//             }
//             console.log("Mmeber Loaded.");
//         }
getPageData(isAppend:boolean=false) {
    console.log("Getpagedata");
    var me = this;
     this.isLoading = true;
     this.memberService.getMembers().subscribe((data) => {
     console.log("ALL MEMBERS",data);
     // this.data = data;
      this.tableData=data;
      if (isAppend===true){
        me.rows = me.rows.concat(data);
    }
    else{
        me.rows = data;
    }
     //this.searchData = this.rows;
     this.isLoading = false;
     },err=>{
       console.log("Unable to load member,Server Error");
       this.toasterService.pop('error','','Unable to load member,Server Error');
   });
  }      
onScroll() {
        console.log("bottom")
        if (this.isLoading===false){
            this.getPageData(true);
        }
    }

}
