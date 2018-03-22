import { Component, OnInit } from '@angular/core';
import {TemplateRef, ViewChild} from '@angular/core';
import { CategoryFee } from '../../models/categoryFee.model';
import { CategoryFeeService } from '../../services/api/categoryfee.service';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
  @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
  @ViewChild('editTemplate') editTemplate: TemplateRef<any>;

categories:any=[]
tempCategoryFee:CategoryFee;
isNewRecord: boolean;
  constructor(private categoryService:CategoryFeeService,private toasterService:ToasterService) {
    this.categories=new Array<CategoryFee>();
   }

  ngOnInit() {
    this.loadCategoryFee();
  }
  //
  loadTemplate(cat: CategoryFee) {
    if (this.tempCategoryFee && this.tempCategoryFee.id == cat.id) {
        return this.editTemplate;
    } else {
        return this.readOnlyTemplate;
    }
  }
  //
  loadCategoryFee(){
    this.categoryService.getCategoryFees(0,100).subscribe(res=>{
      console.log(res);
      this.categories=res;
    },error=>{
      console.error(error);
      this.toasterService.pop("error","","Unable to laod Category Fees");
    });
  }
  //
  editCategoryFee(cat:CategoryFee){
    console.log("Edit Fee" +JSON.stringify(cat));
    this.tempCategoryFee=cat;
}
//
saveCategoryFee(){
if(this.isNewRecord){
  this.categoryService.addCategoryFee(this.tempCategoryFee).subscribe(res=>{
    console.log("Successfully Added."+res);
    this.toasterService.pop("success","","Successfully Added.");
    this.loadCategoryFee();
  },error=>{
    console.error(error);
    this.toasterService.pop("error","","Unable to add Category Fee");
  });
this.isNewRecord=false;
this.tempCategoryFee=null;
}else{
  console.log('Going to update =>'+this.tempCategoryFee)
  this.categoryService.updateCategoryFee(this.tempCategoryFee).subscribe(res=>{
    console.log("Successfully Updated."+res);
    this.toasterService.pop("success","","Successfully Updated");
  },error=>{
    console.error(error);
    this.toasterService.pop("error","","Unable to Edit Category Fee");
  });
  this.tempCategoryFee=null;
}
}
//
addCategoryFee(){
  this.tempCategoryFee=new CategoryFee(0,'',0);
  this.categories.push(this.tempCategoryFee);
  this.isNewRecord=true;
}
//
deleteCategoryFee(id){
  this.categoryService.deleteCategoryFee(id).subscribe(res=>{
    console.log("Successfully Deleted."+res);
    this.toasterService.pop("success","","Successfully Deleted");
    this.loadCategoryFee();
  },error=>{
    console.error(error);
    this.toasterService.pop("error","","Unable to Delete Category Fee");
  });
}
//
cancel(){
  this.tempCategoryFee=null;
  this.loadCategoryFee();
}

}
