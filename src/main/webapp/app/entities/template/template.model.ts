export class Template {
    constructor(
        public id?: number,
        public name?: string,
        public superId?: number,
        public templateKey?: string,
        public content?: any,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
    ) {
    }
}
