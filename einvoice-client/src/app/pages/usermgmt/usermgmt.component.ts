import {TemplateRef, ViewChild, ElementRef} from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import {Usermgmt} from '../../models/usermgmt.model';
import {UsermgmtService} from '../../services/api/usermgmt.service';
import {Headers, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Component({
  selector: 'app-usermgmt',
  templateUrl: './usermgmt.component.html',
  styleUrls: ['./usermgmt.component.css']
})
export class UsermgmtComponent implements OnInit {
  @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
  @ViewChild('editTemplate') editTemplate: TemplateRef<any>;
  @ViewChild('closeBtn') closeBtn: ElementRef;
  tempUser:Usermgmt ;
  users:any=[];    
  userForm: FormGroup;
  addUser: any;
  Regions:any=[];
  Roles:any[]=['ADMIN','SUB-ADMIN','USER'];
  selRegion;
  roles;
   constructor(private usermgmtService: UsermgmtService,private toasterService:ToasterService) {     
     this.users=new Array<Usermgmt>();
 }


 private loadInitialUsers(){
  this.loadUsers();
}   

ngOnInit() {  
this.loadInitialUsers();
this.loadBranches();
this.userForm = new FormGroup({
 email : new FormControl('',Validators.required),
 password : new FormControl('',Validators.required),
 branch: new FormControl('',Validators.required),
 role : new FormControl('',Validators.required),
});
}  
//
loadTemplate(user: Usermgmt) {
if (this.tempUser && this.tempUser.userId === user.userId) {
 return this.editTemplate;
} else {
   return this.readOnlyTemplate;
}
}  

private closeModal(): void {
this.closeBtn.nativeElement.click();
}

private loadUsers(){
this.usermgmtService.getUsers().subscribe(res=>{
 this.users = res;
 console.log("JSONNNNNNN",res);
 //this.loadRoles();
},error=>{
 console.error(error);
 this.toasterService.pop("error","","Unable to load Addresses");
});
}

loadBranches(){
  this.usermgmtService.getRegions().subscribe(res=>{
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

loadRoles(){
console.log('ROLE',this.users.roles);
//   let length=Object.keys(this.users.roles).length;
 
//   for (let i = 0; i < length; i++) {
//     this.roles.push(this.users.roles[0].name);      
// }
}

getRegionSelected(selRegion){
console.log("IDDDDD",selRegion);
}

getRoleSelected(selRole){
  console.log("ROLEEEEE",selRole);
}
editUser(user: Usermgmt) {
this.tempUser = user;
console.log(this.tempUser.branch.name);
console.log( "EDITTTTT",this.tempUser);
}

saveUser() {
  console.log( "save",this.tempUser);
  console.log("Successfully Updated."+JSON.stringify(this.tempUser));
 this.usermgmtService.updateUser(this.tempUser).subscribe(res => {
  console.log("Successfully Updated."+JSON.stringify(res));
   this.toasterService.pop("success","","User Updated Successfully");
   this.loadInitialUsers();
   console.log("initiallllllllllllll.");
 },error=>{
   console.error(error);
   this.toasterService.pop("error","","Unable to Update User");
 });
 this.tempUser = null;
}


cancel(){
this.tempUser = null;
this.loadInitialUsers();
}

// deleteUser(id) {
// console.log("Deleteeeeeeeeeeeeeeeeeeeeeeeee1111111 :"+id);
// this.usermgmtService.deleteUser(id).subscribe(res=>{
// console.log("Successfully Deleted."+res);
// this.toasterService.pop("success","","Successfully Deleted");
// this.loadInitialUsers();
// },error=>{
// console.error(error);
// this.toasterService.pop("error","","Unable to Delete Address");
// });  
// }


addNewUser(userForm) {
console.log("3",userForm.city);
console.log("4",userForm.region);
this.addUser = {
 emailId :userForm.email,
 password:userForm.password,
 branch:{
  name:userForm.branch
 },
 roles:[
   {name:userForm.role}
  ]
};
console.log("AddRESSSSSSSSSSSSSSSSS FINALLLLYY",JSON.stringify(this.addUser));
this.usermgmtService.addUser(this.addUser).subscribe(res => {
   console.log("Successfully Added."+res);
   this.toasterService.pop("success","","Successfully Added.");
   this.loadInitialUsers();
 },error=>{
   console.error(error);
   this.toasterService.pop("error","","Unable to add Taxconfig");
 });
 this.closeModal();
}


}
