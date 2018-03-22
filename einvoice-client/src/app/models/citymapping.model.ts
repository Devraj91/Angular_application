import { Region } from "./Region";
export class CityMapping {
    constructor(public id: number, 
                public city:City,
                public region:Region ) { }
  }


  export class City{
    constructor(
        public name:string,
    ){}
}