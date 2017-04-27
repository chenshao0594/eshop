export class Currency {
    constructor(
        public id?: number,
        public code?: string,
        public supported?: boolean,
        public name?: string,
        public currency?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
    ) {
        this.supported = false;
    }
}
