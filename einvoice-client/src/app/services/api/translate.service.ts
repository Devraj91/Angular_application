import { Injectable, Inject,VERSION } from '@angular/core';
import { AppConfig } from '../../app-config';

@Injectable()
export class TranslateService {
    public dateFormat     = { year:'numeric', month: 'short', day: 'numeric'};
    public dateFormatWithTime     = { year:'numeric', month: 'short', day: 'numeric',hour:'numeric',min:'numeric',sec:'numeric'};

    constructor(private appConfig:AppConfig) { 
       let  formatsDateTest: string[] = [
            'dd/MM/yyyy',
            'dd/MM/yyyy hh:mm:ss',
            'dd-MM-yyyy',
            'dd-MM-yyyy HH:mm:ss',
            'MM/dd/yyyy',
            'MM/dd/yyyy hh:mm:ss',
            'yyyy/MM/dd',
            'yyyy/MM/dd HH:mm:ss',
            'dd/MM/yy',
            'dd/MM/yy hh:mm:ss',
            ];
          
       let   dateNow : Date = new Date();
       let  dateNowISO = dateNow.toISOString();
       let  dateNowMilliseconds = dateNow.getTime();
       }

    getDateStringByInputString(date:string):string{
        console.log("Date from rest API : "+date)
        //console.log(new Date('2017-10-17T18:05:05.803').toLocaleDateString(this.appConfig.locale, this.dateFormatWithTime));
        return new Date(date).toLocaleDateString(this.appConfig.locale, this.dateFormatWithTime);
    }

    getDateStringByInputNumber(datenum:number):string{
        console.log("Date from rest API : "+datenum);
        return new Date(datenum).toLocaleDateString(this.appConfig.locale, this.dateFormatWithTime);
    }
    getTimeStringByInputDateTime(date:string){
        console.log("DateTime from rest API : "+date);
        return new Date(date).toLocaleTimeString(this.appConfig.locale, this.dateFormatWithTime);
    }

    getTime(strDate:string){
        //t = "2017-12-14T12:59:00"
        var a = strDate.split("T");
        var time = a[1];
        return time;
    }
    getDate(strDate:string){
        //t = "2017-12-14T12:59:00"
        var a = strDate.split("T");
        var date = a[0];
        return date;
    }

}
