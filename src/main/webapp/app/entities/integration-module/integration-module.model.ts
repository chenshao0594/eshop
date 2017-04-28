export class IntegrationModule {
    constructor(
        public id?: number,
        public configDetails?: string,
        public customModule?: boolean,
        public type?: string,
        public code?: string,
        public regions?: string,
        public image?: string,
        public module?: string,
        public configuration?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
    ) {
        this.customModule = false;
    }
}
