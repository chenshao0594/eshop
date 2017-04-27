export class EmailTemplate {
    constructor(
        public id?: number,
        public name?: string,
        public subject?: string,
        public content?: any,
        public actionKey?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
    ) {
    }
}
