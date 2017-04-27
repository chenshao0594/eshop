export class Attachment {
    constructor(
        public id?: number,
        public name?: string,
        public content?: any,
        public size?: number,
        public boName?: string,
        public boId?: number,
        public contentContentType?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
    ) {
    }
}
