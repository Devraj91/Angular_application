import {TemplateRef, ViewChild} from '@angular/core';
import { Component, OnInit } from '@angular/core';
import {TaxConfig,City} from '../../models/taxconfig.model';
import {TaxConfigService} from '../../services/api/taxconfig.service';
import {Headers, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Component({
  selector: 'taxconfig',
  templateUrl: './taxconfig.component.html',
  styleUrls: ['./taxconfig.component.css'],
})

export class TaxconfigComponent implements OnInit {

 @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
 @ViewChild('editTemplate') editTemplate: TemplateRef<any>;

 tempTaxconfig: TaxConfig;
 taxconfigs: any=[];
 isNewRecord: boolean;
 taxApplicables:any=['True','False'];
 
  constructor(private taxConfigService: TaxConfigService,private toasterService:ToasterService) {     
    this.taxconfigs=new Array<TaxConfig>();
}
//
 ngOnInit() {  
    this.loadInitialTaxConfigs();
  }
  //
 loadTemplate(taxconfig: TaxConfig) {
    if (this.tempTaxconfig && this.tempTaxconfig.id === taxconfig.id) {
        return this.editTemplate;
    } else {
        return this.readOnlyTemplate;
    }
  }
  //
    private loadInitialTaxConfigs(){
    this.loadTaxConfigs(0,100);
}
  // 
  private loadTaxConfigs(page,size) {
       this.taxConfigService.getTaxConfigs(page,size).subscribe(json=>{
        console.log("TaxConfigs : "+json);
        this.taxconfigs =json;
    },error=>{
        console.error(error);
        this.toasterService.pop("error","","Unable to laod Tax Config");
    });
    }
    //
editTaxConfig(taxconfig: TaxConfig) {
    this.tempTaxconfig = taxconfig;
    }

 //
saveTaxConfig() {
  // if (this.isNewRecord) {
  //     this.taxConfigService.addTaxConfig(this.tempTaxconfig).subscribe(res => {
  //       console.log("Successfully Added."+res);
  //       this.toasterService.pop("success","","Successfully Added.");
  //       this.loadInitialTaxConfigs();
  //     },error=>{
  //       console.error(error);
  //       this.toasterService.pop("error","","Unable to add Taxconfig");
  //     });
  //     this.isNewRecord = false;
  //     this.tempTaxconfig = null;

  // } else {
    console.log('Going to update =>' + this.tempTaxconfig);
      this.taxConfigService.updateTaxConfig(this.tempTaxconfig).subscribe(res => {
        console.log("Successfully Updated."+res);
        this.toasterService.pop("success","","Successfully Updated");
      },error=>{
        console.error(error);
        this.toasterService.pop("error","","Unable to Edit Taxconfig");
      });
      this.tempTaxconfig = null;
}
//
// addTaxConfig() {
//     this.tempTaxconfig = new TaxConfig(0, '', 0, new City(0,''),'')
//     this.taxconfigs.push(this.tempTaxconfig);
//     this.isNewRecord = true;
//   }
//  
cancel() {
  this.tempTaxconfig = null;
  this.loadTaxConfigs(0,100);
}

// deleteTaxConfig(id) {
//     console.log("DeleteTaxConfig :"+id);
//   this.taxConfigService.deleteTaxConfig(id).subscribe(res=>{
//     console.log("Successfully Deleted."+res);
//     this.toasterService.pop("success","","Successfully Deleted");
//    this.loadInitialTaxConfigs();
// },error=>{
//   console.error(error);
//   this.toasterService.pop("error","","Unable to Delete Category Fee");
// });

// }
}
