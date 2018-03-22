import {TemplateRef, ViewChild, ElementRef} from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import {Address} from '../../models/addressmgmt.model';
import {AddressmgmtService} from '../../services/api/addressmgmt.service';
//import {TaxConfig,City} from '../../models/taxconfig.model';
//import {TaxConfigService} from '../../services/api/taxconfig.service';
import {Headers, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Component({
  selector: 'app-addressmgmt',
  templateUrl: './addressmgmt.component.html',
  styleUrls: ['./addressmgmt.component.css']
})
export class AddressmgmtComponent implements OnInit {

      @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
     @ViewChild('editTemplate') editTemplate: TemplateRef<any>;
     @ViewChild('closeBtn') closeBtn: ElementRef;
     tempAddress: Address;
     addresses:any=[];
     isNewRecord: boolean;     
     addressForm: FormGroup;
     addAddress: any;
      constructor(private addressMgmtService: AddressmgmtService,private toasterService:ToasterService) {     
        this.addresses=new Array<Address>();
    }

 //
 ngOnInit() {  
  this.loadInitialAddresses();
  this.addressForm = new FormGroup({
    name : new FormControl('',Validators.required),
    region: new FormControl('',Validators.required),
    street: new FormControl('',Validators.required),
    city: new FormControl('',Validators.required),
    phone: new FormControl('',Validators.required),
    pin: new FormControl('',Validators.required),
    fax: new FormControl('',Validators.required),
    gstNo: new FormControl('',Validators.required)
 });
}
//
loadTemplate(address: Address) {
  if (this.tempAddress && this.tempAddress.id === address.id) {
    return this.editTemplate;
  } else {
      return this.readOnlyTemplate;
  }
}
private closeModal(): void {
  this.closeBtn.nativeElement.click();
}
private loadInitialAddresses(){
  this.addressMgmtService.getAddresses().subscribe(res=>{
    this.addresses = res;
    console.log("JSONNNNNNN",JSON.stringify(res));
},error=>{
    console.error(error);
    this.toasterService.pop("error","","Unable to load Addresses");
});
 }

// //
//   private loadInitialTaxConfigs(){
//   this.loadTaxConfigs(0,100);
// }
// // 
// private loadTaxConfigs(page,size) {
//      this.taxConfigService.getTaxConfigs(page,size).subscribe(json=>{
//       console.log("TaxConfigs : "+json);
//       this.taxconfigs =json;
//   },error=>{
//       console.error(error);
//       this.toasterService.pop("error","","Unable to laod Tax Config");
//   });
//   }
//   //
 editAddress(address: Address) {
   this.tempAddress = address;
   console.log( "EDITTTTT",JSON.stringify(this.tempAddress));
   }

saveAddress() {
  console.log('Going to update =>'+JSON.stringify(this.tempAddress));
    this.addressMgmtService.updateAddress(this.tempAddress).subscribe(res => {
     console.log("Successfully Updated."+JSON.stringify(res));
      this.toasterService.pop("success","","Successfully Updated");
      this.loadInitialAddresses();
      console.log("initiallllllllllllll.");
    },error=>{
      console.error(error);
      this.toasterService.pop("error","","Unable to Edit Address");
    });
    this.tempAddress = null;
}

 
 cancel(){
 this.tempAddress = null;
 }

 deleteAddress(id) {
   console.log("Deleteeeeeeeeeeeeeeeeeeeeeeeee1111111 :"+id);
this.addressMgmtService.deleteAddress(id).subscribe(res=>{
  console.log("Successfully Deleted."+res);
  this.toasterService.pop("success","","Successfully Deleted");
 this.loadInitialAddresses();
},error=>{
console.error(error);
this.toasterService.pop("error","","Unable to Delete Address");
});  
}


addNewAddress(addressForm) {
  this.addAddress = {
    name :addressForm.branch ,
    street:addressForm.street,
    city:{
      name:addressForm.city
    } ,   
       phone:addressForm.phone,   
       pin:addressForm.pin ,  
       fax:addressForm.fax,     
       gstNo:addressForm.gastNo
   };
   console.log("AddRESSSSSSSSSSSSSSSSS FINALLLLYY",JSON.stringify(this.addAddress));
  this.addressMgmtService.addAddress(this.addAddress).subscribe(res => {
      console.log("Successfully Added."+res);
      this.toasterService.pop("success","","Successfully Added.");
      this.loadInitialAddresses();
    },error=>{
      console.error(error);
      this.toasterService.pop("error","","Unable to add Taxconfig");
    });
    this.closeModal();
    this.clearAll();  
}

clearAll()
{
  this.addressForm.reset();
}

}

