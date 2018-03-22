import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule,Routes } from '@angular/router';
import { ReactiveFormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToasterModule, ToasterService} from 'angular2-toaster';
//third party module
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import {CKEditorModule} from 'ng2-ckeditor';
import { LoadingModule, ANIMATION_TYPES } from 'ngx-loading';
//import { PdfViewerModule } from 'ng2-pdf-viewer';

//component
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './pages/404/page-not-found.component';
import { EmailtemplateComponent } from './pages/emailtemplate/emailtemplate.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MenuComponent } from './pages/menu/menu.component';
import { LogoutComponent } from './pages/logout/logout.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { TaxconfigComponent } from './pages/taxconfig/taxconfig.component';
import { CscComponent } from './pages/csc/csc.component';
import { MemberComponent } from './pages/member/member.component';
import { ScheduleComponent } from './pages/schedule/schedule.component';
import { ConfigurationComponent } from './pages/configuration/configuration.component';
import { MembersubscriptionComponent } from './pages/membersubscription/membersubscription.component';
import { UpdateinvoiceComponent } from './pages/updateinvoice/updateinvoice.component';
//services
import { AuthGuard } from './services/auth_guard.service';
import { MenuService } from './services/menu.service';
import {TaxConfigService} from './services/api/taxconfig.service';
import {CscService} from './services/api/csc.service';
import {LoginService} from './services/api/login.service';
import {UserInfoService} from './services/user-info.service';
import {ApiRequestService} from './services/api/api-request.service';
import {AppConfig} from './app-config';
import { MemberService } from './services/api/member.service';
import { TranslateService } from './services/api/translate.service';
import {SchedulerService } from './services/api/schedule.service';
import { MemberSubscrptionService } from './services/api/membersubscription.service';
import { MemberdetailComponent } from './pages/memberdetail/memberdetail.component';
import { EmailtemplateService } from './services/api/emailtemplate.service';
import { UpdateInvoiceService } from './services/api/updateinvoice.service';
import { EditPaidAmtService } from './services/api/editpaidamt.service';
import { PaymentComponent } from './pages/payment/payment.component';
import {UsermgmtService } from './services/api/usermgmt.service';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { CategoryFeeService } from './services/api/categoryfee.service';
import { MasterdataComponent } from './pages/masterdata/masterdata.component';
import { AddressmgmtComponent } from './pages/addressmgmt/addressmgmt.component';
import { AddressmgmtService } from './services/api/addressmgmt.service';
import { CitymgmtService } from './services/api/citymgmt.service';
import { CitymgmtComponent } from './pages/citymgmt/citymgmt.component';
import { UsermgmtComponent } from './pages/usermgmt/usermgmt.component';
import { PaymentService } from './services/api/payment.service';
import { PaymentPageExpireComponent } from './pages/payment-page-expire/payment-page-expire.component';

//import { PaymentPageExpireComponent } from './pages/payment-page-expire/payment-page-expire.component';



const routes: Routes = [
  //Important: The sequence of path is important as the router go over then in sequential manner
  { path: ''     , redirectTo: '/login', pathMatch: 'full' },
  { path: 'home',  component: HomeComponent,
  canActivate:[AuthGuard],
    children: [
     { path: ''     , redirectTo: '/home/dashboard', pathMatch: 'full' },
     { path: 'dashboard', component: DashboardComponent},
     { path: 'emailtemplate',component: EmailtemplateComponent},
     { path: 'taxconfig',component: TaxconfigComponent },
     { path: 'country',component: CscComponent},
     { path: 'configuration',component: ConfigurationComponent },
     { path: 'member',component: MemberComponent },
     { path: 'schedule',component: ScheduleComponent },
     { path: 'masterdata',component: MasterdataComponent },
     
    ]},
  { path: 'login',  component: LoginComponent  },
  { path: 'logout', component: LogoutComponent },
  { path: 'payment', component: PaymentComponent },
  { path: 'ccavResponseHandler', component: PaymentComponent },
  { path: 'submitforgotPassword', component: ForgotPasswordComponent, },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    EmailtemplateComponent,
    DashboardComponent,
    MenuComponent,
    LoginComponent,
    HomeComponent,
    TaxconfigComponent,
    CscComponent,
    LogoutComponent,
    PageNotFoundComponent,
    ConfigurationComponent,
    MemberComponent,
    ScheduleComponent,
    MembersubscriptionComponent,
    MemberdetailComponent,
    UpdateinvoiceComponent,
    PaymentComponent,
    ForgotPasswordComponent,
    CategoriesComponent,
    MasterdataComponent,
    AddressmgmtComponent,
    CitymgmtComponent,
    UsermgmtComponent,
    PaymentPageExpireComponent,
   // PaymentPageExpireComponent,
   ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpModule,
    FormsModule,
    NgbModule.forRoot(),
    RouterModule.forRoot( routes,{useHash:true} ),
    NgxDatatableModule,
    NgbModalModule.forRoot(),
    CommonModule,
    BrowserAnimationsModule, 
    CKEditorModule,
    ToasterModule, 
    LoadingModule.forRoot({
      animationType: ANIMATION_TYPES.wanderingCubes,
      backdropBackgroundColour: 'rgba(0,0,0,0.1)', 
      backdropBorderRadius: '4px',
      primaryColour: '#ffffff', 
      secondaryColour: '#ffffff', 
      tertiaryColour: '#ffffff'
  })

    //PdfViewerModule,  
    
  ],
  providers: [
    MenuService,
    TaxConfigService,
    CscService,
    LoginService,
    UserInfoService,
    ApiRequestService,
    AppConfig,
    AuthGuard,
    MemberService,
    TranslateService,
    SchedulerService,
    MemberSubscrptionService,
    EmailtemplateService,
    UpdateInvoiceService,
    EditPaidAmtService,
    CategoryFeeService,
    AddressmgmtService,
    CitymgmtService,
    PaymentService,
    UsermgmtService,
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
