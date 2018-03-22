import { Component, OnInit ,TemplateRef, ViewChild} from '@angular/core';
import { Router } from '@angular/router';
import { UpdateInvoiceService } from '../../services/api/updateinvoice.service';
import { ToasterService } from 'angular2-toaster/src/toaster.service';
import { Member } from '../../models/Member';
import { SendMember } from '../../models/Member';
import { MemberService } from '../../services/api/member.service';

@Component({
  selector: 'app-updateinvoice',
  templateUrl: './updateinvoice.component.html',
  styleUrls: ['./updateinvoice.component.css']
})
export class UpdateinvoiceComponent implements OnInit {
  modes: any;
  arrModes: any;
  cc;
  filteredList: any[];
  query: string;
  emails: any[];
  columns: any[];
  data: any[];
  rows: any[];
  temp: any[];
  Arrtds :any=[];
  blurloader: boolean=false;
  years: any[]= ['2013', '2014', '2015', '2016', '2017', '2018'];
  TaxApplicable= ['yes', 'no'];
 // searchData: any[];
  pageSize: number= 10;
  currentPage:number=0;
  isLastPageLoaded: boolean=false;
  isLoading: boolean=false;
  isTds:boolean=false;
  seltds:boolean=true;
  baseamt;
  // totalamt:number=0;
  // tdsamt:number=0;
  // tds:number=0;
  paidAmt: number = 0;
  clickedrow;
  clickedrowindex;  
  year: any= (new Date()).getFullYear();
  remarks;
  poYear;
  poNumber;
  memberId: number;
  sendMember: SendMember;
  isTaxApplicable: boolean ;
  isPerforma:boolean;
  UpdatePaidAmt: any;
  CancelInvoice: any;
  UpdateInvocieDetails: any;
  street;
  pin;
  fax;
  phone;
  tableData;
  cities: any;
  content: any;
  address: any;
  cityname: any;
  city: any;
  toEmail:any;
  gstNo:any;
  disabletax: boolean =false;
  AmtType:any[]=['Base Amount','Total Amount'];
  mode:any;
  @ViewChild('updatepaidamountTpl') updatepaidamountTpl: TemplateRef<any>;
  @ViewChild('isTaxApplicableTpl') isTaxApplicableTpl: TemplateRef<any>;

  constructor(private router: Router, private UpdateInvoiceService: UpdateInvoiceService,
    private toasterService: ToasterService, private memberService: MemberService) {
    this.getPageData(false);
   }

  ngOnInit() {

    this.getTds();
      this.isTaxApplicable = false;
      this.isPerforma = false;
      this.sendMember = new SendMember(0);
       this.columns = [
      {prop: 'membershipID' , name: 'MEMBERSHIP ID' , width: 180 },
      {prop: 'name' , name: 'MEMBER NAME' , width: 280},
      {prop: 'year' , name: 'YEAR' , width: 130 },
      {prop: 'invoiceAmt' , name: 'INVOICE AMT' , width: 110},
      {prop: 'balanceAmt' , name: 'BALANCE AMT' , width: 130 },
      {prop: 'paidAmt' , name: 'PAID AMT' , width: 110 },
      {prop: 'poNumber' , name: 'PO NO' , width: 90 },
      {prop: 'status' , name: 'PAYMENT STATUS' , width: 150 },
      {prop: 'isTaxApplicable' , name: 'TAX APPL..' , width: 100, cellTemplate: this.isTaxApplicableTpl},
      {prop: 'actions', name: 'ACTIONS', width:230, cellTemplate: this.updatepaidamountTpl}
    ];

    this.UpdateInvoiceService.getMode().subscribe(res => {
      this.modes = res;
    }, error => {
      console.error(error);
     });

  }

