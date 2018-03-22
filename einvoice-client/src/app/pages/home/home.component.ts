import { Component, OnInit } from '@angular/core';
import { MenuService } from '../../services/menu.service';
import {UserInfoService} from '../../services/user-info.service';
import { ToasterConfig } from 'angular2-toaster/src/toaster-config';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  menuList =  [];
  public userName: string="";
 constructor(private menuService: MenuService,private userInfoService:UserInfoService){
  this.userName = this.userInfoService.getUserName();
  }

    ngOnInit() {
   this.menuList = this.menuService.getMenus();
    }
    //custom config for toaster
    public config : ToasterConfig = new ToasterConfig({
      timeout: 4000, showCloseButton:true, tapToDismiss:true,animation:'fade',limit: 5, positionClass: 'toast-top-center'
    });
}
