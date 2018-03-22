import {Component, ViewChild, TemplateRef, OnInit} from '@angular/core';
import { Scheduler, Type, RecurringType } from '../../models/Scheduler.model';
import {SchedulerService } from '../../services/api/schedule.service';
import { ToasterService } from 'angular2-toaster/src/toaster.service';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {

  rtypes = ['Daily', 'Weekly', 'Monthly'];
  scheduler:Scheduler;
  invoice_checked: boolean=true; 
  recurring_checked: boolean=true;
  member_checked:boolean=true;
  mrecurring_checked:boolean=true;
  date;
  dateToday;
  mm;dd;
  validDate=true;
  mvalidDate=true;
 result;

  constructor(private schedulerService: SchedulerService,private toasterService:ToasterService) {
  }
  
  ngOnInit() { 
      this.today();
    this.scheduler=new Scheduler(0,Type.Invoice,false,'','',false,RecurringType.Daily);
    this.loadInvoiceSchedule();   //default 
   // var displayDate = new Date().toLocaleDateString();
 
}
//
today()
{
  var zero='0';
  var d = new Date();
  this.mm = d.getMonth() + 1;
  if(this.mm<10){ this.mm=zero+this.mm}
  this.dd = d.getDate();
  if(this.dd <10){ this.dd=zero+this.dd }
  var yy = d.getFullYear();
  this.dateToday = yy + '-' + this.mm + '-' + this.dd;
  console.log(this.dateToday);
}

RtypeCheckbox(recurring){
  console.log(recurring,this.recurring_checked);
   this.recurring_checked=!this.recurring_checked;
   if(recurring=='true'){
    this.scheduler.recurringType=null;
   }
}
 
ItypeCheckbox(ischeduler){
  console.log(ischeduler,this.invoice_checked);
   this.invoice_checked=!this.invoice_checked;
   if(ischeduler=='true'){
    this.scheduler.date=null
    this.scheduler.time=null
   }
}

RtypeCheckboxMember(mrecurring){
  console.log(mrecurring,this.recurring_checked);
   this.mrecurring_checked=!this.mrecurring_checked;
   if(mrecurring=='true'){
    this.scheduler.recurringType=null;
   }
}

ItypeCheckboxMember(mscheduler){
  console.log(mscheduler,this.member_checked);
   this.member_checked=!this.member_checked;
   if(mscheduler=='true'){
    this.scheduler.date=null
    this.scheduler.time=null
   }
}

dateValidation(){
var date=this.scheduler.date;
console.log("AA",date,this.dateToday);
if(this.result.type=='Invoice'){
  console.log("BBBB",this.scheduler.type);
  (date<this.dateToday)?(this.validDate=false):(this.validDate=true);
}
else{
  console.log("CCCC",this.scheduler.type);
  (date<this.dateToday)?(this.mvalidDate=false):(this.mvalidDate=true);
}
}

getSchedule(type){
  console.log("Loading schedule for =>"+type)
  this.schedulerService.getScheduleByType(type).subscribe(res => {
    console.log(res);
      this.scheduler = res;
      this.result=res;
      if(type=='Invoice'){
        if(this.scheduler.date){
          console.log("1",this.scheduler.date);
        this.validDate=true;
      }  
      if(this.scheduler.isOn){
        this.invoice_checked=false;
      }
      else{
        this.invoice_checked=true;
      }
      if(this.scheduler.isRecurring){
        this.recurring_checked=false;  
      }
      else{
        this.recurring_checked=true;
        this.scheduler.recurringType=null;
      }
    }
    else if(type=='Member'){
      console.log("2",this.scheduler.type);
      if(this.scheduler.date){
        this.mvalidDate=true;
      }  
      
      if(this.scheduler.isOn){
        this.member_checked=false;
      }
      else{
        this.member_checked=true;
      }
      if(this.scheduler.isRecurring){
        this.mrecurring_checked=false;  
      }
      else{
        this.mrecurring_checked=true;
        this.scheduler.recurringType=null;
      }

    }
  },err=>{
    console.error('Unable to get schedule {} '+err);
    this.toasterService.pop('error','','Unable to get schedule data, Server error');
   
    
  });
}
 //   
loadInvoiceSchedule(){
  //reset
  this.scheduler=new Scheduler(0,Type.Invoice,false,'','',false,RecurringType.Daily);
  this.getSchedule(Type[Type.Invoice]);    
}
//
loadMembeSchedule(){
  this.scheduler=new Scheduler(0,Type.Member,false,'','',false,RecurringType.Daily);
  this.getSchedule(Type[Type.Member]);
}
 //
 updateInvoiceSchedule(){
  this.updateSchedule(Type[Type.Invoice]);    
}
//
updateMembeSchedule(){
  this.updateSchedule(Type[Type.Member]);
}
//
updateSchedule(type){
  console.log("Updating schedule for => "+type);
  let updateScheduler={
    id:this.scheduler.id,
    type:this.scheduler.type,
    isOn:this.scheduler.isOn,
    date:this.scheduler.date+"T"+this.scheduler.time,
    isRecurring:this.scheduler.isRecurring,
    recurringType:this.scheduler.recurringType
  } ;
  console.log(JSON.stringify(updateScheduler));
  this.schedulerService.updateSchedule(updateScheduler).subscribe(res=>{
   console.log('successfully updated. {} '+res)
   this.toasterService.pop('success','','successfully updated.');
   this.getSchedule(type);
  },err=>{
    console.error('Unable to update. {} '+err)
    this.toasterService.pop('error','','Unable to update.')
  });
}

}
