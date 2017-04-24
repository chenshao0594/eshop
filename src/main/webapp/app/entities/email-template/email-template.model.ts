export class EmailTemplate {
    constructor(
        public id?: number,
        public name?: string,
        public subject?: string,
        public content?: any,
        public actionKey?: string,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
    ) {
    }
}
