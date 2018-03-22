export class TaxConfig {
    constructor(
        public id: Number,
        public taxName: string,
        public rate: number,
        public city: City,
        public taxApplicable:string
        ) { }
}

export class City{
    constructor(
        public id:Number,
        public name:string
    ){}
}