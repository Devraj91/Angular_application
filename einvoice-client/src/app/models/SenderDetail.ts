export class  SenderDetail{
    senderId: number;
    name: String;
    email: String;
    designation: String;
    constructor(values:Object={}){
     Object.assign(this, values);
    }

}