import { Injectable } from '@angular/core';
import {Http, Response, RequestOptions, Headers} from '@angular/http';
import {Scheduler} from '../../models/Scheduler.model';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class SchedulerService {
  scheduler:Scheduler;

  constructor(private apiRequest: ApiRequestService,private translate:TranslateService) {}

  getScheduleByType(type):Observable<any>{
    let me=this;
    let scheduleSubject = new Subject<any>();
    this.apiRequest.get("schedule/getByType/"+type).subscribe(jsonResp => {
    console.log(jsonResp);
    let returnObj= new Scheduler(jsonResp.id,jsonResp.type,jsonResp.isOn,this.translate.getDate(jsonResp.date),this.translate.getTime(jsonResp.date),jsonResp.isRecurring,jsonResp.recurringType);
    scheduleSubject.next(returnObj);  
  },error=>{
    this.handleError(error);
  });  
  return scheduleSubject;
  }

private handleError(error: Response | any) {
return Observable.throw(error || 'Server Error');
}

updateSchedule(schedule):Observable<any>{
  return this.apiRequest.put("/schedule",schedule).catch(this.handleError);
}





getischeduler(): Array<Scheduler> {
    return [];
 /*   new Scheduler().init(
      1,
      "InvoiceMail",
       true,
       '2017-10-22',
       '12:00',
       true,
       'Daily'
    ),
    {
    id: 2,
    type:"ReminderMail",
    isOn: true,
    date: '2017-10-21',
    time: '12:00',
    isRecurring: true,
    recurringType : 'Monthly'
    }
    ];*/
}

}
