import { Component, OnInit, ElementRef } from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import { EmailTemplate } from '../../models/EmailTemplate';
import { SenderDetail } from '../../models/SenderDetail';
import { Region } from '../../models/Region';
import { Member } from '../../models/Member';
import {Router, ActivatedRoute, Params} from '@angular/router';

import { EmailtemplateService } from '../../services/api/emailtemplate.service';
import { error } from 'util';
import { ToasterService } from 'angular2-toaster/src/toaster.service';
import { window } from 'rxjs/operators/window';
import { MemberService } from '../../services/api/member.service';


@Component({
  selector: 'app-emailtemplate',
  templateUrl: './emailtemplate.component.html',
  styleUrls: ['./emailtemplate.component.css']
})

export class EmailtemplateComponent implements OnInit{
  email = [];
  query: string;
  filteredList: any[];
  emails= [];
  tableData: any;
  elementRef: any;
     templateForm: FormGroup;
     emailTemplates: EmailTemplate[];
     senderDetails: SenderDetail[];
     senderDetail: SenderDetail;
     emailTemplate: EmailTemplate;
     templateObj: any[];
     emailCategories: any[];
     memberEmailSelected: any;
     allMemberSelected: any;
     regionSelected: any;
     showAttachment: any;
     senderfilter: SenderDetail[];
     regions: any = [];
     members: Member[];
     membersfilter: Member[];
     member: Member;
     regionFilter: Region[];
     dataContainer: any;
     content: any;
     getId: any;
     blurloader: boolean=false;
     constructor( private _emailtemplate: EmailtemplateService, private toasterService: ToasterService,
      private router: Router, private memberService: MemberService, myElement: ElementRef) {
      this.getAllTemplateTypes();
      this.elementRef = myElement;
     }

    ngOnInit() {

      this.templateForm = new FormGroup({
         template : new FormControl('', Validators.required),
         from: new FormControl('', Validators.required),
         subject : new FormControl('', Validators.required),
         recipient : new FormControl('', Validators.required),
         memberEmail : new FormControl(),
         region : new FormControl(),
         body : new FormControl('', [Validators.maxLength(4000)]),
         signature : new FormControl(),
         designation : new FormControl(),
         ps : new FormControl(),
         templateId : new FormControl(),
         attachment : new FormControl(),
      });
      this.allMemberSelected = true;
      this.memberEmailSelected = true;
      this.regionSelected = true;
      this.showAttachment = true;

    }

    memberList(eve) {

      this.memberService.getAllMembers().subscribe((data) => {

        this.tableData = data;

        return this.tableData;

    }, err => {
        console.log('Unable to load member,Server Error');
        this.toasterService.pop('error', '', 'Unable to load member,Server Error');
    });

    if (this.tableData) {
      this.emails = [];
        this.tableData.forEach(element => {
          this.emails.push(element.toEmail);
        });

        if (this.query !== '') {
              this.filteredList = this.emails.filter(function(el){
                  return el.toLowerCase().indexOf(this.query.toLowerCase()) > -1;
              }.bind(this));
          } else {
              this.filteredList = [];
          }

      }
    console.log('Emails' , this.emails);
    }

    select(item) {
      this.query = item;
      this.filteredList = [];

  }


    saveEmailTemplate(templateObj) {
      this._emailtemplate.saveTemplate(templateObj).subscribe(data => {
        console.log(data);
      });
      this.templateForm.reset();
    }

    updateEmailTemplate(emailTemplate){
      this.emailTemplate.subject = this.templateForm.controls['subject'].value;
      this.emailTemplate.body = this.templateForm.controls['body'].value;
      // this.emailTemplate.signature=this.templateForm.controls["signature"].value;
      // this.emailTemplate.designation=this.templateForm.controls["designation"].value;
      this.emailTemplate.ps = this.templateForm.controls['ps'].value;
      this.emailTemplate.templateId = this.templateForm.controls['templateId'].value;
      this._emailtemplate.saveTemplate(emailTemplate).subscribe(data => {
        console.log(data);
      });

    }

