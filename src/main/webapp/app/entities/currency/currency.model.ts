export class Currency {
    constructor(
        public id?: number,
        public code?: string,
        public supported?: boolean,
        public name?: string,
        public currency?: string,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
    ) {
        this.supported = false;
    }
}
