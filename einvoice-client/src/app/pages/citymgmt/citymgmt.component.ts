import {TemplateRef, ViewChild, ElementRef} from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import {CityMapping} from '../../models/citymapping.model';
import {CitymgmtService} from '../../services/api/citymgmt.service';
import {Headers, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Component({
  selector: 'app-citymgmt',
  templateUrl: './citymgmt.component.html',
  styleUrls: ['./citymgmt.component.css']
})
export class CitymgmtComponent implements OnInit {

  @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
  @ViewChild('editTemplate') editTemplate: TemplateRef<any>;
  @ViewChild('closeBtn') closeBtn: ElementRef;
  tempCity:CityMapping ;
  cities:any=[];    
  cityForm: FormGroup;
  addCity: any;
  Regions:any=[];
  selRegion;
   constructor(private citymgmtService: CitymgmtService,private toasterService:ToasterService) {     
     this.cities=new Array<CityMapping>();
 }


ngOnInit() {  
this.loadInitialCities();
this.loadBranches();
this.cityForm = new FormGroup({
 city : new FormControl('',Validators.required),
 region: new FormControl('',Validators.required),
});
}


private loadInitialCities(){
  this.loadCities(0,10);
}

//
loadTemplate(city: CityMapping) {
if (this.tempCity && this.tempCity.id === city.id) {
 return this.editTemplate;
} else {
   return this.readOnlyTemplate;
}
}  

private closeModal(): void {
this.closeBtn.nativeElement.click();
}

private loadCities(page,size){
this.citymgmtService.getCities(page,size).subscribe(res=>{
 this.cities =res;
 console.log("JSONNNNNNN",this.cities);
},error=>{
 console.error(error);  
 this.toasterService.pop("error","","Unable to load Addresses");
});
}

loadBranches(){
  this.citymgmtService.getRegions().subscribe(res=>{
    console.log("REGIONS", res );
    let length=Object.keys(res).length;
    console.log("length",length);
    for (let i = 0; i < length; i++) {
      this.Regions.push(res[i].name);      
  }
    console.log("Regionss", this.Regions );
   },error=>{
    console.error(error);
   });
}
getRegionSelected(selRegion){
console.log("IDDDDD",selRegion);
}

editCity(city: CityMapping) {
this.tempCity = city;
console.log(this.tempCity.region.regionName);
console.log( "EDITTTTT",this.tempCity);
}

saveCity() {
  console.log( "save",this.tempCity);
 this.citymgmtService.updateCity(this.tempCity).subscribe(res => {
  console.log("Successfully Updated."+JSON.stringify(res));
   this.toasterService.pop("success","","Successfully Updated");
   this.loadInitialCities();
   console.log("initiallllllllllllll.");
 },error=>{
   console.error(error);
   this.toasterService.pop("error","","Unable to Edit Address");
 });
 this.tempCity = null;
}


cancel(){
this.tempCity = null;
this.loadInitialCities();
}

// deleteCity(id) {
// console.log("Deleteeeeeeeeeeeeeeeeeeeeeeeee1111111 :"+id);
// this.citymgmtService.deleteCity(id).subscribe(res=>{
// console.log("Successfully Deleted."+res);
// this.toasterService.pop("success","","Successfully Deleted");
// this.loadInitialCities();
// },error=>{
// console.error(error);
// this.toasterService.pop("error","","Unable to Delete Address");
// });  
// }


addNewCity(cityForm) {
console.log("3",cityForm.city);
console.log("4",cityForm.region);
this.addCity = {
 city:{
   name :cityForm.city
  },
 branch:{
  name:cityForm.region
 }
};
console.log("AddRESSSSSSSSSSSSSSSSS FINALLLLYY",JSON.stringify(this.addCity));
this.citymgmtService.addCity(this.addCity).subscribe(res => {
   console.log("Successfully Added."+res);
   this.toasterService.pop("success","","Successfully Added.");
   this.loadInitialCities();
 },error=>{
   console.error(error);
   this.toasterService.pop("error","","Unable to add Taxconfig");
 });
 this.closeModal();
 this.clearAll();
}

clearAll()
{
  this.cityForm.reset();
}
}
 