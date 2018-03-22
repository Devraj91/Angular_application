export class Usermgmt {
    constructor(public userId: number, 
                public emailId: string,
                public branch:{
                  name:string;
                },
                public role: [{name:string}]) { } 
  }