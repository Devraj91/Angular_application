import { Region } from "./Region";

export class Address {
    constructor(
        public id: Number,
        public name: string,
        public address : AddressDetails,
        public pan: string,
        public gstNo:Number
        ) { }
}


export class AddressDetails{
    constructor(
        public id:Number,
        public street: number,
        public city: City,
        public phone:string,
        public pin:Number,
        public fax:string,
    ){}
}

export class City{
    constructor(
        public id:Number,
        public name:string,
        public region:Region,
    ){}
}

