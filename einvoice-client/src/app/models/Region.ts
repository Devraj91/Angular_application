export class Region{
    public regionId: number;
    public regionName: string;
    constructor(values:Object={}){
        Object.assign(this, values);
       }
}