import { Injectable } from '@angular/core';

@Injectable()
export class MenuService {

  constructor() { }

  getMenus() {
     // This could be a service call for dynamic menus
    return [
      { id: 0, text: 'Dashboard', name: 'home',  url: '/home/dashboard', isActive: true},
      { id: 1, text: 'Email', name: 'contact',  url: '/home/emailtemplate'},
      { id: 2, text: 'Config', name: 'configure',  url: '/home/configuration'},
      { id: 3, text: 'Members', name: 'user',  url: '/home/member'},
      { id: 4, text: 'Schedule', name: 'calendar',  url: '/home/schedule'},
      { id: 5, text: 'Master Data', name: 'clusters',  url: '/home/masterdata'},
      ];
  }

}
