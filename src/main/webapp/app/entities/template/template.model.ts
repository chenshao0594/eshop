export class Template {
    constructor(
        public id?: number,
        public name?: string,
        public superId?: number,
        public templateKey?: string,
        public content?: any,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
    ) {
    }
}