    getAllTemplateTypes(){
      this._emailtemplate.getAllTemplateTypes().subscribe(data => {
        this.emailTemplates = data;
        console.log(JSON.stringify(data));
        this.getSenderDetails();
  }, error => {
    this.toasterService.pop('error', '', 'Unable to load Template Type');
    console.error('Unable to load Template Type');
  });
}

getTemplateByTemplateId(template) {
  this._emailtemplate.getTemplateByTemplate(template).subscribe(data => {
  this.emailTemplate = data;
  this.templateForm.controls['subject'].setValue(data.subject);
  this.templateForm.controls['body'].setValue(data.body);
  // this.dataContainer.innerHTML = data.body;
  // this.templateForm.controls['signature'].setValue(data.signature);
 // this.templateForm.controls['designation'].setValue(data.designation);
  this.templateForm.controls['ps'].setValue(data.ps);
  this.emailTemplate.template = data.template;
  this.templateForm.controls['templateId'].setValue(data.templateId);
});
}

getSenderDetails() {
this._emailtemplate.getSenderDetails().subscribe(data => {
  this.senderDetails = data;
  console.log(data);
  this.getEmailCategories();
}, error => {
  this.toasterService.pop('error', '', 'Unable to load Sender Detail');
  console.error('Unable to load SenderDetails ' + error);
});
}

getEmailCategories() {
 this.emailCategories = this._emailtemplate.getEmailCategory();
  // getEmailCategories().subscribe(data=>{  console.log(data);  } )
}
onSelectSender(selectedSenderId) {
  this._emailtemplate.getSenderDetails().subscribe(data => {
     this.senderDetails = data;
       this.senderfilter = this.senderDetails.filter(senderDetails =>
        senderDetails.senderId === selectedSenderId);
        // console.log('membersfilter', this.senderfilter[0].name);
        console.log('membersfilter', this.senderfilter);
        this.templateForm.controls['signature'].setValue(this.senderfilter[0].name);
        this.templateForm.controls['designation'].setValue(this.senderfilter[0].designation);
        this.emailTemplate.from = this.senderfilter[0].name + '<' + this.senderfilter[0].email + '>';

        });
      }




OnEmailCategorySelect(selectedValue) {
  console.log('selected value=', selectedValue);
  this.getId = selectedValue;

  if (selectedValue === '1'){
    this.emailTemplate.category = 'All Members';
    this.allMemberSelected = false;
    this.memberEmailSelected = true;
    this.regionSelected = true;
    this.showAttachment = true;
    this.allMemberSelected = true;

  }  else if (selectedValue === '2'){
    this.emailTemplate.category = 'Member Email';
    this.memberEmailSelected = false;
    this.showAttachment = false;
    this.regionSelected = true;
    this.allMemberSelected = true;

    }  else if (selectedValue === '3'){
  this.emailTemplate.category = 'Region';
 this.getRegions();
  this.regionSelected = false;
  this.memberEmailSelected = true;
  this.showAttachment = true;
  this.allMemberSelected = true;

  }
}
getRegions() {
  this._emailtemplate.getRegions().subscribe(res => {
    const length = Object.keys(res).length;

    for (let i = 0; i < length; i++) {
      this.regions.push(res[i].name);
  }
   }, error => {
    console.error(error);
   });
}
onRegionSelect(region) {
  this.emailTemplate.regionName = region;
}


sendMail(emailTemplate) {
  this.emailTemplate.body = this.templateForm.controls['body'].value;
  this.emailTemplate.subject = this.templateForm.controls['subject'].value;
  this.emailTemplate.ps = this.templateForm.controls['ps'].value;
  this.emailTemplate.signature = this.templateForm.controls['signature'].value;
  this.emailTemplate.designation = this.templateForm.controls['designation'].value;
  this.emailTemplate.email = this.templateForm.controls['memberEmail'].value;
  this.blurloader=true;
  this._emailtemplate.sendMail(emailTemplate).subscribe(data => {
    this.toasterService.pop('success', '', data.message);
    this.templateForm.reset();
    location.reload();
  }, error => {
    if (error.status === '400') {
      this.blurloader=false;
      this.toasterService.pop('error', '', 'Inactive Member. Mail cannot be sent.');
    } else {
    this.toasterService.pop('error', '', 'Unable to Send Mail=>' + error);
    this.blurloader=false;
    console.error('Unable to Send Mail ' + error);
    }
  });
}
}
