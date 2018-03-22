export class Scheduler {
  constructor( 
    public id: number,
    public type:Type,
    public isOn: boolean,
    public date: string,
    public time: string,
    public isRecurring: boolean,
    public recurringType: RecurringType) { 
      }

}

export enum Type{
  Invoice,Member
}

export enum RecurringType {
  Daily, Weekly, Monthly 
}