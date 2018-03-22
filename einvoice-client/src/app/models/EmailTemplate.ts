
export class  EmailTemplate {
    templateId: number;
    template: String;
    subject: String;
    body: String;
    ps: String;
    signature: String;
    designation: String;
    regionName: String;
    category: String;
    from: String;
    email: String;
    constructor(values: Object = {}) {
     Object.assign(this, values);
    }

}