  getTds(){
    this.UpdateInvoiceService.getTds().subscribe(res => {
      const length = Object.keys(res).length;
      for (let i = 0; i < length; i++) {
        this.Arrtds.push(res[i].rate);
    }
     }, error => {
      console.error(error);
     });

  }
  selectyear(year) {
    this.year = year;
    const me = this;
    me.getPageData(false);
  }

  selectcity(city) {
    this.city = city;
  }

  getRow(clickedrow) {
    this.clickedrow = clickedrow;
    // this.tdsamt = 0;
    this.clickedrowindex = clickedrow.$$index;
    this.poNumber=this.clickedrow.poNumber;
    this.isTaxApplicable=this.clickedrow.isTaxApplicable;
    this.selectTaxApplicable(this.isTaxApplicable);
    this.isPerforma=this.clickedrow.isPerforma;
    this.selectPerforma(this.isPerforma);
    if(this.clickedrow.paidAmt>0){ 
      this.disabletax=true;
    }
    else{
      this.disabletax=false;
    }
    this.address=this.clickedrow.address;
    this.cities=this.clickedrow.cities;
    this.city=this.address.city.name;
    this.selectcity(this.city);
    this.street=this.address.street;
    this.pin=this.address.pin;
    this.fax=this.address.fax;
    this.phone=this.address.phone;
    this.paidAmt=this.clickedrow.paidAmt;
    this.gstNo=this.clickedrow.gstNo;
    this.cc=this.clickedrow.ccEmails;
    this.toEmail=this.clickedrow.toEmail;

    }

    selMode(mode){
     this.mode = mode;
    }

    enableTds(){
      this.seltds=!this.seltds;
    }

    selTds(tds){
    console.log("TDSSSSSSSSS",tds);
    }
    selAmtType(type){
      console.log("typeeeee",type);
    }

    addPaidAmount() {
    this.UpdatePaidAmt = {
    invoiceId : this.clickedrow.invoiceId,
    baseAmt : this.baseamt,
    isTdsDeducted: false ,
    tdsRate: null ,
    year: this.year,
    memberId: this.clickedrow.memberId,
    mode: 'MANUAL',
    onlineTaxAmount: 0
    };
   console.log('IIIIIIIIIIIDDDDDDDDDDDDDDD', this.clickedrow.memberId);
   this.sendMember.memberId = this.clickedrow.memberId;
   console.log('IIIIIIIIIIIDDDDDDDDDDDDDDD', this.sendMember);
  console.log('Update Paid Amount ', this.UpdatePaidAmt);
   this.UpdateInvoiceService.updateAmt(this.UpdatePaidAmt).subscribe(data => {
     console.log('MSGGGGGGGGGGGGGG', data.msgCode);
    this.toasterService.pop('success', '', ' Paid Amount updated successfully.Please wait for the receipt');
    this.blurloader=true;
     // If amount updated succefully, generate receipt
    if (data.msgCode === 'Success')  {
      console.log('YYYYYYYYYYYOOOOOOOOOOOO');
      this.blurloader=false;
         this.UpdateInvoiceService.sendReceipt(this.sendMember).subscribe( data => {
      this.toasterService.pop('success', '', ' Receipt sent.');
     },
     error => {
      this.blurloader=false;
      this.toasterService.pop('error', '', 'Unable to send the Receipt.');
     });
    }
   },
   error => {
    this.toasterService.pop('error', '', 'Unable to update paid amount,Server Error');
   });
  
   this.clearalldata();
   setTimeout(() => this.getPageData(false), 550);
  }

  setRemarks(remarks) {
  this.remarks = remarks;
  }
  //
  cancelInvoice() {
     if (this.paidAmt > 0) {
   this.toasterService.pop('error', '', 'You cannot cancel partially paid invoice');
    } else {
  this.CancelInvoice = {
    remarks: this.remarks,
    invoiceId : this.clickedrow.invoiceId
   };
  this.UpdateInvoiceService.cancel(this.CancelInvoice);
  this.clearalldata();
  this.getPageData(false);
  }
  }
 

