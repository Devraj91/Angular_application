export class MemberTransaction{
    constructor(public invoiceId:string,public totalAmount:Number,public isTdsDeducted:boolean,public tdsRate:Number){}
}
//
export class EditPo{
    constructor(public invoiceId:string,public isTaxApplicable:boolean,public year:Number,public poNumber:Number){ }
}
//
export class CancelInvoice{
    constructor(public remarks:string,public invoiceId:string){  }
}