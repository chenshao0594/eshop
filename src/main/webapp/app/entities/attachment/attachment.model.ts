export class Attachment {
    constructor(
        public id?: number,
        public name?: string,
        public content?: any,
        public size?: number,
        public boName?: string,
        public boId?: number,
        public contentContentType?: string,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
    ) {
    }
}