  setPO(poNumber) {
  this.poNumber = poNumber;
  // Number(this.poNumber);
  }
  setAddress(address: string) {
    this.address = address;
  }
  selectpoyear(poyear) {
  this.poYear = poyear;
  }

  selectTaxApplicable(isTaxApplicable) {
    this.isTaxApplicable = isTaxApplicable;
  }
  selectPerforma(isPerforma){
   this.isPerforma=isPerforma;
  }

  setStreet(street) {
    this.street = street;
  }
  setPin(pin) {
    this.pin = pin;
  }
  setFax(fax) {
    this.fax = fax;
  }
  setPhone(phone) {
    this.phone = phone;
  }

  setCc(cc) {
    this.cc = cc;
  }

  setTo(to){
   this.toEmail=to;
  }
  setGst(gst){
    this.gstNo=gst;
  }
  //
  editPO() {
      this.UpdateInvocieDetails = {
      invoiceId : this.clickedrow.invoiceId,
      isTaxApplicable : this.isTaxApplicable,
      isPerforma:this.isPerforma,
      year : this.year,
      poNumber : this.poNumber,
      cityname :  this.city,
      street : this.street,
      pin : this.pin,
      fax : this.fax,
      phone: this.phone,
      ccEmails: this.cc,
      gstNo: this.gstNo,
      toEmail:this.toEmail
     };
    console.log(this.UpdateInvocieDetails);
   this.UpdateInvoiceService.updatePO(this.UpdateInvocieDetails);
   this.clearalldata();
   setTimeout(() => this.getPageData(false), 700);
  }

  getPageData(isAppend: boolean= false) {
    const me = this;
     this.isLoading = true;
     this.UpdateInvoiceService.getMembers().subscribe((data) => {
      this.data = data;
      this.tableData = data;
      if (isAppend === true) {
        me.rows = me.rows.concat(data);
    } else {
        me.rows = data;
    }
     this.isLoading = false;
     }, err => {
      this.isLoading = false;
       console.log('Unable to load member,Server Error');
       this.toasterService.pop('error', '', 'Unable to load member,Server Error');
   });
  }

  onScroll() {
    if (this.isLoading === false) {
        this.getPageData(true);
    }
}
   clearalldata() {
           this.baseamt = '';
          this.poNumber = '';
          this.isTaxApplicable = false;
          this.poYear = 0 ;
          this.remarks = '' ;
       }

memberFilter(event, type) {
const val = event.target.value.toLowerCase();
if (!val) {this.rows = this.tableData; }
let temp;
if (val === '') {
    this.rows = this.tableData;
} else {
    if (event.keyCode === 8) {
        this.rows = this.tableData;
    }
switch (type) {
    case 'MembershipId':
    temp = this.rows.filter(function(d) {
        return d.membershipID.toLowerCase().indexOf(val) !== -1 || !val;
    });
    this.rows = temp;
    break;
    case 'MemberName':
    temp = this.rows.filter(function(d) {
        return d.name.toLowerCase().indexOf(val) !== -1 || !val;
    });
    this.rows = temp;
    break;
    default:
    this.rows = this.tableData;
}
}
}
  getPdf(invoiceId) {
    console.log("AAAAAAAAAAAAAAAAAA",invoiceId);
  this.UpdateInvoiceService.getInvoicePdf(invoiceId).subscribe(response => {
  const blob = new Blob([response._body], { type: 'application/pdf' });
  const fileURL = URL.createObjectURL(blob);
  window.open(fileURL); // if you want to open it in new tab
  });
}

getReceiptPdf(receiptId) {
  console.log("AAAAAAAAAAAAAAAAAA",receiptId);
  this.UpdateInvoiceService.getReceiptPdf(receiptId).subscribe(response => {
  const blob = new Blob([response._body], { type: 'application/pdf' });
  const fileURL = URL.createObjectURL(blob);
  window.open(fileURL); // if you want to open it in new tab
  });
}
}
  

